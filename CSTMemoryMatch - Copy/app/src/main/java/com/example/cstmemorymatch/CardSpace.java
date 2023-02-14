package com.example.cstmemorymatch;

import android.widget.ImageView;

/*
CardSpace
Object which represents a card in the card matching game
Jaydeep Patel, Liam Willis
 */
public class CardSpace {
    // The ImageView which represents this card
    public ImageView img;
    // Whether or not this card has been flipped over
    public boolean flipped;
    // THe type of this card (see CardType enumeration)
    public CardType type;

    // The class' constructor, takes in an ImageView
    public CardSpace(ImageView img)
    {
        // Sets this card's ImageView to the ImageView passed in
        this.img = img;
        // Sets this card to be unflipped
        this.flipped = false;
        // Sets this card's type to UNASSIGNED (the default type)
        this.type = CardType.UNASSIGNED;
    }

    // Secondary constructor, takes in an ImageView and a card type
    public CardSpace(ImageView img, CardType type)
    {
        this.img = img;
        this.flipped = false;
        this.type = type;
    }

    // flip
    // Flips this card over, displaying the image on the other side
    public void flip()
    {
        // Indicates that this card is flipped
        this.flipped = true;
        // Sets the card's image depending on the card's type (see showImage method below)
        showImage();
    }

    // unflip
    // "Un-flips" this card, and sets its image to the default card image
    public void unflip()
    {
        // Indicates that this card is no longer flipped
        this.flipped = false;
        // Sets the image of this card to the default, un-flipped card image
        this.img.setImageResource(R.drawable.b);
    }

    // compareImgView
    // Compares the ImageView object contained in this object to another ImageView by their IDs
    public boolean compareImgView(ImageView imgView2)
    {
        // Returns true or false depending on whether or not the IDs of the 2 ImageViews are the same
        return this.img.getId() == imgView2.getId();
    }

    // showImage
    // Sets the image of this card to the one corresponding to its card type
    private void showImage()
    {
        // Sets the ImageView of this card to the image corresponding to the card type:
        // Ace of Clubs
        if(this.type == CardType.ACECLUBS) this.img.setImageResource(R.drawable.ca);

        // King of Clubs
        else if(this.type == CardType.KINGCLUBS) this.img.setImageResource(R.drawable.ck);

        // Queen of Clubs
        else if(this.type == CardType.QUEENCLUBS) this.img.setImageResource(R.drawable.cq);

        // Ace of Diamonds
        else if(this.type == CardType.ACEDIAMONDS) this.img.setImageResource(R.drawable.da);

        // King of Diamonds
        else if(this.type == CardType.KINGDIAMONDS) this.img.setImageResource(R.drawable.dk);

        // Queen of Diamonds
        else if(this.type == CardType.QUEENDIAMONDS) this.img.setImageResource(R.drawable.dq);
    }
}
