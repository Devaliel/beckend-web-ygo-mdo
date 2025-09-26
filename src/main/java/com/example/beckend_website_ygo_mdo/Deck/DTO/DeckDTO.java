package com.example.beckend_website_ygo_mdo.Deck.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class DeckDTO {
    private Long id;
    private String name;
    private Integer participation;
    private Integer topping;
    private Integer gold;
    private Integer silver;
    private Integer bronze;
    private Integer win;
    private Integer draw;
    private Integer lose;
    private Integer point;
    private String imageUrl;

    // computed
    private Double winrate;       // win rate %
    private String tier;          // Tier 1, Tier 2, Tier 3, Others
    private Double metaShare;     // participation share in % (optional, for graphs)
}
