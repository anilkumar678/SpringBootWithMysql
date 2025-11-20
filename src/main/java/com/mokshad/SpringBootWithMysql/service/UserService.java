package com.mokshad.SpringBootWithMysql.service;
import com.mokshad.SpringBootWithMysql.entity.*;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Long userId);
    List<User> getAllUsers();
    User updateUser(User user);
    void deleteUser(Long userId);
}
