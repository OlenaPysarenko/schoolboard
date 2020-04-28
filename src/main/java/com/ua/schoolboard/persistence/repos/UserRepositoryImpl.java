package com.ua.schoolboard.persistence.repos;

import com.ua.schoolboard.persistence.Role;
import com.ua.schoolboard.persistence.mappers.UserPersistMapper;
import com.ua.schoolboard.persistence.model.UserEntity;
import com.ua.schoolboard.service.model.UserBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ua.schoolboard.exceptions.ErrorCode.*;
import static com.ua.schoolboard.exceptions.ExceptionUtil.getException;
import static com.ua.schoolboard.exceptions.ExceptionUtil.getSupplierException;

@Service
@RequiredArgsConstructor
public class UserRepositoryImpl {
    private final UserRepository userRepository;
    private final UserPersistMapper userPersistMapper;

    public void registerUser(UserBO user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw getException(USER_ALREADY_EXIST);
        }
        UserEntity newUser = userPersistMapper.toUserEntity(user);
        userRepository.save(newUser);
        //return user;
    }

    public UserBO findUserById(long userId) {
        UserEntity userEntityByUserId = userRepository.findUserEntityByUserId(userId).orElseThrow(getSupplierException(USER_NOT_FOUND));
        return userPersistMapper.toUserBO(userEntityByUserId);
    }

    public UserBO findByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(getSupplierException(INVALID_CREDENTIAL));
        return userPersistMapper.toUserBO(userEntity);
    }
    public List<UserBO> findAllByRole(Role role){
        List<UserEntity> usersByRole = userRepository.findAllByRole(role);
        return userPersistMapper.toUserBOs(usersByRole);
    }

    public UserBO updateUser(UserBO userBO) {
        UserEntity userEntity = userRepository.findByEmail(userBO.getEmail()).orElseThrow(getSupplierException(INVALID_CREDENTIAL));
        userEntity.setName(userBO.getName());
        userEntity.setEmail(userBO.getEmail());
        userEntity.setPassword(userBO.getPassword());
        userEntity.setNickname(userBO.getNickname());
        userEntity.setBirthDay(userBO.getBirthDay());
        userEntity.setPhoneNumber(userBO.getPhoneNumber());
        return userPersistMapper.toUserBO(userEntity);
    }

    public void deleteUser(long userId){
        UserEntity user = userRepository.findUserEntityByUserId(userId).orElseThrow(getSupplierException(INVALID_CREDENTIAL));
        userRepository.delete(user);
    }

    public List<UserBO> findAll() {
      return  userPersistMapper.toUserBOs(userRepository.findAll());
    }
}
