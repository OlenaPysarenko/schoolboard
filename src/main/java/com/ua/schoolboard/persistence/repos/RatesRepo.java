package com.ua.schoolboard.persistence.repos;

import com.ua.schoolboard.persistence.model.RatesEntity;
import com.ua.schoolboard.rest.model.Language;
import com.ua.schoolboard.rest.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface RatesRepo extends CrudRepository<RatesEntity, Long> {
    Optional<RatesEntity> findByRateDescription(String rateDescription);

    Optional<RatesEntity> findByLanguage(Language language);

    Optional<RatesEntity> findUserEntityByRatesId(long ratesId);

    List<RatesEntity> findAllByRole(Role role);

    List<RatesEntity> findAll();

    Optional<RatesEntity> findByLanguageAndRateDescription(Language language, String rateDescription);

    Optional<RatesEntity> findByLanguageAndRole(Language language, Role role);


}