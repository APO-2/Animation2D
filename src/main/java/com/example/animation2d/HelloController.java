package com.example.animation2d;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label numberLb;

    @FXML
    private Canvas canvas;

    private GraphicsContext graphicsContext;

    private boolean isRunning;

    private int rectPosX;
    private int imagePosX;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isRunning = true;
        imagePosX = 400;

        runCounter();
        graphicsContext = canvas.getGraphicsContext2D();
//        paint();
        onCharacterMove();

    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void paint() {
        // declaración del hilo
        new Thread(
        // declaración de la función lambda
                () -> {
                    // código de la lambda
                    while(isRunning){
                        // llamado al hilo grafico
                        Platform.runLater(
                                ()->{
                                   graphicsContext.setFill(Color.rgb(0, 0, 0));
                                   graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                                   graphicsContext.setFill(Color.rgb(0, 255, 0));
                                   graphicsContext.fillRect(rectPosX, 50, 50, 50);

                                }
                        );
                        rectPosX++;
                        // retardo del hilo
                        try {
                            Thread.sleep(100);
                        }catch (InterruptedException e){
                            throw new RuntimeException(e);
                        }
                    }
                }
        ).start();
    }

    public void runCounter() {
        new Thread(
                ()->{
                    for (int i = 0; isRunning; i++){
                        System.out.println("contador en: " + i);

                        int finalI = i;
                        Platform.runLater(()->{
                            numberLb.setText("Contador: "+finalI);
                        });

                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e){
                            throw new RuntimeException(e);
                        }
                    }
                }
        ).start();
    }

    public void onCharacterMove(){
        Image image = new Image(getClass().getResourceAsStream("/image/njse22_pixel_game_character_in_walking_sequence_2.png") , 100, 100, true, true);
        Image image2 = new Image(getClass().getResourceAsStream("/image/njse22_pixel_game_character_in_walking_sequence_1") , 100, 100, true, true);

        new Thread(
                ()-> {
                    while (isRunning){
                        graphicsContext.setFill(Color.rgb(100, 87, 121));
                        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        Platform.runLater(()->{
                            if (imagePosX%2 == 0){
                                graphicsContext.drawImage(image2, imagePosX, 100);
                            }
                            else if(imagePosX%2 != 0){
                                graphicsContext.drawImage(image, imagePosX, 100);
                            }
                        });
                        imagePosX--;
                        try {
                            Thread.sleep(500);
                        }catch (InterruptedException e){
                           throw new RuntimeException(e);
                        }
                    }
                }
        ).start();
    }

}