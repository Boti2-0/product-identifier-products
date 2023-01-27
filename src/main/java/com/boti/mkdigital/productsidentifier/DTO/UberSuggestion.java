package com.boti.mkdigital.productsidentifier.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class UberSuggestion {
    private String keyword;
    private double competition;
    private int volume;
    private double cpc;
    private double cpcDollars;
    private Map<String, Integer> ms;
    private int sd;
    private int pd;
    private boolean sd_is_old;
}
