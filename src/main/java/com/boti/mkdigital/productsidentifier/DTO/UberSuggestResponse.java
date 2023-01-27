package com.boti.mkdigital.productsidentifier.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UberSuggestResponse {

    private int suggestionCount;
    private UberSuggestion[] suggestions;
    private int nextKey;
    private UberSuggestion[] searchedKeywords;

}
