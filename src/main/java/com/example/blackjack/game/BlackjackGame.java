package com.example.blackjack.game;
import java.math.BigDecimal;

public class BlackjackGame {
    private final Deck deck;
    private final Hand playerHand;
    private final Hand dealerHand;
    private final BigDecimal betAmount;
    private boolean gameOver;
    private String result;
    private boolean settled;

    public BlackjackGame(BigDecimal betAmount){
        this.deck=new Deck();
        this.playerHand=new Hand();
        this.dealerHand=new Hand();
        this.betAmount=betAmount;
        this.gameOver=false;
        this.settled = false;
        this.result="IN_PROGRESS";
        dealInitialCards();
    }
    private void dealInitialCards(){
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
        if(playerHand.isBlackjack()){
            gameOver=true;
            result="BLACKJACK";
        }
    }
    public void playerHit(){
        if(gameOver){
            return;
        }
        playerHand.addCard(deck.drawCard());
        if(playerHand.isBust()){
            gameOver=true;
            result="LOSE";
        }
    }
    public void playerStand(){
        if(gameOver){
            return;
        }
        while(dealerHand.getScore()<17){
            dealerHand.addCard(deck.drawCard());
        }
        determineWinner();
    }
    private void determineWinner() {
        int playerScore = playerHand.getScore();
        int dealerScore = dealerHand.getScore();

        gameOver = true;

        if (dealerHand.isBust()) {
            result = "WIN";
        } else if (playerScore > dealerScore) {
            result = "WIN";
        } else if (playerScore < dealerScore) {
            result = "LOSE";
        } else {
            result = "DRAW";
        }
    }
    public Deck getDeck(){
        return deck;
    }
    public Hand getPlayerHand(){
        return playerHand;
    }
    public Hand getDealerHand(){
        return dealerHand;
    }
    public BigDecimal getBetAmount(){
        return betAmount;
    }
    public boolean isGameOver(){
        return gameOver;
    }
    public String getResult(){
        return result;
    }
    public boolean isSettled() {
        return settled;
    }
    public void markSettled() {
        this.settled = true;
    }
}
