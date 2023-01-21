package com.bestChoice.bet.feign.fallback;

import com.bestChoice.bet.DTO.ResponseOddBookmakersList;
import com.bestChoice.bet.DTO.ResponseOddLeagueList;
import com.bestChoice.bet.DTO.ResponseOddMatchList;
import com.bestChoice.bet.DTO.ResponseOddMaxOddsList;
import com.bestChoice.bet.feign.client.OddClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Slf4j
public class OddClientFallbackFactory implements FallbackFactory<OddClient> {

    @Override
    public OddClient create(Throwable cause) {
        return new OddClient() {

            @Override
            public ResponseOddMatchList getMatchList(String startDate, String endDate, String status, String sport, Integer page, Integer perPage, String language, Integer popularLeaguesOnly, String geoCode) {
                log.error("fallback; getMatchList reason was: " + cause.getMessage());
                return null;
            }

            @Override
            public ResponseOddLeagueList getLeagues(Integer topLeaguesOnly, Integer includeLeaguesWithoutMatches, String sport, String language, String geoCode) {
                log.error("fallback; getLeagues reason was: " + cause.getMessage());
                return null;
            }

            @Override
            public ResponseOddMaxOddsList getMaxOdds(String startDate, String endDate, String status, Integer excludeSpecialStatus, Integer popularLeaguesOnly, String sport, Integer inplay, String language, String geoCode, String bookmakerGeoCode) {
                log.error("fallback; getMaxOdds reason was: " + cause.getMessage());
                return null;
            }

            @GetMapping(value = "/getBookmakersOverview")
            public ResponseOddBookmakersList getBookmakersOverview(String order,String sort, String language,String geoCode) {
                log.error("fallback; getBookmakersOverview reason was: " + cause.getMessage());
                return null;
            }


        };
    }
}
