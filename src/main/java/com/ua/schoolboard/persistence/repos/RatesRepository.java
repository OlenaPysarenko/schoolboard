package com.ua.schoolboard.persistence.repos;


import com.ua.schoolboard.exceptions.CustomException;
import com.ua.schoolboard.persistence.mappers.RatesEntityMapper;
import com.ua.schoolboard.persistence.model.RatesEntity;
import com.ua.schoolboard.rest.model.Language;
import com.ua.schoolboard.rest.model.Role;
import com.ua.schoolboard.rest.model.UpdateStudentTO;
import com.ua.schoolboard.rest.model.UserTO;
import com.ua.schoolboard.service.mappers.UserMapper;
import com.ua.schoolboard.service.model.RatesBO;
import com.ua.schoolboard.service.model.UserBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ua.schoolboard.exceptions.ErrorCode.*;
import static com.ua.schoolboard.exceptions.ExceptionUtil.getException;
import static com.ua.schoolboard.exceptions.ExceptionUtil.getSupplierException;

@Service
@RequiredArgsConstructor
public class RatesRepository {
    private final RatesRepo ratesRepo;
    private final RatesEntityMapper ratesEntityMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;


    public void registerRates(RatesBO ratesBO) {
        ratesBO.setRatesId(null);
        RatesEntity ratesEntity = ratesEntityMapper.toRatesEntity(ratesBO);
        ratesRepo.save(ratesEntity);
    }

    public void findRatesByLanguage(Language language) throws CustomException {
        ratesRepo.findByLanguage(language).orElseThrow(getSupplierException(RATES_NOT_FOUND));
    }

    public void findRatesByRole(Role role) {
        ratesRepo.findAllByRole(role);
    }

    public void updateRates() {
    }

    public List<RatesBO> getAllRates() {
        List<RatesEntity> ratesEntities = ratesRepo.findAll();
        return ratesEntityMapper.toRatesBOs(ratesEntities);
    }

    public RatesBO findByLangAndDescription(Language language, String rateDescription) throws CustomException {
        RatesEntity ratesEntity = ratesRepo.findByLanguageAndRateDescription(language, rateDescription).orElseThrow(getSupplierException(RATES_NOT_FOUND));
        return ratesEntityMapper.toRatesBO(ratesEntity);
    }

    public List<RatesBO> getRatesByRole(Role role){
        List<RatesEntity> allByRole = ratesRepo.findAllByRole(role);
        return ratesEntityMapper.toRatesBOs(allByRole);
    }
     public RatesBO getByRoleAndLang(Role role, Language language) throws CustomException {
         RatesEntity ratesEntity = ratesRepo.findByLanguageAndRole(language, role).orElseThrow(getSupplierException(RATES_NOT_FOUND));
         return ratesEntityMapper.toRatesBO(ratesEntity);
     }


}
