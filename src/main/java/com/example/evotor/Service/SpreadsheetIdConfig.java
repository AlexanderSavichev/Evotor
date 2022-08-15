package com.example.evotor.Service;

import org.springframework.beans.factory.annotation.Value;

public class SpreadsheetIdConfig {
    public SpreadsheetIdConfig(){}
    @Value("${spreadsheet_id}")
    String spreadsheetId;
    public String getSpreadsheetId(){
        return spreadsheetId;
    }


}
