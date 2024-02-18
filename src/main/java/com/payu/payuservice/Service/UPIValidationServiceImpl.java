package com.payu.payuservice.Service;

import com.payu.payuservice.model.ErrorDetails;
import com.payu.payuservice.model.Response;
import com.payu.payuservice.model.UPIRequest;
import com.payu.payuservice.model.UPIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.SocketException;


@Service
public class UPIValidationServiceImpl implements UPIValidationService {

    @Autowired
    private RestTemplate restTemplate;


    /**
     * @param upiRequest
     * @return
     */
    @Override
    public Response validate(UPIRequest upiRequest) {

        try{
            UPIResponse upiResponse = restTemplate.postForObject("http://localhost:8080/api/v1/validate",upiRequest, UPIResponse.class);
            return upiResponse;
        }catch (RuntimeException exception){
            System.out.println(exception.getMessage());
            UPIResponse upiResponse = new UPIResponse();
            upiResponse.setUpiId(upiRequest.getUpiId());
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setErrorCode(501);
            errorDetails.setErrorMsg("Unable to fetch response from NPCI");
            upiResponse.setErrorDetails(errorDetails);
            return upiResponse;
        }

    }
}
