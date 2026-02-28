package com.mokshad.SpringBootWithMysql.controller;

import com.mokshad.SpringBootWithMysql.Exception.UserNotFoundException;
import com.mokshad.SpringBootWithMysql.service.UserService;
import com.mokshad.SpringBootWithMysql.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

        /**
         * REST Controller for managing User-related operations.
         * Provides endpoints for creating, retrieving, updating, and deleting users.
         */
        @RestController
        @AllArgsConstructor
        @RequestMapping("/api/users")
        public class UserController {
            private UserService userService;

            /**
             * Endpoint to create a new user.
             *
             * @param user The user object to be created.
             * @return ResponseEntity containing the created user and HTTP status.
             */
            @PostMapping
            public ResponseEntity<User> createUser(@RequestBody User user){
                User savedUser = userService.createUser(user);
                return new ResponseEntity<>(savedUser, HttpStatus.OK);
            }

            /**
             * Endpoint to retrieve a user by their ID.
             *
             * @param userId The ID of the user to retrieve.
             * @return ResponseEntity containing the user and HTTP status.
             */
            @GetMapping("/{userId}")
            public ResponseEntity<User> getByUserId(@PathVariable Long userId){
                User getUserId = userService.getUserById(userId);
                return new ResponseEntity<>(getUserId, HttpStatus.OK);
            }

            /**
             * Endpoint to retrieve all users.
             *
             * @return ResponseEntity containing the list of all users and HTTP status.
             */
            @GetMapping("/allUsers")
            public ResponseEntity<List<User>> getAllUsers(){
                List<User> listUsers = userService.getAllUsers();
                return new ResponseEntity<>(listUsers, HttpStatus.OK);
            }

            /**
             * Endpoint to update an existing user.
             *
             * @param user The user object with updated details.
             * @return ResponseEntity containing the updated user and HTTP status.
             */
            @PutMapping("/updateUser")
            public ResponseEntity<User> updateUser(@RequestBody User user){
                User updatedUser = userService.updateUser(user);
                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
            }

            /**
             * Endpoint to delete a user by their ID.
             *
             * @param id The ID of the user to delete.
             * @return ResponseEntity containing a success message and HTTP status.
             */
            @DeleteMapping("/deleteByUserId/{id}")
            public ResponseEntity<String> deleteUserById(@PathVariable Long id){
                userService.deleteUser(id);
                return new ResponseEntity<>("User successfully deleted: " + id, HttpStatus.OK);
            }
        }
@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
 @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
    User savedUser= userService.createUser(user);
         System.out.println("userId: " + userId);
     return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<User>  getByUserId(@PathVariable Long userId){
     System.out.println("userId: " + userId);
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
