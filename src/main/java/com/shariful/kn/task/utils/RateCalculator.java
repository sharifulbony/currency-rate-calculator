package com.shariful.kn.task.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.shariful.kn.task.dtos.CurrencyRateLatest;
import com.shariful.kn.task.dtos.CurrencyRateTimeSeries;
import com.shariful.kn.task.dtos.RateResponse;

import java.util.Iterator;
import java.util.Map;

public class RateCalculator {

    public static RateResponse getMinMaxRate(JsonNode rateNodes) {

        double minimumRate = Double.MAX_VALUE;
        double maximumRate = Double.MIN_VALUE;
        Iterator<Map.Entry<String, JsonNode>> iterator = rateNodes.fields();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> currentEntry = iterator.next();
            double rate = currentEntry.getValue().fields().next().getValue().asDouble();
            if (rate <= minimumRate) {
                minimumRate = rate;
            }
            if (rate >= maximumRate) {
                maximumRate = rate;
            }
        }
        return new RateResponse().setMinRate(minimumRate).setMaxRate(maximumRate);
    }

    public static RateResponse getRateResponse(CurrencyRateTimeSeries rateTimeSeries,
                                               CurrencyRateLatest currencyRateLatest, String inputCurrency) {
        RateResponse rateResponse = getMinMaxRate(rateTimeSeries.getRates());
        rateResponse.setLatestRate(currencyRateLatest.getRates().fields().next().getValue().asDouble());
        rateResponse.setCurrency(inputCurrency);
        return rateResponse;
    }

    private RateCalculator(){
        throw new IllegalStateException("Utility class");
    }
}
