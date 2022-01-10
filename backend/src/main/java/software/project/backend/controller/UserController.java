package software.project.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.project.backend.Model.Book;
import software.project.backend.sercuirty.Singelton;
import software.project.backend.service.userService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    private userService service=new userService();
    private Singelton trackingSystem=Singelton.getInstance();
    @PostMapping("search/{ID}")
    public ResponseEntity<List<Book>> search(@PathVariable("ID") String sessionID,
                                             @RequestBody String temp){
        System.out.println(temp);
        List<Book> books=service.search(sessionID,temp);
        if(books!=null) return  new ResponseEntity<>(books, HttpStatus.ACCEPTED);
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
    @PostMapping("addToCart/{ID}")
    public ResponseEntity<Boolean> addToCart(@PathVariable("ID") String sessionID,
                                             @RequestBody String temp){
        System.out.println(temp);
        if(service.addToCart(sessionID,temp))  return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
    }
    @DeleteMapping("deleteFromCart/{ID}/{ISBN}")
    public ResponseEntity<Boolean> deleteFromCart(@PathVariable("ID") String sessionID,
                                                  @PathVariable("ISBN") String ISBN){
        if(service.deleteFromCart(sessionID,ISBN))  return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
    }
    @DeleteMapping("deleteCart/{ID}")
    public ResponseEntity<Boolean> deleteCart(@PathVariable("ID") String sessionID){
        if(service.deleteCart(sessionID))  return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
    }
    @GetMapping("/getCart/{ID}")
    public ResponseEntity<List<Book>> getCart(@PathVariable("ID") String sessionID){
        List<Book> books=service.getCart(sessionID);
        if(books!=null) return  new ResponseEntity<>(books, HttpStatus.ACCEPTED);
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }
    @GetMapping("/checkCart/{ID}/{ISBN}")
    public ResponseEntity<Boolean> checkCart(@PathVariable("ID") String sessionID,
                                             @PathVariable("ISBN") String ISBN){
        if(service.checkCart(sessionID,ISBN)) return  new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
    }

}
