package com.example.blackjack.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_history")
public class GameHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal betAmount;

    @Column(nullable = false)
    private String result;

    @Column(nullable = false)
    private int playerScore;

    @Column(nullable = false)
    private int dealerScore;

    @Column(nullable = false)
    private BigDecimal balanceAfterGame;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public GameHistory() {
    }

    public GameHistory(User user, BigDecimal betAmount, String result, int playerScore, int dealerScore, BigDecimal balanceAfterGame){
        this.user=user;
        this.betAmount=betAmount;
        this.result=result;
        this.playerScore=playerScore;
        this.dealerScore=dealerScore;
        this.balanceAfterGame=balanceAfterGame;
    }
    public Long getId(){
        return id;
    }
    public User getUser(){
        return user;
    }
    public BigDecimal getBetAmount(){
        return betAmount;
    }
    public String getResult(){
        return result;
    }
    public int getPlayerScore(){
        return playerScore;
    }
    public int getDealerScore(){
        return dealerScore;
    }
    public BigDecimal getBalanceAfterGame(){
        return balanceAfterGame;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
}