package com.example.snakeladder;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.util.Duration;

public class Player {
    private Circle coin;

    private int CurrentPos;
    private String name;
    private static Board gameBoard = new Board();



    public Player(int tileSize, Color coinColor, String playerName){
        coin = new Circle(tileSize/2);
        coin.setFill(coinColor);
        CurrentPos = 0;
        movePlayer(1);
        name = playerName;

    }

    public void movePlayer(int diceValue){
        if(CurrentPos + diceValue <= 100){
            CurrentPos += diceValue;
            TranslateTransition secondMove = null , firstMove = translateAnimation(diceValue);

            int newPosition = gameBoard.getNewPosition(CurrentPos);
            if(newPosition != CurrentPos && newPosition != -1){
                CurrentPos = newPosition;
                secondMove = translateAnimation( 6);
            }
            if(secondMove == null){
                firstMove.play();
            }
            else{
                SequentialTransition sequentialTransition = new SequentialTransition(firstMove, new PauseTransition(Duration.millis(500)),secondMove);
                sequentialTransition.play();
            }
        }


//        int x = gameBoard.getXCoordinate(CurrentPos);
//        int y = gameBoard.getYCoordinate(CurrentPos);
//
//        coin.setTranslateX(x);
//        coin.setTranslateY(y);


    }

    public TranslateTransition translateAnimation(int diceValue){
        TranslateTransition animate = new TranslateTransition(Duration.millis(200*diceValue),coin);
        animate.setToX(gameBoard.getXCoordinate(CurrentPos));
        animate.setToY(gameBoard.getYCoordinate(CurrentPos));
        animate.setAutoReverse(false);
        return animate;
    }

    public void startingPosition(){
        CurrentPos = 0;
        movePlayer(1);
    }

    boolean isWinner(){
        if(CurrentPos == 100) return true;
        return  false;
    }

    public Circle getCoin() {
        return coin;
    }

    public int getCurrentPos() {
        return CurrentPos;
    }

    public String getName() {
        return name;
    }
}
