package com.ua.schoolboard.exceptions;

import java.util.function.Supplier;

public class ExceptionUtil {
    public static Supplier<CustomException> getSupplierException(ErrorCode errorCode) {
        return () -> getException(errorCode);
    }

    public static CustomException getException(ErrorCode errorCode) {
        return CustomException.builder()
                .errorCode(errorCode)
                .build();
    }
}
