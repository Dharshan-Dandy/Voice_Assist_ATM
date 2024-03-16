package com.bank.atm.ui;

import com.bank.Main;
import com.bank.atm.voiceassistant.AudioVoice;
import com.bank.database.AccessDB;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.sql.SQLException;

public class HomeScreen extends Application {

    private PasswordField passTextField;
    private Button button;
    private TextField textField;
    private ImageView padlockImageView;
    @Override
    public void start(Stage primaryStage) {

        //Whole Screen Adjustment
        primaryStage.setMaximized(true);
        primaryStage.initStyle(StageStyle.UNDECORATED);


        // Create root pane
        Pane root = new Pane();
        root.setId("img1");
        root.setPrefSize(948, 541);
        root.getStylesheets().add("Home.css");

        // Create AnchorPane
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setId("an2");
        anchorPane.setPrefSize(925, 560);

        anchorPane.setLayoutX((Screen.getPrimary().getVisualBounds().getWidth() - anchorPane.getPrefWidth()) / 2);
        anchorPane.setLayoutY((Screen.getPrimary().getVisualBounds().getHeight() - anchorPane.getPrefHeight()) / 2);
        anchorPane.getStylesheets().add("Home.css");

        // Create Label
        Label label = new Label("ACCOUNT");
        label.setId("lab1");
        label.setLayoutX(660);
        label.setLayoutY(110);
        label.setPrefSize(120, 120);
        label.setFont(Font.font("Times New Roman Bold", FontWeight.BOLD,21));

        // Create Button
        button = new Button("CONFIRM");
        button.setId("btn1");
        button.setDefaultButton(true);
        button.setLayoutX(630);
        button.setLayoutY(300);
        button.setPrefSize(170, 40);
        button.setTextFill(javafx.scene.paint.Color.WHITE);
        button.setFont(Font.font("Times New Roman Bold",FontWeight.BOLD, 14));
        button.setOnAction(this::checkCardNumber);

        // Create ImageView for account image
        ImageView accountImageView = new ImageView("account.png");
        accountImageView.setId("V");
        accountImageView.setFitHeight(70);
        accountImageView.setFitWidth(75);
        accountImageView.setLayoutX(675);
        accountImageView.setLayoutY(61);
        accountImageView.setPreserveRatio(true);
        accountImageView.setSmooth(false);

        // Create TextField for card number
        textField = new TextField();
        textField.setId("text");
        textField.setAlignment(javafx.geometry.Pos.CENTER);
        textField.setLayoutX(590);
        textField.setLayoutY(225);
        textField.setPrefSize(250, 40);
        textField.setPromptText("Card Number");
        textField.setFont(Font.font("Times New Roman Bold", 30));
        textField.setTextFormatter(new TextFormatter<>(change ->
                (change.getText().matches("[0-9]*")) ? change : null));



        // Create ImageView for admin image
        ImageView adminImageView = new ImageView(new Image(("card_number.png")));
        adminImageView.setFitHeight(30);
        adminImageView.setFitWidth(25);
        adminImageView.setLayoutX(595);
        adminImageView.setLayoutY(233);
        adminImageView.setPickOnBounds(true);
        adminImageView.setPreserveRatio(true);

        // Create AnchorPane for logo
        AnchorPane logoAnchorPane = new AnchorPane();
        logoAnchorPane.setId("an3");
        logoAnchorPane.setLayoutY(-1);
        logoAnchorPane.setPrefSize(500, 561);
        logoAnchorPane.getStylesheets().add("Home.css");

        // Create ImageView for logo image
        ImageView logoImageView = new ImageView(new Image(("Logo.png")));
        logoImageView.setFitHeight(100);
        logoImageView.setFitWidth(700);
        logoImageView.setLayoutX(61);
        logoImageView.setLayoutY(200);
        logoImageView.setPickOnBounds(true);
        logoImageView.setPreserveRatio(true);

        // Create TextField for pin number
        passTextField = new PasswordField();
        passTextField.setId("pass");
        passTextField.setAlignment(javafx.geometry.Pos.CENTER);
        passTextField.setVisible(false);
        passTextField.setLayoutX(590);
        passTextField.setLayoutY(290);
        passTextField.setPrefSize(250, 40);
        passTextField.setPromptText("Pin Number");
        passTextField.setFont(Font.font("Times New Roman Bold", 15));
        passTextField.setTextFormatter(new TextFormatter<>(change ->
                (change.getText().matches("[0-9]*")) ? change : null));

        // Create ImageView for padlock image
        padlockImageView = new ImageView(("padlock.png"));
        padlockImageView.setDisable(true);
        padlockImageView.setVisible(false);
        padlockImageView.setFitHeight(27);
        padlockImageView.setFitWidth(23);
        padlockImageView.setLayoutX(600);
        padlockImageView.setLayoutY(300);
        padlockImageView.setPickOnBounds(true);
        padlockImageView.setPreserveRatio(true);



        // Add children to anchorPane
        anchorPane.getChildren().addAll(label, button, accountImageView, textField, adminImageView, logoAnchorPane, passTextField, padlockImageView);
        logoAnchorPane.getChildren().add(logoImageView);

        // Add anchorPane to root
        root.getChildren().add(anchorPane);

        // Create scene and set stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home");
        primaryStage.show();

        Thread audioThread = new Thread(() -> AudioVoice.voiceOfFile("welcome_nicole"));
        audioThread.start();

    }

    private void checkCardNumber(ActionEvent actTextFieldionEvent) {
        if(! textField.getText().equals("")) {
            try {
                if (AccessDB.isAtm_Registered(textField.getText()) && !passTextField.isVisible()) {

                    Thread audioThread_secret = new Thread(()->AudioVoice.voiceOfFile("secret_pin"));
                    audioThread_secret.start();
                    passTextField.setVisible(true);
                    textField.setDisable(true);
                    TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), button);
                    translateTransition.setToY(10); // Adjust this value as needed
                    translateTransition.play();
                    button.setLayoutY(350);
                    padlockImageView.setVisible(true);

                } else if (passTextField.isVisible()){
                    if(passTextField.getText().equals("")) {
                        playWrongInputAnimation(passTextField);
                    }
                    else if (AccessDB.isPinValid(textField.getText(), passTextField.getText())){

                        Main.startMenu(textField.getText());
                        button.setLayoutY(300);
                        textField.setDisable(false);
                        passTextField.setVisible(false);
                        padlockImageView.setVisible(false);
                        textField.setText("");
                        passTextField.setText("");

                    }
                    else{

                        Thread mismatchThread = new Thread(() -> AudioVoice.voiceOfFile("pinmismatch"));
                        mismatchThread.start();
                        playWrongInputAnimation(passTextField);
                        passTextField.setText("");
                    }
                }else{
                    playWrongInputAnimation(textField);
                }
            } catch (Exception e) {
                playWrongInputAnimation(textField);
                AudioVoice.voiceOfFile("notworking");
            }



        } else {
            playWrongInputAnimation(textField);
        }
    }

    private void playWrongInputAnimation(TextField textField) {
        // Create a timeline for the animation
        Timeline timeline = new Timeline();

        // Add keyframes to animate the translateX property
        double shift = 10;
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.seconds(0.1), new KeyValue(textField.styleProperty(), "-fx-border-color: red;")),
                new KeyFrame(Duration.ZERO, new KeyValue(textField.translateXProperty(), 0)),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(textField.translateXProperty(), -shift)),
                new KeyFrame(Duration.seconds(0.2), new KeyValue(textField.translateXProperty(), shift)),
                new KeyFrame(Duration.seconds(0.3), new KeyValue(textField.translateXProperty(), -shift)),
                new KeyFrame(Duration.seconds(0.4), new KeyValue(textField.translateXProperty(), shift)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(textField.translateXProperty(), -shift)),
                new KeyFrame(Duration.seconds(0.6), new KeyValue(textField.translateXProperty(), shift)),
                new KeyFrame(Duration.seconds(0.7), new KeyValue(textField.translateXProperty(), -shift)),
                new KeyFrame(Duration.seconds(0.8), new KeyValue(textField.translateXProperty(), shift)),
                new KeyFrame(Duration.seconds(0.9), new KeyValue(textField.translateXProperty(), 0)),
        new KeyFrame(Duration.seconds(1), new KeyValue(textField.styleProperty(), "-fx-border-color: Black;"))
        );

        // Play the animation
        timeline.play();
    }
}
