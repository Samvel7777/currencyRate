package am.itspace.restapi.rest;

import am.itspace.restapi.model.Currency;
import am.itspace.restapi.model.CurrencyType;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyController {


    @GetMapping("/getCurrencyRate")
    public HttpEntity<Currency> getCurrencyRate(@RequestParam("currencyType")CurrencyType currencyType){

        


        return ResponseEntity.ok().body(new Currency());
    }
}
