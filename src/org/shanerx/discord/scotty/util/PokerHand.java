package org.shanerx.discord.scotty.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PokerHand {
	
	public PokerHand() { }
	
	public Set<Card> getRandomSet() {
		Set<Card> hand = new HashSet<>();
		for (int i = 0; i < 5; i++) {
			Card c = new Card(Card.Suit.getRandom(), Card.Number.getRandom());
			hand.add(c);
		}
		return hand;
	}
	
	public static class Card {
		
		public Suit suit;
		public Number number;
		
		public Card(Suit s, Number n) {
			suit = s;
			number = n;
		}
		
		public enum Suit {
			DIAMONDS,
			CLUBS,
			HEARTS,
			SPADES;
			
			public static Suit getRandom() {
				Random rand = new Random();
				int nextInt = rand.nextInt(4);
				switch (nextInt) {
				case 0: return DIAMONDS;
				case 1: return CLUBS;
				case 2: return HEARTS;
				case 3: return SPADES;
				}
				return null;
			}
		}
		
		public enum Number {
			ONE,
			TWO,
			THREE,
			FOUR,
			FIVE,
			SIX,
			SEVEN,
			EIGHT,
			NINE,
			TEN,
			JACK,
			QUEEN,
			KING;
			
			public static Number getRandom() {
				Random rand = new Random();
				int nextInt = rand.nextInt(13);
				switch (nextInt) {
				case 0: return ONE;
				case 1: return TWO;
				case 2: return THREE;
				case 3: return FOUR;
				case 4: return FIVE;
				case 5: return SIX;
				case 6: return SEVEN;
				case 7: return EIGHT;
				case 8: return NINE;
				case 9: return TEN;
				case 10: return JACK;
				case 11: return QUEEN;
				case 12: return KING;
				}
				return null;
			
			
			}
			
		}
		
		
		public enum Ranking {
			HIGH_CARD,
			ONE_PAIR,		//
			TWO_PAIR,		//
			THREE_OF_A_KIND,
			FULL_HOUSE,		//
			FOUR_OF_A_KIND, //
			STRAIGHT_FLUSH,
			ACE_STRAIGHT_FLUSH,
			
			INVALID;	//
			
			public static Ranking getRankingOfHand(Set<Card> hand) {
				int k = 0;

				for (Card c : hand) {
					int i = 1;
					hand.remove(c);
					if (hand.contains(c)) {
						hand.remove(c);
						i = 2;
						if (hand.contains(c)) {
							i = 3;
							if (hand.contains(c)) {
								i = 4;
								if (hand.contains(c)) {
									i = 5;
									return INVALID;
								}
							}
						}
					}
					if (i == 4) {
						return FOUR_OF_A_KIND;
					}
					else if (i == 3) {
						for (Card d : hand) {
							hand.remove(d);
							for (Card e : hand) {
								if (d.number == e.number && d.suit == e.suit) {
									return FULL_HOUSE;
								}
							}
						}
					}
					else if (i == 2) {
						for (Card d : hand) {
							int j = 1;
							hand.remove(d);
							if (hand.contains(d)) {
								j = 2;
								hand.remove(d);
								if (hand.contains(d)) {
									j = 3;
									return FULL_HOUSE;
								}
							}
							if (j == 2 && i == 2) {
								return TWO_PAIR;
							}
							else if (j == 1 && i == 2) {
								return ONE_PAIR;
							}
						}
					}
					else {
						k++;
						if (k != 5) {
							continue;
						}
						// IT'S THE FIFTH
					}
				}
				return HIGH_CARD;
			}
			
		}
	}

}