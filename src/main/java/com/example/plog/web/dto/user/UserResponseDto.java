package com.example.plog.web.dto.user;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {
    private Long userId;
    private String nickname;
    private String email;
    private Long requesterId;
    private String requesterNick;
    
    private Long petId; 
    private String token;
}
