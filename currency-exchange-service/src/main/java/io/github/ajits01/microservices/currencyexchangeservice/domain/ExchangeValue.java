package io.github.ajits01.microservices.currencyexchangeservice.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class ExchangeValue {

  @Id private Long id;

  @Column(name = "FROM_CURRENCY")
  private String from;

  @Column(name = "TO_CURRENCY")
  private String to;

  private BigDecimal conversionMultiple;
  private int port;

  public ExchangeValue(Long id, String from, String to, BigDecimal conversionMultiple) {
    super();
    this.id = id;
    this.from = from;
    this.to = to;
    this.conversionMultiple = conversionMultiple;
  }
}
