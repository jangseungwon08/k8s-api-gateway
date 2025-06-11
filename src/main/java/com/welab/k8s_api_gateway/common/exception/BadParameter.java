package com.welab.k8s_api_gateway.common.exception;

public class BadParameter extends ClientError {
    public BadParameter(String errorMessage) {
        this.errorCode = "BadParameter";
        this.errorMessage = errorMessage;
    }
}