package io.github.ajits01.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import io.github.ajits01.microservices.currencyconversionservice.domain.CurrencyConversionValue;

@RestController
public class CurrencyConversionController {

  @GetMapping(path = "/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversionValue getConvertedCurrency(
      @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

    Map<String, String> uriVariables = new HashMap<>();
    uriVariables.put("from", from);
    uriVariables.put("to", to);

    ResponseEntity<CurrencyConversionValue> responseEntity =
        new RestTemplate()
            .getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversionValue.class,
                uriVariables);

    CurrencyConversionValue responseFromExchangeService = responseEntity.getBody();

    CurrencyConversionValue currencyConversionValue = new CurrencyConversionValue(
        responseFromExchangeService.getId(),
        from,
        to,
        responseFromExchangeService.getConversionMultiple(),
        quantity,
        quantity.multiply(responseFromExchangeService.getConversionMultiple()),
        responseFromExchangeService.getPort());
    
    return currencyConversionValue;
  }
}
