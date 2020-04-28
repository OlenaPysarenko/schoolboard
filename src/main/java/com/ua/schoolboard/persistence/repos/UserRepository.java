package com.ua.schoolboard.persistence.repos;

import com.ua.schoolboard.persistence.Role;
import com.ua.schoolboard.persistence.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
   Optional<UserEntity> findByEmail(String email);

   Optional<UserEntity> findUserEntityByUserId(long userId);

   List<UserEntity> findAllByRole(Role role);

   List<UserEntity>findAll();
}