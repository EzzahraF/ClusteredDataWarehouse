package com.progresssoft.clustringdatawarehouse.service;

import com.progresssoft.clustringdatawarehouse.model.FxDeal;
import com.progresssoft.clustringdatawarehouse.model.FxDealDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

@Service
public class FxDealImportService {

    private static final Logger logger = LoggerFactory.getLogger(FxDealImportService.class);
    private final FxDealService fxDealService;

    public FxDealImportService(FxDealService fxDealService) {
        this.fxDealService = fxDealService;
    }

    public boolean saveRow(FxDealDto dto) {
        try {
            FxDeal deal = new FxDeal(dto);
            return fxDealService.process(deal);
        } catch (Exception e) {
            logger.error("Error saving deal {}: {}", dto.getDealUniqueId(), e.getMessage());
            return false;
        }
    }

    public ImportSummary importJsonList(List<FxDealDto> deals) {
        int saved = 0, duplicates = 0, skipped = 0;

        for (FxDealDto dto : deals) {
            boolean ok = saveRow(dto);
            if (ok) saved++;
            else if (fxDealService.exists(dto.getDealUniqueId())) duplicates++;
            else skipped++;
        }

        return new ImportSummary(saved, skipped, duplicates);
    }

    public ImportSummary importCsv(MultipartFile file) {
        int saved = 0, skipped = 0, duplicates = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("DealId")) continue;

                String[] arr = line.split(",");
                if (arr.length != 5) {
                    skipped++;
                    continue;
                }

                FxDealDto dto = new FxDealDto(
                        arr[0].trim(),
                        arr[1].trim(),
                        arr[2].trim(),
                        arr[3].trim(),
                        new BigDecimal(arr[4].trim())
                );

                boolean ok = saveRow(dto);

                if (ok) saved++;
                else if (fxDealService.exists(dto.getDealUniqueId())) duplicates++;
                else skipped++;
            }

        } catch (Exception e) {
            logger.error("CSV import error: {}", e.getMessage());
        }

        return new ImportSummary(saved, skipped, duplicates);
    }

    public static class ImportSummary {
        public final int saved;
        public final int skipped;
        public final int duplicates;

        public ImportSummary(int saved, int skipped, int duplicates) {
            this.saved = saved;
            this.skipped = skipped;
            this.duplicates = duplicates;
        }
    }
}
