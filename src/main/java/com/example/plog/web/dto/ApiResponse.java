package com.example.plog.web.dto;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // ✅ null 값이면 JSON에서 자동 제외
public class ApiResponse<T> {
    private String code;
    private String message; // 성공 or 실패 메시지
    private T data;         // 응답 데이터 (제네릭 타입)

    // 성공 응답
    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(new ApiResponse<>("SU", "Success", data));
    }
    public static ResponseEntity<ApiResponse<Void>> success() {
        return ResponseEntity.ok(new ApiResponse<>("SU", "Success", null));
    }

    // 에러 응답
    public static <T> ResponseEntity<ApiResponse<T>> error(String code, String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new ApiResponse<>(code, message, null));
    }

}
    // 성공 응답 시,
    // controller에서 아래와 같이 작성
    // @RequestMapping("/api")
    // public class PlogController {
    //     @GetMapping("/test")
    //         public ResponseEntity<ApiResponse<String>> test(){ 
    //         return ApiResponse.success("성공");
    //     }
    // }

    // 반환값 : ResponseEntity<ApiResponse<T>> T는 success()안에 들어가는 값에 따라 다르게 ex) Void, String, UserInfoDto 등

    // Ex) return ApiResponse.success() // 응답 data 필요 없을 경우 --> T = Void
    // Ex) return ApiResponse.success(userInfo) --> T = UserInfoDto 



    // 예외 발생 시,
    // Repository 또는 Service 단계에서
    // Ex) orElseThrow(()-> new DatabaseException("해당 ID의 펫을 찾을 수 없습니다."))