package com.tranning.management.controller;

import com.tranning.management.common.response.DataResponse;
import com.tranning.management.common.response.ListResponse;
import com.tranning.management.common.utilities.ApiResources;
import com.tranning.management.dto.StudentDto;
import com.tranning.management.repository.StudentRepository;
import com.tranning.management.service.StudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD Rest APIs for Student Resource",
        description = "CRUD REST APIs - Create Student, Update Student, Get Student, Delete Student"
)
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping(ApiResources.ADD)
    public DataResponse<StudentDto> add(@RequestBody StudentDto studentDto) {
        return studentService.create(studentDto);
    }

    @PutMapping(ApiResources.UPDATE)
    public DataResponse<StudentDto> update(@PathVariable("id") Integer id,
                                           @RequestBody StudentDto studentDto) {
        return studentService.update(id, studentDto);
    }

    @GetMapping(ApiResources.GET_BY_ID)
    public DataResponse<StudentDto> getById(@RequestParam("id") Integer id) {
        return studentService.getById(id);
    }

    @DeleteMapping(ApiResources.DELETE)
    public DataResponse<String> delete(@PathVariable("id") Integer id) {
        return studentService.deleteById(id);
    }

    @GetMapping(ApiResources.GET_ALL)
    public ListResponse<StudentDto> getAll() {
        return studentService.getAll();
    }
}
