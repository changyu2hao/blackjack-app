package com.example.blackjack.service;

import com.example.blackjack.entity.GameHistory;
import com.example.blackjack.entity.User;
import com.example.blackjack.repository.GameHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {

    private final GameHistoryRepository gameHistoryRepository;
    private final UserService userService;

    public HistoryService(GameHistoryRepository gameHistoryRepository, UserService userService) {
        this.gameHistoryRepository = gameHistoryRepository;
        this.userService = userService;
    }

    public Page<GameHistory> getHistoryForUser(UserDetails userDetails, int page) {
        User user = userService.getCurrentUser(userDetails);
        int currentPage = Math.max(page, 0);
        Pageable pageable = PageRequest.of(currentPage, 20);

        return gameHistoryRepository.findByUserOrderByCreatedAtDesc(user, pageable);
    }
}
