package com.revature.scheduler.daos;

import com.revature.scheduler.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Integer> {
}