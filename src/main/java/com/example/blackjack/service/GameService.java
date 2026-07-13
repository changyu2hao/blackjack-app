package com.example.blackjack.service;

import com.example.blackjack.entity.GameHistory;
import com.example.blackjack.entity.User;
import com.example.blackjack.game.BlackjackGame;
import com.example.blackjack.repository.GameHistoryRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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

    public BlackjackGame startGame(BigDecimal betAmount,
                                   UserDetails userDetails,
                                   BlackjackGame existingGame) {
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

    public void split(BlackjackGame game, UserDetails userDetails) {
        if (game == null || !game.canSplit()) {
            throw new RuntimeException("This hand cannot be split");
        }

        User user = userService.getCurrentUser(userDetails);
        BigDecimal totalAfterSplit = game.getBetAmount().multiply(BigDecimal.valueOf(2));

        if (totalAfterSplit.compareTo(user.getBalance()) > 0) {
            throw new RuntimeException("Insufficient balance to split");
        }

        game.playerSplit();
    }

    public void doubleDown(BlackjackGame game, UserDetails userDetails) {
        if (game == null || !game.canDoubleDown()) {
            throw new RuntimeException("Double down is not available");
        }

        User user = userService.getCurrentUser(userDetails);

        BigDecimal activeBet = game.isSplit() && game.getActiveHandIndex() == 1
                ? game.getSplitBetAmount()
                : game.getBetAmount();

        BigDecimal totalAfterDoubleDown = game.getTotalBetAmount().add(activeBet);

        if (totalAfterDoubleDown.compareTo(user.getBalance()) > 0) {
            throw new RuntimeException("Insufficient balance to double down");
        }

        game.playerDoubleDown();
        settleBalanceIfGameOver(game, user);
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

        BigDecimal totalProfit = calculateProfit(game.getResult(), game.getBetAmount());

        if (game.isSplit()) {
            totalProfit = totalProfit.add(
                    calculateProfit(game.getSplitResult(), game.getSplitBetAmount())
            );
        }

        user.setBalance(user.getBalance().add(totalProfit));
        userService.save(user);

        saveHistory(
                user,
                game.getBetAmount(),
                game.getResult(),
                game.getPlayerHand().getScore(),
                game.getDealerHand().getScore()
        );

        if (game.isSplit()) {
            saveHistory(
                    user,
                    game.getSplitBetAmount(),
                    game.getSplitResult(),
                    game.getSplitHand().getScore(),
                    game.getDealerHand().getScore()
            );
        }

        game.markSettled();
    }

    private BigDecimal calculateProfit(String result, BigDecimal betAmount) {
        return switch (result) {
            case "WIN" -> betAmount;
            case "BLACKJACK" -> betAmount.multiply(new BigDecimal("1.5"));
            case "LOSE" -> betAmount.negate();
            case "DRAW" -> BigDecimal.ZERO;
            default -> BigDecimal.ZERO;
        };
    }

    private void saveHistory(User user,
                             BigDecimal betAmount,
                             String result,
                             int playerScore,
                             int dealerScore) {
        GameHistory history = new GameHistory(
                user,
                betAmount,
                result,
                playerScore,
                dealerScore,
                user.getBalance()
        );

        gameHistoryRepository.save(history);
    }
}
