package com.welab.k8s_api_gateway.security.jwt.authentication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.security.Principal;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
//인증된 사용자 정보를 담는 클래스
public class UserPrincipal implements Principal {
    private final String userId;


    //    유저Name이 유저 아이디라고 표현하는 방법이 많다.
    public boolean hasName() {
        return userId != null;
    }

    //    필수 항목들이 다 있냐 라는 hasMandatory메서드
    public boolean hasMandatory() {
        return userId != null;
    }


    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        return userId;
    }


    //    equals와 hahscode는 무조건 같이 나와야됨
    @Override
    public boolean equals(Object another) {
        if (this == another) return true; // 자기자신과 비교
        if (another == null) return false; //null값과 비교(null exception방지)
        if (!getClass().isAssignableFrom(another.getClass())) return false;
        UserPrincipal principal = (UserPrincipal) another;

        if (!Objects.equals(userId, principal.userId)) {
            return false;
        }
        return true;
    }

    @Override
//    자바의 Collection에서 두개 값이 같냐 틀리냐에서 hashCode를 먼저 비교를 한다.
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        return result;
    }
}
