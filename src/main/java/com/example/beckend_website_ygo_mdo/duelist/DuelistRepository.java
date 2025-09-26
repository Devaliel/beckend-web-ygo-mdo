package com.example.beckend_website_ygo_mdo.duelist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DuelistRepository extends JpaRepository<Duelist, Long> {
    // later we can add custom queries, e.g. findByUsername

    List<Duelist> findTop3ByOrderByPointDesc();

    List<Duelist> findAllByOrderByPointDesc();
}
