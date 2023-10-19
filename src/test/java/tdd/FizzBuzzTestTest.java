package tdd;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FizzBuzzTest {

    // if number is divisible by 3 , print Fizz
    // if number is divisible by 5 , print Buzz
    // if number is divisible by 3 and 5 , print FizzBuzz
    // if number is NOT divisible by 3 or 5 , then print he number

    @Test
    @DisplayName("Disable by 3 ")
    @Order(1)
    void testForDIvisibleBy3(){
        String expected = "Fizz";
        assertEquals(expected, FizzBuzz.computer(3), "Fizz가 return 되어야한다");
    }

}