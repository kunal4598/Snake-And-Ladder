package com.example.snakeladder;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;

import java.io.IOException;

public class snakeLadder extends Application {
    public static final int tileSize = 40, width = 10, height = 10;
    public static final int buttonLine = height *tileSize + 50, infoline = buttonLine - 20;

    private static Dice dice = new Dice();
    private Player  playerOne, playerTwo;

    private boolean gameStarted = false, playerOneTurn = true, playerTwoTurn = false;

    private Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(tileSize*width, tileSize*height + 90);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile tile = new Tile(tileSize);
                tile.setTranslateX(j*tileSize);
                tile.setTranslateY(i*tileSize);
                root.getChildren().add(tile);

            }

        }

        //fixed the board
        Image img = new Image("file:C:\\Users\\shadan khan\\IdeaProjects\\snakeLadder\\src\\main\\img.png");
        ImageView board = new ImageView();
        board.setImage(img);

        board.setFitHeight(height*tileSize);
        board.setFitWidth(width*tileSize);



        // button downside the board
        Button playerOneButton = new Button("Player one");
        Button playerTwoButton = new Button("Player two");
        Button start = new Button("Start");

        playerOneButton.setTranslateY(buttonLine);
        playerOneButton.setTranslateX(40);
        playerOneButton.setDisable(true);

        playerTwoButton.setTranslateY(buttonLine);
        playerTwoButton.setTranslateX(290);
        playerTwoButton.setDisable(true);

        start.setTranslateY(buttonLine);
        start.setTranslateX(180);

        // info display
        Label playerOneLabel = new Label("");
        Label playerTwoLabel = new Label("");
        Label dice = new Label("Start the game");

        playerOneLabel.setTranslateY(infoline);
        playerOneLabel.setTranslateX(50);

        playerTwoLabel.setTranslateY(infoline);
        playerTwoLabel.setTranslateX(300);

        dice.setTranslateY(infoline);
        dice.setTranslateX(165);

        // connect remaining class with snakeLadder class

        playerOne = new Player(tileSize, Color.BLACK, "Ifra");
        playerTwo = new Player(tileSize-5, Color.RED, "Azwan");

        //player action
        playerOneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStarted){
                    if(playerOneTurn){

                        int diceVal =  Dice.getRolledDiceValue();
                        dice.setText("diceVal "+ diceVal);
                        playerOne.movePlayer(diceVal);

                        //winning Condition
                        if(playerOne.isWinner()){
                            dice.setText(playerOne.getName()+" won the game");

                            playerOneTurn = true;
                            playerOneButton.setDisable(false);
                            playerOneLabel.setText("");

                            playerTwoTurn = false;
                            playerTwoButton.setDisable(true);
                            playerTwoLabel.setText("");

                            start.setDisable(false);
                            start.setText("Start the game");

                        }
                        else{
                            playerOneTurn = false;
                            playerOneButton.setDisable(true);
                            playerOneLabel.setText("");

                            playerTwoTurn = true;
                            playerTwoButton.setDisable(false);
                            playerTwoLabel.setText("your turn " + playerTwo.getName());
                        }

                    }
                }
            }
        });

        playerTwoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStarted){
                    if(playerTwoTurn){

                        int diceVal =  Dice.getRolledDiceValue();
                        dice.setText("diceVal "+ diceVal);
                        playerTwo.movePlayer(diceVal);

                        //winning condition
                        if(playerTwo.isWinner()){
                            dice.setText(playerTwo.getName()+" won the game");

                            playerOneTurn = false;
                            playerOneButton.setDisable(true);
                            playerOneLabel.setText("");

                            playerTwoTurn = true;
                            playerTwoButton.setDisable(false);
                            playerTwoLabel.setText("");

                            start.setDisable(false);
                            start.setText("Start the game");

                        }
                        else{
                            playerOneTurn = true;
                            playerOneButton.setDisable(false);
                            playerOneLabel.setText("your turn " + playerOne.getName());

                            playerTwoTurn = false;
                            playerTwoButton.setDisable(true);
                            playerTwoLabel.setText("");
                        }
                    }
                }
            }
        });

        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStarted = true;
                dice.setText("Game Started");
                start.setDisable(true);
                playerOneTurn = true;
                playerOneLabel.setText("your turn "+ playerOne.getName());
                playerOneButton.setDisable(false);
                playerOne.startingPosition();

                playerTwoTurn = false;
                playerTwoLabel.setText("");
                playerTwoButton.setDisable(true);
                playerTwo.startingPosition();
            }
        });

        root.getChildren().addAll(board,playerOneButton,playerTwoButton,start,playerOneLabel, playerTwoLabel,dice,
                playerOne.getCoin(),playerTwo.getCoin());

        return root;
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("Snake & ladder");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}