package com.example.beckend_website_ygo_mdo.Deck;

import com.example.beckend_website_ygo_mdo.Deck.DTO.DeckDTO;
import com.example.beckend_website_ygo_mdo.Deck.DTO.DeckMatchDTO;
import com.example.beckend_website_ygo_mdo.Deck.DTO.TierGroupDTO;
import com.example.beckend_website_ygo_mdo.match.DuelMatch;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/decks")
public class DeckController {

    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    // CREATE single deck
    @PostMapping
    public Deck createDeck(@RequestBody Deck deck) {
        return deckService.createDeck(deck);
    }

    // BULK IMPORT decks
    @PostMapping("/import")
    public List<Deck> importDecks(@RequestBody List<Deck> decks) {
        return deckService.createDecks(decks);
    }

    // READ ALL decks
    @GetMapping
    public List<Deck> getAllDecks() {
        return deckService.getAllDecks();
    }

    // READ ONE deck by ID
    @GetMapping("/{id}")
    public Deck getDeckById(@PathVariable Long id) {
        return deckService.getDeckById(id);
    }

    @GetMapping("/{id}/matches")
    public List<DeckMatchDTO> getDeckMatches(@PathVariable Long id) {
        return deckService.getMatchesForDeck(id);
    }

    @GetMapping("/api/decks/tierlist")
    public List<DeckDTO> getDeckTierlist() {
        return deckService.getDecksWithTier();
    }

    @GetMapping("/tiers")
    public List<TierGroupDTO> getDecksGroupedByTier() {
        return deckService.getDecksGroupedByTier();
    }
}
