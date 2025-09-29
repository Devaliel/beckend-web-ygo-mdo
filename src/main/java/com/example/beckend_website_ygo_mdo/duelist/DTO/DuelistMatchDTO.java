package com.example.beckend_website_ygo_mdo.duelist.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DuelistMatchDTO {
    private Long matchId;
    private String round;
    private DeckInfoDTO playerDeck;
    private String opponentName;
    private DeckInfoDTO opponentDeck;
    private Integer playerScore;
    private Integer opponentScore;
}
