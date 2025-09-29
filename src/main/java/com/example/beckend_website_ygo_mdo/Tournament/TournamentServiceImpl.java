package com.example.beckend_website_ygo_mdo.Tournament;

import com.example.beckend_website_ygo_mdo.Deck.Deck;
import com.example.beckend_website_ygo_mdo.Tournament.DTO.DuelistDeckDTO;
import com.example.beckend_website_ygo_mdo.Tournament.DTO.TournamentDetailDTO;
import com.example.beckend_website_ygo_mdo.Tournament.DTO.TournamentListDTO;
import com.example.beckend_website_ygo_mdo.Tournament.DTO.TournamentSummaryDTO;
import com.example.beckend_website_ygo_mdo.duelist.Duelist;
import com.example.beckend_website_ygo_mdo.match.DTO.RoundMatchesDTO;
import com.example.beckend_website_ygo_mdo.match.DuelMatch;
import com.example.beckend_website_ygo_mdo.match.DuelMatchRepository;
import com.example.beckend_website_ygo_mdo.duelist.DuelistRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
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

    @Override
    public List<TournamentListDTO> getTournamentList() {
        List<Tournament> tournaments = tournamentRepository.findAll();

        return tournaments.stream().map(t -> new TournamentListDTO(
                t.getId(),
                t.getName(),
                t.getFormat(),
                t.getBanlist(),
                t.getTop1() != null ? t.getTop1().getName() : null,
                t.getTop2() != null ? t.getTop2().getName() : null,
                t.getTop3() != null ? t.getTop3().getName() : null
        )).toList();
    }


    @Override
    public List<RoundMatchesDTO> getTournamentMatches(Long tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found with id " + tournamentId));

        List<DuelMatch> matches = tournament.getMatches();

        // group by round
        Map<String, List<DuelMatch>> grouped = matches.stream()
                .collect(Collectors.groupingBy(DuelMatch::getRound));

        // convert to DTOs
        return grouped.entrySet().stream()
                .map(entry -> new RoundMatchesDTO(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(RoundMatchesDTO::getRound)) // optional: sort rounds
                .toList();
    }

    @Override
    public TournamentDetailDTO getTournamentDetail(Long tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found with id " + tournamentId));

        // Use a map to prevent duplicate duelist entries
        Map<Long, DuelistDeckDTO> participantMap = new HashMap<>();

        for (DuelMatch match : tournament.getMatches()) {
            if (match.getPlayer1() != null && match.getPlayer1Deck() != null) {
                participantMap.putIfAbsent(
                        match.getPlayer1().getId(),
                        new DuelistDeckDTO(match.getPlayer1().getName(), match.getPlayer1Deck().getName())
                );
            }

            if (match.getPlayer2() != null && match.getPlayer2Deck() != null) {
                participantMap.putIfAbsent(
                        match.getPlayer2().getId(),
                        new DuelistDeckDTO(match.getPlayer2().getName(), match.getPlayer2Deck().getName())
                );
            }
        }

        return new TournamentDetailDTO(
                tournament.getId(),
                tournament.getName(),
                tournament.getFormat(),
                new ArrayList<>(participantMap.values())
        );
    }







}
