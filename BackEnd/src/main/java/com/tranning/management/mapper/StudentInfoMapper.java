package com.tranning.management.mapper;

import com.tranning.management.common.mapper.BaseMapperImpl;
import com.tranning.management.dto.StudentInfoDto;
import com.tranning.management.model.StudentInfo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class StudentInfoMapper extends BaseMapperImpl<StudentInfo, StudentInfoDto> {
    public StudentInfoMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<StudentInfo> getEntityClass() {
        return StudentInfo.class;
    }

    @Override
    protected Class<StudentInfoDto> getDtoClass() {
        return StudentInfoDto.class;
    }
}
