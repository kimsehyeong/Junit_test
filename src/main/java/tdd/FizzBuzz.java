package tdd;

public class FizzBuzz {

    // if number is divisible by 3 , print Fizz
    // if number is divisible by 5 , print Buzz
    // if number is divisible by 3 and 5 , print FizzBuzz
    // if number is NOT divisible by 3 or 5 , then print he number

    public static String computer(int i) {
//        if(i == 3){
//            return "Fizz";
//        }

        if ((i % 3 == 0) && (i % 5 == 0)){
            return "FizzBuzz";
        }else if(i % 3  == 0){
            return "Fizz";
        }else if ( i % 5 == 0){
            return "Buzz";
        }else {
            return "1";
        }
    }
}
