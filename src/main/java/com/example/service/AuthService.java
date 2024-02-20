package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.AuthDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.RegistrationDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exceptions.AppBadException;
import com.example.repository.ProfileRepository;
import com.example.util.JWTUtil;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

    public ApiResponseDTO registration(RegistrationDTO registrationDTO) {
        Optional<ProfileEntity>optional=profileRepository.findByEmailAndVisibleTrue(registrationDTO.getEmail());
        if(optional.isPresent()){
            ProfileEntity profile=optional.get();
            if(profile.getStatus().equals(ProfileStatus.ACTIVE)){
                throw new AppBadException("Email already exists");
            }else if(profile.getStatus().equals(ProfileStatus.NOT_ACTIVE)){
                throw new AppBadException("Your account is blocked");
            }
        }
        ProfileEntity profile=new ProfileEntity();
        profile.setRole(ProfileRole.USER);
        profile.setStatus(ProfileStatus.ACTIVE);
        profile.setPhone(registrationDTO.getPhone());
        profile.setPassword(MD5Util.encode(registrationDTO.getPassword()));
        profile.setF_name(registrationDTO.getF_name());
        profile.setEmail(registrationDTO.getEmail());
        profileRepository.save(profile);

        return new ApiResponseDTO(false,JWTUtil.encode(profile.getId(),profile.getEmail()));
    }
    public ApiResponseDTO login(AuthDTO authDTO){
        Optional<ProfileEntity> optional=profileRepository
                .findByEmailAndPasswordAndVisibleTrue(authDTO.getEmail(), MD5Util.encode(authDTO.getPassword()));
        if(optional.isEmpty()) return new ApiResponseDTO(true,"profile not found");
        ProfileEntity entity=optional.get();
        if(!entity.getStatus().equals(ProfileStatus.ACTIVE)){
            return new ApiResponseDTO(true,"your profile is blocked!");
        }
        ProfileDTO dto= new ProfileDTO();
        dto.setF_name(entity.getF_name());
        dto.setEmail(authDTO.getEmail());
        dto.setPhone(entity.getPhone());
        System.out.println(entity.getId()+"  "+entity.getEmail());
        dto.setJwt(JWTUtil.encode(entity.getId(),entity.getEmail()));
        System.out.println("logged in");
        return  new ApiResponseDTO(false,dto.getJwt());
    }
}
