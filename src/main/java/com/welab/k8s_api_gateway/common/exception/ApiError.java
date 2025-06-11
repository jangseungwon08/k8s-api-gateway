package com.welab.k8s_api_gateway.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

//API, 비즈니스 로직에서 발생한 에러는 runTimeException 을 사용함
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiError extends RuntimeException{
protected String errorCode;
protected String errorMessage;
}
//protected가 있선언되어있으면 매개변수 없는 생성자 생성 불가