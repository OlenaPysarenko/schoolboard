package com.ua.schoolboard.persistence.repos;


import com.ua.schoolboard.persistence.model.UserEntity;
import org.springframework.data.repository.CrudRepository;



public interface UserManagementRepo extends CrudRepository<UserEntity, Long> {
    //List<UserEntity> assignUsersToRates();

//   void assignUserToRates(UserEntity user);

}