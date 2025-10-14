package com.example.beckend_website_ygo_mdo.duelist;

import com.example.beckend_website_ygo_mdo.duelist.DTO.DuelistDeckStatsDTO;
import com.example.beckend_website_ygo_mdo.duelist.DTO.DuelistMatchHistoryDTO;
import com.example.beckend_website_ygo_mdo.duelist.DTO.DuelistMatchesResponse;

import java.util.List;

public interface DuelistService {
    Duelist registerDuelist(Duelist duelist);       // Create single duelist
    List<Duelist> getAllDuelists();                 // Read all duelists
    Duelist getDuelistById(Long id);                // Read duelist by ID
    List<Duelist> saveAllDuelists(List<Duelist> duelists); // Optional bulk save
    List<DuelistMatchHistoryDTO> getDuelistMatchHistory(Long duelistId);

    List<Duelist> getTop3Duelists();
    List<Duelist> getRestDuelists();
    DuelistMatchesResponse getDuelistMatches(Long duelistId);
    List<DuelistDeckStatsDTO> getDuelistDeckStats(Long duelistId);
}
