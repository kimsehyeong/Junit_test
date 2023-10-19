package hello.springmvc.basic.requst;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class RequestParamController {


    @RequestMapping("/request-param-v1")
    public void reqeustParamV1(HttpServletRequest request, HttpServletResponse response){

        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username = {}");

    }
}
