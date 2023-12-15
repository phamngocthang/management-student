package com.tranning.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tranning.management.common.message.StatusCode;
import com.tranning.management.common.response.DataResponse;
import com.tranning.management.common.response.ResponseMapper;
import com.tranning.management.dto.UserDto;
import com.tranning.management.service.UserSevice;
import jdk.jfr.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
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

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserSevice userSevice;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDto userDto;
    @BeforeEach
    private void setup() {
        userDto = UserDto.builder()
                .id(1)
                .username("pntnoah")
                .password("123456")
                .build();
    }

    // JUnit Test for save user controller
    @Test
    @DisplayName("JUnit Test for save user controller")
    public void givenUserObject_whenSaveUser_thenReturnSavedUser() throws Exception {
        // given - preconditions or setup
        UserDto userRequest = UserDto.builder()
                .username("ThangPham")
                .password("123456")
                .build();
        UserDto userResponse = UserDto.builder()
                .id(1)
                .username("ThangPham")
                .password("123456")
                .build();

        DataResponse<UserDto> responseData = ResponseMapper.toDataResponseSuccess(userResponse);
        when(userSevice.create(any(UserDto.class))).thenReturn(responseData);
        // when - action or behavior that we are going test
        ResultActions response = mockMvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)));

        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", CoreMatchers.is(StatusCode.REQUEST_SUCCESS)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username", CoreMatchers.is(userResponse.getUsername())));
    }

    // JUnit Test for get by id controller
    @Test
    @DisplayName("JUnit Test for get by id controller")
    public void givenUserObject_whenFindById_thenReturnUserObject() throws Exception {
        // given - preconditions or setup
        DataResponse<UserDto> responseData = ResponseMapper.toDataResponseSuccess(userDto);
        when(userSevice.getById(any(Integer.class))).thenReturn(responseData);
        // when - action or behavior that we are going test
        ResultActions response = mockMvc.perform(get("/users/get-by-id?id=" + userDto.getId()));
        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", CoreMatchers.is(StatusCode.REQUEST_SUCCESS)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username", CoreMatchers.is(userDto.getUsername())));
    }

    // JUnit Test for update user controller
    @Test
    @DisplayName("JUnit Test for update user controller")
    public void givenUserObject_whenUpdateUser_thenReturnUpdatedUser() throws Exception {
        // given - preconditions or setup
        DataResponse<UserDto> responseData = ResponseMapper.toDataResponseSuccess(userDto);
        when(userSevice.update(any(UserDto.class), any(Integer.class))).thenReturn(responseData);
        // when - action or behavior that we are going test
        ResultActions response = mockMvc.perform(put("/users/update/" + userDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", CoreMatchers.is(StatusCode.REQUEST_SUCCESS)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username", CoreMatchers.is(userDto.getUsername())));
    }

    // JUnit Test for delete user controller
    @Test
    @DisplayName("JUnit Test for delete user controller")
    public void givenUserId_whenDeleteById_thenReturnMessage() throws Exception {
        // given - preconditions or setup
        String message = "Delete user successfully";
        DataResponse<String> responseData = ResponseMapper.toDataResponseSuccess(message);
        when(userSevice.deleteById(any(Integer.class))).thenReturn(responseData);

        // when - action or behavior that we are going test
        ResultActions response = mockMvc.perform(delete("/users/delete/" + userDto.getId()));

        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", CoreMatchers.is(StatusCode.REQUEST_SUCCESS)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", CoreMatchers.is(message)));
    }
}
