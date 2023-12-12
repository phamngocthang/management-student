package com.tranning.management.dto;

import com.tranning.management.model.Student;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class StudentInfoDto {
    private Integer infoId;

    private String address;

    private Double averageScore;

    private Date dateOfBirth;
}
