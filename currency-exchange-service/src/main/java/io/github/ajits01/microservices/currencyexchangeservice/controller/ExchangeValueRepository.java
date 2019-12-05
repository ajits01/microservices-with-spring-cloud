package io.github.ajits01.microservices.currencyexchangeservice.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.ajits01.microservices.currencyexchangeservice.domain.ExchangeValue;

public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long> {
  
  public ExchangeValue findByFromAndTo(String from, String to);
}
