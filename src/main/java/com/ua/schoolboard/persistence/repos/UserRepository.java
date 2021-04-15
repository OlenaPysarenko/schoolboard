package com.ua.schoolboard.persistence.repos;

import com.ua.schoolboard.exceptions.CustomException;
import com.ua.schoolboard.persistence.mappers.UserPersistMapper;
import com.ua.schoolboard.persistence.model.UserEntity;
import com.ua.schoolboard.rest.model.Role;
import com.ua.schoolboard.service.model.UserBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ua.schoolboard.exceptions.ErrorCode.*;
import static com.ua.schoolboard.exceptions.ExceptionUtil.getException;
import static com.ua.schoolboard.exceptions.ExceptionUtil.getSupplierException;

@Service
@RequiredArgsConstructor
public class UserRepository {
    private final UserRepo userRepository;
    private final UserPersistMapper userPersistMapper;

    public void registerUser(UserBO user) throws CustomException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw getException(USER_ALREADY_EXISTS);
        }
        UserEntity newUser = userPersistMapper.toUserEntity(user);
        userRepository.save(newUser);

    }

    public UserBO findUserById(long userId) throws CustomException {
        UserEntity userEntityByUserId = userRepository.findUserEntityByUserId(userId).orElseThrow(getSupplierException(USER_NOT_FOUND));
        return userPersistMapper.toUserBO(userEntityByUserId);
    }

    public UserBO findByEmail(String email) throws CustomException {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(getSupplierException(INVALID_CREDENTIALS));
        return userPersistMapper.toUserBO(userEntity);
    }

    public List<UserBO> findAllByRole(Role role) {
        List<UserEntity> usersByRole = userRepository.findAllByRole(role);
        return userPersistMapper.toUserBOs(usersByRole);
    }
    public UserBO getStudentByNameAndSurname(String name, String surname) throws CustomException {
        UserEntity userEntity = userRepository.getByNameAndSurname(name, surname).orElseThrow(getSupplierException(USER_NOT_FOUND));
        return userPersistMapper.toUserBO(userEntity);
    }

    public UserBO updateUser(UserBO updateUserBO) throws CustomException {
        UserEntity persistedEntity = userRepository.findById(updateUserBO.getUserId()).orElseThrow(getSupplierException(INVALID_CREDENTIALS));
        UserEntity update = userPersistMapper.toUserEntity(updateUserBO);
        update.setEmail(persistedEntity.getEmail());
        update.setRates(persistedEntity.getRates());
        update.setJoinedDate(persistedEntity.getJoinedDate());
        update.setRole(persistedEntity.getRole());
        UserEntity saved = userRepository.save(update);
        return userPersistMapper.toUserBO(saved);
    }


    public void disableUser(long userId) throws CustomException {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(getSupplierException(USER_NOT_FOUND));
        userEntity.setActive(!userEntity.isActive());
    }

    public void deleteUser(long userId) throws CustomException {
        UserEntity user = userRepository.findUserEntityByUserId(userId).orElseThrow(getSupplierException(INVALID_CREDENTIALS));
        userRepository.delete(user);
    }
    public List<UserBO> findAll() {
        return userPersistMapper.toUserBOs(userRepository.findAll());
    }
}
