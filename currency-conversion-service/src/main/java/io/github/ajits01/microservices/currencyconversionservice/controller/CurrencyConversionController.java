package io.github.ajits01.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import io.github.ajits01.microservices.currencyconversionservice.domain.CurrencyConversionValue;
import io.github.ajits01.microservices.currencyconversionservice.restclient.CurrencyExchangeServiceClient;

@RestController
public class CurrencyConversionController {

  private String serviceId = "currency-exchange-service";

  @Autowired private DiscoveryClient discoveryClient;

  @Autowired private CurrencyExchangeServiceClient currencyExchangeClient;

  @GetMapping(path = "/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversionValue getConvertedCurrency(
      @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

    List<ServiceInstance> list = discoveryClient.getInstances(serviceId);
    String host = "";
    int port = 0;

    if (list != null && list.size() > 0) {
      host = list.get(0).getHost();
      port = list.get(0).getPort();
    }

    Map<String, String> uriVariables = new HashMap<>();
    uriVariables.put("from", from);
    uriVariables.put("to", to);
    uriVariables.put("host", host);
    uriVariables.put("port", String.valueOf(port));

    ResponseEntity<CurrencyConversionValue> responseEntity =
        new RestTemplate()
            .getForEntity(
                "http://{host}:{port}/currency-exchange/from/{from}/to/{to}",
                CurrencyConversionValue.class,
                uriVariables);

    CurrencyConversionValue responseFromExchangeService = responseEntity.getBody();

    CurrencyConversionValue currencyConversionValue =
        new CurrencyConversionValue(
            responseFromExchangeService.getId(),
            from,
            to,
            responseFromExchangeService.getConversionMultiple(),
            quantity,
            quantity.multiply(responseFromExchangeService.getConversionMultiple()),
            responseFromExchangeService.getPort());

    return currencyConversionValue;
  }

  @GetMapping(path = "currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversionValue getConvertedCurrencyWithFeign(
      @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

    CurrencyConversionValue responseFromExchangeService =
        currencyExchangeClient.getExchangeValue(from, to);

    CurrencyConversionValue currencyConversionValue =
        new CurrencyConversionValue(
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
