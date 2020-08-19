package com.ua.schoolboard.persistence.repos;

import com.ua.schoolboard.rest.model.Role;
import com.ua.schoolboard.persistence.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findUserEntityByUserId(long userId);

    List<UserEntity> findAllByRole(Role role);

   // Optional<UserEntity> findUsersByLanguages(Language language);

    List<UserEntity> findAll();

   Optional<UserEntity> getByNameAndSurname(String name, String surname);

    //void assignUserToRates(UserEntity user);

}
