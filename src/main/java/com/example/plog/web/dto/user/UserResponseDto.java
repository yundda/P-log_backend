package com.example.plog.web.dto.user;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {
    private Long userId;
    private String nickname;
    private String email;
    private String profileImage;
    
    private Long requestId;
    private String requesterNick;
    private String receiverEmail;
    
    private String petName; 
    private String token;

    private Boolean isRegisteredUser;
    private Boolean isRequesterOwner;
    private Boolean isAlreadyRequested;
    private List<String> familyList;
}
