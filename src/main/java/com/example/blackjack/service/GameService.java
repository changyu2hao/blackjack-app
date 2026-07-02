package com.example.blackjack.service;

import com.example.blackjack.entity.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.example.blackjack.entity.GameHistory;
import com.example.blackjack.game.BlackjackGame;
import com.example.blackjack.repository.GameHistoryRepository;

import java.math.BigDecimal;

@Service
public class GameService {

    private final GameHistoryRepository gameHistoryRepository;
    private final UserService userService;
    public GameService(GameHistoryRepository gameHistoryRepository,
                       UserService userService) {
        this.gameHistoryRepository = gameHistoryRepository;
        this.userService = userService;
    }
    public String validateBetAmount(BigDecimal betAmount, User user) {
        if (betAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return "Bet amount must be greater than 0";
        }

        if (betAmount.compareTo(user.getBalance()) > 0) {
            return "Bet amount cannot exceed your balance";
        }

        return null;
    }
    public void hit(BlackjackGame game, UserDetails userDetails) {
        if (game == null) {
            return;
        }

        game.playerHit();

        User user = userService.getCurrentUser(userDetails);
        settleBalanceIfGameOver(game, user);
    }
    public void stand(BlackjackGame game, UserDetails userDetails) {
        if (game == null) {
            return;
        }

        game.playerStand();

        User user = userService.getCurrentUser(userDetails);
        settleBalanceIfGameOver(game, user);
    }

    public BlackjackGame startGame(BigDecimal betAmount, UserDetails userDetails, BlackjackGame existingGame) {
        if (existingGame != null && !existingGame.isGameOver()) {
            throw new RuntimeException("Finish the current game before starting a new one");
        }

        User user = userService.getCurrentUser(userDetails);

        String error = validateBetAmount(betAmount, user);
        if (error != null) {
            throw new RuntimeException(error);
        }

        BlackjackGame game = new BlackjackGame(betAmount);
        settleBalanceIfGameOver(game, user);

        return game;
    }

    public void validateNewGame(BlackjackGame game) {
        if (game != null && !game.isGameOver()) {
            throw new RuntimeException("Finish the current game before starting a new one");
        }
    }

    private void settleBalanceIfGameOver(BlackjackGame game, User user) {
        if (game == null || !game.isGameOver() || game.isSettled()) {
            return;
        }

        BigDecimal balance = user.getBalance();
        BigDecimal betAmount = game.getBetAmount();

        switch (game.getResult()) {
            case "WIN" -> user.setBalance(balance.add(betAmount));
            case "BLACKJACK" -> user.setBalance(balance.add(betAmount.multiply(new BigDecimal("1.5"))));
            case "LOSE" -> user.setBalance(balance.subtract(betAmount));
            case "DRAW" -> {
                // Balance stays the same.
            }
        }

        userService.save(user);

        GameHistory history = new GameHistory(
                user,
                betAmount,
                game.getResult(),
                game.getPlayerHand().getScore(),
                game.getDealerHand().getScore(),
                user.getBalance()
        );

        gameHistoryRepository.save(history);
        game.markSettled();
    }
}
