package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

    @GetMapping
    public String user(){
        return "get users";
    }

    @PostMapping
    public String addUser(){
        return "post user";
    }
    @GetMapping
    public String findUser(@PathVariable String userId){
        return "get userID" + userId;
    }
    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId){
        return "update userID" + userId;
    }
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId){
        return "delete userID" + userId;
    }
}
