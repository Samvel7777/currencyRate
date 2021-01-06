package am.itspace.restapi.dto;

import am.itspace.restapi.model.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDto {

    private double value;
    private CurrencyType currencyType;
}
