package am.itspace.restapi.endpoint;

import am.itspace.restapi.dto.CurrencyDto;
import am.itspace.restapi.model.Currency;
import am.itspace.restapi.model.CurrencyType;
import am.itspace.restapi.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;


    @GetMapping("/getCurrencyRate")
    public HttpEntity<Currency> getCurrencyRate(@RequestParam("currencyType") CurrencyType currencyType) {
        if (currencyType == null) {
            return ResponseEntity.badRequest().build();
        }
        Currency currencyRate = currencyService.getCurrencyRate(currencyType);

        return ResponseEntity.ok(currencyRate);
    }

    @GetMapping("/getRateHistory")
    public HttpEntity<List<Currency>> getRateHistory() {
        List<Currency> rates = currencyService.findAllRateHistory();
        return ResponseEntity.ok(rates);
    }


    @PostMapping("/calculateCurrency")
    public HttpEntity<CurrencyDto> calculateCurrency(@RequestBody CurrencyDto currencyDto) {
        CurrencyDto currency = currencyService.calculateCurrency(currencyDto.getValue(), currencyDto.getCurrencyType());
        if (currency == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(currency);

    }

}
