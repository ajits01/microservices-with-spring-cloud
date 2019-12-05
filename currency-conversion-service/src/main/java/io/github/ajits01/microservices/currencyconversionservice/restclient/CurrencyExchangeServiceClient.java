package io.github.ajits01.microservices.currencyconversionservice.restclient;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import io.github.ajits01.microservices.currencyconversionservice.domain.CurrencyConversionValue;

@FeignClient(name = "currency-exchange-service")
@RibbonClient(name = "currency-exchange-service")
public interface CurrencyExchangeServiceClient {

  @GetMapping(path = "/currency-exchange/from/{from}/to/{to}")
  public CurrencyConversionValue getExchangeValue(
      @PathVariable("from") String from, @PathVariable("to") String to);
}
