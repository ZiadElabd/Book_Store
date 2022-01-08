package software.project.backend.controller;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.project.backend.Model.User;
import software.project.backend.service.AuthenticationService;
import software.project.backend.service.userService;
@RestController
@CrossOrigin
public class AuthenticationController {
    AuthenticationService service=new AuthenticationService();
    @PostMapping("/signup")
    public ResponseEntity<Boolean> signupController(@RequestBody String temp){
        System.out.println(temp);
        if (service.signUp(temp)) return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
    }


    @GetMapping("/checkUsername/{userName}")
    public ResponseEntity<Boolean> checkUsernameController(@PathVariable("userName") String temp){
        System.out.println(temp);
        if(service.checkUserName(temp)) return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
    }
    @PostMapping("/signin")
    public ResponseEntity<String> signinController(@RequestBody String dataSent){
        System.out.println(dataSent);
        JSONObject user =service.signIn(dataSent);
        if (user!=null)  return new ResponseEntity<>(user.toString(), HttpStatus.OK);
        System.out.println(user.toString());
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
