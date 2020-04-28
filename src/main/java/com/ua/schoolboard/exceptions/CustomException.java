package com.ua.schoolboard.exceptions;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomException extends RuntimeException {
     private ErrorCode errorCode;

     public  CustomException(ErrorCode errorCode){this.errorCode=errorCode;}
}
