package com.example.beckend_website_ygo_mdo.Tournament.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TournamentListDTO {
    private Long id;
    private String name;
    private String format;
    private String banlist;
    private String top1;
    private String top2;
    private String top3;
}
