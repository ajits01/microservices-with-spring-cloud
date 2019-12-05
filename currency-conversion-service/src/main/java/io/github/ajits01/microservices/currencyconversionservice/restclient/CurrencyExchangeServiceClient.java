package io.github.ajits01.microservices.currencyconversionservice.restclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import io.github.ajits01.microservices.currencyconversionservice.domain.CurrencyConversionValue;

@FeignClient(name = "currency-conversion-service", url = "localhost:8000/currency-exchange")
public interface CurrencyExchangeServiceClient {

  @GetMapping(path = "/from/{from}/to/{to}")
  public CurrencyConversionValue getExchangeValue(
      @PathVariable String from, @PathVariable String to);
}
