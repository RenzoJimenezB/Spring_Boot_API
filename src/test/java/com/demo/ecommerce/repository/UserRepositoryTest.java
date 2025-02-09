package com.demo.ecommerce.repository;

import com.demo.ecommerce.enums.Role;
import com.demo.ecommerce.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;


    @Test
    void findByEmail() {
        // given
        String email = "test@test.com";
        User user = User.builder()
                .name("John")
                .lastName("Doe")
                .email(email)
                .password("password")
                .phone("123456789")
                .role(Role.USER)
                .build();

        underTest.save(user);

        // when
        Optional<User> userOptional = underTest.findByEmail(email);

        // then
        assertThat(userOptional.isPresent()).isTrue();
    }
}