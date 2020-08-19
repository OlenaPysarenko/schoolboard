package com.ua.schoolboard.persistence.mappers;

import com.ua.schoolboard.persistence.model.RatesEntity;
import com.ua.schoolboard.service.model.RatesBO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RatesEntityMapper {

    RatesBO toRatesBO(RatesEntity source);

    RatesEntity toRatesEntity(RatesBO source);

    List<RatesBO> toRatesBOs(List<RatesEntity> source);

    List<RatesEntity> toRatesEntities(List<RatesBO> source);
}

