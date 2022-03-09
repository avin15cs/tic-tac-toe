package com.modia.tictactoe;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    boolean gameActive = true;
    // 0-x
    // 1-o
    int activePlayer=0;
    int[] gameState={2,2,2,2,2,2,2,2,2};
    //state meanings:
    //0 - x
    //1 - o
    //2 - null
    int[][] winPositions= { {0,1,2},{3,4,5},{6,7,8},
                            {0,3,6},{1,4,7},{2,5,8},
                            {0,4,8},{2,4,6} };

    int count=0;

    MediaPlayer mp;
    Button button;

    public void playerTap(View view){
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());

        button = (Button) findViewById(R.id.button2);
        button.setVisibility(INVISIBLE);

        if(!gameActive)
            gameReset(view);

        if(gameState[tappedImage] == 2) {
            gameState[tappedImage] = activePlayer;
            img.setTranslationY(-1000f);

            //increase count
            count++;

            if (activePlayer == 0) {
                img.setImageResource(R.drawable.x_value);
                activePlayer = 1;
                TextView status = findViewById(R.id.status);
                status.setText("O's Turn - Tap to play");
            } else {
                img.setImageResource(R.drawable.o_value);
                activePlayer = 0;
                TextView status = findViewById(R.id.status);
                status.setText("X's Turn - Tap to play");
            }
            img.animate().translationYBy(1000f).setDuration(300);
        }
        //check if any player has won
        for(int[] winPosition: winPositions){
            if(gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] !=2 ) {
                //Somebody has won! - Find out who!
                gameActive = false;
                String winner;
                if(gameState[winPosition[0]] == 0) {
                    winner = "X has Won";
                } else {
                    winner = "O has Won";
                }

                button.setText("Restart");
                button.setVisibility(VISIBLE);

                //Update the status bar for winner announcement
                TextView status = findViewById(R.id.status);
                status.setText(winner);

                mp = MediaPlayer.create(MainActivity.this, R.raw.bell);
                mp.start();
            }
        }

        //check if board is full
        if(count==gameState.length && gameActive) {
            gameActive = false;

            TextView status = findViewById(R.id.status);
            status.setText("It's a Draw!");

            button.setText("Restart");
            button.setVisibility(VISIBLE);


            mp = MediaPlayer.create(MainActivity.this, R.raw.gameover);
            mp.start();
        }

    }

    public void gameReset(View view){
        gameActive = true;
        activePlayer = 0;
        count = 0;
        button.setVisibility(INVISIBLE);
        for(int i=0;i<gameState.length;i++)
            gameState[i] =2;

        ((ImageView)findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView8)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView9)).setImageResource(0);

        TextView status = findViewById(R.id.status);
        status.setText("X's Turn - Tap to play");


    }

    public void onClickButton(View view) {
        button.setVisibility(INVISIBLE);
        gameReset(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}