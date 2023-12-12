package com.tranning.management.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer id;

    @Column(name = "student_name", length = 20, nullable = false)
    private String studentName;

    @Column(name = "student_code", length = 10, nullable = false)
    private String studentCode;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "student")
    private StudentInfo studentInfo;
}
