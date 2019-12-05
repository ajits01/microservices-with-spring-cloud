package io.github.ajits01.microservices.currencyexchangeservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import io.github.ajits01.microservices.currencyexchangeservice.domain.ExchangeValue;

@RestController
public class CurrencyExchangeController {

  @Autowired private Environment env;

  @Autowired private ExchangeValueRepository repository;

  @GetMapping(path = "/from/{from}/to/{to}")
  public ExchangeValue getExchangeValue(@PathVariable String from, @PathVariable String to) {

    ExchangeValue exchangeValue = repository.findByFromAndTo(from, to);

    exchangeValue.setPort(Integer.parseInt(env.getProperty("local.server.port")));
    
    return exchangeValue;
  }
}
