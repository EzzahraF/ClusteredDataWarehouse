package com.progresssoft.clustringdatawarehouse.service;

import com.progresssoft.clustringdatawarehouse.model.FxDeal;
import com.progresssoft.clustringdatawarehouse.repository.FxDealRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class FxDealService {

    private final FxDealRepository repository;
    private final Validator validator;

    private static final Logger logger = LoggerFactory.getLogger(FxDealService.class);

    public FxDealService(FxDealRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public List<FxDeal> getAll() {
        return repository.findAll();
    }

    public boolean exists(String id) {
        return repository.existsByDealId(id);
    }

    public boolean process(FxDeal deal) {
        Set<ConstraintViolation<FxDeal>> violations = validator.validate(deal);

        if (!violations.isEmpty()) {
            violations.forEach(v ->
                    logger.error("Validation error {}: {}", v.getPropertyPath(), v.getMessage())
            );
            return false;
        }

        if (repository.existsByDealId(deal.getDealId())) {
            logger.warn("Duplicate deal skipped: {}", deal.getDealId());
            return false;
        }

        try {
            repository.save(deal);
            return true;
        } catch (DataIntegrityViolationException e) {
            logger.warn("Duplicate deal from DB constraint: {}", deal.getDealId());
            return false;
        }
    }
}
