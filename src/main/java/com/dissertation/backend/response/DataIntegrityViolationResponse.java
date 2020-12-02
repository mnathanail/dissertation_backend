package com.dissertation.backend.response;

public class DataIntegrityViolationResponse {


    private Long customErrorCode;
    private String errorMsg;

    public DataIntegrityViolationResponse() {

    }

    public DataIntegrityViolationResponse(Long code, String msg) {
        this.customErrorCode = code;
        this.errorMsg = msg;
    }

    public Long getCustomErrorCode () {
        return customErrorCode ;
    }

    public void setCustomErrorCode (Long customErrorCode ) {
        this.customErrorCode = customErrorCode ;
    }

    public String getErrorMsg  () {
        return errorMsg ;
    }

    public void setErrorMsg  (String errorMsg ) {
        this.errorMsg = errorMsg ;
    }
}
