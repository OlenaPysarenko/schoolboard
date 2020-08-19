package com.ua.schoolboard.service.mappers;

import com.ua.schoolboard.rest.model.ClassSessionTO;
import com.ua.schoolboard.rest.model.GroupTO;
import com.ua.schoolboard.service.model.ClassSessionBO;
import com.ua.schoolboard.service.model.GroupBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassSessionMapper {
    ClassSessionTO toClassTO(ClassSessionBO source);

    ClassSessionBO toClassBO(ClassSessionTO source);

    List<ClassSessionBO> toClassesBOs(List<ClassSessionTO> source);

    List<ClassSessionTO> toClassesTOs(List<ClassSessionBO> source);

    @Mapping(target = "classesCovered", ignore = true)
    GroupBO toGroupBo(GroupTO source);
}
