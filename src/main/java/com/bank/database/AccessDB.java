package com.bank.database;

import com.bank.database.connection.Dblink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccessDB {
    public static boolean isAtm_Registered(String cardNumber) throws Exception {
        try (Connection conn = Dblink.connect();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT acct_no FROM atm_card_details WHERE card_no = ?;")) {
            conn.setReadOnly(true);
            preparedStatement.setInt(1, Integer.parseInt(cardNumber));
            ResultSet queryOutput = preparedStatement.executeQuery();
            if (queryOutput.next())
                return true;
            return false;
        } catch (SQLException e) {
            System.out.println("H DB Error");
        }
        return false;
    }

    public static boolean isPinValid(String cardno, String dob) throws Exception {
        try (Connection conn = Dblink.connect();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT acct_no, atm_pin FROM atm_card_details WHERE card_no = ?;")) {
            conn.setReadOnly(true);
            preparedStatement.setInt(1, Integer.parseInt(cardno));
            ResultSet queryOutput = preparedStatement.executeQuery();
            if (queryOutput.next()) {
                String pins = queryOutput.getString("atm_pin");
                if (pins.equals(String.valueOf(dob)))
                    return true;
                return false;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getName(String cardNo) {
        try (Connection conn = Dblink.connect();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT holder_name FROM account_details WHERE acct_no = (SELECT acct_no from atm_card_details WHERE card_no = ?);")) {
            conn.setReadOnly(true);
            preparedStatement.setInt(1, Integer.parseInt(cardNo));
            ResultSet queryOutput = preparedStatement.executeQuery();
            if (queryOutput.next()) {
                String hold_name = queryOutput.getString("holder_name");
                return hold_name;
            }
            System.out.println(queryOutput.getString(1));
            return "Not-Available";

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Not-Available";
    }

    public static String getBalance(String card_no) throws SQLException,NullPointerException {

        try(Connection conn = Dblink.connect();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT amount FROM account_details WHERE acct_no = (SELECT acct_no from atm_card_details WHERE card_no = ?);"))
        {
            conn.setReadOnly(true);
            preparedStatement.setInt(1, Integer.parseInt(card_no));
            ResultSet queryOutput = preparedStatement.executeQuery();
            if(queryOutput.next()) {
                String amount = queryOutput.getString("amount");
                return amount;
            }
            return "Sorry Error";

        }
    }

    public static boolean withdrawAmount(String card_no, Integer amount) throws SQLException {
        try(Connection conn = Dblink.connect();
            PreparedStatement preparedStatement = conn.prepareStatement(" UPDATE account_details set amount = amount - ? where acct_no = (select acct_no from atm_card_details where card_no = ?);"))
        {
            conn.setReadOnly(true);
            preparedStatement.setInt(2,Integer.valueOf(card_no));
            preparedStatement.setInt(1, amount);
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if any rows were affected by the update
            if (rowsAffected > 0) {
                return true;
            } else {
                // Rollback the transaction if no rows were affected
                return false;
            }
        }
    }

    public static boolean depositAmount(String cardNo, Integer amount) throws SQLException {
        try(Connection conn = Dblink.connect();
            PreparedStatement preparedStatement = conn.prepareStatement(" UPDATE account_details set amount = amount + ? where acct_no = (select acct_no from atm_card_details where card_no = ?);"))
        {
            conn.setReadOnly(true);
            preparedStatement.setInt(2,Integer.valueOf(cardNo));
            preparedStatement.setInt(1, amount);
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if any rows were affected by the update
            if (rowsAffected > 0) {
                return true;
            } else {
                // Rollback the transaction if no rows were affected
                return false;
            }
        }
    }

    public static boolean updatePin(String cardNo, String pin) throws SQLException {
        try(Connection conn = Dblink.connect();
            PreparedStatement preparedStatement = conn.prepareStatement(" UPDATE atm_card_details SET atm_pin = ? where card_no = ?;"))
        {
            conn.setReadOnly(true);
            preparedStatement.setInt(2,Integer.valueOf(cardNo));
            preparedStatement.setInt(1, Integer.valueOf(pin));
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if any rows were affected by the update
            if (rowsAffected > 0) {
                return true;
            } else {
                // Rollback the transaction if no rows were affected
                return false;
            }
        }
    }
}
