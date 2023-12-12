package com.tranning.management.dto;

import com.tranning.management.model.StudentInfo;
import jakarta.persistence.Column;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class StudentDto {
    private Integer id;
    private String studentName;
    private String studentCode;
    private StudentInfoDto studentInfo;
}
