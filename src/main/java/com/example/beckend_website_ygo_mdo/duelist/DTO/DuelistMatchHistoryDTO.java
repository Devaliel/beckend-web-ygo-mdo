package com.example.beckend_website_ygo_mdo.duelist.DTO;

import com.example.beckend_website_ygo_mdo.Deck.Deck;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DuelistMatchHistoryDTO {
    private Long matchId;
    private Long tournamentId;
    private String tournamentName;
    private Long opponentId;
    private String opponentName;
    private String opponentAvatar;
    private Deck playerDeck;
    private Deck opponentDeck;
    private Integer playerScore;
    private Integer opponentScore;
    private String round;
}
