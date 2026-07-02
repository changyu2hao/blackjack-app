package com.example.blackjack.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (String suit : suits) {
            for (String rank : ranks) {
                int value = getValueForRank(rank);
                cards.add(new Card(suit, rank, value));
            }
        }

        shuffle();
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new RuntimeException("No cards left in the deck");
        }

        return cards.remove(0);
    }

    public int remainingCards() {
        return cards.size();
    }

    private void shuffle() {
        Collections.shuffle(cards);
    }

    private int getValueForRank(String rank) {
        if (rank.equals("A")) {
            return 11;
        }

        if (rank.equals("K") || rank.equals("Q") || rank.equals("J")) {
            return 10;
        }

        return Integer.parseInt(rank);
    }
}