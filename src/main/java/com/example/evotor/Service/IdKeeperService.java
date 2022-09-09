package com.example.evotor.Service;
import java.io.*;
import java.util.Properties;


public class IdKeeperService {
   public String SpreadsheetId;
   public String getSpreadsheetId(){
       LoadSpreadsheetId();
       return SpreadsheetId;
   }
   public void LoadSpreadsheetId(){
       try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
           Properties prop = new Properties();
           prop.load(input);
           SpreadsheetId = prop.getProperty("SpreadsheetId");
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    public String SetSpreadsheetId (String newSpread) {
        LoadSpreadsheetId();
        if (SpreadsheetId != newSpread && newSpread != null) {
            SpreadsheetId = newSpread;
            try (
                    OutputStream output = new FileOutputStream("src/main/resources/config.properties")) {
                Properties prop = new Properties();
                prop.setProperty("SpreadsheetId", newSpread);
                prop.store(output, null);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(SpreadsheetId);
        return SpreadsheetId;
    }
}
