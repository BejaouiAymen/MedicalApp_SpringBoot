package com.userservice.user.Login.resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import com.userservice.user.Login.model.User;
import com.userservice.user.Login.service.UserService;

@RestController
@RequestMapping("/login")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

   
    @GetMapping("/auth/{email}")
    public ResponseEntity<User> login(@PathVariable("email") String email) {
        String password0;
        String email0;

        email0=StringUtils.substringBefore(email, " ");

        password0=email.substring(email.lastIndexOf(" ") + 1);
       // return email0;
        User newUser = userService.findUserByEmail(email0);  
        if(password0.equals(newUser.getpassword())){
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(newUser, HttpStatus.CONFLICT);
        }
     
       // return new ResponseEntity<>(newUser, HttpStatus.OK);
    }
    
}