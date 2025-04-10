package com.tabish.example.student;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentMapperTest {

    private StudentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new StudentMapper();
    }

    @Test
    public void shouldMapStudentDtoToStudent() {
        StudentDto dto = new StudentDto(
                "John",
                "Doe",
                "john.doe@example.com",
                1);

        Student student = mapper.toStudent(dto);

        Assertions.assertEquals(dto.firstName(), student.getFirstName());
        Assertions.assertEquals(dto.lastName(), student.getLastName());
        Assertions.assertEquals(dto.email(), student.getEmail());
        Assertions.assertNotNull(student.getSchool());
        Assertions.assertEquals(dto.schoolId(), student.getSchool().getId());
    }

    @Test
    public void should_throw_null_pointer_exception_when_studentDto_is_null() {
        var exp = Assertions.assertThrows(NullPointerException.class, () -> mapper.toStudent(null));
        Assertions.assertEquals("The StudentDto is null", exp.getMessage());
    }

    @Test
    public void shouldMapStudentToStudentResponseDto() {
        // Given
        Student student = new Student(
                "Abigail",
                "Goman",
                "abigail.goman@google.com",
                35);

        //When
        StudentResponseDto dto = mapper.toStudentResponseDto(student);

        //Then
        Assertions.assertEquals(student.getFirstName(), dto.firstName());
        Assertions.assertEquals(student.getLastName(), dto.lastName());
        Assertions.assertEquals(student.getEmail(), dto.email());
    }
}