package com.example.beckend_website_ygo_mdo.Deck;

import com.example.beckend_website_ygo_mdo.Deck.DTO.DeckMatchDTO;

import java.util.List;

public interface DeckService {
    Deck createDeck(Deck deck);           // Create single deck
    List<Deck> createDecks(List<Deck> decks); // Bulk create decks
    List<Deck> getAllDecks();             // Read all decks
    Deck getDeckById(Long id);            // Read deck by ID
    List<DeckMatchDTO> getMatchesForDeck(Long deckId);
}
