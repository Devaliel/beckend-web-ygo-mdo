package com.example.beckend_website_ygo_mdo.duelist.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuelistDeckStatsDTO {
    private Long duelistId;
    private String duelistName;
    private Long deckId;
    private String deckName;
    private Integer matchesPlayed;
    private Integer wins;
    private Integer losses;
    private Integer draws;

}
