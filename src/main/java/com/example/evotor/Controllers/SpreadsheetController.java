package com.example.evotor.Controllers;

import com.example.evotor.Service.IdKeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.security.GeneralSecurityException;


@Controller
public class SpreadsheetController {

    @Autowired
    IdKeeperService idKeeperService;
    @Autowired
    GoogleAuthorizeUtil googleAuthorizeUtil;

    @RequestMapping(value ="evotor", method = RequestMethod.GET)
    public String CurrentId (Model model){
        String spreadsheetId = idKeeperService.getSpreadsheetId();
        model.addAttribute("CurrentId",spreadsheetId );
        return "evotor";
    }

    @GetMapping("param")
    public String GetSpreadsheetId (@RequestParam(value = "SpreadsheetId") String SpreadsheetURL, Model model) throws IOException, GeneralSecurityException {
        googleAuthorizeUtil.authorize();
        try{
            String SpreadsheetId = SpreadsheetURL.substring(SpreadsheetURL.indexOf("/d/") + 3, SpreadsheetURL.indexOf("/edit"));
            //model.addAttribute("CurrentUri",googleAuthorizeUtil.getRedirectUri());
            model.addAttribute("CurrentId",SpreadsheetId );
            idKeeperService.SetSpreadsheetId(SpreadsheetId);

        return "evotor";}
        catch (StringIndexOutOfBoundsException e){
            String SpreadsheetId = null;
            idKeeperService.SetSpreadsheetId(SpreadsheetId);
            model.addAttribute("ErrorId","Введен неверный SpreadsheetId" );
            //model.addAttribute("CurrentUri",googleAuthorizeUtil.getRedirectUri());
            String oldId = idKeeperService.getSpreadsheetId();
            model.addAttribute("CurrentId",oldId );
            return "evotor";
        }

    }


}
