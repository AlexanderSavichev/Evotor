package com.example.evotor.Controllers;
import com.example.evotor.Models.EvotorReceiptRequest;
import com.example.evotor.Service.SheetsServiceUtil;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
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
                        Arrays.asList(evotorReceiptRequest.id,
                                evotorReceiptRequest.timestamp,
                                evotorReceiptRequest.userId,
                                evotorReceiptRequest.type,
                                evotorReceiptRequest.version,
                                evotorReceiptRequest.data.id,
                                evotorReceiptRequest.data.deviceId,
                                evotorReceiptRequest.data.storeId,
                                evotorReceiptRequest.data.dateTime,
                                evotorReceiptRequest.data.type,
                                evotorReceiptRequest.data.shiftId,
                                evotorReceiptRequest.data.employeeId,
                                evotorReceiptRequest.data.paymentSource,
                                evotorReceiptRequest.data.infoCheck,
                                evotorReceiptRequest.data.egais,
                                evotorReceiptRequest.data.items.id,
                                evotorReceiptRequest.data.items.name,
                                evotorReceiptRequest.data.items.itemType,
                                evotorReceiptRequest.data.items.measureName,
                                evotorReceiptRequest.data.items.quantity,
                                evotorReceiptRequest.data.items.price,
                                evotorReceiptRequest.data.items.costPrice,
                                evotorReceiptRequest.data.items.sumPrice,
                                evotorReceiptRequest.data.items.tax,
                                evotorReceiptRequest.data.items.taxPercent,
                                evotorReceiptRequest.data.items.discount,
                                evotorReceiptRequest.data.totalTax,
                                evotorReceiptRequest.data.totalDiscount,
                                evotorReceiptRequest.data.totalAmount,
                                evotorReceiptRequest.data.extras
                                )
                ));
       AppendValuesResponse result = sheetsService.spreadsheets().values()
               .append(SPREADSHEET_ID, "A1", body)
               .setValueInputOption("USER_ENTERED")
               .setInsertDataOption("INSERT_ROWS")
               .setIncludeValuesInResponse(true)
               .execute();

        ValueRange total = result.getUpdates().getUpdatedData();
        assert(total.getValues().get(0).get(1)).equals("65");

    }
}
