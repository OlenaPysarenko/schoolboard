package com.ua.schoolboard.service.services;

import com.ua.schoolboard.exceptions.CustomException;
import com.ua.schoolboard.persistence.repos.CLassRepository;
import com.ua.schoolboard.rest.model.*;
import com.ua.schoolboard.service.mappers.ClassSessionMapper;
import com.ua.schoolboard.service.mappers.GroupMapper;
import com.ua.schoolboard.service.model.ClassSessionBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ClassService {
    private final CLassRepository cLassRepository;
    private final ClassSessionMapper mapper;
    private final GroupMapper groupMapper;

    public List<ClassSessionTO> getClassesByTeacher(Long teacherId) throws CustomException {
        List<ClassSessionBO> classesByTeacher = cLassRepository.getClassesByTeacher(teacherId);
        return mapper.toClassesTOs(classesByTeacher);
    }

    public ClassSessionTO getById(Long id) throws CustomException {
        ClassSessionBO classBO = cLassRepository.getById(id);
        return mapper.toClassTO(classBO);
    }

    @Transactional
    public void register(ClassSessionTO classSessionTO) throws CustomException {
        Date date = new Date();
        classSessionTO.setClassDate(date);
        GroupTO group = classSessionTO.getGroup();
        int classesCovered = classSessionTO.getClassesCovered();
        classesCovered++;
        classSessionTO.setClassesCovered(classesCovered);
        ClassSessionBO classSessionBO = mapper.toClassBO(classSessionTO);
        List<UpdateStudentTO> students = group.getStudents();
        updateStudentBalance(group, students);
        cLassRepository.register(classSessionBO);

    }

    public void edit(ClassSessionTO classTO) {
        cLassRepository.edit(mapper.toClassBO(classTO));
    }


    private void updateStudentBalance(GroupTO group, List<UpdateStudentTO> students) {
        for (UpdateStudentTO s : students) {
            RatesTO ratesTO = s.getRates().iterator().next();
            Integer classCost = group.getGroupType().equals(GroupType.GROUP) ? ratesTO.getGroupRate() : ratesTO.getIndRate();
            s.updateBalance(-classCost, -1);
        }
    }
    public List<ClassSessionTO> findAllByGroup(GroupTO groupTO){
        return mapper.toClassesTOs(cLassRepository.getAllByGroup(groupMapper.toGroupBO(groupTO)));
    }
}
