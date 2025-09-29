package com.example.beckend_website_ygo_mdo.match;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DuelMatchRepository extends JpaRepository<DuelMatch, Long> {
    List<DuelMatch> findByPlayer1IdOrPlayer2Id(Long player1Id, Long player2Id);
    List<DuelMatch> findAllByPlayer1DeckIdOrPlayer2DeckId(Long player1DeckId, Long player2DeckId);
    List<DuelMatch> findByPlayer1_IdOrPlayer2_Id(Long player1Id, Long player2Id);

}
