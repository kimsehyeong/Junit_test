package tdd;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FizzBuzzTest {


    // if number is divisible by 3 , print Fizz
    @Test
    @DisplayName("Disable by 3 ")
    @Order(1)
    void testForDIvisibleBy3(){
        String expected = "Fizz";
        assertEquals(expected, FizzBuzz.computer(3), "Fizz가 return 되어야한다");
    }

    // if number is divisible by 5 , print Buzz
    @Test
    @DisplayName("Disable by 5 ")
    @Order(2)
    void testForDIvisibleBy5(){
        String expected = "Buzz";
        assertEquals(expected, FizzBuzz.computer(5), "Buzz return 되어야한다");
    }

    // if number is divisible by 3 and 5 , print FizzBuzz
    @Test
    @DisplayName("Disable by 3 and 5 ")
    @Order(3)
    void testForDIvisibleBy3and5(){
        String expected = "FizzBuzz";
        assertEquals(expected, FizzBuzz.computer(30), "FizzBuzz return 되어야한다");
    }

    // if number is NOT divisible by 3 or 5 , then print he number
    @Test
    @DisplayName("Not Disable by 3 and 5 ")
    @Order(4)
    void testForNOTDIvisibleBy3and5(){
        String expected = "1";
        assertEquals(expected, FizzBuzz.computer(1), "1이 되어야한다");
    }
}