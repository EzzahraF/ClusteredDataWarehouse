package com.progresssoft.clustringdatawarehouse;

import com.progresssoft.clustringdatawarehouse.service.*;
import com.progresssoft.clustringdatawarehouse.model.FxDeal;
import com.progresssoft.clustringdatawarehouse.repository.FxDealRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FxDealServiceTest {

    private FxDealRepository repository;
    private Validator validator;
    private FxDealService service;

    @BeforeEach
    void setup() {
        repository = mock(FxDealRepository.class);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        service = new FxDealService(repository, validator);
    }

    @Test
    void process_validDeal_savesSuccessfully() {
        FxDeal deal = new FxDeal();
        deal.setDealId("D001");
        deal.setFromCurrencyIso("USD");
        deal.setToCurrencyIso("EUR");
        deal.setDealAmount(BigDecimal.valueOf(100));
        deal.setDealDate(LocalDate.now());

        when(repository.existsByDealId("D001")).thenReturn(false);
        when(repository.save(deal)).thenReturn(deal);

        boolean result = service.process(deal);

        assertTrue(result);
        verify(repository, times(1)).save(deal);
    }

    @Test
    void process_duplicateDeal_skipped() {
        FxDeal deal = new FxDeal();
        deal.setDealId("D002");

        when(repository.existsByDealId("D002")).thenReturn(true);

        boolean result = service.process(deal);

        assertFalse(result);
        verify(repository, never()).save(deal);
    }

    @Test
    void process_invalidDeal_failsValidation() {
        FxDeal deal = new FxDeal(); // missing mandatory fields

        boolean result = service.process(deal);

        assertFalse(result);
        verify(repository, never()).save(any());
    }
}
