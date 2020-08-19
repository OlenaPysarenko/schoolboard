package com.ua.schoolboard.service.services;

import com.ua.schoolboard.rest.model.Role;
import com.ua.schoolboard.persistence.repos.UserRepository;
import com.ua.schoolboard.rest.model.*;
import com.ua.schoolboard.service.mappers.RatesMapper;
import com.ua.schoolboard.service.mappers.UserMapper;
import com.ua.schoolboard.service.model.UserBO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

import static com.ua.schoolboard.exceptions.ErrorCode.INVALID_CREDENTIAL;
import static com.ua.schoolboard.exceptions.ErrorCode.USER_NOT_FOUND;
import static com.ua.schoolboard.exceptions.ExceptionUtil.getException;


@Service
@RequiredArgsConstructor
public class UserService {
    private final RatesMapper ratesMapper;
    private final UserMapper mapper;
    private final UserRepository userRepository;
    private  final GroupService groupService;

    public void register(UserTO userTO) {
        UserTO user = userTO.getRole().newUser();
        user.setEmail(userTO.getEmail());
        user.setPassword(userTO.getPassword());
        Date joinDate = new Date();
        user.setJoinedDate(joinDate);
        user.setUserId(userTO.getUserId());
        user.setRole(userTO.getRole());
        UserBO userBO = mapper.toUserBO(user);

        userRepository.registerUser(userBO);
    }

    public UserTO findById(long userId) {
        UserBO userById = userRepository.findUserById(userId);
        return mapper.toUserTO(userById);
    }

    public UpdateStudentTO getStudent(long userId) {
        UserBO user = userRepository.findUserById(userId);
        return mapper.toStudentTO(user);
    }
    public UpdateTeacherTO getTeacher(long userId) {
        UserBO user = userRepository.findUserById(userId);
        return mapper.toTeacherTO(user);
    }
    public UpdateAdminTO getAdmin(long userId){
        UserBO user = userRepository.findUserById(userId);
        return mapper.toAdminTO(user);
    }

    public UserTO findByEmail(String email) {
        UserBO userByEmail = userRepository.findByEmail(email);
        return mapper.toUserTO(userByEmail);
    }

    public UpdateStudentTO getStudentByNameAndSurname(String name, String surname) {
        UserBO bo = userRepository.getStudentByNameAndSurname(name, surname);
        return mapper.toStudentTO(bo);
    }
    public UpdateTeacherTO getTeacherByNameAndSurname(String name, String surname){
        UserBO bo = userRepository.getStudentByNameAndSurname(name, surname);
        return mapper.toTeacherTO(bo);
    }

    public void login(String email, String password) {
        UserBO userByEmail = userRepository.findByEmail(email);
        if (!userByEmail.getEmail().equals(email)) {
            throw getException(USER_NOT_FOUND);
        }
        if (!userByEmail.getPassword().equals(password)) {
            throw getException(INVALID_CREDENTIAL);
        }
        mapper.toUserTO(userByEmail);
    }

  /*  public List<UpdateStudentTO> getStudentsByGroup(long groupId){
        GroupTO groupById = groupService.getGroupById(groupId);
        List<UserBO> all = userRepository.findAll();

    }*/

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


    public UserTO updateStudent(UpdateStudentTO updUserTO) {
        UserBO bo = mapper.toUserBO(updUserTO);
        return mapper.toUserTO(userRepository.updateUser(bo));
    }

    public UserTO updateTeacher(UpdateTeacherTO updUserTO) {
        //DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        UserBO userBO = mapper.toUpdateTeacherBO(updUserTO);
        return mapper.toUserTO(userRepository.updateUser(userBO));
    }



    private boolean isNameAndSurnameValid(String name, String surname) {
        if (name.length() < 1) {
            throw HttpClientErrorException.create(HttpStatus.NOT_ACCEPTABLE, "Name should be at least 2 characters long", new HttpHeaders(), null, null);
        } else if (name.contains("/d") || surname.contains("/d")) {
            throw HttpClientErrorException.create(HttpStatus.NOT_ACCEPTABLE, "Name and surname shouldn't contain digits", new HttpHeaders(), null, null);
        } else if (name.contains("[^a-zA-Z]") || name.contains("[^а-яА-Я]") || surname.contains("[^a-zA-Z]") || surname.contains("[^а-яА-Я]")) {
            throw HttpClientErrorException.create(HttpStatus.NOT_ACCEPTABLE, "Name and surname should contain letters only!", new HttpHeaders(), null, null);
        } else if (!name.contains("[aeiouy]") || !name.contains("[аоеиуеэяю]") || !surname.contains("[aeiouy]") || !surname.contains("[аоеиуеэяю]")) {
            throw HttpClientErrorException.create(HttpStatus.NOT_ACCEPTABLE, "Name and surname should contain vowels", new HttpHeaders(), null, null);
        }
        return true;
    }

    public void disableUser(long userId) {
        //UserBO userById = userRepository.findUserById(userId);
        userRepository.disableUser(userId);
    }

    public void deleteUser(long userId) {
        UserBO userById = userRepository.findUserById(userId);
        userRepository.deleteUser(userById.getUserId());
        //mapper.toUserTO(userById);
    }

    public UserTO updateAdmin(UpdateAdminTO updUser) {
        UserBO userBO = mapper.toUserBO(updUser);
        return mapper.toUserTO(userRepository.updateUser(userBO));
    }

}
