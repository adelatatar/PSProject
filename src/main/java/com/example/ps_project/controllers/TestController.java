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

    /**
     * Se realizeaza adaugarea unui nou test in baza de date. Daca testul nu este gasit (se cauta dupa id),
     * acesta se adauga cu succes, altfel se returneaza un mesaj de eroare.
     * @param testDTO
     * @return
     */
    @PostMapping(path = "createTest")
    public ResponseEntity<DTO> createNewTest(@RequestBody TestDTO testDTO) {return testService.createNewTest(testDTO);}

    /**
     * Stergerea unui test dupa id. Se cauta testul dupa id, daca este gasit acesta va fi sters si se afiseaza
     * un mesaj de succes, in caz contrar, se afiseaza un mesaj de eroare.
     * @param idTest
     * @return
     */
    @DeleteMapping(path = "deleteTest")
    public ResponseEntity<DTO> deleteTest(@RequestBody Integer idTest) {return testService.deleteTest(idTest);}

    /**
     * Se verifica daca intrebarea exista, in caz afirmativ se returneaza un mesaj de eroare, iar in caz negativ
     * se continua procesul cu cautarea testului dupa idCourse. In cazul in care testul nu exista vom primi un mesaj
     * de eroare, altfel se adauga intrebarea atat in tabela Question cat si in tabela intermediara QuestionsInCourses
     * si se va primi un mesaj de succes.
     * @param questionDTO
     * @param idtest
     * @return
     */
    @PostMapping(path = "addQuestion")
    public ResponseEntity<DTO> addNewQuestion(@RequestBody QuestionDTO questionDTO, @RequestHeader(name = "idTest") Integer idtest) {
        return testService.addNewQuestion(questionDTO, idtest);
    }
}
