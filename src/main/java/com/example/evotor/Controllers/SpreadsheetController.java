package com.example.evotor.Controllers;

import com.example.evotor.Service.IdKeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SpreadsheetController {

    @Autowired
    IdKeeperService idKeeperService;

    @GetMapping("/param")
    public String GetSpreadsheetId (@RequestParam(value = "SpreadsheetId") String SpreadsheetURL, Model model){
        try{
            String SpreadsheetId = SpreadsheetURL.substring(SpreadsheetURL.indexOf("/d/") + 3, SpreadsheetURL.indexOf("/edit"));
            model.addAttribute("CurrentId",SpreadsheetId );
            idKeeperService.SetSpreadsheetId(SpreadsheetId);

        return "/evotor";}
        catch (StringIndexOutOfBoundsException e){
            String SpreadsheetId = null;
            System.out.println(SpreadsheetId);
            idKeeperService.SetSpreadsheetId(SpreadsheetId);
            model.addAttribute("ErrorId","Введен неверный SpreadsheetId" );
            String oldId = idKeeperService.getSpreadsheetId();
            model.addAttribute("CurrentId",oldId );
            return "/evotor";
        }

    }

}
