package com.example.beckend_website_ygo_mdo.Tournament;

import com.example.beckend_website_ygo_mdo.Tournament.DTO.TournamentDetailDTO;
import com.example.beckend_website_ygo_mdo.Tournament.DTO.TournamentListDTO;
import com.example.beckend_website_ygo_mdo.Tournament.DTO.TournamentSummaryDTO;
import com.example.beckend_website_ygo_mdo.duelist.Duelist;
import com.example.beckend_website_ygo_mdo.match.DTO.RoundMatchesDTO;
import com.example.beckend_website_ygo_mdo.match.DuelMatch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    // Create a new tournament
    @PostMapping
    public Tournament createTournament(@RequestBody Tournament tournament) {
        return tournamentService.createTournament(tournament);
    }

    // Get all tournaments
    @GetMapping
    public List<Tournament> getAllTournaments() {
        return tournamentService.getAllTournaments();
    }

    // Get tournament by ID
    @GetMapping("/{id}")
    public Tournament getTournamentById(@PathVariable Long id) {
        return tournamentService.getTournamentById(id);
    }

    // Add players to a tournament
    @PostMapping("/{id}/players")
    public Tournament addPlayersToTournament(@PathVariable Long id, @RequestBody List<Long> playerIds) {
        return tournamentService.addPlayersToTournament(id, playerIds);
    }

    // Add a duel match to a tournament
    // Add a match to a tournament
    @PostMapping("/{id}/matches")
    public ResponseEntity<String> addMatchToTournament(@PathVariable Long id, @RequestBody DuelMatch match) {
        tournamentService.addMatchToTournament(id, match);
        return ResponseEntity.ok("Match added successfully!");
    }

    @GetMapping("/{id}/players")
    public List<Duelist> getTournamentPlayers(@PathVariable Long id) {
        return tournamentService.getTournamentPlayers(id);
    }



    @GetMapping("/{id}/summary")
    public TournamentSummaryDTO getTournamentSummary(@PathVariable Long id) {
        return tournamentService.getTournamentSummary(id);
    }


    @PostMapping("/{id}/top")
    public Tournament setTopPlayers(
            @PathVariable Long id,
            @RequestBody List<Long> topPlayerIds // Expecting exactly 3 IDs
    ) {
        if (topPlayerIds.size() != 3) {
            throw new IllegalArgumentException("You must provide exactly 3 Duelist IDs for top 1-3");
        }
        return tournamentService.assignTopPlayers(id, topPlayerIds.get(0), topPlayerIds.get(1), topPlayerIds.get(2));
    }


    @GetMapping("/list")
    public List<TournamentListDTO> getTournamentList() {
        return tournamentService.getTournamentList();
    }

    @GetMapping("/{tournamentId}/matches")
    public List<RoundMatchesDTO> getTournamentMatches(@PathVariable Long tournamentId) {
        return tournamentService.getTournamentMatches(tournamentId);
    }

    @GetMapping("/details/{tournamentId}")
    public TournamentDetailDTO getTournamentDetail(@PathVariable Long tournamentId) {
        return tournamentService.getTournamentDetail(tournamentId);
    }




}
