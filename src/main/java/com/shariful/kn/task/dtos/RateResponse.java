package com.shariful.kn.task.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RateResponse {
    private Double minRate;
    private Double maxRate;
    private Double latestRate;
    private String currency;

    @Override
    public String toString() {
        return "RateResponse{" +
                "minRate=" + minRate + " " + currency +
                ", maxRate=" + maxRate + " " + currency +
                ", latestRate=" + latestRate + " " + currency +
                '}';
    }
}
