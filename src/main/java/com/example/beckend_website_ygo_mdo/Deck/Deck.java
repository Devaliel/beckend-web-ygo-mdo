package com.example.beckend_website_ygo_mdo.Deck;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
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

    // optional now
    private String imageUrl;
}
