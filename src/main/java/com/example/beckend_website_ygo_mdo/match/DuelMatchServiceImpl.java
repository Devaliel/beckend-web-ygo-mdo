package com.example.beckend_website_ygo_mdo.match;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DuelMatchServiceImpl implements DuelMatchService {

    private final DuelMatchRepository duelMatchRepository;

    public DuelMatchServiceImpl(DuelMatchRepository duelMatchRepository) {
        this.duelMatchRepository = duelMatchRepository;
    }

    @Override
    public DuelMatch createMatch(DuelMatch match) {
        return duelMatchRepository.save(match);
    }

    @Override
    public List<DuelMatch> getAllMatches() {
        return duelMatchRepository.findAll();
    }

    @Override
    public DuelMatch getMatchById(Long id) {
        return duelMatchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found with id " + id));
    }
}
