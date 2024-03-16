module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires sphinx4.core;
    requires java.sql;
    requires javafx.base;
    requires java.desktop;


    opens com.bank.atm.ui to javafx.fxml;
    exports com.bank.atm.ui;
    exports com.bank.atm.security;
    opens com.bank.atm.security to javafx.fxml;
    exports com.bank.atm.voiceassistant;
    opens com.bank.atm.voiceassistant to javafx.fxml;
    exports com.bank;
}