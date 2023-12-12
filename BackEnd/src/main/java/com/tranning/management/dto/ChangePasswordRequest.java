package com.tranning.management.dto;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
