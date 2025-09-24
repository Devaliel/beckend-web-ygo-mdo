package com.example.beckend_website_ygo_mdo.Deck;

import com.example.beckend_website_ygo_mdo.Deck.DTO.DeckMatchDTO;
import com.example.beckend_website_ygo_mdo.match.DuelMatch;
import com.example.beckend_website_ygo_mdo.match.DuelMatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeckServiceImpl implements DeckService {

    private final DeckRepository deckRepository;
    private final DuelMatchRepository duelMatchRepository;
    public DeckServiceImpl(DeckRepository deckRepository, DuelMatchRepository duelMatchRepository) {
        this.deckRepository = deckRepository;

        this.duelMatchRepository = duelMatchRepository;
    }

    @Override
    public Deck createDeck(Deck deck) {
        return deckRepository.save(deck);
    }

    @Override
    public List<Deck> createDecks(List<Deck> decks) {
        return decks.stream()
                .map(deckRepository::save)
                .collect(Collectors.toList());
    }

    @Override
    public List<Deck> getAllDecks() {
        return deckRepository.findAll();
    }

    @Override
    public Deck getDeckById(Long id) {
        return deckRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deck not found with id " + id));
    }


    @Override
    public List<DeckMatchDTO> getMatchesForDeck(Long deckId) {
        List<DuelMatch> matches = duelMatchRepository.findAllByPlayer1DeckIdOrPlayer2DeckId(deckId, deckId);
        return matches.stream().map(m -> new DeckMatchDTO(
                m.getId(),
                m.getPlayer1().getName(),
                m.getPlayer2().getName(),
                m.getPlayer1Deck().getName(),
                m.getPlayer2Deck().getName(),
                m.getPlayer1Score(),
                m.getPlayer2Score(),
                m.getRound()
        )).toList();
    }
}
