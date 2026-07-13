package com.example.blackjack.game;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private final List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card removeCard(int index) {
        return cards.remove(index);
    }

    public int getScore() {
        int score = 0;
        int aceCount = 0;

        for (Card card : cards) {
            score += card.getValue();

            if (card.getRank().equals("A")) {
                aceCount++;
            }
        }

        while (score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }

        return score;
    }

    public boolean isBust() {
        return getScore() > 21;
    }

    public boolean isBlackjack() {
        return cards.size() == 2 && getScore() == 21;
    }
}