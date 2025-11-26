package com.progresssoft.clustringdatawarehouse;

import com.progresssoft.clustringdatawarehouse.model.FxDeal;
import com.progresssoft.clustringdatawarehouse.repository.FxDealRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class FxDealRepositoryTest {

    @Autowired
    private FxDealRepository repository;

    @Test
    void saveAndFindDeal() {
        FxDeal deal = new FxDeal();
        deal.setDealId("R001");
        deal.setDealUniqueId("U001"); // <-- ajouté
        deal.setFromCurrencyIso("USD");
        deal.setToCurrencyIso("JPY");
        deal.setDealAmount(BigDecimal.valueOf(500));
        deal.setDealDate(LocalDate.now());

        repository.save(deal);

        assertThat(repository.existsByDealId("R001")).isTrue();
        assertThat(repository.findByDealUniqueId("U001")).isNotNull(); // <-- vérifie uniqueId
    }

}
