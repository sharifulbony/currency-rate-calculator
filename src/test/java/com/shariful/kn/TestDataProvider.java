package com.shariful.kn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shariful.kn.task.dtos.CurrencyRateLatest;
import com.shariful.kn.task.dtos.CurrencyRateTimeSeries;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class TestDataProvider {

    @Autowired
    ObjectMapper objectMapper;

    public static String fileAsString(String path){
        try {
            return IOUtils.toString(Objects.requireNonNull(TestDataProvider.class.getResourceAsStream(path)),
                    StandardCharsets.UTF_8);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private <T> T getGenericMessage(String filePath, Class<T> classType) throws JsonProcessingException {
        return objectMapper.readValue(fileAsString(filePath),classType);
    }

    public CurrencyRateTimeSeries getTimeSeriesCurrencyRate () throws JsonProcessingException {
        return getGenericMessage("/test-data/time-series-response.json", CurrencyRateTimeSeries.class);
    }

    public CurrencyRateLatest getLatestCurrencyRate () throws JsonProcessingException {
        return getGenericMessage("/test-data/latest-rate-response.json", CurrencyRateLatest.class);
    }
}
