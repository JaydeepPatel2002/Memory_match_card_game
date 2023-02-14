package com.example.cstmemorymatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayAgain extends AppCompatActivity {

    private TextView winText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_again);
        //declaration of button and win text.
        Button btnPlayAgain = findViewById(R.id.playAgainBtn);
        winText =  this.findViewById(R.id.winTextview);

        Intent intent = getIntent();
        // getting total number of guesses from the extras bundle.
        Bundle bundle = intent.getExtras();
        int guessses = (int) bundle.getInt("total");
        //setting text view based on data got from bundle.
        String text = String.format("You Won after %d guesses.",guessses);
        winText.setText(text);
        // setting onclick function for play again button.
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //closing screen and returning to main app.
                finish();
            }
        });


    }
}