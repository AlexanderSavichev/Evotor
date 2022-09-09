package com.example.evotor.Controllers;

import com.example.evotor.Service.IdKeeperService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SpreadsheetController {
    @GetMapping("/param")
    public String GetSpreadsheetId (@RequestParam(value = "SpreadsheetId") String SpreadsheetURL, Model model){
        try{
            String SpreadsheetId = SpreadsheetURL.substring(SpreadsheetURL.indexOf("/d/") + 3, SpreadsheetURL.indexOf("/edit"));
            IdKeeperService idKeeperService = new IdKeeperService();
            model.addAttribute("CurrentId",SpreadsheetId );
            idKeeperService.SetSpreadsheetId(SpreadsheetId);

        return "/evotor";}
        catch (StringIndexOutOfBoundsException e){
            String SpreadsheetId = null;
            IdKeeperService idKeeperService = new IdKeeperService();
            System.out.println(SpreadsheetId);
            idKeeperService.SetSpreadsheetId(SpreadsheetId);
            model.addAttribute("ErrorId","Введен неверный SpreadsheetId" );
            String oldId = idKeeperService.getSpreadsheetId();
            model.addAttribute("CurrentId",oldId );
            return "/evotor";
        }

    }

}
