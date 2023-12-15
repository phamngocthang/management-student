package com.tranning.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tranning.management.common.message.StatusCode;
import com.tranning.management.common.response.DataResponse;
import com.tranning.management.common.response.ResponseMapper;
import com.tranning.management.dto.StudentDto;
import com.tranning.management.dto.StudentInfoDto;
import com.tranning.management.service.StudentService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.sql.Date;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    ObjectMapper objectMapper;

    private StudentInfoDto studentInfoDto;
    private StudentDto studentDto;
    @BeforeEach
    private void setup() {
        studentInfoDto = StudentInfoDto.builder()
                .infoId(1)
                .address("64 Ben Chuong Duong")
                .averageScore(8.5)
                .dateOfBirth(new Date(2002, 04, 30))
                .build();

        studentDto = StudentDto.builder()
                .id(1)
                .studentCode("20110728")
                .studentName("Thang Pham")
                .studentInfo(studentInfoDto)
                .build();
    }

    // JUnit Test for get student by id method controller
    @Test
    @DisplayName("JUnit Test for get student by id method controller")
    public void givenStudentObject_whenFindById_thenStudentObject() throws Exception {
        // given - preconditions or setup
        DataResponse<StudentDto> responseData = ResponseMapper.toDataResponseSuccess(studentDto);
        when(studentService.getById(any(Integer.class))).thenReturn(responseData);

        // when - action or behavior that we are going test
        ResultActions response = mockMvc.perform(get("/student/get-by-id?id=" +studentDto.getId()));

        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.studentCode", CoreMatchers.is(studentDto.getStudentCode())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.studentName", CoreMatchers.is(studentDto.getStudentName())));
    }

    // JUnit Test for create user method controller
    @DisplayName("JUnit Test for create user method controller")
    @Test
    public void givenStudentObject_whenCreate_thenSavedStudent() throws Exception {
        // given - preconditions or setup
        StudentDto studentRequest = StudentDto.builder()
                .studentCode("20110728")
                .studentName("Thang Pham")
                .studentInfo(StudentInfoDto.builder()
                        .address("64 Ben Chuong Duong")
                        .averageScore(8.5)
                        .dateOfBirth(new Date(2002, 04, 30))
                        .build())
                .build();

        DataResponse<StudentDto> responseData = ResponseMapper.toDataResponseSuccess(studentDto);
        when(studentService.create(any(StudentDto.class))).thenReturn(responseData);
        // when - action or behavior that we are going test
        ResultActions response = mockMvc.perform(post("/student/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentRequest)));

        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", CoreMatchers.is(StatusCode.REQUEST_SUCCESS)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.studentCode", CoreMatchers.is(studentDto.getStudentCode())));
    }

    // JUnit Test for update student method
    @Test
    @DisplayName("JUnit Test for update student method")
    public void givenUserObject_whenUpdateUser_thenUpdatedUser() throws Exception{
        // given - preconditions or setup
        DataResponse<StudentDto> responseData = ResponseMapper.toDataResponseSuccess(studentDto);
        when(studentService.update(any(Integer.class), any(StudentDto.class))).thenReturn(responseData);
        // when - action or behavior that we are going test
        ResultActions response = mockMvc.perform(put("/student/update/" + studentDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDto)));
        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", CoreMatchers.is(StatusCode.REQUEST_SUCCESS)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.studentCode", CoreMatchers.is(studentDto.getStudentCode())));
    }

    // JUnit Test for delete student method controller
    @Test
    @DisplayName("JUnit Test for delete student method controller")
    public void givenStudentId_whenDeleteStudent_thenReturnMessage() throws Exception {
        // given - preconditions or setup
        String message = "Delete student successfully";
        DataResponse<String> responseData = ResponseMapper.toDataResponseSuccess(message);
        when(studentService.deleteById(any(Integer.class))).thenReturn(responseData);
        // when - action or behavior that we are going test
        ResultActions response = mockMvc.perform(delete("/student/delete/" + studentDto.getId()));
        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", CoreMatchers.is(StatusCode.REQUEST_SUCCESS)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", CoreMatchers.is(message)));
    }
}
