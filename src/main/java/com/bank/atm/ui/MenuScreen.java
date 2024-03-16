package com.bank.atm.ui;

import com.bank.Main;
import com.bank.atm.voiceassistant.AudioVoice;
import com.bank.atm.voiceassistant.OptionsVoiceRecognize;
import com.bank.atm.voiceassistant.TamilBalanceVoice;
import com.bank.database.AccessDB;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MenuScreen extends Application {

    private static String card_no ;
    private final String holderName ;
    private Text balanceText;

    private static Stage MENUSTAGE;

    public MenuScreen(String card_number){
        this.card_no = card_number;
        this.holderName = AccessDB.getName(card_number);
        newoeold = false;
    }

    //For Close When User inactive:
    Timer inactiveTimer = new Timer();
    TimerTask timeOutTask = new TimerTask() {
        @Override
        public void run() {
            closeStage(MENUSTAGE);
        }
    };
    final long TIMEOUT = 30 * 1000; // in milliseconds
    long remainingTime = TIMEOUT;

    //For Inactive Task
    private void closeStage (Stage currentStage){
        Platform.runLater(() -> {
            System.out.println("\nTime's up! Process terminated.");
            newoeold = false;
            op = null;
            AudioVoice.stopPlayback();
            timeOutTask.cancel();
            currentStage.close();
        });
    }

    //Reset the Timer
    private void resetTimer(){

            timeOutTask.cancel();
            timeOutTask = new TimerTask() {
                public void run() {
                    System.out.println("\nTime's up! Process terminated.");
                    newoeold = false;
                    op = null;
                    AudioVoice.stopPlayback();
                    closeStage(MENUSTAGE);
                    timeOutTask.cancel();
                }
            };
            inactiveTimer.schedule(timeOutTask, TIMEOUT);
    }

    //IsTheStage is Alive
    public static boolean isAliveStage(){
        return MENUSTAGE.isShowing();
    }


    //Accessories
    private OptionsVoiceRecognize op;
    private AnchorPane balancepane;
    private AnchorPane Withdraw;
    private AnchorPane fastCashPane;
    private AnchorPane pinchangePane;
    private ImageView withdrawImageView;
    private ImageView depositImage;
    private Text withdrawText;
    private Button withdrawButton;
    private TextField WithorDraw;

    private boolean newoeold = false;
    private  void voiceAccessPane(){
        op = OptionsVoiceRecognize.getInstance();
        while (newoeold){
            System.out.println("Welcome");
            if(newoeold) {
                String voiceCommand = op.listen();
                System.out.println(voiceCommand);
                switch (voiceCommand.toLowerCase()) {
                    case "thogaii":
                    case "thoogaie":
                        balancePane(new ActionEvent());
                        break;
                    case "aedukavum":
                    case "aedukkauvum":
                    case "aedukkavum":
                        withdrawPane(new ActionEvent());
                        break;
                    case "seluththuvum":
                    case "saeluththuvum":
                        depositPane(new ActionEvent());
                        break;
                    case "maatruovum":
                    case "maatrovum":
                        pinchangePane(new ActionEvent());
                        break;

                }
            }
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
//        inactiveTimer.scheduleAtFixedRate(timeOutTask, TIMEOUT, 1000); // Schedule to run every second

        this.MENUSTAGE = primaryStage;
        primaryStage.setMaximized(true);
        AnchorPane scene2 = new AnchorPane();
        scene2.setMinHeight(568.0);
        scene2.setMinWidth(958.0);
        scene2.setMaxHeight(Double.MAX_VALUE);
        scene2.setMaxWidth(Double.MAX_VALUE);
        scene2.setId("scene2");
        scene2.getStylesheets().add("Home.css");


        AnchorPane an3 = new AnchorPane();
        an3.setLayoutY(17.0);
        an3.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight() - 68);
        an3.setPrefWidth(254.0);
        an3.setStyle("-fx-background-color: white;");
        an3.setId("an3");
        an3.getStylesheets().add("Home.css");


        ImageView imageView = new ImageView(new Image("User.png"));
        imageView.setFitHeight(47.0);
        imageView.setFitWidth(45.0);
        imageView.setLayoutX(12.0);
        imageView.setLayoutY(31.0);
        imageView.setId("imageView");

        Label label = new Label(holderName);
        label.setLayoutX(60.0);
        label.setLayoutY(40.0);
        label.setPrefHeight(26.0);
        label.setPrefWidth(180.0);
        label.setFont(new Font("Times New Roman Bold", 21.0));
        label.setId("label");

        Button button1 = new Button("Balance");
        button1.setLayoutX(28.0);
        button1.setLayoutY(149.0);
        button1.setPrefHeight(34.0);
        button1.setPrefWidth(180.0);
        button1.setTextFill(javafx.scene.paint.Color.WHITE);
        button1.setFont(new Font("Times New Roman", 17.0));
        button1.setId("InScene");

        Button button2 = new Button("WithDraw");
        button2.setLayoutX(28.0);
        button2.setLayoutY(218.0);
        button2.setPrefHeight(34.0);
        button2.setPrefWidth(180.0);
        button2.setTextFill(javafx.scene.paint.Color.WHITE);
        button2.setFont(new Font("Times New Roman", 17.0));
        button2.setId("InScene");

        Button button3 = new Button("Deposit");
        button3.setLayoutX(28.0);
        button3.setLayoutY(287.0);
        button3.setPrefHeight(34.0);
        button3.setPrefWidth(180.0);
        button3.setTextFill(javafx.scene.paint.Color.WHITE);
        button3.setFont(new Font("Times New Roman", 17.0));
        button3.setId("InScene");

        Button button4 = new Button("Fast Cash");
        button4.setLayoutX(32.0);
        button4.setLayoutY(360.0);
        button4.setPrefHeight(34.0);
        button4.setPrefWidth(170.0);
        button4.setTextFill(javafx.scene.paint.Color.WHITE);
        button4.setFont(new Font("Times New Roman", 17.0));
        button4.setId("InScene");

        Button button5 = new Button("Pin Change");
        button5.setLayoutX(32.0);
        button5.setLayoutY(435.0);
        button5.setPrefHeight(34.0);
        button5.setPrefWidth(170.0);
        button5.setTextFill(javafx.scene.paint.Color.WHITE);
        button5.setFont(new Font("Times New Roman", 17.0));
        button5.setId("InScene");

        Text text = new Text("Account");
        text.setLayoutX(130.0);
        text.setLayoutY(80.0);
        text.setFont(new Font("Times New Roman", 12.0));
        text.setId("text");

        an3.getChildren().addAll(imageView, label, button1, button2, button3, button4, button5);


        AnchorPane Dis = new AnchorPane();
        Dis.setLayoutX(298.0);
        Dis.setLayoutY(50.0);
        Dis.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight()-130);
        Dis.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth()-350);
        Dis.setStyle("-fx-background-color: white;");
        Dis.setId("Dis");

        balancepane = new AnchorPane();
        //balancepane.setDisable(true);
        balancepane.setLayoutX(100.0);
        balancepane.setLayoutY(100.0);
        balancepane.setPrefHeight(430.0);
        balancepane.setPrefWidth(815.0);
        balancepane.setStyle("-fx-background-color: white;");
        balancepane.setVisible(false);
        balancepane.setId("balancepane");

        balanceText = new Text("Your Account Balance is Rs.");
        balanceText.setLayoutX(45.0);
        balanceText.setLayoutY(197.0);
        balanceText.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        balanceText.setStrokeWidth(0.0);
        balanceText.setWrappingWidth(456.1031494140625);
        balanceText.setFont(new Font("Arial", 20.0));
        balanceText.setId("balanceText");

        ImageView balanceImageView = new ImageView(new Image("cash1.png"));
        balanceImageView.setFitHeight(69.0);
        balanceImageView.setFitWidth(80.0);
        balanceImageView.setLayoutX(263.0);
        balanceImageView.setLayoutY(58.0);
        balanceImageView.setId("balanceImageView");

        balancepane.getChildren().addAll(balanceText, balanceImageView);

        Withdraw = new AnchorPane();
        Withdraw.setLayoutX(100.0);
        Withdraw.setLayoutY(100.0);
        Withdraw.setPrefHeight(430.0);
        Withdraw.setPrefWidth(815.0);
        Withdraw.setStyle("-fx-background-color: white;");
        Withdraw.setId("Withdraw");
        Withdraw.setVisible(false);

        withdrawImageView = new ImageView(new Image("withdraw.png"));
        withdrawImageView.setFitHeight(69.0);
        withdrawImageView.setFitWidth(80.0);
        withdrawImageView.setLayoutX(263.0);
        withdrawImageView.setLayoutY(41.0);
        withdrawImageView.setId("withdrawImageView");

        depositImage = new ImageView(new Image("deposit.png"));
        depositImage.setFitHeight(69.0);
        depositImage.setFitWidth(80.0);
        depositImage.setLayoutX(263.0);
        depositImage.setLayoutY(41.0);
        depositImage.setVisible(false);
        depositImage.setId("depositImage");

        WithorDraw = new TextField();
        WithorDraw.setLayoutX(163.0);
        WithorDraw.setLayoutY(165.0);
        WithorDraw.setPrefHeight(38.0);
        WithorDraw.setPrefWidth(282.0);
        WithorDraw.setId("WithorDraw");
        WithorDraw.setAlignment(javafx.geometry.Pos.CENTER);
        WithorDraw.setTextFormatter(new TextFormatter<>(change ->
                (change.getText().matches("[0-9]*")) ? change : null));

        withdrawText = new Text("Withdraw");
        withdrawText.setLayoutX(262.0);
        withdrawText.setLayoutY(143.0);
        withdrawText.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        withdrawText.setStrokeWidth(0.0);
        withdrawText.setText("Withdraw");
        withdrawText.setFont(new Font("Times New Roman", 18.0));
        withdrawText.setId("WithAmountBox");

        withdrawButton = new Button("WithDraw");
        withdrawButton.setLayoutX(229.0);
        withdrawButton.setLayoutY(232.0);
        withdrawButton.setMnemonicParsing(false);
        withdrawButton.setPrefHeight(34.0);
        withdrawButton.setPrefWidth(137.0);
        withdrawButton.setTextFill(javafx.scene.paint.Color.WHITE);
        withdrawButton.setFont(new Font("Times New Roman Bold", 16.0));
        withdrawButton.setId("InScene");

        AnchorPane PayStatus = new AnchorPane();
        //PayStatus.setDisable(true);
        PayStatus.setLayoutX(101.0);
        PayStatus.setLayoutY(58.0);
        PayStatus.setPrefHeight(200.0);
        PayStatus.setPrefWidth(406.0);
        PayStatus.setStyle("-fx-background-color: White;");
        PayStatus.setVisible(false);
        PayStatus.setId("PayStatus");

        ImageView payStatusImageView = new ImageView(new Image("icons8-verified-account-96.png"));
        payStatusImageView.setFitHeight(56.0);
        payStatusImageView.setFitWidth(62.0);
        payStatusImageView.setLayoutX(177.0);
        payStatusImageView.setLayoutY(32.0);
        payStatusImageView.setId("payStatusImageView");

        Label payStatusLabel = new Label("Payment Succesfull");
        payStatusLabel.setLayoutX(123.0);
        payStatusLabel.setLayoutY(110.0);
        payStatusLabel.setText("Payment Succesfull");
        payStatusLabel.setFont(new Font("Times New Roman Bold", 20.0));
        payStatusLabel.setId("payStatusLabel");



  //Balance Button
        //Withdraw Button

        withdrawButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                resetTimer();
                if(WithorDraw.getText() != "" && withdrawText.getText().equals("WithDraw")){
                    //Withdraw
                    try {
                        Integer req_amount = Integer.valueOf(WithorDraw.getText());
                        if(req_amount % 100 == 0 || req_amount % 200 == 0 || req_amount % 500 == 0 ) {
                            if (Integer.valueOf(AccessDB.getBalance(card_no)) - 100 > Integer.valueOf(WithorDraw.getText())) {
                                if (AccessDB.withdrawAmount(card_no, Integer.valueOf(WithorDraw.getText()))) {
                                    //Success
                                    Thread playAudio = new Thread(()->AudioVoice.voiceOfFile("takeAmount"));
                                    playAudio.start();
                                    Withdraw.setVisible(false);
                                    newoeold = false;
                                    op = null;
                                    primaryStage.close();
                                    Main.closeMenu();
                                } else {
                                    //Failure
                                    System.out.println("Sorry Plesse Visit Bank");
                                }
                            }
                        }else{
                            //Not Correct Amount available in ATM..
                            WithorDraw.setText("");
                            Thread noAmount = new Thread(() -> AudioVoice.voiceOfFile("correctAmount"));
                            noAmount.start();
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if(WithorDraw.getText() != "" && withdrawText.getText().equals("Deposit")){
                    //Deposit

                    try {
                        Integer req_amount = Integer.valueOf(WithorDraw.getText());
                        if(req_amount % 100 == 0 || req_amount % 200 == 0 || req_amount % 500 == 0 ) {
                             if (AccessDB.depositAmount(card_no, Integer.valueOf(WithorDraw.getText()))) {
                                    //Success
                                    Thread ok = new Thread(()->AudioVoice.voiceOfFile("finished"));
                                    ok.start();
                                    Withdraw.setVisible(false);
                                    newoeold = false;

                                    WithorDraw.setText("");
                                } else {
                                 //Failure
                                 System.out.println("Sorry Plesse Visit Bank");
                             }
                        }else{
                            //Not Correct Amount available in India..
                            Thread noAmount = new Thread(() -> AudioVoice.voiceOfFile("correctAmount"));
                            noAmount.start();
                            WithorDraw.setText("");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });




        PayStatus.getChildren().addAll(payStatusImageView, payStatusLabel);

        Withdraw.getChildren().addAll(withdrawImageView, depositImage, WithorDraw, withdrawText, withdrawButton);

        // FastCash Pane
        fastCashPane = new AnchorPane();
//        fastCashPane.setDisable(true);
        fastCashPane.setLayoutX(56.0);
        fastCashPane.setLayoutY(88.0);
        fastCashPane.setPrefHeight(330.0);
        fastCashPane.setPrefWidth(594.0);
        fastCashPane.setStyle("-fx-background-color: white;");
        fastCashPane.setVisible(false);
        fastCashPane.getStylesheets().add("Home.css");
        fastCashPane.setId("FastCash");

        ImageView moneyImageView = new ImageView(new Image("money.png"));
        moneyImageView.setFitHeight(69.0);
        moneyImageView.setFitWidth(72.0);
        moneyImageView.setLayoutX(263.0);
        moneyImageView.setLayoutY(13.0);

        EventHandler<ActionEvent> fastCashWithraw = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resetTimer();
                String eventString = actionEvent.toString();
                String whichCash = eventString.substring(eventString.indexOf("'") + 1, eventString.lastIndexOf("'"));;
                switch (whichCash){
                    case "Rs.100":
                        try {
                            if (AccessDB.withdrawAmount(card_no, 100)) {
                                //Success
                                Thread playAudio = new Thread(()->AudioVoice.voiceOfFile("takeAmount"));
                                playAudio.start();
                                fastCashPane.setVisible(false);
                            } else {
                                //Failure
                                System.out.println("Sorry Plesse Visit Bank");
                            }
                        } catch (SQLException e) {
                            Thread notsucces = new Thread(()-> AudioVoice.voiceOfFile("notworking"));
                            notsucces.start();
                        }
                        break;

                    case "Rs.200":
                        try {
                            if (AccessDB.withdrawAmount(card_no, 200)) {
                                //Success
                                Thread playAudio = new Thread(()->AudioVoice.voiceOfFile("takeAmount"));
                                playAudio.start();
                                fastCashPane.setVisible(false);
                            } else {
                                //Failure
                                System.out.println("Sorry Plesse Visit Bank");
                            }
                        } catch (SQLException e) {
                            Thread notsucces = new Thread(()-> AudioVoice.voiceOfFile("notworking"));
                            notsucces.start();
                        }
                        break;

                    case "Rs.500":
                        try {
                            if (AccessDB.withdrawAmount(card_no, 500)) {
                                //Success
                                Thread playAudio = new Thread(()->AudioVoice.voiceOfFile("takeAmount"));
                                playAudio.start();
                                fastCashPane.setVisible(false);
                            } else {
                                //Failure
                                System.out.println("Sorry Plesse Visit Bank");
                            }
                        } catch (SQLException e) {
                            Thread notsucces = new Thread(()-> AudioVoice.voiceOfFile("notworking"));
                            notsucces.start();
                        }
                        break;
                    case "Rs.1000":
                        try {
                            if (AccessDB.withdrawAmount(card_no, 1000)) {
                                //Success
                                Thread playAudio = new Thread(()->AudioVoice.voiceOfFile("takeAmount"));
                                playAudio.start();
                                fastCashPane.setVisible(false);
                            } else {
                                //Failure
                                System.out.println("Sorry Plesse Visit Bank");
                            }
                        } catch (SQLException e) {
                            Thread notsucces = new Thread(()-> AudioVoice.voiceOfFile("notworking"));
                            notsucces.start();
                        }
                        break;
                    case "Rs.1500":
                        try {
                            if (AccessDB.withdrawAmount(card_no, 1500)) {
                                //Success
                                Thread playAudio = new Thread(()->AudioVoice.voiceOfFile("takeAmount"));
                                playAudio.start();
                                fastCashPane.setVisible(false);
                            } else {
                                //Failure
                                System.out.println("Sorry Plesse Visit Bank");
                            }
                        } catch (SQLException e) {
                            Thread notsucces = new Thread(()-> AudioVoice.voiceOfFile("notworking"));
                            notsucces.start();
                        }
                        break;
                }
            }
        };

        Button rs100Button = new Button("Rs.100");
        rs100Button.setLayoutX(40.0);
        rs100Button.setLayoutY(123.0);
        rs100Button.setMnemonicParsing(false);
        rs100Button.setPrefHeight(34.0);
        rs100Button.setPrefWidth(116.0);
        rs100Button.setTextFill(javafx.scene.paint.Color.WHITE);
        rs100Button.setFont(new javafx.scene.text.Font("Times New Roman Bold", 18.0));
        rs100Button.setId("InScene");

        rs100Button.setOnAction(fastCashWithraw);

        Button rs200Button = new Button("Rs.200");
        rs200Button.setLayoutX(40.0);
        rs200Button.setLayoutY(203.0);
        rs200Button.setMnemonicParsing(false);
        rs200Button.setPrefHeight(34.0);
        rs200Button.setPrefWidth(116.0);
        rs200Button.setTextFill(javafx.scene.paint.Color.WHITE);
        rs200Button.setFont(new javafx.scene.text.Font("Times New Roman Bold", 18.0));
        rs200Button.setId("InScene");
        rs200Button.setOnAction(fastCashWithraw);

        Button rs500Button = new Button("Rs.500");
        rs500Button.setLayoutX(239.0);
        rs500Button.setLayoutY(123.0);
        rs500Button.setMnemonicParsing(false);
        rs500Button.setPrefHeight(34.0);
        rs500Button.setPrefWidth(116.0);
        rs500Button.setTextFill(javafx.scene.paint.Color.WHITE);
        rs500Button.setFont(new javafx.scene.text.Font("Times New Roman Bold", 18.0));
        rs500Button.setId("InScene");
        rs500Button.setOnAction(fastCashWithraw);

        Button rs1000Button = new Button("Rs.1000");
        rs1000Button.setLayoutX(240.0);
        rs1000Button.setLayoutY(203.0);
        rs1000Button.setMnemonicParsing(false);
        rs1000Button.setPrefHeight(34.0);
        rs1000Button.setPrefWidth(116.0);
        rs1000Button.setTextFill(javafx.scene.paint.Color.WHITE);
        rs1000Button.setFont(new javafx.scene.text.Font("Times New Roman Bold", 18.0));
        rs1000Button.setId("InScene");
        rs1000Button.setOnAction(fastCashWithraw);

        Button rs1500Button = new Button("Rs.1500");
        rs1500Button.setLayoutX(426.0);
        rs1500Button.setLayoutY(123.0);
        rs1500Button.setMnemonicParsing(false);
        rs1500Button.setPrefHeight(34.0);
        rs1500Button.setPrefWidth(116.0);
        rs1500Button.setTextFill(javafx.scene.paint.Color.WHITE);
        rs1500Button.setFont(new javafx.scene.text.Font("Times New Roman Bold", 18.0));
        rs1500Button.setId("InScene");
        rs1500Button.setOnAction(fastCashWithraw);

        Button returnButton = new Button("Return");
        returnButton.setLayoutX(426.0);
        returnButton.setLayoutY(203.0);
        returnButton.setMnemonicParsing(false);
        returnButton.setPrefHeight(34.0);
        returnButton.setPrefWidth(116.0);
        returnButton.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        returnButton.setTextFill(javafx.scene.paint.Color.WHITE);
        returnButton.setFont(new javafx.scene.text.Font("Times New Roman Bold", 18.0));

        fastCashPane.getChildren().addAll(moneyImageView, rs100Button, rs200Button, rs500Button, rs1000Button, rs1500Button);

        // Pinchange Pane
        pinchangePane = new AnchorPane();
        pinchangePane.setVisible(false);
        pinchangePane.setLayoutX(55.0);
        pinchangePane.setLayoutY(88.0);
        pinchangePane.setPrefHeight(330.0);
        pinchangePane.setPrefWidth(594.0);
        pinchangePane.setStyle("-fx-background-color: white;");
        pinchangePane.getStylesheets().add("Home.css");
        pinchangePane.setId("Pinchange");

        ImageView keyImageView = new ImageView(new Image("key.png"));
        keyImageView.setFitHeight(56.0);
        keyImageView.setFitWidth(56.0);
        keyImageView.setLayoutX(269.0);
        keyImageView.setLayoutY(26.0);

        PasswordField currentPin = new PasswordField();
        currentPin.setLayoutX(200.0);
        currentPin.setLayoutY(138.0);
        currentPin.setPrefHeight(34.0);
        currentPin.setPrefWidth(197.0);

        PasswordField newPin = new PasswordField();
        newPin.setLayoutX(198.0);
        newPin.setLayoutY(203.0);
        newPin.setPrefHeight(34.0);
        newPin.setPrefWidth(198.0);

        Button changeButton = new Button("Change");
        changeButton.setLayoutX(247.0);
        changeButton.setLayoutY(260.0);
        changeButton.setMnemonicParsing(false);
        changeButton.setPrefHeight(26.0);
        changeButton.setPrefWidth(116.0);
        changeButton.setTextFill(javafx.scene.paint.Color.WHITE);
        changeButton.setFont(new javafx.scene.text.Font("Times New Roman Bold", 18.0));
        changeButton.setId("InScene");

        Text currentText = new Text("Current Pin");
        currentText.setLayoutX(209.0);
        currentText.setLayoutY(126.0);
        currentText.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        currentText.setStrokeWidth(0.0);
        currentText.setFont(new javafx.scene.text.Font("Times New Roman Bold", 13.0));

        Text newText = new Text("New Pin");
        newText.setLayoutX(208.0);
        newText.setLayoutY(195.0);
        newText.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        newText.setStrokeWidth(0.0);
        newText.setFont(new javafx.scene.text.Font("Times New Roman Bold", 13.0));

        //PinChange


        changeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resetTimer();
                try {
                    if(AccessDB.isPinValid(card_no, currentPin.getText())){
                        if(AccessDB.updatePin(card_no, newPin.getText())) {
                            Thread finish = new Thread(()->AudioVoice.voiceOfFile("pinchanged"));
                            finish.start();
                            currentPin.setText("");
                            newPin.setText("");
                            balancepane.setVisible(false);
                            Withdraw.setVisible(false);
                            withdrawImageView.setVisible(true);
                            depositImage.setVisible(false);
                            fastCashPane.setVisible(false);
                            pinchangePane.setVisible(false);

                        }
                        else
                            AudioVoice.voiceOfFile("notworking");
                    }else{
                        Thread pinmis = new Thread(()->AudioVoice.voiceOfFile("pinmismatch"));
                        pinmis.start();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        pinchangePane.getChildren().addAll(keyImageView, currentPin, newPin, changeButton, currentText, newText);


        Dis.getChildren().addAll(balancepane, Withdraw, fastCashPane, pinchangePane);

        button1.setOnAction(this::balancePane);

        //Withdraw view button

        button2.setOnAction(this::withdrawPane);

        button3.setOnAction(this::depositPane);

        button4.setOnAction(this::fastcashPane);

        button5.setOnAction(this::pinchangePane);

        newoeold = true;

        Thread voiceAcces = new Thread(() -> voiceAccessPane());
        voiceAcces.start();

        scene2.getChildren().addAll(an3, Dis);
        scene2.setPadding(new Insets(10));

        Scene scene = new Scene(scene2, 958, 568);
        scene.getStylesheets().add("Home.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("ATM Interface");
        primaryStage.show();

        Thread audioMenu = new Thread(() -> AudioVoice.voiceOfFile("menu_options"));
        audioMenu.start();
    }

    private void pinchangePane(ActionEvent event){
        resetTimer();
        balancepane.setVisible(false);
        Withdraw.setVisible(false);
        fastCashPane.setVisible(false);
        pinchangePane.setVisible(true);
        Thread startOver = new Thread(() -> AudioVoice.voiceOfFile("pinchangevoice"));
        startOver.start();
    }
    private void fastcashPane(ActionEvent event){
        resetTimer();
        balancepane.setVisible(false);
        Withdraw.setVisible(false);
        fastCashPane.setVisible(true);
        pinchangePane.setVisible(false);
        Thread startOver = new Thread(() -> AudioVoice.voiceOfFile("fastcashvoice"));
        startOver.start();
    }

    private void balancePane(ActionEvent event){

            resetTimer();
            if (!balancepane.isVisible()) {
                balancepane.setVisible(true);
                Withdraw.setVisible(false);
                fastCashPane.setVisible(false);
                pinchangePane.setVisible(false);
                try {
                    Thread startBalance = new Thread(() -> AudioVoice.voiceOfFile("balance"));
                    startBalance.start();
                    Thread balanceAudio = new Thread(() -> {
                        try {
                            TamilBalanceVoice.balance2Voice(AccessDB.getBalance(card_no));
                        } catch (SQLException e) {
                            Thread notworking_thread = new Thread(() -> AudioVoice.voiceOfFile("notworking"));
                        }
                    });
                    Thread.sleep(2000);
                    balanceAudio.start();
                    balanceText.setText("Your Account Balance is  " + AccessDB.getBalance(card_no) + "  .");
                } catch (SQLException | NullPointerException | InterruptedException e) {
                    Thread notworking_thread = new Thread(() -> AudioVoice.voiceOfFile("notworking"));
                    notworking_thread.start();
                    System.exit(1);
                }
            }

    }

    private void withdrawPane(ActionEvent event){
        resetTimer();
        balancepane.setVisible(false);
        Withdraw.setVisible(true);
        withdrawImageView.setVisible(true);
        depositImage.setVisible(false);
        withdrawText.setText("WithDraw");
        withdrawButton.setText("WithDraw");
        WithorDraw.setText("");
        fastCashPane.setVisible(false);
        pinchangePane.setVisible(false);
        Thread startOver = new Thread(() -> AudioVoice.voiceOfFile("withdrawvoice"));
        startOver.start();
    }

    private void depositPane(ActionEvent event){
        resetTimer();
        balancepane.setVisible(false);
        Withdraw.setVisible(true);
        withdrawImageView.setVisible(false);
        depositImage.setVisible(true);
        withdrawText.setText("Deposit");
        WithorDraw.setText("");
        withdrawButton.setText("Deposit");
        fastCashPane.setVisible(false);
        pinchangePane.setVisible(false);
        Thread startOver = new Thread(() -> AudioVoice.voiceOfFile("depositvoice"));
        startOver.start();
    }

    private void reloadStage(Stage stage) {
        Scene scene = stage.getScene();
        stage.setScene(null);
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
