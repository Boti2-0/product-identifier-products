package com.bestChoice.bet.repository;

import com.bestChoice.bet.domain.Bookmaker;
import com.bestChoice.bet.domain.Match;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmakerRepository
    extends
        CrudRepository<Bookmaker, Integer> {
    List<Bookmaker> findAllByActive(boolean b);
    List<Bookmaker> findAll();
}
