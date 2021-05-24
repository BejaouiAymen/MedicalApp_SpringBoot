package com.pfa.user.resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import com.google.common.io.Files;
import com.pfa.user.model.User;
import com.pfa.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserResource {
    public static String uploadDir=System.getProperty("user.dir")+ "/Uploads";
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers () {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUserById (@PathVariable("id") Long id) {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user ) {
        /*
        @RequestParam("files") MultipartFile file
        StringBuilder fileNames = new StringBuilder();
        
        Path fileNameAndPath = Paths.get(uploadDir, file.getOriginalFilename());
		  fileNames.append(file.getOriginalFilename()+" ");
		  try {
              Files.write(file.getBytes(), file.);
		} catch (IOException e) {
			e.printStackTrace();
		}
        */
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updateUser = userService.updateUser(user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}