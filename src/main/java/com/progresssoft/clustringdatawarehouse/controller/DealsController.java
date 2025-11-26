package com.progresssoft.clustringdatawarehouse.controller;

import com.progresssoft.clustringdatawarehouse.model.FxDeal;
import com.progresssoft.clustringdatawarehouse.model.FxDealDto;
import com.progresssoft.clustringdatawarehouse.service.FxDealImportService;
import com.progresssoft.clustringdatawarehouse.service.FxDealService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/deals")
public class DealsController {

    private final FxDealImportService importService;
    private final FxDealService fxDealService;

    public DealsController(FxDealImportService importService, FxDealService fxDealService) {
        this.importService = importService;
        this.fxDealService = fxDealService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody FxDealDto dto) {
        boolean ok = importService.saveRow(dto);

        if (!ok)
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Duplicate dealUniqueId"));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("status", "saved"));
    }

    @GetMapping
    public List<FxDeal> getAll() {
        return fxDealService.getAll();
    }

    @PostMapping(value = "/bulk", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> bulk(@RequestBody List<@Valid FxDealDto> deals) {
        return ResponseEntity.ok(importService.importJsonList(deals));
    }

    @PostMapping(value = "/upload/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadCsv(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(importService.importCsv(file));
    }
}
