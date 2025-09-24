package com.example.beckend_website_ygo_mdo.Tournament.DTO;

import com.example.beckend_website_ygo_mdo.duelist.Duelist;
import com.example.beckend_website_ygo_mdo.Deck.Deck;
import com.example.beckend_website_ygo_mdo.match.DuelMatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TournamentSummaryDTO {
    private Long tournamentId;
    private String name;
    private String format;
    private String banlist;

    private Duelist top1;
    private Duelist top2;
    private Duelist top3;     // Top 1-3
    private List<DuelMatch> matches;         // All matches in tournament
    private List<Deck> decks;                // All decks used in tournament
}
