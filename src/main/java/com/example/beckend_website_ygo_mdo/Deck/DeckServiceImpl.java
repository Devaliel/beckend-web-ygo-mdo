package com.example.beckend_website_ygo_mdo.Deck;

import com.example.beckend_website_ygo_mdo.Deck.DTO.DeckDTO;
import com.example.beckend_website_ygo_mdo.Deck.DTO.DeckMatchDTO;
import com.example.beckend_website_ygo_mdo.Deck.DTO.TierGroupDTO;
import com.example.beckend_website_ygo_mdo.match.DuelMatch;
import com.example.beckend_website_ygo_mdo.match.DuelMatchRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
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



    public List<DeckDTO> getDecksWithTier() {
        List<Deck> decks = deckRepository.findAll();

        int maxParticipation = decks.stream()
                .map(d -> d.getParticipation() != null ? d.getParticipation() : 0)
                .max(Integer::compareTo)
                .orElse(1); // avoid divide by 0

        return decks.stream().map(d -> {
            int wins = d.getWin() != null ? d.getWin() : 0;
            int losses = d.getLose() != null ? d.getLose() : 0;
            int draws = d.getDraw() != null ? d.getDraw() : 0;
            int total = wins + losses + draws;

            double winrate = total > 0 ? (wins * 100.0 / total) : 0.0;

            int participation = d.getParticipation() != null ? d.getParticipation() : 0;
            double participationPercent = (participation * 100.0) / maxParticipation;

            String tier;
            if (winrate >= 55 && participationPercent >= 15) {
                tier = "Tier 1";
            } else if (winrate >= 50 && participationPercent >= 8) {
                tier = "Tier 2";
            } else if (winrate >= 45 && participationPercent >= 3) {
                tier = "Tier 3";
            } else {
                tier = "Others";
            }

            return new DeckDTO(
                    d.getId(),
                    d.getName(),
                    d.getParticipation(),
                    d.getTopping(),
                    d.getGold(),
                    d.getSilver(),
                    d.getBronze(),
                    d.getWin(),
                    d.getDraw(),
                    d.getLose(),
                    d.getPoint(),
                    d.getImageUrl(),
                    winrate,
                    tier,
                    null // we don’t need score anymore
            );
        }).collect(Collectors.toList());
    }
    public List<TierGroupDTO> getDecksGroupedByTier() {
        List<DeckDTO> allDecks = getDecksWithTier(); // reuse your existing method

        // Group decks by tier
        Map<String, List<DeckDTO>> grouped = allDecks.stream()
                .collect(Collectors.groupingBy(DeckDTO::getTier));

        // Convert to List<TierGroupDTO>
        return grouped.entrySet().stream()
                .map(e -> new TierGroupDTO(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(TierGroupDTO::getTier)) // optional sorting
                .collect(Collectors.toList());
    }



}
