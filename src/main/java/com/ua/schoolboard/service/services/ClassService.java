package com.ua.schoolboard.service.services;

import com.ua.schoolboard.persistence.mappers.ClassEntityMapper;
import com.ua.schoolboard.persistence.model.ClassSessionEntity;
import com.ua.schoolboard.persistence.repos.CLassRepository;
import com.ua.schoolboard.rest.model.*;
import com.ua.schoolboard.service.mappers.ClassSessionMapper;
import com.ua.schoolboard.service.model.ClassSessionBO;
import com.ua.schoolboard.service.model.GroupBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassService {
    private final CLassRepository cLassRepository;
    private final ClassSessionMapper mapper;
    private final ClassEntityMapper classEntityMapper;

    public List<ClassSessionTO> getClassesByTeacher(Long teacherId) {
        List<ClassSessionBO> classesByTeacher = cLassRepository.getClassesByTeacher(teacherId);
        return mapper.toClassesTOs(classesByTeacher);
    }

    public ClassSessionTO getById(Long id) {
        ClassSessionBO classBO = cLassRepository.getById(id);
        return mapper.toClassTO(classBO);
    }


    public void register(ClassSessionTO classSessionTO) {
        Date date = new Date();
        classSessionTO.setClassDate(date);
        GroupTO group = classSessionTO.getGroup();
        int classesCovered = classSessionTO.getClassesCovered();
        classesCovered++;
        classSessionTO.setClassesCovered(classesCovered);
        group.getClassesCovered().add(classSessionTO);
        ClassSessionBO classSessionBO = mapper.toClassBO(classSessionTO);
        List<UpdateStudentTO> students = group.getStudents();
        //TODO figure out balance
        updateStudentBalance(group, students);
        cLassRepository.register(classSessionBO);
    }


    private void updateStudentBalance(GroupTO group, List<UpdateStudentTO> students) {
        for (UpdateStudentTO s:students) {
            RatesTO ratesTO = s.getRates().iterator().next();
            Integer classCost = group.getGroupType().equals(GroupType.GROUP) ? ratesTO.getGroupRate() : ratesTO.getIndRate();
            s.updateBalance(-classCost,-1);
           /* Integer amount = s.getBalance().getAmount();
            s.getBalance().setAmount(amount-classCost);
            Integer classesPaid = s.getBalance().getClassesPaid();
            classesPaid--;
            s.getBalance().setClassesPaid(classesPaid);
            PaymentTO payment= new PaymentTO();
            payment.setDate(new Date());
            payment.setAmount(amount);
            payment.setUser(s);
            s.getBalance().getPayments().add(payment);*/
        }
    }
}
