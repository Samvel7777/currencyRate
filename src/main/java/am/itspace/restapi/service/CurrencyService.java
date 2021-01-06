package am.itspace.restapi.service;

import am.itspace.restapi.model.Currency;
import am.itspace.restapi.model.CurrencyType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyService {

    String baseUrl = "https://free.currconv.com/api/v7/convert?q=%s&compact=ultra&apiKey=f039cb64ec8e664cb8b5";

    public Currency getCurrencyRate(CurrencyType currencyType) {
        String url = String.format(baseUrl, currencyType.toString());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String statusCode = response.getStatusCode().getReasonPhrase();
        if (statusCode.equals("OK")) {
            return convertor(response.getBody(),currencyType);
        }
        return null;
    }

    private Currency convertor(String responseRate,CurrencyType currencyType){

        


        return null;
    }
}
