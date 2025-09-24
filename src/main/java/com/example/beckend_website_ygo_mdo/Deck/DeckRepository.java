package com.example.beckend_website_ygo_mdo.Deck;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
    // You can add custom queries here later if needed
}
