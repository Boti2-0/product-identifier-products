package com.boti.mkdigital.productsidentifier.feign.client;

import com.boti.mkdigital.productsidentifier.DTO.UberSuggestRequestParams;
import com.boti.mkdigital.productsidentifier.DTO.UberSuggestResponse;
import com.boti.mkdigital.productsidentifier.feign.fallback.UberSuggestClientFallbackFactory;
import feign.HeaderMap;
import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@Component
@FeignClient(name = "${uberSuggest.service.name}", url = "${uberSuggest.host}", path = "/${uberSuggest.path}",
        fallback = UberSuggestClientFallbackFactory.class)

public interface UberSuggestClient {
    @PostMapping("/match_keywords")
    UberSuggestResponse getMatchKeywords(@RequestHeader Map<String, Object> headers, @RequestBody() UberSuggestRequestParams params);

}
