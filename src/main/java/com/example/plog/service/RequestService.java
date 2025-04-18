package com.example.plog.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.plog.repository.Enum.Role;
import com.example.plog.repository.Enum.Status;
import com.example.plog.repository.family.FamilyEntity;
import com.example.plog.repository.family.FamilyJpaRepository;
import com.example.plog.repository.pet.PetEntity;
import com.example.plog.repository.pet.PetJpaRepository;
import com.example.plog.repository.request.RequestEntity;
import com.example.plog.repository.request.RequestJpaRepository;
import com.example.plog.repository.user.UserEntity;
import com.example.plog.repository.user.UserJpaRepository;
import com.example.plog.security.UserPrincipal;
import com.example.plog.service.exceptions.AuthenticationException;
import com.example.plog.service.exceptions.AuthorizationException;
import com.example.plog.service.exceptions.DatabaseException;
import com.example.plog.service.exceptions.InvalidValueException;
import com.example.plog.service.exceptions.NotFoundException;
import com.example.plog.service.mapper.FamilyMapper;
import com.example.plog.service.resolver.EntityFinder;
import com.example.plog.web.dto.request.RequestInviteDto;
import com.example.plog.web.dto.request.RequestPermissionDto;
import com.example.plog.web.dto.user.UserResponseDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RequestService {
    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    RequestJpaRepository requestJpaRepository;

    @Autowired
    PetJpaRepository petJpaRepository;

    @Autowired
    FamilyJpaRepository familyJpaRepository;

    @Autowired
    UserService userService;

    @Autowired
    EntityFinder entityFinder;


    @Transactional
    public UserResponseDto requestPermission(UserPrincipal userPrincipal, RequestPermissionDto requestPermissionDto) {
        UserEntity requester = entityFinder.getUserById(userPrincipal.getId());
        UserEntity receiver = entityFinder.getUserByNickname(requestPermissionDto.getOwnerNick());
        PetEntity pet = entityFinder.findPetOrThrowIfNotFoundOrIfNotOwner(requestPermissionDto.getPetName(), receiver);
        RequestEntity request = requestJpaRepository.findByRequesterAndReceiverAndPet(requester,receiver,pet)
            .orElse(null);

        if(request != null){
            if(request.getStatus() == Status.PENDING){
                return UserResponseDto.builder()
                    .requestId(request.getId())
                    .isAlreadyRequested(true)
                    .build();
            }else{
                handleExistingRequest(request);
            }
        }else{
            request = new RequestEntity(requester, pet, receiver, Status.PENDING,false);
        }
        try{
            requestJpaRepository.save(request);
            return UserResponseDto.builder()
                .requestId(request.getId())
                .build();
        }catch (Exception e){
            log.error("요청을 DB에 저장하는 중 오류가 발생했습니다. {}", e.getMessage());
            throw new DatabaseException("요청을 DB에 저장하는 중 오류가 발생했습니다.");    
        }
    }
    @Transactional
    public UserResponseDto requestInvite(UserPrincipal userPrincipal, RequestInviteDto requestInviteDto) {
        UserEntity requester = entityFinder.getUserById(userPrincipal.getId());
        PetEntity pet = entityFinder.findPetOrThrowIfNotFoundOrIfNotOwner(requestInviteDto.getPetName(), requester);
        String receiverNick = requestInviteDto.getFamilyNick();
        String receiverEmail = requestInviteDto.getFamilyEmail();
        RequestEntity request = null;
        UserEntity receiver = null;

        if ((receiverNick == null || receiverNick.isEmpty()) && (receiverEmail == null || receiverEmail.isEmpty())) {
            throw new InvalidValueException("닉네임 또는 이메일 중 하나는 반드시 필요합니다.");
        }
        // 가입자일 경우 닉네임으로 검색
        if(receiverNick != null && !receiverNick.isEmpty()){
            receiver = entityFinder.getUserByNickname(receiverNick);
            request = requestJpaRepository.findByRequesterAndReceiverAndPet(requester,receiver,pet)
                .orElse(null);
        // 미가입자일 경우 이메일로 검색
        }else if(receiverEmail != null && !receiverEmail.isEmpty()){
            request = requestJpaRepository.findByRequesterAndReceiverEmailAndPet(requester,receiverEmail,pet)
                .orElse(null);
        };
        // 기존 요청이 있는 경우 응답 Status 확인
        if(request != null){
            if(request.getStatus() == Status.PENDING){
                return UserResponseDto.builder()
                    .requestId(request.getId())
                    .isAlreadyRequested(true)
                    .build();
            }else{
                handleExistingRequest(request);
            }
        }else {
            request = (receiver != null)
            ? new RequestEntity(requester, pet, receiver, Status.PENDING,true)
            : new RequestEntity(requester, pet, receiverEmail, Status.PENDING,true);
        }
        try{
            requestJpaRepository.save(request);
            return UserResponseDto.builder()
                .requestId(request.getId())
                .receiverEmail(receiverEmail) // 가입자일 경우 null, 미가입자일 경우 이메일
                .build();
            }catch (Exception e){
                log.error("요청을 DB에 저장하는 중 오류가 발생했습니다. {}", e.getMessage());
                throw new DatabaseException("요청을 DB에 저장하는 중 오류가 발생했습니다.");
            }
    }
    @Transactional
    public UserResponseDto requestPending(UserPrincipal userPrincipal, Long requestId) {
        UserEntity user = entityFinder.getUserById(userPrincipal.getId());
        RequestEntity request = requestJpaRepository.findById(requestId)
            .orElseThrow(() -> new NotFoundException("해당 요청을 찾을 수 없습니다."));

        // 유저와 수신자가 동일한지 확인
        Long receiverId = request.getReceiver().getId();
        if(!receiverId.equals(user.getId()) && !request.getReceiverEmail().equals(user.getEmail())){
            throw new AuthenticationException("해당 요청에 권한이 없습니다.");
        }
        return UserResponseDto.builder()
            .requesterNick(request.getRequester().getNickname())
            .petName(request.getPet().getPetName())
            .requestId(requestId)
            .isRequesterOwner(request.getIsRequesterOwner())
            .build();
    }
    @Transactional
    public void requestAccept(UserPrincipal userPrincipal, Long requestId) {
        UserEntity user = entityFinder.getUserById(userPrincipal.getId());
        RequestEntity request = findRequestOrThrowIfNotPending(requestId);
        Long requesterId = request.getRequester().getId();
        Long petId = request.getPet().getId();
        Boolean isRequesterOwner = request.getIsRequesterOwner();

        // 수신자가 user인지 확인
        validateReceiverPermission(request, user);
        // 요청자가 owner일 경우 -> 수신자 family 등록
        request.setStatus(Status.ACCEPTED);
        FamilyEntity family;
        if(isRequesterOwner){
             family = FamilyMapper.INSTANCE.userIdAndPetIdAndRoleToFamilyEntity(user.getId(),petId,Role.FAMILY);
        }else{
            // 요청자가 family일 경우 -> 요청자 family 등록
            family = FamilyMapper.INSTANCE.userIdAndPetIdAndRoleToFamilyEntity(requesterId,petId,Role.FAMILY);
        }
        try{
            familyJpaRepository.save(family);
            requestJpaRepository.save(request);
        }catch (Exception e){
            log.error("요청 수락 결과 저장 중 오류 - 요청 ID: {}, 에러: {}", requestId, e.getMessage());
            throw new DatabaseException("요청 수락 결과를 DB에 저장하는 중 오류가 발생했습니다.");
        }
    }
    
    @Transactional
    public void requestReject(UserPrincipal userPrincipal, Long requestId) {
        UserEntity user = entityFinder.getUserById(userPrincipal.getId());
        RequestEntity request = findRequestOrThrowIfNotPending(requestId);
        // 수신자가 user인지 확인
        validateReceiverPermission(request, user);
        request.setStatus(Status.REJECTED);
        try{
            requestJpaRepository.save(request);
        }catch (Exception e){
            log.error("요청 거절 결과 저장 중 오류 - 요청 ID: {}, 에러: {}", requestId, e.getMessage());
            throw new DatabaseException("요청 거절 결과를 DB에 저장하는 중 오류가 발생했습니다.");
        }
    }
    
    private void validateReceiverPermission(RequestEntity request, UserEntity user) {
        Long receiverId = request.getReceiver() != null ? request.getReceiver().getId() : null;
        String receiverEmail = request.getReceiverEmail();
        if (!Objects.equals(receiverId, user.getId()) && !Objects.equals(receiverEmail, user.getEmail())) {
            throw new AuthorizationException("해당 요청에 권한이 없습니다.");
        }
    }

    private RequestEntity findRequestOrThrowIfNotPending(Long requestId) {
        RequestEntity request = requestJpaRepository.findById(requestId)
            .orElseThrow(() -> new NotFoundException("해당 요청을 찾을 수 없습니다."));
        if (request.getStatus() != Status.PENDING) {
            throw new InvalidValueException("해당 요청은 대기상태가 아닙니다.");
        }
        return request;
    }

    private void handleExistingRequest(RequestEntity request) {
        switch (request.getStatus()) {
            case ACCEPTED:
                throw new InvalidValueException("해당 요청은 이미 수락되었습니다.");
            case REJECTED:
                request.setStatus(Status.PENDING);
                break;
            default:
                break;
        }
    }

}
