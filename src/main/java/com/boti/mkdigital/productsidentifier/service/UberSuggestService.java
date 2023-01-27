package com.boti.mkdigital.productsidentifier.service;

import com.boti.mkdigital.productsidentifier.DTO.UberSuggestRequestParams;
import com.boti.mkdigital.productsidentifier.DTO.UberSuggestResponse;
import com.boti.mkdigital.productsidentifier.feign.client.UberSuggestClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UberSuggestService {
    private final UberSuggestClient client;

    public UberSuggestResponse getMatchKeywords(String keyword){


        Map<String, Object> headerMap = new HashMap<>();
        UberSuggestRequestParams params = UberSuggestRequestParams.builder().keywords(Arrays.asList(keyword)).locId(2840).language("en").build();
        headerMap.put("Authorization", "Bearer app#unlogged__feaa5414eee2e0420c92911a4072430cb40d3f86");
        headerMap.put("Host", "app.neilpatel.com");

        return client.getMatchKeywords(headerMap, params);
    }
}