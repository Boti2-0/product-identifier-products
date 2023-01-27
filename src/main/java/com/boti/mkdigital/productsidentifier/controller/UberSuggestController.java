package com.boti.mkdigital.productsidentifier.controller;

import com.boti.mkdigital.productsidentifier.DTO.UberSuggestResponse;
import com.boti.mkdigital.productsidentifier.service.UberSuggestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@RestController
public class UberSuggestController {

    private final UberSuggestService service;

    @GetMapping("/uber-suggest/{keyword}")
    public ResponseEntity<UberSuggestResponse> home(@PathVariable String keyword) throws UnsupportedEncodingException, JsonProcessingException {
        UberSuggestResponse res = service.getMatchKeywords(keyword);
        return ResponseEntity.ok().body(res);
    }

}
