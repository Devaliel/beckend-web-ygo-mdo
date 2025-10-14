package com.example.beckend_website_ygo_mdo.duelist;

import com.example.beckend_website_ygo_mdo.match.DuelMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DuelistRepository extends JpaRepository<Duelist, Long> {
    // later we can add custom queries, e.g. findByUsername

    List<Duelist> findTop3ByOrderByPointDesc();

    List<Duelist> findAllByOrderByPointDesc();

    @Query(value = """
        SELECT 
            d.id AS duelistId,
            d.name AS duelistName,
            dk.id AS deckId,
            dk.name AS deckName,
            COUNT(DISTINCT m.id) AS matchesPlayed,
            SUM(
                CASE 
                    WHEN (m.player1_id = d.id AND m.player1score > m.player2score)
                      OR (m.player2_id = d.id AND m.player2score > m.player1score)
                    THEN 1 ELSE 0 
                END
            ) AS wins,
            SUM(
                CASE 
                    WHEN (m.player1_id = d.id AND m.player1score < m.player2score)
                      OR (m.player2_id = d.id AND m.player2score < m.player1score)
                    THEN 1 ELSE 0 
                END
            ) AS losses,
            SUM(
                CASE 
                    WHEN (m.player1_id = d.id AND m.player1score = m.player2score)
                      OR (m.player2_id = d.id AND m.player2score = m.player1score)
                    THEN 1 ELSE 0 
                END
            ) AS draws
        FROM duelist d
        JOIN duel_match m 
            ON d.id IN (m.player1_id, m.player2_id)
        JOIN deck dk 
            ON (dk.id = m.player1_deck_id AND d.id = m.player1_id)
            OR (dk.id = m.player2_deck_id AND d.id = m.player2_id)
        WHERE d.id = :duelistId
        GROUP BY d.id, d.name, dk.id, dk.name
        ORDER BY dk.name
        """,
            nativeQuery = true)
    List<Object[]> findDeckStatsByDuelistId(@Param("duelistId") Long duelistId);

}
