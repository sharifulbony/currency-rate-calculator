package com.shariful.kn.task.dtos;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CurrencyRateBase {
    private JsonNode motd;
    private boolean success;
    private String base;
    private JsonNode rates;

}
