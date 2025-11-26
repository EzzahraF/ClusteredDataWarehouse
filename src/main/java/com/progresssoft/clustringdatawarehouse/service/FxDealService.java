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

    /**
     * Retourne tous les FX deals existants dans la base.
     */
    public List<FxDeal> getAll() {
        return repository.findAll();
    }

    /**
     * Vérifie si un deal existe déjà par son ID.
     */
    public boolean exists(String dealId) {
        return repository.existsByDealId(dealId);
    }

    /**
     * Valide et sauvegarde un deal. Ignore les doublons.
     */
    public boolean process(FxDeal deal) {
        // Validation du deal
        Set<ConstraintViolation<FxDeal>> violations = validator.validate(deal);
        if (!violations.isEmpty()) {
            violations.forEach(v ->
                    logger.error("Validation error {}: {}", v.getPropertyPath(), v.getMessage())
            );
            return false;
        }

        // Vérification des doublons
        if (repository.existsByDealId(deal.getDealId())) {
            logger.warn("Duplicate deal skipped: {}", deal.getDealId());
            return false;
        }

        // Sauvegarde en base
        try {
            repository.save(deal);
            logger.info("Deal saved successfully: {}", deal.getDealId());
            return true;
        } catch (DataIntegrityViolationException e) {
            logger.warn("Duplicate deal from DB constraint: {}", deal.getDealId());
            return false;
        }
    }
}
