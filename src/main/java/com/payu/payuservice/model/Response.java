package com.payu.payuservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Response {

    private String statusCode;
    private String statusMsg;

    private ErrorDetails errorDetails;
}
