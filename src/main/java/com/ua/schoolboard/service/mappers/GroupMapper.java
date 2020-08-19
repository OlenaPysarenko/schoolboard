package com.ua.schoolboard.service.mappers;

import com.ua.schoolboard.rest.model.GroupTO;
import com.ua.schoolboard.rest.model.UpdGroupTO;
import com.ua.schoolboard.service.model.GroupBO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupBO toGroupBO(GroupTO source);

    GroupTO toGroupTO(GroupBO source);

   // UpdGroupTO toUpdGroupTO(GroupBO source);

    GroupBO toUpdGroupBO(UpdGroupTO source);

    List<GroupBO> toGroupBOs(List<GroupTO> source);

    List<GroupTO> toGroupTOs(List<GroupBO> source);
}
