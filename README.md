# **Stay On Course**
> Proiectare Software
> Student: Tatar Adela Georgiana
> Facultatea: Automatica si Calculatoare

# **Ce face proiectul?**
> Scopul acestui proiect este crearea unui mediu de invatare, care ofera un set de cursuri din diferite domenii. Fiecare curs este format dintr-o parte toretica (mai multe lectii), care trebuie parcursa si un test de cunostinte, din cele prezentate in lectii, care stabileste daca studentul a absolvit sau nu cursul. 
> La fiecare curs exista sectiune de intrebari, unde studentul poate pune intrebari despre problemele pe care le intampina atunci cand parcurge un anumit curs, iar unul dintre profesorii inscrisi pe site va putea sa ii raspunda nelamuririlor. 
> Fiecarea student poate vedea cat de mult a parcurs dintr-un anumit curs si ce cursuri sunt necesare a fi parcurse pentru a incepe un alt curs nou (unele cursuri necestita cursuri pregatitoare pentru a putea fi parcurse). 

# **Ce functionalitati expune proiectul?**
> Permite inregistrarea/conecta pe site a mai multor tipuri de useri: student, profesor sau admin.
> ##### Adminul:
> * Poate adauga cursuri sau materiale pe site.
> * Poate sterge cursuri sau materiale de pe site.
> * Poate sterge useri, intrebari sau raspunsuri.
> ##### Studentul:
> * Se poate inrola la diferite cursuri in functie de cerintele acestora.
> * Poate pune intrebari atunci cand are nelamuriri.
> * La finalul unui curs trebuie sa treaca un test de evaluare din cunostintele dobandite. 
> * Evalueaza calitatea cursurilor, din punctul lui de vedere.
> ##### Profesorul:
> * Ofera raspunsuri la intrebarile studentilor.
> * Stabileste punctajul obtinut de student in urma sustinerii unui test de evaluare.

# **Cum arata baza de date?**
> Baza de date contine tabelele corespunzatoare entitatilor din proiect:
> * User : id, firstName, lastName, age, email, password, role.
> * Course : id, name.
> * Lecture : id, name, continut.
> * LecturesInCourses : id, idCourse, idLecture.
> * Question : id, name, question_text, answer.
> * Test : id, name.
> * QuestionsInTests: id, idTest, id Question. 
> * Role: id, name (poate lua valori dintr-un enum definit in proiect -- RoleEnum).
> * Enrollments: id, idUser, idCouse.
> * UsersRole: id, idUser, idRole.

# **Diagrama bazei de date**
[BazaDate](https://www.planttext.com/api/plantuml/png/bLJBJiCm4BpxAwmSaNBWMd6eL7geX90evGEiTaajx7NmEWgeyE_OURjfI7lSySnuToVEafomhj2ACVgGoAqXOCn9fu1lGGad3T9HviHJlbA2Y3zAaLcX6TjNvSeKbk34E4ytw203o-KXLA_EIimu2WtijsK_mTuLzchwIm4KEk0pWhxyR1Gx-783M22LO7lZhV7pNJ0D-1eBVTP71vzO3-xyJNEDTUGtykVJv7TfoJt7etXT1xKYaGqPkNSkZHqQWHPInTVIrB1z5MWn4ZIZs6Y3TTpqlrrDYh76OfojDmsRdB4Cb1XfzRtZ6grHIWFEcOt7RJhsLjMo7dxIWoanxO9Vy0qUmKedSE_VHvLcSf_mkxJ8KvveWTz9l-o5VnKwDoIh_Yh7QycixcJC7qKGwvhZctMiUs8ZvNb0iR3w-c_KJ0utI2vMN5xq99qj0G_-P_O7)

# **EndPoints - Descriere**
> UserService
> * public List<User> getUsers(): Resturneaza o lista cu toti utilizatorii din baza de date.
> * public ResponseEntity<DTO> registerNewUser(UserDTO userDTO): Inregistrarea unui nou user pe site. Se cauta user-ul dupa email (pentru a nu fi deja inregistrat), daca nu este gasit si datele introduse respecta conditiile inregistraea se realizeaza cu succes, aftel se va afisa un mesaj de eraoare.
> * public ResponseEntity<DTO> deleteUser(int id): Se realizeaza stergerea unui anumit user, identificat prin id-ul acestuia. Daca acesta este gasit in baza de date, se sterge si se returneaza un mesaj de succes, altfet, daca nu este gasit, se returneaza un mesaj de  eroare.
> * public ResponseEntity<DTO> changePassword(UpdateUserDTO userToUpdate): Se cauta user-ul dupa email-ul acestuia, daca este gasit si noua parola respecta conditiile impuse, parola va fi actualizata, altfel se va returna un mesaj de eroare.
> * public ResponseEntity<DTO> loginUser(LoginUserDTO loginUserDTO): Autentificarea utilizatorului pe site are loc doar daca datele intreoduse sunt corecte, altfel se afiseaza un mesaj de eroare.

> Course Service
> * public List<Course> getAllCourses(): Resturneaza o lista cu toate cursurile din baza de date
> * public ResponseEntity<DTO> addNewCourse(CourseDTO courseDTO): Se realizeaza adaugarea unui nou curs in baza de date. Daca cursul nu este gasit (se cauta dupa id), acesta se adauga cu succes, altfel se returneaza un mesaj de eroare.
> * public ResponseEntity<DTO> deleteCourse(int id): Stergerea unui curs dupa id. Se cauta cursul dupa id, daca este gasit acesta va fi sters si se afiseaza un mesaj de succes, in caz contrar, se afiseaza un mesaj de eroare.
> * public ResponseEntity<DTO> addNewLecture(LectureDTO lectureDTO, Integer idCourse): Se verifica daca lectia exista, in caz afirmativ se returneaza un mesaj de eroare, iar in caz negativ se continua procesul cu cautarea cursului dupa idCourse. In cazul in care cursul nu exista vom primi un mesaj de eroare, altfel se adauga lectia atat in tabela Lecture cat si in tabela intermediara LectureInCourses si se va primi un mesaj de succes. 

> LectureService
> * public ResponseEntity<DTO> deleteLecture(Integer idLecture): Se realizeaza stergerea unei anumite lectii in functie de id-ul acesteia. Prima data se cauta lectia, daca nu se gaseste se returneaza un mesaj de eroare, iar in cazul in care se gaseste se sterge atat din tabela Lecture, cat si toate aparitiile din tabela LectureInCourses.

> EnrolmentsService:
> * public ResponseEntity<DTO> getCoursesByUser(UsersCoursesDTO usersCoursesDTO): Se returneaza o lista cu toate cursurile unui anumit utilizator, daca utilizatorul exista (se cauta dupa id), in caz contrar se returneaza un mesaj de eroare.
> * public ResponseEntity<DTO> enrolStudentToCourse(EnrolStudentDTO enrolStudentDTO): Se realizeaza inrolarea unui urilizator la un anumit curs. Se cauta atat cursul cat si utilizatorul, in cazul in care nu sunt gasiti se afiseaza un mesaj de eraoare, altfel se realizeaza irolarea acestuia la cursul dorit.  

> TestService:
> * public ResponseEntity<DTO> createNewTest(TestDTO testDTO): Se realizeaza adaugarea unui nou test in baza de date. Daca testul nu este gasit (se cauta dupa id), acesta se adauga cu succes, altfel se returneaza un mesaj de eroare.
> * public ResponseEntity<DTO> deleteTest(Integer idTest): Stergerea unui test dupa id. Se cauta testul dupa id, daca este gasit acesta va fi sters si se afiseaza un mesaj de succes, in caz contrar, se afiseaza un mesaj de eroare.
> * public ResponseEntity<DTO> addNewQuestion(QuestionDTO questionDTO, Integer idTest): Se verifica daca intrebarea exista, in caz afirmativ se returneaza un mesaj de eroare, iar in caz negativ se continua procesul cu cautarea testului dupa idCourse. In cazul in care testul nu exista vom primi un mesaj de eroare, altfel se adauga intrebarea atat in tabela Question cat si in tabela intermediara QuestionsInCourses si se va primi un mesaj de succes.

# **Teste - JavaDoc**
> UserServiceTest: Teste pentru toate metodele (pentru fiecare caz posibil) din UserService
> * public void getUsersTest() : Testeaza metoda getUsers din UserService.
> * public void registerUserTest_NewUser() : Testeaza metoda registerUser din UserService in cazul in care inregisrarea unui nou user se realizeaza cu succes.
> * public void registerUser_ExistingUser() : Testeaza metoda registerUser din UserService in cazul in care inregisrarea unui nou user NU se realizeaza cu succes deoarece userul exista deja.
> * public void deleteUserTest_Success() : Testeaza metoda deleteUser din UserService in cazul in care stergerea unui user se realizeaza cu succes.
> * public void deleteUserTest_NotFound() : Testeaza metoda deleteUser din UserService in cazul in care userul nu exista, deci stergerea nu se poate realiza.
> * public void deleteUserTest_NotDeleted() : Testeaza metoda deleteUser din UserService in cazul in care stergerea unui user NU se realizeaza cu succes.
> * public void loginUserTest_Success() : Testeaza metoda loginUser din UserService in cazul in care stergerea unui user se realizeaza cu succes.
> * public void loginUserTest_WrongEmail() : Testeaza metoda loginUser din UserService in cazul in care email-ul este gresit.
> * public void loginUserTest_WrongPassword() : Testeaza metoda loginUser din UserService in cazul in care parola este gresita.
> * public void changePasswordTest_Success() : Testeaza metoda changePassword din UserService in cazul in care schimbarea parolei se realizeaza cu Succes.
> * public void changePasswordTest_WrongUser() : Testeaza metoda changePassword din UserService in cazul in care userul nu exista.
> * public void changePasswordTest_BadPassword() : Testeaza metoda changePassword din UserService in cazul in care parola este prea scurta si nu se poate schimba.

> CourseServiceTest: Teste pentru toate metodele (pentru fiecare caz posibil) din CourseService
> * public void getCoursesTest(): Testeaza metoda getCourses din CourseService.
> * public void testAddNewCourse_Success(): Testeaza metoda addNewCourse din CourseService in cazul in care adaugarea unui nou curs se realizeaza cu Succes.
> * public void testAddNewCourse_ExistingCourse(): Testeaza metoda addNewCourse din CourseService in cazul in care cursul deja exista, asta ducand la un BAD_REQUEST si un mesaj de eroare.
> * public void testAddNewCourse_NotAdded(): Testeaza metoda addNewCourse din CourseService in cazul in care adaugarea cursului nu se realizeaza cu succes, asta ducand la un mesaj de eroare.
> * public void testDeleteCourse_Success(): Testeaza metoda deleteCourse din CourseService in cazul in care stergerea unui curs se realizeaza cu succes.
> * public void testDeleteCourse_NoCourse(): Testeaza metoda deleteCourse din CourseService in cazul in care nu exista cursul pe care dorim sa il stergem.
> * public void testDeleteCourse_NotDeleted(): Testeaza metoda deleteCourse din CourseService in cazul in care stergerea nu s-a ealizat cu succes.
> * public void addNewLectureTest_Success(): Testeaza metoda addNewLecture din CourseService in cazul in care adaugarea unui lectii noi se realizeaza cu succes.
> * public void addNewLectureTest_ExistingLecture(): Testeaza metoda addNewLecture din CourseService in cazul in care lectia deja exista, lectia nefiind adaugata.
> * public void addNewLectureTest_NoCourse(): Testeaza metoda addNewLecture din CourseService in cazul in care cursul nu exista, deci adaugarea lectiei nu se realizeza.
> * public void addNewLectureTest_LectureNotAdded(): Testeaza metoda addNewLecture din CourseService in cazul in care adaugarea unui lectii noi NU se realizeaza cu succes.

> LectureServiceTest: Testeaza toate metodele (in toate cazurile posibile) din LectureService
> * public void deleteLectureTest_Success(): Testeaza metoda deleteLecture din LectureService in cazul in care stergerea unei lectii se realizeaza cu succes.
> * public void deleteLectureTest_Failed(): Testeaza metoda deleteLecture din LectureService in cazul in care stergerea unei lectii NU se realizeaza cu succes.
> * public void deleteLectureTest_NoLecture(): Testeaza metoda deleteLecture din LectureService in cazul in care lectia pe care dorim sa o stergem nu exista, deci stergerea nu va avea loc.

> EnrolmentsServiceTest: Testeaza toate metodele (in toate cazurile posibile) din EnrolmentService
> * public void enrolStudentToCourseTest_Success(): Testeaza metoda enrolStudentToCourse din EnrolmentsService in cazul in care inrolarea unui user la un curs se realizeaza cu succes.
> * public void enrolStudentToCourseTest_NoUser(): Testeaza metoda enrolStudentToCourse din EnrolmentsService in cazul in care userul nu exista, deci inrolarea nu se poate realiza.
> * public void enrolStudentToCourseTest_NoCourse(): Testeaza metoda enrolStudentToCourse din EnrolmentsService in cazul in care cursul nu exista, deci inrolarea nu se poate realiza.

> TestServiceTest: Testeaza toate metodele (in toate cazurile posibile) din TestService
> * public void testCreateNewTest_Success(): Testeaza metoda testCreateNew din TestService in cazul in care crearea unui test se realizeaza cu Succes.
> * public void testCreateNewTest_Failed(): Testeaza metoda testCreateNew din TestService in cazul in care crearea unui test NU se realizeaza cu Succes.
> *  public void testCreateNewTest_AlredyExist(): Testeaza metoda testCreateNew din TestService in cazul in care testul deja exista.
> * public void testDeleteTest_Success(): Testeaza metoda deleteTest din TestService in cazul in care stergerea unui test se realizeaza cu Succes.
> * public void testDeleteTest_Failed(): Testeaza metoda deleteTest din TestService in cazul in care stergerea unui test NU se realizeaza cu Succes.
> * public void testDeleteTest_NoTest(): Testeaza metoda deleteTest din TestService in cazul in care testul nu exista.
> * public void testAddNewQuestion_Success(): Testeaza metoda addNewQuestion din TestService in cazul in care adaugarea unei intrebari noi se realizeaza cu Succes.
> * public void testAddNewQuestion_Failed(): Testeaza metoda addNewQuestion din TestService in cazul in care adaugarea unei intrebari noi NU se realizeaza cu Succes.
> * public void testAddNewQuestion_NoTest(): Testeaza metoda addNewQuestion din TestService in cazul in care testul nu exista.
> * public void testAddNewQuestion_ExistingQuestion(): Testeaza metoda addNewQuestion din TestService in cazul in care intrebarea exista.





















                             