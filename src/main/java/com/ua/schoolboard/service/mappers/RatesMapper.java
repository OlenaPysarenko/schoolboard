package com.ua.schoolboard.service.mappers;

import com.ua.schoolboard.rest.model.RatesTO;
import com.ua.schoolboard.rest.model.TeacherRatesTO;
import com.ua.schoolboard.service.model.RatesBO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RatesMapper {
    RatesTO toRatesTO(RatesBO source);

    @Mapping(target = "groupRatePerStudent", ignore = true)
    @Mapping(target = "groupRateMin", ignore = true)
    @Mapping(target = "language", source = "language")
    RatesBO toRatesBO(RatesTO source);

    RatesBO toRatesBO(TeacherRatesTO source);

   List<RatesBO> toRatesBOs(List<RatesTO> source);

    List<RatesTO> toRatesTOs(List<RatesBO> source);

}
