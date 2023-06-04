package com.ankat.exception;

public class MissMatchRecordException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MissMatchRecordException(String message) {
        super(message);
    }
}