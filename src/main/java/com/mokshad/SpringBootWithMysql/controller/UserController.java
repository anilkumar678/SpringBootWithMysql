package com.mokshad.SpringBootWithMysql.controller;

import com.mokshad.SpringBootWithMysql.Exception.UserNotFoundException;
import com.mokshad.SpringBootWithMysql.service.UserService;
import com.mokshad.SpringBootWithMysql.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
 @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
    User savedUser= userService.createUser(user);
     return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<User>  getByUserId(@PathVariable Long userId){
     User getUserId=userService.getUserById(userId);
     return new ResponseEntity<>(getUserId,HttpStatus.OK);
    }
    @GetMapping("/allUsers")
    public ResponseEntity<List<User>>  getAllUsers(){
     List<User> listUsers= userService.getAllUsers();
     return new ResponseEntity<>(listUsers,HttpStatus.OK);
    }
    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user){
    User updatedUser= userService.updateUser(user);
    return  new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }
    @DeleteMapping("/deleteByUserId/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id){
     try {
         userService.deleteUser(id);
         return ResponseEntity.ok("User sucessfully deleted" + id);
     }
     catch(UserNotFoundException userException){
         System.out.println("catch block executed");
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userException.getMessage());
     }
    }

}
