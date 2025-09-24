package com.example.beckend_website_ygo_mdo.duelist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DuelistRepository extends JpaRepository<Duelist, Long> {
    // later we can add custom queries, e.g. findByUsername
}
