package com.demo.ecommerce.repository;

import com.demo.ecommerce.enums.Role;
import com.demo.ecommerce.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

//    @AfterEach
//    void tearDown() {
//        underTest.deleteAll();
//    }


    @Test
    void shouldReturnUser_WhenEmailExists() {
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


    @Test
    void shouldReturnNull_WhenEmailDoesNotExist() {
        // given
        String email = "test@test.com";

        // when
        Optional<User> userOptional = underTest.findByEmail(email);

        // then
        assertThat(userOptional.isPresent()).isFalse();
    }


    @Test
    void shoudlReturnUser_WhenEmailDomainExists() {
        // given
        String email = "test@gmail.com";
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
        List<User> users = underTest.searchByEmailDomain("gmail.com");

        // then
        assertThat(users.size()).isEqualTo(1);
    }
}