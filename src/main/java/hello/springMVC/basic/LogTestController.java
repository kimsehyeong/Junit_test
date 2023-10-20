package hello.springMVC.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogTestController {
//    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest(){

        String name = "spring";
        System.out.println("name = " + name);
        log.info("info log = {}", name);
        log.info("info log={}", name);

        return "ok";
    }
}
