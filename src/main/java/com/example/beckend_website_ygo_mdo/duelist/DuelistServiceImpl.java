package com.example.beckend_website_ygo_mdo.duelist;

import com.example.beckend_website_ygo_mdo.Deck.Deck;
import com.example.beckend_website_ygo_mdo.duelist.DTO.DuelistMatchHistoryDTO;
import com.example.beckend_website_ygo_mdo.match.DuelMatch;
import com.example.beckend_website_ygo_mdo.match.DuelMatchRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DuelistServiceImpl implements DuelistService {

    private final DuelistRepository duelistRepository;
    private final DuelMatchRepository duelMatchRepository;

    public DuelistServiceImpl(DuelistRepository duelistRepository, DuelMatchRepository duelMatchRepository) {
        this.duelistRepository = duelistRepository;
        this.duelMatchRepository = duelMatchRepository;
    }

    @Override
    public Duelist registerDuelist(Duelist duelist) {
        // no password handling anymore
        return duelistRepository.save(duelist);
    }

    @Override
    public List<Duelist> getAllDuelists() {
        return duelistRepository.findAll();
    }

    @Override
    public Duelist getDuelistById(Long id) {
        return duelistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Duelist not found with id " + id));
    }

    // Optional: bulk save for Excel import
    public List<Duelist> saveAllDuelists(List<Duelist> duelists) {
        return duelistRepository.saveAll(duelists);
    }

    @Override
    public List<DuelistMatchHistoryDTO> getDuelistMatchHistory(Long duelistId) {
        List<DuelMatch> matches = duelMatchRepository.findByPlayer1IdOrPlayer2Id(duelistId, duelistId);

        return matches.stream().map(match -> {
            boolean isPlayer1 = match.getPlayer1().getId().equals(duelistId);
            Duelist opponent = isPlayer1 ? match.getPlayer2() : match.getPlayer1();
            Deck playerDeck = isPlayer1 ? match.getPlayer1Deck() : match.getPlayer2Deck();
            Deck opponentDeck = isPlayer1 ? match.getPlayer2Deck() : match.getPlayer1Deck();
            Integer playerScore = isPlayer1 ? match.getPlayer1Score() : match.getPlayer2Score();
            Integer opponentScore = isPlayer1 ? match.getPlayer2Score() : match.getPlayer1Score();

            return new DuelistMatchHistoryDTO(
                    match.getId(),
                    match.getTournament().getId(),
                    match.getTournament().getName(),
                    opponent.getId(),
                    opponent.getName(),
                    opponent.getAvatarUrl(),
                    playerDeck,
                    opponentDeck,
                    playerScore,
                    opponentScore,
                    match.getRound()
            );
        }).toList();
    }

    @Override
    public List<Duelist> getTop3Duelists() {
        return duelistRepository.findTop3ByOrderByPointDesc();
    }

    @Override
    public List<Duelist> getRestDuelists() {
        List<Duelist> all = duelistRepository.findAllByOrderByPointDesc();
        return all.size() > 3 ? all.subList(3, all.size()) : List.of();
    }
}
