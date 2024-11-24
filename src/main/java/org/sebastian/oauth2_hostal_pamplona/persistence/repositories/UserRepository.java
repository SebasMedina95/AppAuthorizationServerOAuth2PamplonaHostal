package org.sebastian.oauth2_hostal_pamplona.persistence.repositories;

import org.sebastian.oauth2_hostal_pamplona.persistence.entities.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("SELECT u FROM User u WHERE UPPER(u.username) = UPPER(:username)")
    Optional<User> getUserByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :username OR LOWER(u.email) = LOWER(:email)")
    Optional<User> getUserByUserNameAndEmail(String username, String email);

}
