package junit;

import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.*;
import org.springframework.boot.convert.DataSizeUnit;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JunitDemoTest {

    JunitDemo junitDemo;

    @BeforeEach
    void setupBeforeEach(){
        junitDemo = new JunitDemo();
//        System.out.println( " @BeforeEach ececutes before the execution of each test method");
    }

//    @AfterEach
//    void tearDownAfterEach(){
//        System.out.println("Running @After each");
//        System.out.println("");
//    }

    @Test
    @DisplayName("이름을 지정할수있다.")
    void testEqulas(){
//        System.out.println("running test ::: testEquals");

        assertEquals(6,junitDemo.add(3,3),"TEST init 3 + 3");
        assertNotEquals(6,junitDemo.add(4,3),"TEST init 3 + 3");
    }

    @Test
    @DisplayName("same , not same 테스트 케이스")
    void testSameAndNotSame(){
        String str = "luv2code";

        assertSame(junitDemo.getAcademy(),junitDemo.getAcademyDuplicate(),"같아야한다.");
        assertNotSame(str,junitDemo.getAcademy(),"같지 않아야한다.");
    }
    @Test
    @DisplayName("true , false 테스트 케이스")
    void testTrueOrFalse(){
        int gradeOne = 10;
        int gradeTwo = 5;

        assertTrue(junitDemo.isGreater(gradeOne,gradeTwo),"true가 retune되어야한다.");
        assertFalse(junitDemo.isGreater(gradeTwo,gradeOne),"False가 retune되어야한다.");

    }

    @Test
    @DisplayName("Array Equals 테스트")
    void testArrayEquals(){
        // expected :::::
        String[] testList = {"A","B","C",};

        assertArrayEquals(testList,junitDemo.listTestArray,"두게의 배열의 값은 같아야한다.");
    }

    @Test
    @DisplayName("Iterable 테스트")
    @Order(1)
    void testIterablelTest(){
        //expected ::
        ArrayList<String> list = new ArrayList<>(Arrays.asList("luv", "2", "code"));

        assertIterableEquals(list, junitDemo.list() ,"리스트의 두 타입과 순서는 같아야한다.");
    }

    @Test
    @DisplayName("linees match 테스트")
    void testLinesMatch(){
        ArrayList<String> list = new ArrayList<>(Arrays.asList("luv", "2", "code"));
        assertLinesMatch(list,junitDemo.list(),"두 문자열이 같은지?");
    }

    @Test
    @DisplayName("throws 테스트")
    void throwsExciton(){
        assertThrows(Exception.class, () -> { junitDemo.throwException(-1); } ,"excetion 의 내용은 같아야한다 ?");
        assertDoesNotThrow(() -> { junitDemo.throwException(2); } ,"excetion 의 내용은 같아야한다 ?");
    }

    @Test
    @DisplayName("time out 테스트 , 사용잘안할듯?")
    void timeoutTest(){
        assertTimeoutPreemptively(Duration.ofSeconds(3), ()->{junitDemo.checkTimeout();}
        ,"타입 아웃 체크");

    }




}