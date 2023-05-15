package com.example.ps_project;

import com.example.ps_project.DTOs.*;
import com.example.ps_project.entities.*;
import com.example.ps_project.repositories.QuestionRespository;
import com.example.ps_project.repositories.QuestionsInTestsRepository;
import com.example.ps_project.repositories.TestRepository;
import com.example.ps_project.services.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TestServiceTest {
    @Mock
    private TestRepository testRepository;

    @Mock
    private QuestionRespository questionRespository;

    @Mock
    private QuestionsInTestsRepository questionsInTestsRepository;

    @InjectMocks
    private TestService testService;

    @Test
    public void testCreateNewTest_Success(){
        TestDTO testDTO = new TestDTO(1, "Test1");
        com.example.ps_project.entities.Test test = new com.example.ps_project.entities.Test();
        test.setName(testDTO.getName());

        when(testRepository.findById(testDTO.getId())).thenReturn(Optional.empty());
        when(testRepository.save(test)).thenReturn(test);
        when(testRepository.existsById(test.getId())).thenReturn(true);

        ResponseEntity<DTO> response = testService.createNewTest(testDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test saved!", ((SuccessMessage) response.getBody()).getSuccessMessage());
        verify(testRepository, times(1)).findById(testDTO.getId());
    }

    @Test
    public void testCreateNewTest_Failed(){
        TestDTO testDTO = new TestDTO(1, "Test1");
        com.example.ps_project.entities.Test test = new com.example.ps_project.entities.Test();
        test.setName(testDTO.getName());

        when(testRepository.findById(testDTO.getId())).thenReturn(Optional.empty());
        when(testRepository.save(test)).thenReturn(test);
        when(testRepository.existsById(test.getId())).thenReturn(false);

        ResponseEntity<DTO> response = testService.createNewTest(testDTO);

        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());
        assertEquals("The test has not been saved!", ((ErrorDTO) response.getBody()).getErrorMessage());
        verify(testRepository, times(1)).findById(testDTO.getId());
    }

    @Test
    public void testCreateNewTest_AlredyExist(){
        TestDTO testDTO = new TestDTO(1, "Test1");
        com.example.ps_project.entities.Test test = new com.example.ps_project.entities.Test();
        test.setName(testDTO.getName());

        when(testRepository.findById(testDTO.getId())).thenReturn(Optional.of(test));

        ResponseEntity<DTO> response = testService.createNewTest(testDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("This test alredy exist", ((ErrorDTO) response.getBody()).getErrorMessage());
        verify(testRepository, times(1)).findById(testDTO.getId());
    }

    @Test
    public void testDeleteTest_Success(){
        int idTest = 1;
        Optional<com.example.ps_project.entities.Test> mockTest = Optional.of(new com.example.ps_project.entities.Test(idTest, "Test1", null));

        when(testRepository.findById(idTest)).thenReturn(mockTest);
        when(testRepository.existsById(idTest)).thenReturn(false);

        ResponseEntity<DTO> response = testService.deleteTest(idTest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The test was deleted!", ((SuccessMessage) response.getBody()).getSuccessMessage());
        verify(testRepository, times(1)).findById(idTest);
    }

    @Test
    public void testDeleteTest_Failed(){
        int idTest = 1;
        Optional<com.example.ps_project.entities.Test> mockTest = Optional.of(new com.example.ps_project.entities.Test(idTest, "Test1", null));

        when(testRepository.findById(idTest)).thenReturn(mockTest);
        when(testRepository.existsById(idTest)).thenReturn(true);

        ResponseEntity<DTO> response = testService.deleteTest(idTest);

        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());
        assertEquals("The test has not been deleted!", ((ErrorDTO) response.getBody()).getErrorMessage());
        verify(testRepository, times(1)).findById(idTest);
    }

    @Test
    public void testDeleteTest_NoTest(){
        int idTest = 1;
        Optional<com.example.ps_project.entities.Test> mockTest = Optional.of(new com.example.ps_project.entities.Test(idTest, "Test1", null));

        when(testRepository.findById(idTest)).thenReturn(Optional.empty());

        ResponseEntity<DTO> response = testService.deleteTest(idTest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The test does't exit!", ((ErrorDTO) response.getBody()).getErrorMessage());
        verify(testRepository, times(1)).findById(idTest);
    }

    @Test
    public void testAddNewQuestion_Success(){
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setName("Question1");
        questionDTO.setId(1);
        questionDTO.setQuestionText("Ce este un endpoint?");
        questionDTO.setAnswerText("Blalalala");

        int testId = 1;

        Question question = new Question();
        question.setName(questionDTO.getName());
        question.setQuestionText(questionDTO.getQuestionText());
        question.setAnswer(questionDTO.getAnswerText());
        question.setId(question.getId());

        com.example.ps_project.entities.Test test = new com.example.ps_project.entities.Test();
        test.setId(testId);
        test.setName("Test1");

        QuestionsInTest questionsInTest = new QuestionsInTest();
        questionsInTest.setTest(test);
        questionsInTest.setQuestion(question);

        when(questionRespository.findById(questionDTO.getId())).thenReturn(Optional.empty());
        when(questionRespository.save(question)).thenReturn(question);
        when(questionRespository.existsById(questionRespository.save(question).getId())).thenReturn(false);
        when((testRepository.findById(testId))).thenReturn(Optional.of(test));
        when(questionsInTestsRepository.findById(questionsInTest.getId())).thenReturn(Optional.of(questionsInTest));
        when(questionsInTestsRepository.save(questionsInTest)).thenReturn(questionsInTest);

        ResponseEntity<DTO> response = testService.addNewQuestion(questionDTO, testId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(SuccessMessage.class, response.getBody().getClass());
        assertEquals("Question saved!", ((SuccessMessage) response.getBody()).getSuccessMessage());
    }

    @Test
    public void testAddNewQuestion_Failed(){
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setName("Question1");
        questionDTO.setId(1);
        questionDTO.setQuestionText("Ce este un endpoint?");
        questionDTO.setAnswerText("Blalalala");

        int testId = 1;

        Question question = new Question();
        question.setName(questionDTO.getName());
        question.setQuestionText(questionDTO.getQuestionText());
        question.setAnswer(questionDTO.getAnswerText());
        question.setId(question.getId());

        com.example.ps_project.entities.Test test = new com.example.ps_project.entities.Test();
        test.setId(testId);
        test.setName("Test1");

        QuestionsInTest questionsInTest = new QuestionsInTest();
        questionsInTest.setTest(test);
        questionsInTest.setQuestion(question);

        when(questionRespository.findById(questionDTO.getId())).thenReturn(Optional.empty());
        when(questionRespository.save(question)).thenReturn(question);
        when(questionRespository.existsById(questionRespository.save(question).getId())).thenReturn(false);
        when((testRepository.findById(testId))).thenReturn(Optional.of(test));
        when(questionsInTestsRepository.findById(questionsInTest.getId())).thenReturn(Optional.empty());
        when(questionsInTestsRepository.save(questionsInTest)).thenReturn(questionsInTest);

        ResponseEntity<DTO> response = testService.addNewQuestion(questionDTO, testId);
        assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());
        assertEquals(ErrorDTO.class, response.getBody().getClass());
        assertEquals("The question has not been added in the test!", ((ErrorDTO) response.getBody()).getErrorMessage());
    }

    @Test
    public void testAddNewQuestion_NoTest(){
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setName("Question1");
        questionDTO.setId(1);
        questionDTO.setQuestionText("Ce este un endpoint?");
        questionDTO.setAnswerText("Blalalala");

        int testId = 1;

        Question question = new Question();
        question.setName(questionDTO.getName());
        question.setQuestionText(questionDTO.getQuestionText());
        question.setAnswer(questionDTO.getAnswerText());
        question.setId(question.getId());

        com.example.ps_project.entities.Test test = new com.example.ps_project.entities.Test();
        test.setId(testId);
        test.setName("Test1");

        when(questionRespository.findById(questionDTO.getId())).thenReturn(Optional.empty());
        when(questionRespository.save(question)).thenReturn(question);
        when(questionRespository.existsById(questionRespository.save(question).getId())).thenReturn(false);
        when((testRepository.findById(testId))).thenReturn(Optional.empty());

        ResponseEntity<DTO> response = testService.addNewQuestion(questionDTO, testId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ErrorDTO.class, response.getBody().getClass());
        assertEquals("The test doesn't exist!", ((ErrorDTO) response.getBody()).getErrorMessage());
    }

    @Test
    public void testAddNewQuestion_ExistingQuestion(){
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setName("Question1");
        questionDTO.setId(1);
        questionDTO.setQuestionText("Ce este un endpoint?");
        questionDTO.setAnswerText("Blalalala");

        int testId = 1;

        Question question = new Question();
        question.setName(questionDTO.getName());
        question.setQuestionText(questionDTO.getQuestionText());
        question.setAnswer(questionDTO.getAnswerText());
        question.setId(question.getId());

        when(questionRespository.findById(questionDTO.getId())).thenReturn(Optional.of(question));

        ResponseEntity<DTO> response = testService.addNewQuestion(questionDTO, testId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ErrorDTO.class, response.getBody().getClass());
        assertEquals("The question already exist!", ((ErrorDTO) response.getBody()).getErrorMessage());
    }
}
