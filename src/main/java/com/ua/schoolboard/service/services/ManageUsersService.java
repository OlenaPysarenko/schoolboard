package com.ua.schoolboard.service.services;

import com.ua.schoolboard.persistence.repos.RatesRepository;
import com.ua.schoolboard.persistence.repos.UserManagementRepository;
import com.ua.schoolboard.persistence.repos.UserRepository;
import com.ua.schoolboard.rest.model.*;
import com.ua.schoolboard.service.mappers.RatesMapper;
import com.ua.schoolboard.service.mappers.UserMapper;
import com.ua.schoolboard.service.model.RatesBO;
import com.ua.schoolboard.service.model.UserBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.ua.schoolboard.exceptions.ErrorCode.RATES_NOT_FOUND;
import static com.ua.schoolboard.exceptions.ExceptionUtil.getException;

@Service
@RequiredArgsConstructor
public class ManageUsersService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RatesMapper ratesMapper;
    private final RatesRepository ratesRepository;
    final private UserManagementRepository userManagementRepository;

    //TODO add field active to User
/*    public void getAllActiveUsersByLang(Language language){
        UserBO byLanguage = userRepository.findByLanguage(language);
    }*/
    public void assignStudentsToRates(UpdateStudentTO studentTO, RatesTO ratesTO) {
        //UserBO userById = userRepository.getStudentByNameAndSurname(studentTO.getName(), studentTO.getSurname());
        RatesBO ratesBO = ratesMapper.toRatesBO(ratesTO);
        UserBO userBO = userMapper.toUserBO(studentTO);
        userBO.setRates(Collections.singletonList(ratesBO));
        userManagementRepository.assignUserToRates(userBO);
    }

    public void assignTeacherToRates(UpdateTeacherTO teacherTO, RatesTO ratesTO) {
        UserBO userById = userRepository.getStudentByNameAndSurname(teacherTO.getName(), teacherTO.getSurname());
        RatesBO ratesBO = ratesMapper.toRatesBO(ratesTO);
        userById.setRates(Collections.singletonList(ratesBO));
        userManagementRepository.assignUserToRates(userById);
    }


}
