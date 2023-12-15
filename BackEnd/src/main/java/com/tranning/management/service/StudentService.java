package com.tranning.management.service;

import com.tranning.management.common.error.exception.ResourceNotFoundException;
import com.tranning.management.common.message.StatusCode;
import com.tranning.management.common.message.StatusMessage;
import com.tranning.management.common.response.DataResponse;
import com.tranning.management.common.response.ListResponse;
import com.tranning.management.common.response.ResponseMapper;
import com.tranning.management.dto.SearchByKeywordDto;
import com.tranning.management.dto.StudentDto;
import com.tranning.management.dto.UserDto;
import com.tranning.management.mapper.StudentMapper;
import com.tranning.management.model.Student;
import com.tranning.management.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    public DataResponse<StudentDto> create(StudentDto studentDto) {
        Student student = studentMapper.dtoToEntity(studentDto);
        student.getStudentInfo().setStudent(student);
        studentRepository.save(student);
        return ResponseMapper.toDataResponseSuccess(studentMapper.entityToDto(student));
    }

    public DataResponse<StudentDto> update(Integer id, StudentDto studentDto) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if(studentOptional.isEmpty()) {
            throw new ResourceNotFoundException("Student doesn't exists.");
        }
        Student student = studentOptional.get();
        studentMapper.dtoToEntity(studentDto, student);
        student.getStudentInfo().setStudent(student);
        student.setId(id);
        studentRepository.save(student);
        return ResponseMapper.toDataResponseSuccess(studentMapper.entityToDto(student));
    }

    public DataResponse<String> deleteById(Integer id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if(studentOptional.isEmpty()) {
            throw new ResourceNotFoundException("Student doesn't exists");
        }
        studentRepository.delete(studentOptional.get());
        return ResponseMapper.toDataResponseSuccess("Delete student successfully");
    }

    public DataResponse<StudentDto> getById(Integer id) {
        return studentRepository.findById(id).map(value -> ResponseMapper.toDataResponseSuccess(studentMapper.entityToDto(value)))
                .orElseGet(() -> ResponseMapper.toDataResponse("Student doesn't exists", StatusCode.DATA_NOT_FOUND, StatusMessage.DATA_NOT_FOUND));
    }

    public ListResponse<StudentDto> getAll() {
        return ResponseMapper.toListResponseSuccess(
                studentRepository.findAll()
                .stream().map((student) ->
                                studentMapper.entityToDto(student))
                .collect(Collectors.toList())
        );
    }

    public ListResponse<StudentDto> searchByKeyword(SearchByKeywordDto searchByKeywordDto) {
        Pageable pageable = PageRequest.of(searchByKeywordDto.getPageIndex(), searchByKeywordDto.getPageSize());
        String keyword = searchByKeywordDto.getKeyword();

        Page<Student> students = studentRepository.searchByKeyword(keyword, pageable);
        return ResponseMapper.toPagingResponseSuccess(students.map(student -> studentMapper.entityToDto(student)));
    }
}
