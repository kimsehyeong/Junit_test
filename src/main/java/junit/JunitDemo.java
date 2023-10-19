package junit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JunitDemo {

    private String academy = "Luv2code Academy";
    private String academyDuplicate = academy;

     private List<String> getList = Arrays.asList("luv","2","code");
     private List<String> list = new ArrayList<>(Arrays.asList("luv","2","code"));

     String[] listTestArray = {"A","B","C"};

    public int add(int a , int b){
        return a + b;
//        return  a+b+b;
    }
    public String getAcademy(){
        return academy;
    }
    public String getAcademyDuplicate(){
        return academyDuplicate;
    }
    public Boolean isGreater(int n1, int n2){
        return n1 > n2;
    }

    public List<String> list() {
        return list;
    }
    public String throwException(int a) throws Exception {
        if (a < 0) {
            throw new Exception("Value should be greater than or equal to 0");
        }
        return "Value is greater than or equal to 0";
    }

    public void checkTimeout() throws InterruptedException {
        System.out.println("I am going to sleep");
        Thread.sleep(2000);
        System.out.println("Sleeping over");
    }

}
