package com.example.evotor.Controllers;
import com.example.evotor.Models.EvotorReceiptRequest;
import com.example.evotor.Models.Items;
import com.example.evotor.Service.IdKeeperService;
import com.example.evotor.Service.SheetsServiceUtil;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@RestController
@SpringBootApplication
public class Controller {
    IdKeeperService idKeeperService = new IdKeeperService();
    String spreadsheetId = idKeeperService.getSpreadsheetId();
    @PostMapping("/processReceipt")
    public void processReceipt(@RequestBody EvotorReceiptRequest evotorReceiptRequest) throws IOException, GeneralSecurityException {
        final Sheets sheetsService;
        sheetsService = SheetsServiceUtil.getSheetsService();
        List<Object> newItems = new LinkedList<>();
        newItems.add(evotorReceiptRequest.id);
        newItems.add(evotorReceiptRequest.timestamp);
        newItems.add(evotorReceiptRequest.userId);
        newItems.add(evotorReceiptRequest.type);
        newItems.add(evotorReceiptRequest.version);
        newItems.add(evotorReceiptRequest.data.id);
        newItems.add(evotorReceiptRequest.data.deviceId);
        newItems.add(evotorReceiptRequest.data.storeId);
        newItems.add(evotorReceiptRequest.data.dateTime);
        newItems.add(evotorReceiptRequest.data.type);
        newItems.add(evotorReceiptRequest.data.shiftId);
        newItems.add(evotorReceiptRequest.data.employeeId);
        newItems.add(evotorReceiptRequest.data.paymentSource);
        newItems.add(evotorReceiptRequest.data.infoCheck);
        newItems.add(evotorReceiptRequest.data.egais);
        for (Items item:evotorReceiptRequest.data.items){
            newItems.add(item.id);
            newItems.add(item.name);
            newItems.add(item.itemType);
            newItems.add(item.measureName);
            newItems.add(item.quantity);
            newItems.add(item.price);
            newItems.add(item.costPrice);
            newItems.add(item.sumPrice);
            newItems.add(item.tax);
            newItems.add(item.taxPercent);
            newItems.add(item.discount);
        }
        newItems.add(evotorReceiptRequest.data.totalTax);
        newItems.add(evotorReceiptRequest.data.totalDiscount);
        newItems.add(evotorReceiptRequest.data.totalAmount);
        newItems.forEach(System.out::println);
        ValueRange body = new ValueRange()
                .setValues(Arrays.asList(
                        (newItems)
                ));
       AppendValuesResponse result = sheetsService.spreadsheets().values()
               .append(spreadsheetId, "A1", body)
               .setValueInputOption("USER_ENTERED")
               .setInsertDataOption("INSERT_ROWS")
               .setIncludeValuesInResponse(true)
               .execute();
        ValueRange total = result.getUpdates().getUpdatedData();
        assert(total.getValues().get(0).get(1)).equals("65");

    }
}
