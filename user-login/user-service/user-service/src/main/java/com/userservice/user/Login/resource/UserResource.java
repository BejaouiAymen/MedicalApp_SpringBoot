package com.userservice.user.Login.resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.userservice.user.Login.model.User;
import com.userservice.user.Login.service.UserService;
import com.userservice.user.Login.utility.JWTUtility;

@RestController
@RequestMapping("/login")
public class UserResource {
    
    @Autowired
    private JWTUtility jwtUtility;
    private final UserService userService;
    private Cookie cookie;
    public UserResource(UserService userService) {
        this.userService = userService;
    }

   
    @GetMapping("/auth/{email}")
    public ResponseEntity<?> login(@PathVariable("email") String email,HttpServletRequest request,HttpServletResponse response) {
        String password0;
        String email0;

        email0=StringUtils.substringBefore(email, " ");

        password0=email.substring(email.lastIndexOf(" ") + 1);
       // return email0;

        User newUser = userService.findUserByEmail(email0); 
 
        if(password0.equals(newUser.getpassword())){
            final String token=jwtUtility.generateToken(newUser);
            cookie = new Cookie("jwt", token);
            cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
            cookie.setSecure(false);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
           return new ResponseEntity<>(newUser, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(newUser, HttpStatus.CONFLICT);
        }  
    }
    
     @PostMapping("/add")   
    public ResponseEntity<User> addUser(HttpServletResponse   response, @RequestBody User user) {
        User newUser = userService.findUserByEmail(user.getEmail());  
       
        if(user.getpassword().equals(newUser.getpassword())){
            final String token=jwtUtility.generateToken(newUser);
            cookie = new Cookie("jwt", token);
            cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
            cookie.setSecure(false);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(newUser, HttpStatus.CONFLICT);
        }    
    }
    @GetMapping("/safe")
    public ResponseEntity<?> safe(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        User user=new User();

        if (cookies != null) {
             if(Arrays.stream(cookies)
             .map(c ->c.getName().equalsIgnoreCase("jwt")) != null){
                String token="";
                for (Cookie ck : cookies){
                    if ("jwt".equals(ck.getName())) {
                            token=ck.getValue();
                            user.setEmail(jwtUtility.getUsernameFromToken(token));
                    }
                }
                return new ResponseEntity<>(user,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);

            }
        }else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    @GetMapping("/logout")   
    public ResponseEntity<User> logout(HttpServletResponse   response) {
            cookie = new Cookie("jwt", null);
            cookie.setMaxAge(0); // expires in 7 days
            cookie.setSecure(false);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            
            return new ResponseEntity<>( HttpStatus.OK);
        }    
    
            
        
    
}