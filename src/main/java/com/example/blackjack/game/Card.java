package com.example.blackjack.game;

public class Card {

    private final String suit;
    private final String rank;
    private final int value;

    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        return rank + " of " + suit;
    }

    public String getSuitSymbol() {
        return switch (suit) {
            case "Hearts" -> "\u2665";
            case "Diamonds" -> "\u2666";
            case "Clubs" -> "\u2663";
            case "Spades" -> "\u2660";
            default -> suit;
        };
    }

    public boolean isRed() {
        return suit.equals("Hearts") || suit.equals("Diamonds");
    }
}
