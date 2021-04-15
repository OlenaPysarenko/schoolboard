package com.ua.schoolboard.service.services;

import com.ua.schoolboard.exceptions.CustomException;
import com.ua.schoolboard.persistence.repos.RatesRepository;
import com.ua.schoolboard.persistence.repos.UserManagementRepository;
import com.ua.schoolboard.persistence.repos.UserRepository;
import com.ua.schoolboard.rest.model.RatesTO;
import com.ua.schoolboard.rest.model.UpdateStudentTO;
import com.ua.schoolboard.rest.model.UpdateTeacherTO;
import com.ua.schoolboard.service.mappers.RatesMapper;
import com.ua.schoolboard.service.mappers.UserMapper;
import com.ua.schoolboard.service.model.RatesBO;
import com.ua.schoolboard.service.model.UserBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageUsersService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RatesMapper ratesMapper;
    private final RatesRepository ratesRepository;
    final private UserManagementRepository userManagementRepository;

    public void assignStudentsToRates(UpdateStudentTO studentTO, RatesTO ratesTO) throws CustomException {
        UserBO studentByNameAndSurname = userRepository.getStudentByNameAndSurname(studentTO.getName(), studentTO.getSurname());
        RatesBO ratesBO = ratesMapper.toRatesBO(ratesTO);
        studentByNameAndSurname.setRates(Collections.singletonList(ratesBO));
        userManagementRepository.assignUserToRates(studentByNameAndSurname);
    }

    public void assignTeacherToRates(UpdateTeacherTO teacherTO, RatesTO ratesTO) throws CustomException {
        UserBO userById = userRepository.findUserById(teacherTO.getUserId());
        RatesBO ratesBO = ratesMapper.toRatesBO(ratesTO);
        List<RatesBO> rates = userById.getRates();
        rates.add(ratesBO);
        userById.setRates(rates);
        userManagementRepository.assignUserToRates(userById);
    }
}
