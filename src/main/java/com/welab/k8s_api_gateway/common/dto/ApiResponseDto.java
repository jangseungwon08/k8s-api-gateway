package com.welab.k8s_api_gateway.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponseDto<T> {
    private String code; //코드는 무조건 String으로 출력
    private String message;
    private T data; //데이터는 null일 수도 있고 String일 수도 있고, 객체일 수도 있으니

    //
    private ApiResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    //데이터가 들어오는 경우(스태틱한 생성 메서드)
    private ApiResponseDto(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
//이 생성자는 위 private 생성자를 쓰지 못한다.
    public static <T> ApiResponseDto<T>  createOk(T data){
        return new ApiResponseDto<>("OK","요청이 성공하였습니다.",data);
    }
//    디폴트 Ok값
    public static ApiResponseDto<String> defaultOk(){
        return ApiResponseDto.createOk(null);
    }
//    에러 발생시 응답 포맷팅
    public static ApiResponseDto<String> createError(String code, String message){
        return new ApiResponseDto<>(code,message);
    }
}
