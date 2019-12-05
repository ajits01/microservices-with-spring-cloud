package io.github.ajits01.microservices.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.ajits01.microservices.limitsservice.config.LimitConfigurationValue;
import io.github.ajits01.microservices.limitsservice.config.LimitsConfiguration;

@RestController
public class LimitsConfigurationController {

  @Autowired private LimitsConfiguration configuration;

  @GetMapping(path = "/limits")
  public LimitConfigurationValue retrieveLimitsFromConfiguration() {

    return new LimitConfigurationValue(configuration.getMinimum(), configuration.getMaximum());
  }
}
