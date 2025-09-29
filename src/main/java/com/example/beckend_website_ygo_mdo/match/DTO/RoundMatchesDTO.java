package com.example.beckend_website_ygo_mdo.match.DTO;

import com.example.beckend_website_ygo_mdo.match.DuelMatch;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RoundMatchesDTO {
    private String round;
    private List<DuelMatch> matches;
}
