package com.boti.mkdigital.productsidentifier.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ClickBankMarketplaceSearchResponse {
   private Integer totalHits;
   private Integer offset;
   private List<ClickBankHitsResponse> hits;
}


