package com.example.ps_project.controllers;

import com.example.ps_project.DTOs.DTO;
import com.example.ps_project.DTOs.QuestionDTO;
import com.example.ps_project.DTOs.TestDTO;
import com.example.ps_project.services.TestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/test")
@AllArgsConstructor
public class TestController {
    private final TestService testService;

    @PostMapping(path = "createTest")
    public ResponseEntity<DTO> createNewTest(@RequestBody TestDTO testDTO) {return testService.createNewTest(testDTO);}

    @DeleteMapping(path = "deleteTest")
    public ResponseEntity<DTO> deleteTest(@RequestBody Integer idTest) {return testService.deleteTest(idTest);}

    @PostMapping(path = "addQuestion")
    public ResponseEntity<DTO> addNewQuestion(@RequestBody QuestionDTO questionDTO, @RequestHeader(name = "idTest") Integer idtest) {
        return testService.addNewQuestion(questionDTO, idtest);
    }
}
