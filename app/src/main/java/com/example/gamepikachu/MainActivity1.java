package com.example.gamepikachu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity1 extends AppCompatActivity implements View.OnClickListener, DialogInterface.OnCancelListener {

    private GridLayout gridLayout;
    private GridLayout.LayoutParams layoutParams;
    private Controller algorithm;
    private ConstraintLayout game;
    private ProgressBar progressBar;
    private MainActivity mainActivity;
    private Point p1 = null;
    private Point p2 = null;
    public ImageButton[][] arrayImageButton;
    private PointLine line;
    private TextView textViewScore, txtPlayer;
    public static int score = 0;
    private int row = 6;
    private int col = 13;
    private int imageListId[] = {0, R.drawable._1, R.drawable._2, R.drawable._3, R.drawable._4, R.drawable._5, R.drawable._6,
            R.drawable._7, R.drawable._8, R.drawable._9, R.drawable._10, R.drawable._11, R.drawable._12,
            R.drawable._13, R.drawable._14, R.drawable._15, R.drawable._16, R.drawable._17, R.drawable._18,
            R.drawable._19, R.drawable._20, R.drawable._21, R.drawable._22, R.drawable._23, R.drawable._24,
            R.drawable._25, R.drawable._26};
    //private ArrayList<ImageButton> imageButtons = new ArrayList<>();
    private Map<ImageButton, String> meMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_game);

        gridLayout = findViewById(R.id.gridGame);

        progressBar = findViewById(R.id.progressBar);
        this.row = row + 2;
        this.col = col + 2;

        arrayImageButton = new ImageButton[row][col];

        textViewScore = findViewById(R.id.textViewScore);
        txtPlayer = findViewById(R.id.textViewPlayer);
        Bundle extras = getIntent().getExtras();
        txtPlayer.setText("halo " + extras.get("name"));

        game = findViewById(R.id.game);

        newGame();

    }

    private void newGame() {

        score = 0;
        textViewScore.setText("Score: " + score);
        algorithm = new Controller(this.row, this.col);
        //Log.d("ma tran",algorithm.showMatrix());
        addArrayImageButton();
//        Handler h = new Handler();
//        Runnable changeProcessBarRunnable = new Runnable() {
//            @Override
//            public void run() {
//                changeProcessbar(time);
//            }
//        };
//
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 100; i > 0; i--) {
//                    time = i;
//                    changeProcessbar(time);
//                    Log.d("ahihi", Thread.currentThread().getName());
////                    h.post(changeProcessBarRunnable);
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                Toast.makeText(MainActivity1.this, "Hết giờ", Toast.LENGTH_LONG).show();
//            }
//
//        };
//
//        Thread a = new Thread(runnable);
//        a.setName("ahihi");
//        a.start();


        CountDownTimer countDownTimer = new CountDownTimer(100000, 1000) {
            @Override
            public void onTick(long l) {
                int count = progressBar.getProgress();
                count = count - 1;
                progressBar.setProgress(count);
            }

            @Override
            public void onFinish() {
                    Toast.makeText(MainActivity1.this, "Hết giờ", Toast.LENGTH_LONG).show();
            }
        }.start();

    }

    private void addArrayImageButton() {

        //imageButtons = new ArrayList<>();
        gridLayout.removeAllViews();

        for(int i = 1 ; i < row - 1; i++) {

            for(int j = 1; j < col - 1; j++) {

                ImageButton imageButton = new ImageButton(this);

                layoutParams = new GridLayout.LayoutParams();
                layoutParams.setMargins(5, 5, 5, 5);
                layoutParams.height = 100;
                layoutParams.width = 100;
                layoutParams.setGravity(Gravity.CENTER);

                meMap.put(imageButton, i + "," + j);

                imageButton.setImageResource(imageListId[algorithm.getMatrix()[i][j]]);
                imageButton.setOnClickListener(v->onClick(imageButton));
                imageButton.setBackgroundColor(Color.LTGRAY);
                imageButton.setPadding(0, 0, 0, 0);
                imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
                imageButton.setLayoutParams(layoutParams);

                //imageButtons.add(imageButton);
                gridLayout.addView(imageButton);
                arrayImageButton[i][j] = imageButton;


            }
        }
    }


    @Override
    public void onClick(View view) {
        String btnIndex = (String) meMap.get(view);

        assert btnIndex != null;
        int indexDot = btnIndex.lastIndexOf(",");
        int x = Integer.parseInt(btnIndex.substring(0, indexDot));
        int y = Integer.parseInt(btnIndex.substring(indexDot + 1));
        if (p1 == null) {
            p1 = new Point(x, y);
            //btn[p1.x][p1.y].setBorder(new LineBorder(Color.red));
            arrayImageButton[p1.x][p1.y].setBackgroundResource(R.drawable.border_button);
        }
        else if (p2 == null) {
            //btn[p1.x][p1.y].setBackground(Color.lightGray);
            p2 = new Point(x, y);
            System.out.println("(" + p1.x + "," + p1.y + ")" + " --> " + "("
                    + p2.x + "," + p2.y + ")");
            line = algorithm.checkTwoPoint(p1, p2);
            algorithm.showMatrix();
            if (line != null) {
                System.out.println("line != null");
                algorithm.getMatrix()[p1.x][p1.y] = 0;
                algorithm.getMatrix()[p2.x][p2.y] = 0;
                algorithm.showMatrix();
                execute(p1, p2);
                line = null;
                score += 10;

                textViewScore.setText("Score: " + score);

                if(score == 390) {
                    WinGameDialogFragment dialogFragment = new WinGameDialogFragment(this);
//                    dialogFragment.requireDialog().setOnCancelListener(this);
                    dialogFragment.show(getSupportFragmentManager(), null);

                    newGame();
                }
                /*frame.time++;
                frame.lbScore.setText(score + "");
                if (frame.time == 10)
                    frame.showDialogNewGame("You lost. Do you want play new game ?", "Good Luck", 0);
                else
                {
                    if(score == 10 * (row-2) * (col-2) / 2) {

                        frame.showDialogNewGame("You win. Do you want play new game ?", "Congratulation", 0);
                        ConnectSQL sql = new ConnectSQL();
                        tmp = sql.getMaxStt()+1;
                        sql.add(tmp,Main.name2,score);
                    }

                }*/
            }
            arrayImageButton[p1.x][p1.y].setBackgroundColor(Color.LTGRAY);
            p1 = null;
            p2 = null;
            System.out.println("done");
        }
    }
    public void execute(Point p1, Point p2) {
        System.out.println("delete");

        setDisable(arrayImageButton[p1.x][p1.y]);
        setDisable(arrayImageButton[p2.x][p2.y]);

    }

    private void setDisable(ImageButton imagebtn) {

        imagebtn.setVisibility(View.INVISIBLE);

    }

    public void onClickSuccess(View view) {

        ArrayList<Point> list = algorithm.suggestPoint() ;
        if (list != null){
            Point p1 = list.get(0);
            Point p2 = list.get(1);
            arrayImageButton[p1.x][p1.y].setBackgroundResource(R.drawable.border_button);
            arrayImageButton[p2.x][p2.y].setBackgroundResource(R.drawable.border_button);

        }
        else algorithm.turnMatrix();
    }
    int time = 0;

    public void onClickTurn(View view) {

        algorithm.turnMatrix();
        gridLayout.removeAllViews();

        score = 0;
        textViewScore.setText("Score: " + score);

        meMap = new HashMap<>();

        for(int i = 1 ; i < row - 1; i++) {

            for(int j = 1; j < col - 1; j++) {

                ImageButton imageButton = new ImageButton(this);

                layoutParams = new GridLayout.LayoutParams();
                layoutParams.setMargins(5, 5, 5, 5);

                layoutParams.height = 100;
                layoutParams.width = 100;

                if(algorithm.getMatrix()[i][j] != 0) {

                    meMap.put(imageButton, i + "," + j);

                    imageButton.setImageResource(imageListId[algorithm.getMatrix()[i][j]]);
                    imageButton.setOnClickListener(v->onClick(imageButton));
                    imageButton.setBackgroundColor(Color.LTGRAY);
                    imageButton.setPadding(0, 0, 0, 0);
                    imageButton.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageButton.setLayoutParams(layoutParams);

                    //imageButtons.add(imageButton);
                    gridLayout.addView(imageButton);
                    arrayImageButton[i][j] = imageButton;
                }
                else {
                    //imageButtons.add(imageButton);
                    gridLayout.addView(imageButton);
                    setDisable(imageButton);
                }
            }
        }
    }

    private void changeProcessbar(int time) {
        progressBar.setProgress(time);
    }

    public void onClickPause(View view) {
        Toast.makeText(this,"hiển thị cho vui", Toast.LENGTH_SHORT).show();
    }
    public void onClickRestart(View view) {
        //gridLayout = findViewById(R.id.gridGame);
        gridLayout.removeAllViews();

        newGame();
    }
    public void onClickExit(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        Log.d("ahihi", "on cancel");
        newGame();
    }
}