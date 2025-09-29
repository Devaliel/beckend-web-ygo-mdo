package com.example.beckend_website_ygo_mdo.Tournament.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TournamentDetailDTO {
    private Long id;
    private String name;
    private String date;
    private List<DuelistDeckDTO> participants;
}
