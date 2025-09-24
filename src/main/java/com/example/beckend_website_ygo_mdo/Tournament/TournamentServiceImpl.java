package com.example.beckend_website_ygo_mdo.Tournament;

import com.example.beckend_website_ygo_mdo.Deck.Deck;
import com.example.beckend_website_ygo_mdo.Tournament.DTO.TournamentSummaryDTO;
import com.example.beckend_website_ygo_mdo.duelist.Duelist;
import com.example.beckend_website_ygo_mdo.match.DuelMatch;
import com.example.beckend_website_ygo_mdo.match.DuelMatchRepository;
import com.example.beckend_website_ygo_mdo.duelist.DuelistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final DuelistRepository duelistRepository;
    private final DuelMatchRepository duelMatchRepository;

    public TournamentServiceImpl(TournamentRepository tournamentRepository,
                                 DuelistRepository duelistRepository,
                                 DuelMatchRepository duelMatchRepository) {
        this.tournamentRepository = tournamentRepository;
        this.duelistRepository = duelistRepository;
        this.duelMatchRepository = duelMatchRepository;
    }

    @Override
    public Tournament createTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    @Override
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    @Override
    public Tournament getTournamentById(Long id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tournament not found with id " + id));
    }

    @Override
    public Tournament addPlayersToTournament(Long tournamentId, List<Long> playerIds) {
        Tournament tournament = getTournamentById(tournamentId);
        List<Duelist> players = duelistRepository.findAllById(playerIds);
        tournament.getPlayers().addAll(players);
        return tournamentRepository.save(tournament);
    }

    @Override
    public DuelMatch addMatchToTournament(Long tournamentId, DuelMatch match) {
        Tournament tournament = getTournamentById(tournamentId);
        match.setTournament(tournament);
        DuelMatch savedMatch = duelMatchRepository.save(match);
        tournament.getMatches().add(savedMatch);
        tournamentRepository.save(tournament);
        return savedMatch;
    }

    @Override
    public List<Duelist> getTournamentPlayers(Long tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found with id " + tournamentId));
        return tournament.getPlayers(); // assuming a getPlayers() List<Duelist>
    }

    @Override
    public List<DuelMatch> getTournamentMatches(Long tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found with id " + tournamentId));
        return tournament.getMatches(); // assuming a getMatches() List<DuelMatch>
    }

    @Override
    public TournamentSummaryDTO getTournamentSummary(Long tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));

        TournamentSummaryDTO dto = new TournamentSummaryDTO();
        dto.setTournamentId(tournament.getId());
        dto.setName(tournament.getName());
        dto.setFormat(tournament.getFormat());
        dto.setBanlist(tournament.getBanlist());
        dto.setTop1(tournament.getTop1());
        dto.setTop2(tournament.getTop2());
        dto.setTop3(tournament.getTop3());
        dto.setMatches(tournament.getMatches());

        // Collect all unique decks used in matches
        List<Deck> decks = tournament.getMatches().stream()
                .flatMap(m -> List.of(m.getPlayer1Deck(), m.getPlayer2Deck()).stream())
                .distinct()
                .toList();
        dto.setDecks(decks);

        return dto;
    }

    @Override
    public Tournament assignTopPlayers(Long tournamentId, Long top1Id, Long top2Id, Long top3Id) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));

        Duelist top1 = duelistRepository.findById(top1Id)
                .orElseThrow(() -> new RuntimeException("Top1 Duelist not found"));
        Duelist top2 = duelistRepository.findById(top2Id)
                .orElseThrow(() -> new RuntimeException("Top2 Duelist not found"));
        Duelist top3 = duelistRepository.findById(top3Id)
                .orElseThrow(() -> new RuntimeException("Top3 Duelist not found"));

        tournament.setTop1(top1);
        tournament.setTop2(top2);
        tournament.setTop3(top3);

        return tournamentRepository.save(tournament);
    }




}
