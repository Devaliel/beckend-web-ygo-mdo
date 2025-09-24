package com.example.beckend_website_ygo_mdo.match;

import com.example.beckend_website_ygo_mdo.duelist.Duelist;
import com.example.beckend_website_ygo_mdo.Deck.Deck;
import com.example.beckend_website_ygo_mdo.Tournament.Tournament;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "duel_match") // safe table name
public class DuelMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tournament this match belongs to
    @ManyToOne
    @JoinColumn(name = "tournament_id")
    @JsonIgnore
    private Tournament tournament;


    // Players
    @ManyToOne
    @JoinColumn(name = "player1_id")
    private Duelist player1;

    @ManyToOne
    @JoinColumn(name = "player2_id")
    private Duelist player2;

    // Decks used
    @ManyToOne
    @JoinColumn(name = "player1_deck_id")
    private Deck player1Deck;

    @ManyToOne
    @JoinColumn(name = "player2_deck_id")
    private Deck player2Deck;

    // Match result
    private Integer player1Score;
    private Integer player2Score;

    // Optional: match round or description
    private String round;
}
