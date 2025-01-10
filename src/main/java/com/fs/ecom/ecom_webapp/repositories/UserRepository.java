package com.fs.ecom.ecom_webapp.repositories;

import com.fs.ecom.ecom_webapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> getReferenceByEmail(String email);

    Optional<User> findByUserName(String userName);

}
