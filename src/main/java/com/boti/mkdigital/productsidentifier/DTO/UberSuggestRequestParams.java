package com.boti.mkdigital.productsidentifier.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class UberSuggestRequestParams {

    private List<String> keywords;
    private int locId;
    private String language;
//    private String sortby;
//    private int limit;
//    private int previousKey;
//    private Map<String, Object> filters;

}
