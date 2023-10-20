package hello.springMVC;

import hello.springMVC.models.CollegeStudent;
import hello.springMVC.models.StudentGrade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringmvcApplication.class)
class SpringmvcApplicationTests {

	private static int COUNT = 0;

	@Value("${info.app.name}")
	private String appInfo;

	@Value("${info.app.description}")
	private String appDescription;

	@Value("${info.app.version}")
	private String appVersion;

	@Value("${info.school.name}")
	private String schoolName;

	@Autowired
	CollegeStudent student;

	@Autowired
	StudentGrade studentGrade;

	@Autowired
	ApplicationContext context;

	@BeforeEach
	public void beforeEach(){
		COUNT = COUNT+1;
		System.out.println("Testing : "+ appInfo + " which is "+ appDescription+
				"  version : " + appVersion + " . Ececution of test method  "+ COUNT);
		student.setFirstname("kim");
		student.setLastname("seheyong");
		student.setEmailAddress("lego@naver.com");
		studentGrade.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0 , 85.0, 76.50,91.75)));
		student.setStudentGrades(studentGrade);
	}

	@Test
	@DisplayName("assertEquals_테스트")
	public void addGradeResultForStudentGrades(){
//		assertEquals(353.25,studentGrade.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));
		assertEquals(353.25, studentGrade.addGradeResultsForSingleClass(studentGrade.getMathGradeResults()));
	}
	@Test
	@DisplayName(" assertNotEquals_테스트")
	public void addGradeResultForStudentGradesNotEquals(){
		assertNotEquals(25,studentGrade.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));
	}
	@Test
	@DisplayName(" true 테스트:gradeOne이 gradeTwo보다 큰지?")
	void assertTrueTest(){
		assertTrue(studentGrade.isGradeGreater(90,75));
	}
	@Test
	@DisplayName("false : 테스트:gradeOne이 gradeTwo보다 큰지?")
	void assertFalseTest(){
		assertFalse(studentGrade.isGradeGreater(90,75));
	}
	@Test
	@DisplayName("null인지아닌지 테스트")
	void assertNotNullTest(){
		assertNotNull(studentGrade.checkNull(studentGrade.getMathGradeResults()));
	}
	@Test
	@DisplayName("초기 before값이아닌 추가된거 테스트")
	void creatStudentTwo(){
		CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
		studentTwo.setFirstname("park");
		studentTwo.setLastname("jonho");
		studentTwo.setEmailAddress("hgio@naver.com");
//		assertNotNull();

	}
	@Test
	@DisplayName("assertAll 테스트")
	void assertAllTest(){
		assertAll("Testing all assertEquals",
				()-> assertEquals(353.25,studentGrade.addGradeResultsForSingleClass(studentGrade.getMathGradeResults())),
				() ->assertEquals(88.31,studentGrade.findGradePointAverage(studentGrade.getMathGradeResults()))
				);
	}
}
