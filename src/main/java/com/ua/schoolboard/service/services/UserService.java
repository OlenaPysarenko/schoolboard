package com.ua.schoolboard.service.services;

import com.ua.schoolboard.exceptions.CustomException;
import com.ua.schoolboard.persistence.repos.UserRepository;
import com.ua.schoolboard.rest.model.*;
import com.ua.schoolboard.service.mappers.RatesMapper;
import com.ua.schoolboard.service.mappers.UserMapper;
import com.ua.schoolboard.service.model.BalanceBO;
import com.ua.schoolboard.service.model.UserBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static com.ua.schoolboard.exceptions.ErrorCode.INVALID_CREDENTIALS;
import static com.ua.schoolboard.exceptions.ErrorCode.USER_NOT_FOUND;
import static com.ua.schoolboard.exceptions.ExceptionUtil.getException;


@Service
@RequiredArgsConstructor
public class UserService {
    private final RatesMapper ratesMapper;
    private final UserMapper mapper;
    private final UserRepository userRepository;
    private  final GroupService groupService;

    public void register(UserTO userTO) throws CustomException {
        UserTO user = userTO.getRole().newUser();
        user.setEmail(userTO.getEmail());
        user.setPassword(userTO.getPassword());
        Date joinDate = new Date();
        user.setJoinedDate(joinDate);
        user.setUserId(userTO.getUserId());
        user.setRole(userTO.getRole());
        UserBO userBO = mapper.toUserBO(user);
        userBO.setBalance(new BalanceBO());
        userBO.setRates(new ArrayList<>());
        userBO.setLanguages(new HashSet<>());
        userRepository.registerUser(userBO);
    }

    public UserTO findById(long userId) throws CustomException {
        UserBO userById = userRepository.findUserById(userId);
        return mapper.toUserTO(userById);
    }

    public UpdateStudentTO getStudent(long userId) throws CustomException {
        UserBO user = userRepository.findUserById(userId);
        return mapper.toStudentTO(user);
    }
    public UpdateTeacherTO getTeacher(long userId) throws CustomException {
        UserBO user = userRepository.findUserById(userId);
        return mapper.toTeacherTO(user);
    }
    public UpdateAdminTO getAdmin(long userId) throws CustomException {
        UserBO user = userRepository.findUserById(userId);
        return mapper.toAdminTO(user);
    }

    public UserTO findByEmail(String email) throws CustomException {
        UserBO userByEmail = userRepository.findByEmail(email);
        return mapper.toUserTO(userByEmail);
    }

    public UpdateStudentTO getStudentByNameAndSurname(String name, String surname) throws CustomException {
        UserBO bo = userRepository.getStudentByNameAndSurname(name, surname);
        return mapper.toStudentTO(bo);
    }

    public void login(String email, String password) throws CustomException {
        UserBO userByEmail = userRepository.findByEmail(email);
        if (!userByEmail.getEmail().equals(email)) {
            throw getException(USER_NOT_FOUND);
        }
        if (!userByEmail.getPassword().equals(password)) {
            throw getException(INVALID_CREDENTIALS);
        }
        mapper.toUserTO(userByEmail);
    }

    public List<UserBO> getAllByRole(Role role) {
        return userRepository.findAllByRole(role);
    }

    public List<UpdateTeacherTO> getAllTeachers() {
        List<UserBO> allTeachers = userRepository.findAllByRole(Role.TEACHER);
        return mapper.toTeacherTOs(allTeachers);
    }

    public List<UpdateStudentTO> getAllStudents() {
        List<UserBO> allStudents = userRepository.findAllByRole(Role.STUDENT);
        return mapper.toStudentTOs(allStudents);

    }


    public UserTO updateStudent(UpdateStudentTO updUserTO) throws CustomException {
        UserBO bo = mapper.toUserBO(updUserTO);
        return mapper.toUserTO(userRepository.updateUser(bo));
    }

    public UserTO updateTeacher(UpdateTeacherTO updUserTO) throws CustomException {
        UserBO userBO = mapper.toUpdateTeacherBO(updUserTO);
        return mapper.toUserTO(userRepository.updateUser(userBO));
    }

    public void disableUser(long userId) throws CustomException {
        userRepository.disableUser(userId);
    }

    public void deleteUser(long userId) throws CustomException {
        UserBO userById = userRepository.findUserById(userId);
        userRepository.deleteUser(userById.getUserId());
    }

    public UserTO updateAdmin(UpdateAdminTO updUser) throws CustomException {
        UserBO userBO = mapper.toUserBO(updUser);
        return mapper.toUserTO(userRepository.updateUser(userBO));
    }

}
