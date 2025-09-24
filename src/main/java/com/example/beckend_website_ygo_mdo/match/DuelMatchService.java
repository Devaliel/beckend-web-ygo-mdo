package com.example.beckend_website_ygo_mdo.match;

import java.util.List;

public interface DuelMatchService {
    DuelMatch createMatch(DuelMatch match);       // Create a match
    List<DuelMatch> getAllMatches();             // Get all matches
    DuelMatch getMatchById(Long id);             // Get match by ID
}
