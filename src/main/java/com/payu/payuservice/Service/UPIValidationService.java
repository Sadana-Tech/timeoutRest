package com.payu.payuservice.Service;

import com.payu.payuservice.model.Response;
import com.payu.payuservice.model.UPIRequest;
import org.springframework.stereotype.Service;


public interface UPIValidationService {
    public Response validate(UPIRequest upiRequest);
}
