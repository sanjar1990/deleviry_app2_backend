package com.example.service;

import com.example.dto.ProfileDTO;
import com.example.entity.ProfileEntity;
import com.example.exceptions.AppBadException;
import com.example.repository.ProfileRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO userInfo() {
        Optional<ProfileEntity> optional=profileRepository.findById(SpringSecurityUtil.getProfileId());
        if(optional.isEmpty())throw new AppBadException("Profile not found");
        ProfileEntity entity= optional.get();
        ProfileDTO dto=new ProfileDTO();
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setId(entity.getId());
        dto.setF_name(entity.getF_name());
        return dto;
    }
}
