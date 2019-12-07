package io.github.ajits01.microservices.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.github.ajits01.microservices.limitsservice.config.LimitConfigurationValue;
import io.github.ajits01.microservices.limitsservice.config.LimitsConfiguration;

@RestController
public class LimitsConfigurationController {

  @Autowired private LimitsConfiguration configuration;

  @GetMapping(path = "/limits")
  public LimitConfigurationValue retrieveLimitsFromConfiguration() {

    return new LimitConfigurationValue(configuration.getMinimum(), configuration.getMaximum());
  }

  @GetMapping(path = "/limits-hystrix")
  @HystrixCommand(
      fallbackMethod = "returnFallbackLimitConfiguration",
      commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
      })
  public LimitConfigurationValue retrieveLimitsHystrixImpl() {

    throw new RuntimeException("Limits Service Not Available");
  }

  public LimitConfigurationValue returnFallbackLimitConfiguration() {

    final int FALLBACK_MINIMUM = 10;
    final int FALLBACK_MAXIMUM = 100;

    return new LimitConfigurationValue(FALLBACK_MINIMUM, FALLBACK_MAXIMUM);
  }
}
