package com.example.beckend_website_ygo_mdo.Deck.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class TierGroupDTO {
    private String tier;           // "Tier 1", "Tier 2", etc.
    private List<DeckDTO> decks;   // decks inside this tier
}
