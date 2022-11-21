package com.shariful.kn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shariful.kn.task.dtos.CurrencyRateLatest;
import com.shariful.kn.task.dtos.CurrencyRateTimeSeries;
import com.shariful.kn.task.dtos.RateResponse;
import com.shariful.kn.task.utils.RateCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RateCalculatorTest {

    private static final double MAX_RATE = 22503.155556;
    private static final double MIN_RATE = 15893.920635;
    private static final double LATEST_RATE = 16741.580645;
    private static final String USD = "USD";
    @Autowired
    TestDataProvider testDataProvider;

    @Test
    void verify_minMaxRateCalculation() throws JsonProcessingException {
        //given
        CurrencyRateTimeSeries currencyRateTimeSeries = testDataProvider.getTimeSeriesCurrencyRate();
        RateResponse expectedResponse = new RateResponse().setMaxRate(MAX_RATE).setMinRate(MIN_RATE);
        //when
        RateResponse rateResponse = RateCalculator.getMinMaxRate(currencyRateTimeSeries.getRates());
        //then
        assertEquals(expectedResponse.getMaxRate(), rateResponse.getMaxRate());
        assertEquals(expectedResponse.getMinRate(), rateResponse.getMinRate());
        assertNull(rateResponse.getLatestRate());
    }

    @Test
    void verify_RateResponseCalculation() throws JsonProcessingException {
        //given
        CurrencyRateTimeSeries currencyRateTimeSeries = testDataProvider.getTimeSeriesCurrencyRate();
        CurrencyRateLatest currencyRateLatest = testDataProvider.getLatestCurrencyRate();
        RateResponse expectedResponse = new RateResponse()
                .setMaxRate(MAX_RATE)
                .setMinRate(MIN_RATE)
                .setLatestRate(LATEST_RATE);
        //when
        RateResponse rateResponse = RateCalculator.getRateResponse(currencyRateTimeSeries, currencyRateLatest, USD);
        //then
        assertEquals(expectedResponse.getMaxRate(), rateResponse.getMaxRate());
        assertEquals(expectedResponse.getMinRate(), rateResponse.getMinRate());
        assertEquals(expectedResponse.getLatestRate(), rateResponse.getLatestRate());

    }
}
