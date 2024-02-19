package com.payu.payuservice.Service;

import com.payu.payuservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.SocketException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Service
public class UPIValidationServiceImpl implements UPIValidationService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String,UPIStatus> redisTemplate;


    /**
     * @param upiRequest
     * @return
     */
    @Override
    public Response validate(UPIRequest upiRequest) {

        try{
            UPIResponse upiResponse = restTemplate.postForObject("http://localhost:8080/api/v1/validate",upiRequest, UPIResponse.class);


            ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

            ses.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {

                    UPIStatus upiStatus = redisTemplate.opsForValue().get(upiRequest.getTrnId());
                    upiResponse.setValid(upiStatus.isStatus());
                    upiResponse.setTrnId(upiStatus.getTrnId());
                    upiResponse.setUpiId(upiStatus.getUpiId());
                    if(upiStatus==null){
                        ses.shutdown();
                    }
                }
            }, 0, 1, TimeUnit.SECONDS);



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

    @Override
    public Response updateStatus(UPIStatus upiStatus) {
        redisTemplate.opsForValue().set(upiStatus.getTrnId(),upiStatus);
        System.out.println("Redis Key"+ redisTemplate.opsForValue().get(upiStatus.getTrnId()));
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("UPI validation status recieved");
        return response ;
    }
}
