package com.payu.payuservice.Controller;

import com.payu.payuservice.Service.UPIValidationService;
import com.payu.payuservice.model.Response;
import com.payu.payuservice.model.UPIRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UPIValidationController {

    @Autowired
    private UPIValidationService upiValidationService;
    @PostMapping("/api/v1/validate")
    public ResponseEntity<Response> validate(@RequestBody UPIRequest upiRequest){
        return new ResponseEntity<>(upiValidationService.validate(upiRequest),HttpStatus.ACCEPTED);

    }

    @PostMapping("/api/v1/status")
    public ResponseEntity<Response> status(@RequestBody UPIRequest upiRequest){

        return new ResponseEntity<>(upiValidationService.validate(upiRequest),HttpStatus.ACCEPTED);

    }
}
