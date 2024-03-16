package com.bank;

import com.bank.atm.ui.HomeScreen;
import com.bank.atm.ui.MenuScreen;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main{

    private static boolean menuShows = false;
    private static Thread homeViewThread;
    private static MenuScreen menuScreen;


    public static void startMenu(String card_no) {
        // Start the menu screen
        menuScreen = new MenuScreen(card_no);
        try {
            Stage menuStage = new Stage();
            menuStage.setMaximized(true);
            menuStage.initStyle(StageStyle.UNDECORATED);
            menuScreen.start(menuStage);
            menuShows = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeMenu(){
        try {
            menuScreen.stop();
            menuShows = false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        // Start the HomeScreen on a separate thread
        homeViewThread = new Thread(() -> Application.launch(HomeScreen.class, args));
        homeViewThread.start();
    }

    public static boolean canRecongize() {
        return menuShows;
    }
}
