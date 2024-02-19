package com.payu.payuservice.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UPIStatus implements Serializable {

    private String upiId;
    private String trnId;
    private boolean status;
}
