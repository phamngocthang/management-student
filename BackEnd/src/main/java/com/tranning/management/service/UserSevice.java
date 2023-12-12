package com.tranning.management.service;

import com.tranning.management.common.error.exception.DataNotFoundException;
import com.tranning.management.common.error.exception.ResourceNotFoundException;
import com.tranning.management.common.message.StatusCode;
import com.tranning.management.common.message.StatusMessage;
import com.tranning.management.common.response.DataResponse;
import com.tranning.management.common.response.ListResponse;
import com.tranning.management.common.response.ResponseMapper;
import com.tranning.management.dto.ChangePasswordRequest;
import com.tranning.management.dto.LoginRequest;
import com.tranning.management.dto.UserDto;
import com.tranning.management.mapper.UserMapper;
import com.tranning.management.model.User;
import com.tranning.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserSevice {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public DataResponse<UserDto> login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findUserByUsername(loginRequest.getUsername());
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            if (BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
                return ResponseMapper.toDataResponseSuccess(userMapper.entityToDto(user));
            } else {
                return ResponseMapper.toDataResponse("Password is not valid", StatusCode.DATA_NOT_MAP, StatusMessage.DATA_NOT_MAP);
            }
        } else {
            throw new DataNotFoundException("User doesn't exists.");
        }
    }

    public DataResponse<UserDto> create(UserDto userDto) {
        User user = userMapper.dtoToEntity(userDto);
        String hashedPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());
        System.out.println("User: " + user);
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return ResponseMapper.toDataResponseSuccess(userMapper.entityToDto(user));
    }

    public DataResponse<UserDto> update(UserDto userDto, Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            userMapper.dtoToEntity(userDto, user);
            user.setId(id);
            userRepository.save(user);
            return ResponseMapper.toDataResponseSuccess(userMapper.entityToDto(user));
        } else {
            throw new ResourceNotFoundException("User doesn't exists.");
        }
    }

    public DataResponse<UserDto> getById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(value -> ResponseMapper.toDataResponseSuccess(userMapper.entityToDto(value)))
                .orElseGet(() -> ResponseMapper.toDataResponse("User doesn't exists.", StatusCode.DATA_NOT_FOUND, StatusMessage.DATA_NOT_FOUND));
    }

    public ListResponse<UserDto> getAll() {
        return ResponseMapper.toListResponseSuccess(
                userRepository.findAll()
                        .stream().map(value -> {
                            return userMapper.entityToDto(value);
                        })
                        .collect(Collectors.toList()));
    }

    public DataResponse<String> deleteById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return ResponseMapper.toDataResponseSuccess("Delete user successfully");
        } else {
            return ResponseMapper.toDataResponse("User doesn't exists", StatusCode.DATA_NOT_FOUND, StatusMessage.DATA_NOT_FOUND);
        }
    }

    public DataResponse<UserDto> changePassword(Integer id, ChangePasswordRequest changePasswordRequest) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            if(BCrypt.checkpw(changePasswordRequest.getOldPassword(), user.getPassword())) {
                String newPassword = BCrypt.hashpw(changePasswordRequest.getNewPassword(), BCrypt.gensalt());
                user.setPassword(newPassword);
                User updatedUser = userRepository.save(user);
                return ResponseMapper.toDataResponseSuccess(userMapper.entityToDto(updatedUser));
            } else {
                return ResponseMapper.toDataResponse("Password is not valid", StatusCode.DATA_NOT_MAP, StatusMessage.DATA_NOT_MAP);
            }
        } else {
            throw new ResourceNotFoundException("User doesn't exists.");
        }
    }
}
