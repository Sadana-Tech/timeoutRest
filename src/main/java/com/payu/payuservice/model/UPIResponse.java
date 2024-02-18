package com.payu.payuservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UPIResponse extends Response{

    private String upiId;
    private boolean isValid;


}
