package com.example.plog.service;

import java.util.List;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

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
import com.example.plog.service.exceptions.AuthorizationException;
import com.example.plog.service.exceptions.DatabaseException;
import com.example.plog.service.exceptions.InvalidValueException;
import com.example.plog.service.exceptions.NotFoundException;
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

    public UserResponseDto requestPermission(UserPrincipal userPrincipal, RequestPermissionDto requestPermissionDto) {
        Long userId = userPrincipal.getId();
        String petName = requestPermissionDto.getPetName();
        UserEntity requester = userJpaRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));
        UserEntity reciever = userJpaRepository.findByNickname(requestPermissionDto.getOwnerNick())
            .orElseThrow(() -> new NotFoundException("해당 owner를 찾을 수 없습니다."));
        // 펫이 존재하는지 확인
        if(petJpaRepository.findByNameExists(petName)){ throw new NotFoundException("해당 펫을 찾을 수 없습니다."); }
        // 펫 이름으로 owner 찾기
        List<UserEntity> ownerList = familyJpaRepository.findByPetNameAndRole(petName,Role.OWNER);
        // reciever가 owner인지 확인
        if(!ownerList.isEmpty()){
            throw new InvalidValueException("해당 펫의 owner가 없습니다.");
        }else if(!ownerList.contains(reciever)){
            throw new InvalidValueException("해당 펫의 owner가 아닙니다.");
        }
        // pet정보 찾기
        PetEntity pet = familyJpaRepository.findByUserIdAndPetName(reciever.getId(), petName);

        RequestEntity request = requestJpaRepository.findByInviterAndReceiverAndPet(requester,reciever,pet)
            .orElse(null);

        if(request != null){
            switch (request.getStatus()){
                case PENDING:
                    throw new InvalidValueException("해당 요청은 이미 대기 중입니다. 요청 Id: " + request.getId());
                case ACCEPTED:
                    throw new InvalidValueException("해당 요청은 이미 수락되었습니다.");
                case REJECTED:
                    request.setStatus(Status.PENDING);
                break;
            }
        }else{
            request = new RequestEntity(requester, pet, reciever, Status.PENDING,false);
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
    
    public UserResponseDto requestInvite(UserPrincipal userPrincipal, RequestInviteDto requestInviteDto) {
        Long userId = userPrincipal.getId();
        String petName = requestInviteDto.getPetName();
        UserEntity requester = userJpaRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));

        // 펫이 존재하는지 확인
        if(petJpaRepository.findByNameExists(petName)){ throw new NotFoundException("해당 펫을 찾을 수 없습니다."); }
        // 펫 이름으로 owner 찾기
        List<UserEntity> ownerList = familyJpaRepository.findByPetNameAndRole(petName,Role.OWNER);
        // requester가 owner인지 확인
        if(!ownerList.isEmpty()){
            throw new InvalidValueException("해당 펫의 owner가 없습니다.");
        }else if(!ownerList.contains(requester)){
            throw new InvalidValueException("해당 펫의 owner가 아닙니다.");
        }
        // pet정보 찾기
        PetEntity pet = familyJpaRepository.findByUserIdAndPetName(requester.getId(), petName);

        String familyNick = requestInviteDto.getFamilyNick();
        String familyEmail = requestInviteDto.getFamilyEmail();
        RequestEntity request = null;
        UserEntity family = null;

        if ((familyNick == null || familyNick.isEmpty()) && (familyEmail == null || familyEmail.isEmpty())) {
            throw new InvalidValueException("닉네임 또는 이메일 중 하나는 반드시 필요합니다.");
        }
        // 가입자일 경우 닉네임으로 검색
        if(familyNick != null && !familyNick.isEmpty()){
            family = userJpaRepository.findByNickname(requestInviteDto.getFamilyNick())
            .orElseThrow(() -> new NotFoundException("해당 family를 찾을 수 없습니다."));
            request = requestJpaRepository.findByInviterAndReceiverAndPet(requester,family,pet)
                .orElse(null);
        // 미가입자일 경우 이메일로 검색
        }else if(familyEmail != null && !familyEmail.isEmpty()){
            request = requestJpaRepository.findByInviterAndRecieverEmailAndPet(requester,familyEmail,pet)
                .orElse(null);
        };
        // 기존 요청이 있는 경우 응답 Status 확인
        if(request != null){
            switch (request.getStatus()){
                case PENDING:
                    throw new InvalidValueException("해당 요청은 이미 대기 중입니다. 요청 Id: " + request.getId());
                case ACCEPTED:
                    throw new InvalidValueException("해당 요청은 이미 수락되었습니다.");
                case REJECTED:
                    request.setStatus(Status.PENDING);
                break;
            }
        }else {
            request = (family != null)
            ? new RequestEntity(requester, pet, family, Status.PENDING,true)
            : new RequestEntity(requester, pet, familyEmail, Status.PENDING,true);
        }
            try{
                requestJpaRepository.save(request);
                return UserResponseDto.builder()
                    .requestId(request.getId())
                    .receiverEmail(familyEmail) // 가입자일 경우 null, 미가입자일 경우 이메일
                    .build();
            }catch (Exception e){
                log.error("요청을 DB에 저장하는 중 오류가 발생했습니다. {}", e.getMessage());
                throw new DatabaseException("요청을 DB에 저장하는 중 오류가 발생했습니다.");
                
            }
    }

    public UserResponseDto requestPending(UserPrincipal userPrincipal, Long requestId) {
        Long userId = userPrincipal.getId();
        UserEntity user = userJpaRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));
        RequestEntity request = requestJpaRepository.findById(requestId)
            .orElseThrow(() -> new NotFoundException("해당 요청을 찾을 수 없습니다."));
        // 유저와 수신자가 동일한지 확인
        Long receiverId = request.getReceiver().getId();
        if(receiverId != userId && request.getReceiverEmail() != user.getEmail()){
            throw new AuthorizationException("해당 요청에 권한이 없습니다.");
        }
        return UserResponseDto.builder()
            .requesterNick(request.getRequester().getNickname())
            .petName(request.getPet().getName())
            .requestId(requestId)
            .build();
    }

    public UserResponseDto requestAccept(UserPrincipal userPrincipal, Long requestId) {
        Long userId = userPrincipal.getId();
        UserEntity user = userJpaRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));
        RequestEntity request = requestJpaRepository.findById(requestId)
            .orElseThrow(() -> new NotFoundException("해당 요청을 찾을 수 없습니다."));
        Long requesterId = request.getRequester().getId();
        Long recieverId = request.getReceiver().getId();
        // 요청자가 owner일 경우 -> 수신자 family 등록
        // UserEntity requester = familyJpaRepository.findUserEntityByUserIdAndPetName(requesterId, request.getPet().getName())
            // .orElseThrow(() -> new NotFoundException("해당 펫은 사용자의 펫이 아닙니다."));

        // 요청자가 family일 경우 -> 수신자가 owner인지 확인 후 요청자 family 등록

        if(request.getReceiver().getId() != userId || request.getReceiverEmail() != user.getEmail()){
            throw new AuthorizationException("해당 요청에 권한이 없습니다.");
        }
        if(request.getStatus() != Status.PENDING){
            throw new InvalidValueException("해당 요청은 수락할 수 없습니다.");
        }
        // 수락 처리

        throw new UnsupportedOperationException("Unimplemented method 'requestAccept'");
    }

    public UserResponseDto requestReject(UserPrincipal userPrincipal, Long requestId) {
        Long userId = userPrincipal.getId();
        RequestEntity request = requestJpaRepository.findById(requestId)
            .orElseThrow(() -> new NotFoundException("해당 요청을 찾을 수 없습니다."));
        if(request.getReceiver().getId() != userId){
            throw new AuthorizationException("해당 요청에 권한이 없습니다.");
        }

        throw new UnsupportedOperationException("Unimplemented method 'requestReject'");
    }
    
}
