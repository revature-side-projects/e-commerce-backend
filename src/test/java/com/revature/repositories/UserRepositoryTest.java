package com.revature.repositories;

import com.revature.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository underTest;

    @Test
    void checkIfFoundByEmailAndPassword() {

        // given
        String email = "rdub@revature.com";
        String password = "RDub123";
        User expected = new User(
                1,
                email,
                password,
                "Reyna",
                "Banks"
        );
        underTest.save(expected);

        // when
        User actual = underTest.findByEmailAndPassword(email, password).orElse(null);


        // then
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    void checkIfFoundById() {

        //given
        String email = "rdub@revature.com";
        String password = "RDub123";
        User expected = new User(1, email, password, "Reyna", "Banks");

        underTest.save(expected);

        //when
        User actual = underTest.getById(1);

        //then
        assertThat(actual).isEqualTo(expected);
    }
}