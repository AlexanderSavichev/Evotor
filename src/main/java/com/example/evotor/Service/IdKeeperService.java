package com.example.evotor.Service;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Objects;
import java.util.Properties;


@Service
public class IdKeeperService {

   private String SpreadsheetId;

   public String getSpreadsheetId(){
       return SpreadsheetId;
   }

   @PostConstruct
   public void loadFromFile(){
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
        if (!Objects.equals(SpreadsheetId, newSpread) && newSpread != null) {
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
