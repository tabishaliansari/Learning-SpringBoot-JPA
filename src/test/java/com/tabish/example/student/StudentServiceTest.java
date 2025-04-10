package com.tabish.example.student;

import net.bytebuddy.NamingStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class StudentServiceTest {

    //which service we want to test?
    @InjectMocks
    private StudentService studentService;

    //declare the dependencies
    @Mock
    private StudentRepository repository;
    @Mock
    private StudentMapper studentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_successfully_save_student() {
        StudentDto dto = new StudentDto(
                "Johnny",
                "Doe",
                "johnny.doe@apple.com",
                1
        );

        Student student = new Student(
                "Johnny",
                "Doe",
                "johnny.doe@apple.com",
                20
        );

        Student savedStudent = new Student(
                "Johnny",
                "Doe",
                "johnny.doe@apple.com",
                20
        );
        savedStudent.setId(1);

        //Mock the calls
        when(studentMapper.toStudent(dto))
                .thenReturn(student);
        when(repository.save(student))
                .thenReturn(savedStudent);
        when(studentMapper.toStudentResponseDto(savedStudent))
                .thenReturn(new StudentResponseDto(
                        "Johnny",
                        "Doe",
                        "johnny.doe@apple.com"));

        //When
        StudentResponseDto responseDto = studentService.saveStudent(dto);

        //Then
        Assertions.assertEquals(dto.firstName(), responseDto.firstName());
        Assertions.assertEquals(dto.lastName(), responseDto.lastName());
        Assertions.assertEquals(dto.email(), responseDto.email());

        verify(studentMapper, times(1))
                .toStudent(dto);
        verify(repository, times(1))
                .save(student);
        verify(studentMapper, times(1))
                .toStudentResponseDto(savedStudent);
    }

    @Test
    public void should_return_all_students() {
        //Given
        List<Student> students = new ArrayList<>();
        students.add(new Student(
                "johnny.doe@apple.com",
                "Doe",
                "Johnny",
                20
        ));

        //mock the calls
        when(repository.findAll()).thenReturn(students);
        when(studentMapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        "Johnny",
                        "Doe",
                        "johnny.doe@apple.com"
                ));

        //when
        List<StudentResponseDto> responseDtos = studentService.findAllStudent();

        //then
        Assertions.assertEquals(students.size(), responseDtos.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void should_return_student_by_id() {
        //Given
        int studentId = 1;

        Student student = new Student(
                "Johnny",
                "Doe",
                "johnny.doe@apple.com",
                20
        );

        //mock the calls
        when(repository.findById(studentId))
                .thenReturn(Optional.of(student));
        when(studentMapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        "Johnny",
                        "Doe",
                        "johnny.doe@apple.com"
                ));

        //when
        StudentResponseDto dto = studentService.getStudentById(studentId);

        //then
        Assertions.assertEquals(dto.firstName(), student.getFirstName());
        Assertions.assertEquals(dto.lastName(), student.getLastName());
        Assertions.assertEquals(dto.email(), student.getEmail());

        verify(repository, times(1)).findById(studentId);
    }

    @Test
    public void should_find_student_by_name() {
        //Given
        String name = "Johnny";
        List<Student> students = new ArrayList<>();
        students.add(new Student(
                "johnny.doe@apple.com",
                "Doe",
                "Johnny",
                20
        ));

        //mock the calls
        when(repository.findAllByFirstNameContaining(name))
                .thenReturn(students);
        when(studentMapper.toStudentResponseDto(any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        "Johnny",
                        "Doe",
                        "johnny.doe@apple.com"
                ));

        //when
        var responseDto = studentService.findStudentByFirstName(name);

        //then
        Assertions.assertEquals(students.size(), responseDto.size());
        verify(repository, times(1))
                .findAllByFirstNameContaining(name);
    }
}