package com.example.blackjack.repository;
import com.example.blackjack.entity.GameHistory;
import com.example.blackjack.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameHistoryRepository extends JpaRepository<GameHistory, Long>{
    List<GameHistory> findByUserOrderByCreatedAtDesc(User user);
    Page<GameHistory> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
