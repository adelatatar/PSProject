package com.example.ps_project.services;

import com.example.ps_project.DTOs.*;
import com.example.ps_project.entities.Question;
import com.example.ps_project.entities.QuestionsInTest;
import com.example.ps_project.entities.Test;
import com.example.ps_project.repositories.QuestionRespository;
import com.example.ps_project.repositories.QuestionsInTestsRepository;
import com.example.ps_project.repositories.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Clasa TestService contine toate metodele/endpoint-urile necesare pentru functionalitatile dorite in aplicatia la care lucrez
 */
@Service
@AllArgsConstructor
public class TestService {
    private final TestRepository testRepository;
    private final QuestionsInTestsRepository questionsInTestsRepository;
    private final QuestionRespository questionRespository;

    /**
     * Se realizeaza adaugarea unui nou test in baza de date. Daca testul nu este gasit (se cauta dupa id),
     * acesta se adauga cu succes, altfel se returneaza un mesaj de eroare.
     * @param testDTO
     * @return
     */
    public ResponseEntity<DTO> createNewTest(TestDTO testDTO) {
        Optional<Test> foundTest = testRepository.findById(testDTO.getId());
        if(foundTest.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("This test alredy exist"));
        }
        Test newTest = new Test();
        newTest.setName(testDTO.getName());
        testRepository.save(newTest);

        if(testRepository.existsById(newTest.getId())){
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("Test saved!"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ErrorDTO("The test has not been saved!"));
        }
    }

    /**
     * Stergerea unui test dupa id. Se cauta testul dupa id, daca este gasit acesta va fi sters si se
     * afiseaza un mesaj de succes, in caz contrar, se afiseaza un mesaj de eroare.
     * @param idTest
     * @return
     */
    public ResponseEntity<DTO> deleteTest(Integer idTest) {
        Optional<Test> foundTest = testRepository.findById(idTest);
        if(foundTest.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("The test does't exit!"));
        } else {
            testRepository.deleteById(idTest);
        }

        if(testRepository.existsById(idTest)) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ErrorDTO("The test has not been deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("The test was deleted!"));
        }
    }

    /**
     * Se verifica daca intrebarea exista, in caz afirmativ se returneaza un mesaj de eroare, iar in caz negativ se continua
     * procesul cu cautarea testului dupa idCourse. In cazul in care testul nu exista vom primi un mesaj de eroare,
     * altfel se adauga intrebarea atat in tabela Question cat si in tabela intermediara QuestionsInCourses
     * si se va primi un mesaj de succes.
     * @param questionDTO
     * @param idTest
     * @return
     */
    public ResponseEntity<DTO> addNewQuestion(QuestionDTO questionDTO, Integer idTest) {
        Optional<Question> foundQuestion = questionRespository.findById(questionDTO.getId());

        if(foundQuestion.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("The question already exist!"));
        }

        Question question = new Question();
        question.setName(questionDTO.getName());
        question.setQuestionText(questionDTO.getQuestionText());
        question.setAnswer(questionDTO.getAnswerText());

        Question savedQuestion = questionRespository.save(question);
        Optional<Test> foundTest = testRepository.findById(idTest);
        if(foundTest.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("The test doesn't exist!"));
        }

        Test test = foundTest.get();
        QuestionsInTest questionsInTest = new QuestionsInTest();
        questionsInTest.setQuestion(savedQuestion);
        questionsInTest.setTest(test);
        questionsInTestsRepository.save(questionsInTest);

        if(questionsInTestsRepository.findById(questionsInTest.getId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ErrorDTO("The question has not been added in the test!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessMessage("Question saved!"));
        }
    }
}
