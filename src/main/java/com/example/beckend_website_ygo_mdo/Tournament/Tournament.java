package com.example.beckend_website_ygo_mdo.Tournament;

import com.example.beckend_website_ygo_mdo.duelist.Duelist;
import com.example.beckend_website_ygo_mdo.match.DuelMatch;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String format;    // e.g., Swiss, Single Elimination
    private String banlist;   // e.g., OCG/TCG list

    // Players participating in this tournament
    @ManyToMany
    @JoinTable(
            name = "tournament_players",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "duelist_id")
    )
    private List<Duelist> players = new ArrayList<>();

    // Matches in this tournament
    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DuelMatch> matches = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "top1_id")
    private Duelist top1;

    @ManyToOne
    @JoinColumn(name = "top2_id")
    private Duelist top2;

    @ManyToOne
    @JoinColumn(name = "top3_id")
    private Duelist top3;

}
