package com.revature.repositories;

import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Optional throws error if more than 1 result found
     */
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findById(Integer id);
}
