package com.tranning.management.mapper;


import com.tranning.management.common.mapper.BaseMapperImpl;
import com.tranning.management.dto.StudentDto;
import com.tranning.management.model.Student;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class StudentMapper extends BaseMapperImpl<Student, StudentDto> {
    public StudentMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<Student> getEntityClass() {
        return Student.class;
    }

    @Override
    protected Class<StudentDto> getDtoClass() {
        return StudentDto.class;
    }
}
