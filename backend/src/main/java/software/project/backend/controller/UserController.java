package software.project.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.project.backend.Model.Book;
import software.project.backend.sercuirty.Singelton;
import software.project.backend.service.adminService;
import software.project.backend.service.userService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    private adminService service=new adminService();
    private Singelton trackingSystem=Singelton.getInstance();
    @PostMapping("search/{ID}")
    public ResponseEntity<List<Book>> search(@PathVariable("ID") String sessionID,
                                             @RequestBody String temp){
        System.out.println(temp);
        List<Book> books=service.search(sessionID,temp);
        if(books!=null) return  new ResponseEntity<>(books, HttpStatus.ACCEPTED);
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

}
