package com.bank;

import com.bank.atm.voiceassistant.AudioVoice;
import com.bank.atm.voiceassistant.OptionsVoiceRecognize;
import com.bank.database.AccessDB;

import java.sql.SQLException;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Buffer {
    public static void main(String[] args) {
        OptionsVoiceRecognize op = OptionsVoiceRecognize.getInstance();
        op.listen();
    }
}

//public class Buffer {
//    public static void main(String[]args){
//        try {
//           if(AccessDB.isPinValid("9999","12321")){
//               System.out.println("Ok");
//           }
//           else
//               System.out.println("No");
//        }
//        catch (Exception e){
//            AudioVoice.voiceOfFile("notworking");
////            e.printStackTrace();
//       }
//    }
//}
