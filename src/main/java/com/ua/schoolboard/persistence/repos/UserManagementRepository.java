package com.ua.schoolboard.persistence.repos;

import com.ua.schoolboard.persistence.mappers.RatesEntityMapper;
import com.ua.schoolboard.persistence.mappers.UserPersistMapper;
import com.ua.schoolboard.persistence.model.RatesEntity;
import com.ua.schoolboard.persistence.model.UserEntity;
import com.ua.schoolboard.rest.model.UpdateStudentTO;
import com.ua.schoolboard.service.mappers.RatesMapper;
import com.ua.schoolboard.service.model.RatesBO;
import com.ua.schoolboard.service.model.UserBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ua.schoolboard.exceptions.ErrorCode.*;
import static com.ua.schoolboard.exceptions.ExceptionUtil.*;

@Service
@RequiredArgsConstructor
public class UserManagementRepository {
    private final UserManagementRepo userManagementRepo;
    private final UserPersistMapper userMapper;
    private final RatesEntityMapper ratesMapper;


/*    public List<UserBO> assignStudentsToRates(List<UpdateStudentTO> listOfStudents) {

        return null;

    }*/
//TODO add some check
    public void assignUserToRates(UserBO userBO) {
        UserEntity userEntity = userMapper.toUserEntity(userBO);
        //userManagementRepo.assignUserToRates(userEntity);
        userManagementRepo.save(userEntity);
        //return userMapper.toUserBO(assignedUserEntity);

    }


}
