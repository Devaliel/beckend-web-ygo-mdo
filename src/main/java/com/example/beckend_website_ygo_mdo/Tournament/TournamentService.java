package com.example.beckend_website_ygo_mdo.Tournament;

import com.example.beckend_website_ygo_mdo.Tournament.DTO.TournamentDetailDTO;
import com.example.beckend_website_ygo_mdo.Tournament.DTO.TournamentListDTO;
import com.example.beckend_website_ygo_mdo.Tournament.DTO.TournamentSummaryDTO;
import com.example.beckend_website_ygo_mdo.duelist.Duelist;
import com.example.beckend_website_ygo_mdo.match.DTO.RoundMatchesDTO;
import com.example.beckend_website_ygo_mdo.match.DuelMatch;

import java.util.List;

public interface TournamentService {

    // Create a new tournament
    Tournament createTournament(Tournament tournament);

    // Get all tournaments
    List<Tournament> getAllTournaments();

    // Get tournament by ID
    Tournament getTournamentById(Long id);

    // Add players to an existing tournament
    Tournament addPlayersToTournament(Long tournamentId, List<Long> playerIds);

    // Add a match to an existing tournament
    DuelMatch addMatchToTournament(Long tournamentId, DuelMatch match);


    // Get only the players of a tournament
    List<Duelist> getTournamentPlayers(Long tournamentId);

    // Get only the matches of a tournament


    TournamentSummaryDTO getTournamentSummary(Long tournamentId);

    Tournament assignTopPlayers(Long tournamentId, Long top1Id, Long top2Id, Long top3Id);
    List<TournamentListDTO> getTournamentList();

    List<RoundMatchesDTO> getTournamentMatches(Long tournamentId);
    TournamentDetailDTO getTournamentDetail(Long tournamentId);

}
