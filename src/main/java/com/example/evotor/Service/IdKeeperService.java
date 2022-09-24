package com.example.evotor.Service;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

@Service
public class IdKeeperService {
   private String SpreadsheetId;
   String data = "";
   public String getSpreadsheetId() throws IOException {
       return SpreadsheetId;
   }
   @PostConstruct
   public void loadFromFile() throws IOException {
       ClassPathResource cpr = new ClassPathResource("/config.properties");
       byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
       data = new String(bdata, StandardCharsets.UTF_8);
       SpreadsheetId = data.substring(data.indexOf("Id=")+3);
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
        return SpreadsheetId;
    }
}
