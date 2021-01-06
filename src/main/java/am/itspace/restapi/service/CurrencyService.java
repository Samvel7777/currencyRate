package am.itspace.restapi.service;

import am.itspace.restapi.dto.CurrencyDto;
import am.itspace.restapi.model.Currency;
import am.itspace.restapi.model.CurrencyType;
import am.itspace.restapi.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Value("${currency.url}")
    private String baseUrl;

    public Currency getCurrencyRate(CurrencyType currencyType) {
        String url = String.format(baseUrl, currencyType.toString());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String statusCode = response.getStatusCode().getReasonPhrase();
        if (statusCode.equals("OK")) {
            return convertor(response.getBody(), currencyType);
        }
        return null;
    }

    private Currency convertor(String responseRate, CurrencyType currencyType) {
        JSONObject jsonObject = new JSONObject(responseRate);
        double value = Double.parseDouble(jsonObject.get(currencyType.name()).toString());
        Currency currency = new Currency();
        currency.setRate(value);
        currency.setCurrencyType(currencyType);
        currencyRepository.save(currency);
        return currency;
    }

    public List<Currency> findAllRateHistory() {
        return currencyRepository.findAllByOrderByCreatedDateDesc();
    }

    public CurrencyDto calculateCurrency(double value, CurrencyType currencyType) {
        Currency currencyRate = getCurrencyRate(currencyType);
        double result = currencyRate.getRate() * value;
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setValue(result);
        currencyDto.setCurrencyType(currencyType);
        return currencyDto;
    }

}
