package com.example.cstmemorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
Main Activity
The main activity of the card matching game
Jaydeep Patel, Liam Willis
 */
public class MainActivity extends AppCompatActivity {

    // An ArrayList of CardSpace objects which will store the CardSpace representing each card
    // (see CardSpace class)
    private ArrayList<CardSpace> cardList;
    // The number of guesses the player has made
    private int guesses;

    // The CardSpace object representing the first card the player has clicked (out of a guess of 2 cards)
    private CardSpace firstClicked;
    // The CardSpace object representing the second card the player has clicked
    private CardSpace secondClicked;

    // Whether or not the player is currently allowed to click on cards, or start a new game
    private boolean canClick = true;

    // The TextView which displays how many guesses the player has made
    private TextView guessText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Gets the guess TextView by its ID
        guessText = findViewById(R.id.txtGuesses);

        // Initializes the ArrayList of CardSpace objects (see initCards method below)
        initCards();
        // Resets the game to its initial state (See reset method below)
        reset();


    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.new_menu, menu);

        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (canClick)
        {
            reset();
        }
        return true;

    }

    // initCards
    // Initializes the CardSpace list to contain an object for all 12 cards
    private void initCards()
    {
        // Initializes the card list
        cardList = new ArrayList<CardSpace>();

        // Adds a CardSpace object for each of the 12 cards on screen, passing in the ImageView associated with
        // each card by its ID
        cardList.add(new CardSpace(findViewById(R.id.card0)));
        cardList.add(new CardSpace(findViewById(R.id.card1)));
        cardList.add(new CardSpace(findViewById(R.id.card2)));
        cardList.add(new CardSpace(findViewById(R.id.card3)));
        cardList.add(new CardSpace(findViewById(R.id.card4)));
        cardList.add(new CardSpace(findViewById(R.id.card5)));
        cardList.add(new CardSpace(findViewById(R.id.card6)));
        cardList.add(new CardSpace(findViewById(R.id.card7)));
        cardList.add(new CardSpace(findViewById(R.id.card8)));
        cardList.add(new CardSpace(findViewById(R.id.card9)));
        cardList.add(new CardSpace(findViewById(R.id.card10)));
        cardList.add(new CardSpace(findViewById(R.id.card11)));

        // For each CardSpace:
        for(CardSpace c: cardList)
        {
            // Sets the on-click method for each card ImageView to the clickCard method (see clickCard method below)
            c.img.setOnClickListener(v -> clickCard(v));
        }
    }

    // reset
    // Resets the matching game to its initial state
    private void reset()
    {
        // For each CardSpace:
        for(CardSpace c: cardList)
        {
            // "Un-flips" every card, changing its image to the unflipped card image
            // (see unflip method in CardSpace class)
            c.unflip();
        }

        // Randomly assigns a value to each card to simulate shuffling them around
        // (see shuffleCards method below)
        shuffleCards();

        // Sets the player's guess count to 0
        guesses = 0;
        // Updates the guess TextView (see updateGuessText method below)
        updateGuessText();

        // Sets the player's first and second guessed cards to null
        firstClicked = null;
        secondClicked = null;
    }

    // clickCard
    // Function which is executed when the player clicks a card
    // Will flip the card (if possible), and compare what the player has guessed (if possible)
    private void clickCard(View clickedImg)
    {
        // If the player is currently allowed to click on cards:
        if(canClick)
        {
            // Gets the clicked card by matching the clicked ImageView to the CardSpace object it belongs to
            // (see getCardObj method below)
            CardSpace clicked = getCardObj((ImageView) clickedImg);

            // If the card the player clicked is unflipped:
            if(!clicked.flipped)
            {
                // Flips the clicked card over (see flip method in CardSpace class)
                clicked.flip();

                // If the player has not clicked on any cards yet (both guess objects are null)
                if(firstClicked == null && secondClicked == null)
                {
                    // Sets the first guess object to the card the player clicked
                    firstClicked = clicked;
                }

                // If the player has already clicked on a card before this:
                else if(firstClicked != null && secondClicked == null)
                {
                    // Sets the second guess object to the card the player clicked
                    secondClicked = clicked;
                    // Prevents the player from clicking on any other cards or starting a new game
                    canClick = false;

                    // Creates a new CountDownTimer object which stops the program from executing code until
                    // three quarters of a second has passed:
                    new CountDownTimer(750, 375)
                    {

                        // What happens on every interval of the countdown (once, halfway through the timer in this case):
                        public void onTick(long l) {
                            // If both cards the player clicked are of the same type:
                            if(firstClicked.type == secondClicked.type)
                            {
                                // Sets the text of the guess text view to tell the player they were correct
                                guessText.setText("Correct!");
                            }

                            // If the cards the player clicked do not match:
                            else
                            {
                                // Sets the text of the guess text view to tell the player they were incorrect
                                guessText.setText("Incorrect.");
                            }
                        }

                        // When the timer finishes:
                        public void onFinish() {
                            // If the two cards the player clicked do not match:
                            if(firstClicked.type != secondClicked.type)
                            {
                                // Un-flips both cards
                                firstClicked.unflip();
                                secondClicked.unflip();
                            }

                            // Sets both guesses to null
                            firstClicked = null;
                            secondClicked = null;

                            // Increments the guess counter
                            guesses++;
                            // Updates the guess counter
                            updateGuessText();

                            // Checks to see if the player has flipped over all 12 cards (see checkIfAllFlipped method below)
                            checkIfAllFlipped();

                            // Allows the player to click on cards/start a new game again
                            canClick = true;
                        }
                        // Starts the timer
                    }.start();


                }
            }


        }


    }

    // getCardObj
    // Gets the CardSpace object corresponding to the ImageView passed in
    private CardSpace getCardObj(ImageView imageView)
    {
        // The CardSpace which will be returned, null to start
        CardSpace res = null;

        // For each CardSpace object in the list:
        for(CardSpace c: cardList)
        {
            // If the current CardSpace's ImageView matches the passed-in ImageView
            // (see compareImgView method in CardSpace class):
            if(c.compareImgView(imageView))
            {
                // Sets the return CardSpace to the current match
                res = c;
                // Breaks out of the current for loop
                break;
            }
        }

        // Return the matching CardSpace object
        return res;
    }

    // shuffleCards
    // Randomly assigns a type to each of the CardSpace objects in the list in a way that there will be 2 copies of
    // each card type
    private void shuffleCards()
    {
        // An array of integers which will store how many of all 6 card types have been assigned
        int[] typeCounts = new int[6];
        // An ArrayList of integers which contains each of the card type assignments, as an integer
        ArrayList<Integer> typeGen = new ArrayList<Integer>();
        // A random number generator
        Random rng = new Random();
        // Whether or not a valid type has been found (one which has not been assigned 2 times already)
        boolean validFound = false;
        // The current randomly generated card type
        int currentType = 0;

        // For loop which runs 12 times (once for each card):
        for(int i = 0; i < 12; i++)
        {
            // While a valid type number has not been generated:
            while(!validFound)
            {
                // Generates a random number between 0 and 5 which corresponds to a CardType
                currentType = rng.nextInt(6);

                // If the the number of cards which will be assigned this type is less than 2:
                if(typeCounts[currentType] < 2)
                {
                    // Adds the current type number to the type assignment list
                    typeGen.add(currentType);
                    // Increments the number of assignments for this type number
                    typeCounts[currentType]++;
                    // Indicates that a valid type was generated, ending the while loop
                    validFound = true;
                }
            }

            // Resets the validFound flag for the next iteration of the for loop
            validFound = false;
        }

        // For each CardSpace object in the card list:
        for(int i = 0; i < cardList.size(); i++)
        {
            // Assigns the CardType of the current CardSpace object to the enumeration value corresponding to the
            // current type number in the type list
            cardList.get(i).type = CardType.values()[typeGen.get(i)];
        }

    }

    // updateGuessText
    // Updates the guess TextView to display the current number of guesses the player has made
    private void updateGuessText()
    {
        // The string the guess TextView's text will be set to
        String text = "";

        // Switch on the number of guesses:
        switch(guesses)
        {
            // If the player has guessed once:
            case 1:
                // Sets the text to indicate that the player has made 1 guess, with proper formatting
                text = "You've made 1 guess";
                // Breaks out of this case
                break;

                // If the player has made any other number of guesses:
            default:
                // Sets the text to display the number of guesses the player has made
                text = String.format("You've made %d guesses", guesses);
        }

        // Sets the text of the guess TextView to the string produced earlier.
        guessText.setText(text);
    }

    // checkIfAllFlipped
    // Checks if the player has flipped over all cards, and advances them to the win screen if they have
    private void checkIfAllFlipped()
    {
        // Whether or not all cards have been flipped, true by default
        boolean allFlipped = true;

        // For each card:
        for(CardSpace c: cardList)
        {
            // If the current card is not flipped:
            if(!c.flipped)
            {
                // Indicates that not all cards have been flipped
                allFlipped = false;
                // Breaks out of the for/each loop
                break;
            }
        }

        // If all of the cards have been flipped:
        if(allFlipped)
        {
            //connects main activity to it's play again activity.
            Intent intent = new Intent(this,PlayAgain.class);
            intent.putExtra("total",guesses);
            startActivity(intent);
            //basically reset the game to it's initial state.
            reset();

        }
    }

}