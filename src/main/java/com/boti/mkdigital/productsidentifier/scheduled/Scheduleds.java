package com.bestChoice.bet.runner;

import com.bestChoice.bet.DTO.ResponseOddLeagueList;
import com.bestChoice.bet.DTO.ResponseOddMatchList;
import com.bestChoice.bet.DTO.ResponseOddMaxOddsList;
import com.bestChoice.bet.feign.client.OddClient;
import com.bestChoice.bet.service.BookmakerService;
import com.bestChoice.bet.service.LeagueService;
import com.bestChoice.bet.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class Scheduleds {

    @Autowired
    private MatchService matchService;
    @Autowired
    private BookmakerService bookmakerService;


    @Scheduled(fixedDelay = 100000000)
//    @Scheduled(cron = "0 0 12 * * ?")
    public void getMatchListOfToday() {
        matchService.updateMatches();
        bookmakerService.updateBookmakers();
    }

}