package com.mokshad.SpringBootWithMysql.repository;

import com.mokshad.SpringBootWithMysql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
