package com.ua.schoolboard.service.services;

import com.ua.schoolboard.persistence.repos.RatesRepository;
import com.ua.schoolboard.rest.model.*;
import com.ua.schoolboard.service.mappers.RatesMapper;
import com.ua.schoolboard.service.model.RatesBO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ua.schoolboard.exceptions.ErrorCode.*;
import static com.ua.schoolboard.exceptions.ExceptionUtil.getException;

@Service
@RequiredArgsConstructor
public class RatesService {
    private final RatesMapper ratesMapper;
    private final RatesRepository ratesRepository;

    public void registerRates(RatesTO rates) {
        RatesBO ratesBO = ratesMapper.toRatesBO(rates);
        ratesRepository.registerRates(ratesBO);
    }

    public void registerTeacherRates(TeacherRatesTO rates) {
        RatesBO ratesBO = ratesMapper.toRatesBO(rates);
        ratesRepository.registerRates(ratesBO);
    }

    public List<RatesTO> getAllRatesTO() {
        List<RatesBO> allRates = ratesRepository.getAllRates();
        return ratesMapper.toRatesTOs(allRates);
    }

    public RatesTO getByLangAndDescription(Language language, String rateDescription) {
        RatesBO ratesBO = ratesRepository.findByLangAndDescription(language, rateDescription);
        return ratesMapper.toRatesTO(ratesBO);
    }

    public List<String> getAllDescriptions(Role role) {
        List<String> descriptions = new ArrayList<>();
        List<RatesBO> allRates = ratesRepository.getAllRates();
        for (RatesBO rates : allRates) {
            String rateDescription = rates.getRateDescription();
            descriptions.add(rateDescription);
        }
        return descriptions;
    }

    public List<RatesTO> getRatesByRole(Role role){
        List<RatesBO> ratesByRole = ratesRepository.getRatesByRole(role);
        return ratesMapper.toRatesTOs(ratesByRole);

    }

    public RatesTO getByRoleAndLang(Role role, Language language){
        RatesBO byRoleAndLang = ratesRepository.getByRoleAndLang(role, language);
        return ratesMapper.toRatesTO(byRoleAndLang);
    }


}
