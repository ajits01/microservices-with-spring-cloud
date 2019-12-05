package io.github.ajits01.microservices.limitsservice.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LimitConfigurationValue {
  private int minimum;
  private int maximum;
}
