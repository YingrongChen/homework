/*
  Yingrong Chen
  ych2429
  
THIS CODE WAS MY OWN WORK , IT WAS WRITTEN WITHOUT CONSULTING ANY
SOURCES OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. Yingrong Chen
*/

import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Collections;

public class BlackJack {

    // fill in code here
    // define data members
    
    public static void buildDeck(ArrayList<Card> deck) {
	// fill in code here
	// Given an empty deck, construct a standard deck of playing cards
	// A conventional deck of 52 playing cards
		deck.clear();
		for (String count:Card.COUNT) {
			for (String suit:Card.SUIT){
				Card aCard = new Card(count, suit);
				deck.add(aCard);
			}
		}
    }

    public static void initialDeal(ArrayList<Card> deck, ArrayList<Card> playerHand, ArrayList<Card> dealerHand){
	// fill in code here
	// Deal two cards from the deck into each of the player's hand and dealer's hand

		// Shuffle and Deal from the end of the deck
		Collections.shuffle(deck);
		playerHand.clear();
		dealerHand.clear();
		playerHand.add(deck.get(deck.size() - 1));
		playerHand.add(deck.get(deck.size() - 2));
		dealerHand.add(deck.get(deck.size() - 3));
		dealerHand.add(deck.get(deck.size() - 4));
		// Remove cards that have been given out
		for (int i = 0; i < 4; i++) {
			deck.remove(deck.size() - 1);
		}
	}

    public static void dealOne(ArrayList<Card> deck, ArrayList<Card> hand){
	// fill in code here
	// this should deal a single card from the deck to the hand
		hand.add(deck.get(deck.size() - 1));
		deck.remove(deck.size() - 1);
	}

    public static boolean checkBust(ArrayList<Card> hand){
	// fill in code here
	// This should return whether a given hand's value exceeds 21
		return sum(hand) > 21;
	}

    public static boolean dealerTurn(ArrayList<Card> deck, ArrayList<Card> hand){
	// fill in code here
	// This should conduct the dealer's turn and
	// Return true if the dealer busts; false otherwise
		if (sum(hand) < 17){ //dealer hits
			hand.add(deck.get(deck.size() - 1));
			deck.remove(deck.size() - 1);
		}
		return sum(hand) > 21;
	}

    public static int whoWins(ArrayList<Card> playerHand, ArrayList<Card> dealerHand){
	// fill in code here
	// This should return 1 if the player wins and 2 if the dealer wins
		if (sum(playerHand) > sum(dealerHand)){
			return 1;
		}
		return 2;
    }

    public static String displayCard(ArrayList<Card> hand){
	// fill in code here
	// Return a string describing the card which has index 1 in the hand
		return hand.get(1).getSuit() + hand.get(1).getCount();
    }

    public static String displayHand(ArrayList<Card> hand){
	// fill in code here
	// Return a string listing the cards in the hand
		StringBuilder display = new StringBuilder();
		for (Card card:hand){
			display.append(card.getSuit());
			display.append(card.getCount());
			display.append(" ");
		}
		return display.toString();
    }

    // fill in code here (Optional)
    // feel free to add methods as necessary

	// Convert the count from string to integer for further calculation
	public static int parseInt(String str){
		int num;
		if (str.equals("J") ||str.equals("Q")||str.equals("K")){
			num = 10;
		} else {
			num = Integer.parseInt(str);
		}
		return num;
	}

	// Sum the card in hand considering the special value of Ace
	public static int sum(ArrayList<Card> hand){
		int sum = 0;
		boolean hasA = false;
		for (Card card:hand){
			sum = sum + parseInt(card.getCount());
			if (card.getCount().equals("1")){
				hasA = true;
			}
		}
		if (sum <= 11 && hasA){
			sum = sum + 10;
		}
		return sum;
	}

	//test method for edge cases
	public static void test_initialDeal(ArrayList<Card> deck, ArrayList<Card> playerHand, ArrayList<Card> dealerHand){
		playerHand.clear();
		dealerHand.clear();
		//all Aces
		playerHand.add(deck.get(0));
		playerHand.add(deck.get(0));
		dealerHand.add(deck.get(0));
		dealerHand.add(deck.get(0));
		for (int i = 0; i < 4; i++) {
			deck.remove(deck.size() - 1);
		}
	}

    public static void main(String[] args) {

		int playerChoice, winner;
		ArrayList<Card> deck = new ArrayList<Card>();
		
		
		playerChoice = JOptionPane.showConfirmDialog(
			null, 
			"Ready to Play Blackjack?", 
			"Blackjack", 
			JOptionPane.OK_CANCEL_OPTION
		);

		if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
		    System.exit(0);
		
		Object[] options = {"Hit","Stand"};
		boolean isBusted;	// Player busts? 
		boolean dealerBusted;
		boolean isPlayerTurn;
		ArrayList<Card> playerHand = new ArrayList<>();
		ArrayList<Card> dealerHand = new ArrayList<>();
	
		do{ // Game loop
			buildDeck(deck);  // Initializes the deck for a new game
		    initialDeal(deck, playerHand, dealerHand);
//			check initial states
//			System.out.println(displayHand(deck));
//			System.out.println(deck.size());
//			System.out.println(displayHand(playerHand));
//			System.out.println(displayHand(dealerHand));

//			test_initialDeal(deck, playerHand, dealerHand);

			isPlayerTurn=true;
		    isBusted=false;
		    dealerBusted=false;
		    
		    while(isPlayerTurn){

				// Shows the hand and prompts player to hit or stand
				playerChoice = JOptionPane.showOptionDialog(
					null,
					"Dealer shows " + displayCard(dealerHand) + "\n Your hand is: " 
						+ displayHand(playerHand) + "\n What do you want to do?",
					"Hit or Stand",
				   JOptionPane.YES_NO_OPTION,
				   JOptionPane.QUESTION_MESSAGE,
				   null,
				   options,
				   options[0]
				);

				if(playerChoice == JOptionPane.CLOSED_OPTION)
				    System.exit(0);
				
				else if(playerChoice == JOptionPane.YES_OPTION){
				    dealOne(deck, playerHand);
				    isBusted = checkBust(playerHand);
				    if(isBusted){
						// Case: Player Busts
						playerChoice = JOptionPane.showConfirmDialog(
							null,
							"Player has busted!", 
							"You lose", 
							JOptionPane.OK_CANCEL_OPTION
						);

						if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						    System.exit(0);
						
						isPlayerTurn=false;
				    }
				}
			    
				else{
				    isPlayerTurn=false;
				}
		    }

		    if(!isBusted){ // Continues if player hasn't busted
				dealerBusted = dealerTurn(deck, dealerHand);
//			System.out.println(displayHand(dealerHand));
				if(dealerBusted){ // Case: Dealer Busts
				    playerChoice = JOptionPane.showConfirmDialog(
				    	null, 
				    	"The dealer's hand: " +displayHand(dealerHand) + "\n \n Your hand: " 
				    		+ displayHand(playerHand) + "\nThe dealer busted.\n Congrautions!", 
				    	"You Win!!!", 
				    	JOptionPane.OK_CANCEL_OPTION
				    );		    

					if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						System.exit(0);
				}
			
			
				else{ //The Dealer did not bust.  The winner must be determined
				    winner = whoWins(playerHand, dealerHand);

				    if(winner == 1){ //Player Wins
						playerChoice = JOptionPane.showConfirmDialog(
							null, 
							"The dealer's hand: " +displayHand(dealerHand) + "\n \n Your hand: " 
								+ displayHand(playerHand) + "\n Congrautions!", 
							"You Win!!!", 
							JOptionPane.OK_CANCEL_OPTION
						);

						if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						    System.exit(0);
				    }

				    else{ //Player Loses
						playerChoice = JOptionPane.showConfirmDialog(
							null, 
							"The dealer's hand: " +displayHand(dealerHand) + "\n \n Your hand: " 
								+ displayHand(playerHand) + "\n Better luck next time!", 
							"You lose!!!", 
							JOptionPane.OK_CANCEL_OPTION
						); 
					
						if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						    System.exit(0);
				    }
				}
		    }
		}while(true);
    }
}



class Card {
	// Specify data fields for an individual card
	public static final String[] COUNT = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
	public static final String[] SUIT = {"Clubs", "Diamonds", "Hearts", "Spades"};

	private String count;
	private String suit;

	Card(String count, String suit){
		// Fill in constructor method
		this.count = count;
		this.suit = suit;
	}

	public String getCount(){
		return count;
	}
	public String getSuit(){
		return suit;
	}
}
