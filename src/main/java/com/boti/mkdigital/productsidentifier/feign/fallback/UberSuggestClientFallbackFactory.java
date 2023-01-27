package com.boti.mkdigital.productsidentifier.feign.fallback;

import com.boti.mkdigital.productsidentifier.DTO.UberSuggestRequestParams;
import com.boti.mkdigital.productsidentifier.DTO.UberSuggestResponse;
import com.boti.mkdigital.productsidentifier.feign.client.UberSuggestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.Map;

@Slf4j
public class UberSuggestClientFallbackFactory implements FallbackFactory<UberSuggestClient> {

    @Override
    public UberSuggestClient create(Throwable cause) {
        return new UberSuggestClient() {
            @Override
            public UberSuggestResponse getMatchKeywords(Map<String, Object> headers, UberSuggestRequestParams params) {
                log.error("fallback; get match-keywords reason was: " + cause.getMessage() );
                return null;
            }
        };
    }
}
