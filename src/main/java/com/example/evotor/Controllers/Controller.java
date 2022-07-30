package com.example.evotor.Controllers;
import com.example.evotor.Models.EvotorReceiptRequest;
import com.example.evotor.Service.SheetsServiceUtil;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;


@RestController
public class Controller {
    @PostMapping("/processReceipt")
    public void processReceipt(@RequestBody EvotorReceiptRequest evotorReceiptRequest) throws IOException, GeneralSecurityException {
        final Sheets sheetsService;
        final String SPREADSHEET_ID = "1OTrvLcvqIAMQsn5hNZd_UiFyCn1cn76QJ13NY268vz8";
        sheetsService = SheetsServiceUtil.getSheetsService();
        ValueRange body = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList(evotorReceiptRequest.id),
                        Arrays.asList(evotorReceiptRequest.timestamp),
                        Arrays.asList(evotorReceiptRequest.userId),
                        Arrays.asList(evotorReceiptRequest.type),
                        Arrays.asList(evotorReceiptRequest.version)
                ));
        UpdateValuesResponse result = sheetsService.spreadsheets().values()
                .update(SPREADSHEET_ID, "A1", body)
                .setValueInputOption("RAW")
                .execute();
    }
}
