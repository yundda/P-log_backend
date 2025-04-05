package com.example.plog.web.dto.user;


import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserUpdateDto {
    private String nickname;
    private String beforePassword;
    private String afterPassword;
}
