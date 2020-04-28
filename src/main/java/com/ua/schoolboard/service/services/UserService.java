package com.ua.schoolboard.service.services;

import com.ua.schoolboard.persistence.Role;
import com.ua.schoolboard.persistence.repos.UserRepositoryImpl;
import com.ua.schoolboard.rest.model.UserTO;
import com.ua.schoolboard.service.mappers.UserMapper;
import com.ua.schoolboard.service.model.UserBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ua.schoolboard.exceptions.ErrorCode.INVALID_CREDENTIAL;
import static com.ua.schoolboard.exceptions.ErrorCode.USER_NOT_FOUND;
import static com.ua.schoolboard.exceptions.ExceptionUtil.getException;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final UserRepositoryImpl userRepository;

    public void register(UserTO userTO) {
        UserBO userBO = mapper.toUserBO(userTO);
        userRepository.registerUser(userBO);
    }

    public UserTO findById(long userId) {//why do u even need it?
        UserBO userById = userRepository.findUserById(userId);
        return mapper.toUserTO(userById);
    }

    public UserTO login(String email, String password) {
        UserBO userByEmail = userRepository.findByEmail(email);
        if (!userByEmail.getEmail().equals(email)) {
            throw getException(USER_NOT_FOUND);
        }
        if (!userByEmail.getPassword().equals(password)) {
            throw getException(INVALID_CREDENTIAL);
        }
        return mapper.toUserTO(userByEmail);
    }

    public List<UserBO> getAllByRole(Role role) {
        return userRepository.findAllByRole(role);
    }

    public List<UserTO> getAll() {
        return mapper.toUserTOs(userRepository.findAll());
    }

    public UserTO updateUser(UserTO userTO) {
        UserBO userEntity = userRepository.findByEmail(userTO.getEmail());
        return mapper.toUserTO(userEntity);//set all the fields
    }

    public UserTO deleteUser(long userId) {
        UserBO userById = userRepository.findUserById(userId);
        return mapper.toUserTO(userById);
    }

}
