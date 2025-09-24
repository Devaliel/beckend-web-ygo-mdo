package com.example.beckend_website_ygo_mdo.Deck.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeckMatchDTO {
    private Long matchId;
    private String player1Name;
    private String player2Name;
    private String player1DeckName;
    private String player2DeckName;
    private Integer player1Score;
    private Integer player2Score;
    private String round;
}
