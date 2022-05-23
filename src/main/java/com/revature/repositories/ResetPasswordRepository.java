package com.revature.repositories;

import com.revature.models.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Integer> {
    ResetPassword findbyId(String id);

}
