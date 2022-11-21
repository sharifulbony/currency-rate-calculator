package com.shariful.kn.task.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CurrencyRateLatest extends CurrencyRateBase {
    @JsonProperty("date")
    private String date;
}
