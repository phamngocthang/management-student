package com.tranning.management.dto;

import jakarta.persistence.Column;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class UserDto {
    private Integer id;

    private String username;

    private String password;
}
