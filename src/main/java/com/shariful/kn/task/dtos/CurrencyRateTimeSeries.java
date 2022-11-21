package com.shariful.kn.task.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CurrencyRateTimeSeries extends CurrencyRateBase {
    @JsonProperty("timeseries")
    private boolean timeSeries;
    @JsonProperty("start_date")
    @JsonAlias("date")
    private String startDate;
    @JsonProperty("end_date")
    private String endDate;
}
