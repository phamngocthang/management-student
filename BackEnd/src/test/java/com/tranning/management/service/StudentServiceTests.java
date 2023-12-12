package com.tranning.management.service;

import com.tranning.management.common.error.exception.ResourceNotFoundException;
import com.tranning.management.common.message.StatusCode;
import com.tranning.management.common.message.StatusMessage;
import com.tranning.management.common.response.DataResponse;
import com.tranning.management.dto.StudentDto;
import com.tranning.management.dto.StudentInfoDto;
import com.tranning.management.mapper.StudentMapper;
import com.tranning.management.mapper.UserMapper;
import com.tranning.management.model.Student;
import com.tranning.management.model.StudentInfo;
import com.tranning.management.repository.StudentRepository;
import com.tranning.management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTests {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentMapper studentMapper;
    @InjectMocks
    private StudentService studentService;

    private Student student;
    private StudentDto studentDto;

    @BeforeEach
    public void setupTest() {
        StudentInfoDto studentInfoDto = StudentInfoDto.builder()
                .infoId(1)
                .address("60 Ben Chuong Duong")
                .averageScore(9.5)
                .dateOfBirth(new Date(2002, 04, 30))
                .build();

        studentDto = StudentDto.builder()
                .id(1)
                .studentCode("20110728")
                .studentName("Thang Pham")
                .studentInfo(studentInfoDto)
                .build();

        StudentInfo studentInfo = StudentInfo.builder()
                .infoId(1)
                .address("60 Ben Chuong Duong")
                .averageScore(9.5)
                .dateOfBirth(new Date(2002, 04, 30))
                .build();

        student = Student.builder()
                .id(1)
                .studentCode("20110728")
                .studentName("Thang Pham")
                .studentInfo(studentInfo)
                .build();
        student.getStudentInfo().setStudent(student);
    }

    // JUnit Test for save student method
    @Test
    @DisplayName("JUnit Test for save student method")
    public void givenStudentObject_whenSave_thenStudentObject() {
        // given - preconditions or setup
        when(studentMapper.dtoToEntity(studentDto)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(studentMapper.entityToDto(student)).thenReturn(studentDto);

        // when - action or behavior that we are going test
        DataResponse<StudentDto> response = studentService.create(studentDto);

        // then - verify the output
        System.out.println(response.getData());
        assertThat(response).isNotNull();
        assertThat(response.getData()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(StatusCode.REQUEST_SUCCESS);
    }

    // JUnit Test for get student by id method
    @Test
    @DisplayName("JUnit Test for get student by id method")
    public void givenStudentObject_whenFindById_thenStudentObject() {
        // given - preconditions or setup
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(studentMapper.entityToDto(student)).thenReturn(studentDto);

        // when - action or behavior that we are going test
        DataResponse<StudentDto> response = studentService.getById(student.getId());

        // then - verify the output
        System.out.println(response.getData());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(StatusCode.REQUEST_SUCCESS);
        assertThat(response.getData().getStudentCode()).isEqualTo(student.getStudentCode());
    }

    // JUnit Test for get student by id method (negative scenario)
    @Test
    @DisplayName("JUnit Test for get student by id method (negative scenario)")
    public void givenStudentObject_whenGetById_thenMessageError() {
        // given - preconditions or setup
        when(studentRepository.findById(student.getId())).thenReturn(Optional.empty());
        // when - action or behavior that we are going test
        DataResponse<StudentDto> response = studentService.getById(student.getId());
        // then - verify the output
        System.out.println(response.getData());
        assertThat(response).isNotNull();
        assertThat(response.getStatusMessage()).isEqualTo(StatusMessage.DATA_NOT_FOUND);
    }

    // JUnit Test for update student method
    @Test
    @DisplayName("JUnit Test for update student method")
    public void givenStudentObject_whenUpdate_thenUpdatedStudent() {
        // given - preconditions or setup
        studentDto.setStudentCode("200000");
        studentDto.getStudentInfo().setAverageScore(6.5);
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(studentMapper.entityToDto(student)).thenReturn(studentDto);
        // when - action or behavior that we are going test
        DataResponse<StudentDto> response = studentService.update(student.getId(), studentDto);
        // then - verify the output
        System.out.println(response.getData());
        assertThat(response.getStatusCode()).isEqualTo(StatusCode.REQUEST_SUCCESS);
        assertThat(response.getData().getStudentCode()).isEqualTo(studentDto.getStudentCode());
        assertThat(response.getData().getStudentInfo().getAverageScore()).isEqualTo(studentDto.getStudentInfo().getAverageScore());
    }

    // JUnit Test for update student method (negative scenario)
    @Test
    @DisplayName("JUnit Test for update student method (negative scenario)")
    public void givenNotExistStudent_whenUpdate_thenThrowException() {
        // given - preconditions or setup
        when(studentRepository.findById(student.getId())).thenReturn(Optional.empty());
        // when - action or behavior that we are going test
        Throwable throwable = catchThrowable(() -> studentService.update(student.getId(), studentDto));

        // then - verify the output
        assertThat(throwable).isInstanceOf(ResourceNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Student doesn't exists.");
    }
}
