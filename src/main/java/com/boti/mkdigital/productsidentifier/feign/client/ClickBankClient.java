package com.bestChoice.bet.feign.client;

import com.bestChoice.bet.DTO.ResponseOddBookmakersList;
import com.bestChoice.bet.DTO.ResponseOddLeagueList;
import com.bestChoice.bet.DTO.ResponseOddMatchList;
import com.bestChoice.bet.DTO.ResponseOddMaxOddsList;
import com.bestChoice.bet.feign.fallback.OddClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Component
@FeignClient(name = "${oddspedia.service.name}", url = "${oddspedia.host}", path = "/${oddspedia.path}",
        fallback = OddClientFallbackFactory.class)
public interface OddClient {

    @GetMapping(value = "/getMatchList")
    ResponseOddMatchList getMatchList(@RequestParam(name = "startDate", required = false)
                                      String startDate,
                                      @RequestParam(name = "endDate", required = false)
                                      String endDate,
                                      @RequestParam(name = "status", required = false)
                                      String status,
                                      @RequestParam(name = "sport", required = false)
                                      String sport,
                                      @RequestParam(name = "page", required = false)
                                      Integer page,
                                      @RequestParam(name = "perPage", required = false)
                                      Integer perPage,
                                      @RequestParam(name = "language", required = false)
                                      String language,
                                      @RequestParam(name = "popularLeaguesOnly", required = false)
                                      Integer popularLeaguesOnly,
                                      @RequestParam(name = "geoCode", required = false)
                                      String geoCode);

    @GetMapping(value = "/getLeagues")
    ResponseOddLeagueList getLeagues(@RequestParam(name = "topLeaguesOnly", required = false)
                                      Integer topLeaguesOnly,
                                     @RequestParam(name = "includeLeaguesWithoutMatches", required = false)
                                      Integer includeLeaguesWithoutMatches,
                                     @RequestParam(name = "sport", required = false)
                                      String sport,
                                     @RequestParam(name = "language", required = false)
                                      String language,
                                     @RequestParam(name = "geoCode", required = false)
                                     String geoCode);

    @GetMapping(value = "/getMaxOdds")
    ResponseOddMaxOddsList getMaxOdds(@RequestParam(name = "startDate", required = false)
                                     String startDate,
                                      @RequestParam(name = "endDate", required = false)
                                     String endDate,
                                      @RequestParam(name = "status", required = false)
                                     String status,
                                      @RequestParam(name = "excludeSpecialStatus", required = false)
                                     Integer excludeSpecialStatus,
                                      @RequestParam(name = "popularLeaguesOnly", required = false)
                                     Integer popularLeaguesOnly,
                                      @RequestParam(name = "sport", required = false)
                                     String sport,
                                      @RequestParam(name = "inplay", required = false)
                                     Integer inplay,
                                      @RequestParam(name = "language", required = false)
                                     String language,
                                      @RequestParam(name = "geoCode", required = false)
                                      String geoCode,
                                      @RequestParam(name = "bookmakerGeoCode", required = false)
                                      String bookmakerGeoCode);

    @GetMapping(value = "/getBookmakersOverview")
    ResponseOddBookmakersList getBookmakersOverview(@RequestParam(name = "order", required = false)
                                      String order,
                                                    @RequestParam(name = "sort", required = false)
                                      String sort,
                                                    @RequestParam(name = "language", required = false)
                                      String language,
                                                    @RequestParam(name = "geoCode", required = false)
                                      String geoCode);

}
