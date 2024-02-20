package com.example.controller;

import com.example.dto.ProfileDTO;
import com.example.service.CustomerService;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/info")
    ResponseEntity<ProfileDTO> userInfo(){
        System.out.println("Id: "+ SpringSecurityUtil.getProfileId());
        return ResponseEntity.ok(customerService.userInfo());
    }
}
