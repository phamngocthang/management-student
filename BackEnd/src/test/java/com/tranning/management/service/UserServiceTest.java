package com.tranning.management.service;

import com.tranning.management.common.error.exception.DataNotFoundException;
import com.tranning.management.common.message.StatusCode;
import com.tranning.management.common.response.DataResponse;
import com.tranning.management.common.response.ListResponse;
import com.tranning.management.dto.LoginRequest;
import com.tranning.management.dto.UserDto;
import com.tranning.management.mapper.UserMapper;
import com.tranning.management.model.User;
import com.tranning.management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private BCrypt bCrypt;
    @InjectMocks
    private UserSevice userSevice;

    // JUnit test for save user method
    @Test
    @DisplayName("JUnit test for save user method")
    public void givenUserObject_whenSaveUser_thenReturnUserObject() {
        // given - preconditions or setup
        UserDto userDto = UserDto.builder()
                .username("ThangPham")
                .password("123456")
                .build();

        User user = User.builder()
                .id(1)
                .username("ThangPham")
                .password("123456")
                .build();

        String hashedPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());

        when(userMapper.dtoToEntity(userDto)).thenReturn(user);
        user.setPassword(hashedPassword);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.entityToDto(user)).thenReturn(new UserDto(user.getId(), user.getUsername(), hashedPassword));
        // when - action or behaviour that we are going test
        DataResponse<UserDto> result = userSevice.create(userDto);
        System.out.println(result.getData());

        // then - verify the output
        System.out.println(BCrypt.checkpw(userDto.getPassword(), hashedPassword));
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(StatusCode.REQUEST_SUCCESS);
        assertThat(result.getData()).isNotNull();
    }

    // JUnit Test for login User method
    @Test
    @DisplayName("JUnit Test for login User method")
    public void givenLoginRequest_whenLogin_thenUserLogin() {
        // given - preconditions or setup
        LoginRequest loginRequest = new LoginRequest("pntnoah1", "123456");
        String hashedPassword = BCrypt.hashpw(loginRequest.getPassword(), BCrypt.gensalt());

        Optional<User> optionalUser = Optional.of(new User(1, "pntnoah1", hashedPassword));
        User user = optionalUser.get();

        when(userRepository.findUserByUsername(loginRequest.getUsername())).thenReturn(optionalUser);
        when(userMapper.entityToDto(user)).thenReturn(new UserDto(user.getId(), user.getUsername(), user.getPassword()));

        // when - action or behavior that we are going test
        DataResponse<UserDto> result = userSevice.login(loginRequest);
        System.out.println(result.getData());
        System.out.println(BCrypt.checkpw("123456", result.getData().getPassword()));
        // then - verify the output
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(StatusCode.REQUEST_SUCCESS);
        assertThat(result.getData().getUsername()).isEqualTo("pntnoah1");
    }

    // JUnit Test for login throw exception method
    @Test
    @DisplayName("JUnit Test for login throw exception method")
    public void givenLoginRequest_whenLogin_thenThrowException() {
        // given - preconditions or setup
        LoginRequest loginRequest = new LoginRequest("pntnoah1", "123456");
        when(userRepository.findUserByUsername(loginRequest.getUsername())).thenReturn(Optional.empty());
        // when - action or behavior that we are going test
        Throwable throwable = catchThrowable(() -> userSevice.login(loginRequest));
        // then - verify the output
        assertThat(throwable).isInstanceOf(DataNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("User doesn't exists.");
    }

    // JUnit Test for get by id for user method
    @Test
    @DisplayName("JUnit Test for get by id for user method")
    public void givenUserObject_whenFindById_thenUserObject() {
        // given - preconditions or setup
        String hashedPassword = BCrypt.hashpw("123456", BCrypt.gensalt());
        User user = User.builder()
                .id(1)
                .username("pntnoah1")
                .password(hashedPassword)
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.entityToDto(user)).thenReturn(new UserDto(user.getId(), user.getUsername(), user.getPassword()));
        // when - action or behavior that we are going test
        DataResponse<UserDto> response = userSevice.getById(user.getId());
        // then - verify the output
        System.out.println(response.getData());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(StatusCode.REQUEST_SUCCESS);
        assertThat(response.getData().getUsername()).isEqualTo(user.getUsername());
    }

    // JUnit Test for get by id user method (negative scenario)
    @Test
    @DisplayName("JUnit Test for get by id user method (negative scenario)")
    public void givenUserObject_whenFindById_thenReturnEmptyObject() {
        // given - preconditions or setup
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        // when - action or behavior that we are going test
        DataResponse<UserDto> response = userSevice.getById(1);
        // then - verify the output
        System.out.println(response.getData());
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(StatusCode.DATA_NOT_FOUND);
    }

    // JUnit Test for get all user method
    @Test
    @DisplayName("JUnit Test for get all user method")
    public void givenUserList_whenGetAll_thenUserList() {
        // given - preconditions or setup
        User user1 = new User(1, "pntnoah1", "123456");
        User user2 = new User(2, "pntnoah2", "123456");
        List<User> userList = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(userList);
        when(userMapper.entityToDto(user1)).thenReturn(new UserDto(user1.getId(), user1.getUsername(), user1.getPassword()));
        when(userMapper.entityToDto(user2)).thenReturn(new UserDto(user2.getId(), user2.getUsername(), user2.getPassword()));
        // when - action or behavior that we are going test
        ListResponse<UserDto> response = userSevice.getAll();
        // then - verify the output

        response.getData().forEach(System.out::println);
//        response.getData().forEach(System.out::println);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(StatusCode.REQUEST_SUCCESS);
        assertThat(response.getTotalRecords()).isEqualTo(2);
    }

    // JUnit Test for delete user by id method
    @Test
    @DisplayName("JUnit Test for delete user by id method")
    public void givenUserObject_whenDelete_thenReturnReponse() {
        // given - preconditions or setup
        Optional<User> optionalUser = Optional.of(new User(1, "pntnoah1", "123456"));

        when(userRepository.findById(1)).thenReturn(optionalUser);
        // when - action or behavior that we are going test
        DataResponse<String> response = userSevice.deleteById(optionalUser.get().getId());
        // then - verify the output
        assertThat(response).isNotNull();
        assertThat(response.getData()).isEqualTo("Delete user successfully");
    }

    // JUnit Test for update user method
    @Test
    @DisplayName("JUnit Test for update user method")
    public void givenUserObject_whenUpdate_thenUpdatedUser() {
        // given - preconditions or setup
        User user = new User(1, "pntnoah1", "123456");
        Optional<User> optionalUser = Optional.of(user);
        UserDto userDto = UserDto.builder()
                .username("thangpham")
                .build();

        when(userRepository.findById(user.getId())).thenReturn(optionalUser);
        userMapper.dtoToEntity(userDto, user);
        user.setId(1);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.entityToDto(user)).thenReturn(new UserDto(user.getId(), user.getUsername(), user.getPassword()));
        // when - action or behavior that we are going test
        DataResponse<UserDto> response = userSevice.update(userDto, user.getId());

        // then - verify the output
        System.out.println(response.getData());
        assertThat(response).isNotNull();
        assertThat(response.getData()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(StatusCode.REQUEST_SUCCESS);
    }
}
