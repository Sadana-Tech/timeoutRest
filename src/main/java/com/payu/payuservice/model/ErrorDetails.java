package com.payu.payuservice.model;

import lombok.Data;

@Data
public class ErrorDetails {

    private int errorCode;
    private String errorMsg;
}
