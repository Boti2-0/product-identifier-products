package com.boti.mkdigital.productsidentifier.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class ClickBankMarketplaceSearchResponse {
   private Integer totalHits;
   private Integer offset;
   private List<ClickBankHitsResponse> hits;
}


