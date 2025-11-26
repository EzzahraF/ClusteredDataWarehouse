package com.progresssoft.clustringdatawarehouse.repository;

import com.progresssoft.clustringdatawarehouse.model.FxDeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FxDealRepository extends JpaRepository<FxDeal, String> {

    Optional<FxDeal> findByDealId(String dealId);

    boolean existsByDealId(String dealId);
    FxDeal findByDealUniqueId(String dealUniqueId);
}
