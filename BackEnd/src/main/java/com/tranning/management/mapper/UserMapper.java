package com.tranning.management.mapper;

import com.tranning.management.common.mapper.BaseMapperImpl;
import com.tranning.management.dto.UserDto;
import com.tranning.management.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapper extends BaseMapperImpl<User, UserDto> {
    public UserMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    protected Class<UserDto> getDtoClass() {
        return UserDto.class;
    }
}
