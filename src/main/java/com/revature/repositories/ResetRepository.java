package com.revature.repositories;

import com.revature.models.ResetCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetRepository extends JpaRepository<ResetCode, String> {

}
