package com.example.blackjack.game;

import java.math.BigDecimal;

public class BlackjackGame {

    private final Deck deck;
    private final Hand playerHand;
    private final Hand dealerHand;

    private Hand splitHand;

    private BigDecimal betAmount;
    private BigDecimal splitBetAmount;

    private boolean split;
    private int activeHandIndex;

    private boolean gameOver;
    private String result;
    private String splitResult;
    private boolean settled;

    public BlackjackGame(BigDecimal betAmount) {
        this.deck = new Deck();
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
        this.betAmount = betAmount;
        this.splitBetAmount = BigDecimal.ZERO;
        this.split = false;
        this.activeHandIndex = 0;
        this.gameOver = false;
        this.result = "IN_PROGRESS";
        this.splitResult = null;
        this.settled = false;

        dealInitialCards();
    }

    private void dealInitialCards() {
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());

        if (playerHand.isBlackjack()) {
            gameOver = true;
            result = "BLACKJACK";
        }
    }

    public void playerHit() {
        if (gameOver) {
            return;
        }

        Hand currentHand = getActiveHand();
        currentHand.addCard(deck.drawCard());

        if (currentHand.isBust()) {
            finishCurrentHand();
        }
    }

    public void playerStand() {
        if (gameOver) {
            return;
        }

        finishCurrentHand();
    }

    public boolean canSplit() {
        if (gameOver || split || playerHand.getCards().size() != 2) {
            return false;
        }

        Card firstCard = playerHand.getCards().get(0);
        Card secondCard = playerHand.getCards().get(1);

        // Cards with the same blackjack value can be split.
        // This means 10, J, Q and K may be split with each other.
        return firstCard.getValue() == secondCard.getValue();
    }

    public void playerSplit() {
        if (!canSplit()) {
            return;
        }

        split = true;
        splitHand = new Hand();
        splitBetAmount = betAmount;

        Card secondCard = playerHand.removeCard(1);
        splitHand.addCard(secondCard);

        playerHand.addCard(deck.drawCard());
        splitHand.addCard(deck.drawCard());

        activeHandIndex = 0;
    }

    public boolean canDoubleDown() {
        if (gameOver) {
            return false;
        }

        return getActiveHand().getCards().size() == 2;
    }

    public void playerDoubleDown() {
        if (!canDoubleDown()) {
            return;
        }

        if (activeHandIndex == 0) {
            betAmount = betAmount.multiply(BigDecimal.valueOf(2));
        } else {
            splitBetAmount = splitBetAmount.multiply(BigDecimal.valueOf(2));
        }

        getActiveHand().addCard(deck.drawCard());

        // Double down always gives exactly one card and then stands.
        finishCurrentHand();
    }

    private void finishCurrentHand() {
        if (split && activeHandIndex == 0) {
            activeHandIndex = 1;
            return;
        }

        playDealerAndDetermineWinner();
    }

    private void playDealerAndDetermineWinner() {
        boolean allPlayerHandsBust = playerHand.isBust()
                && (!split || splitHand.isBust());

        if (!allPlayerHandsBust) {
            while (dealerHand.getScore() < 17) {
                dealerHand.addCard(deck.drawCard());
            }
        }

        result = determineResult(playerHand);

        if (split) {
            splitResult = determineResult(splitHand);
        }

        gameOver = true;
    }

    private String determineResult(Hand hand) {
        if (hand.isBust()) {
            return "LOSE";
        }

        if (dealerHand.isBust()) {
            return "WIN";
        }

        int playerScore = hand.getScore();
        int dealerScore = dealerHand.getScore();

        if (playerScore > dealerScore) {
            return "WIN";
        }
        if (playerScore < dealerScore) {
            return "LOSE";
        }
        return "DRAW";
    }

    private Hand getActiveHand() {
        if (!split || activeHandIndex == 0) {
            return playerHand;
        }
        return splitHand;
    }

    public Deck getDeck() {
        return deck;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Hand getSplitHand() {
        return splitHand;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public Hand getActivePlayerHand() {
        return getActiveHand();
    }

    public BigDecimal getBetAmount() {
        return betAmount;
    }

    public BigDecimal getSplitBetAmount() {
        return splitBetAmount;
    }

    public BigDecimal getTotalBetAmount() {
        return betAmount.add(splitBetAmount);
    }

    public boolean isSplit() {
        return split;
    }

    public int getActiveHandIndex() {
        return activeHandIndex;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getResult() {
        return result;
    }

    public String getSplitResult() {
        return splitResult;
    }

    public boolean isSettled() {
        return settled;
    }

    public void markSettled() {
        this.settled = true;
    }
}
