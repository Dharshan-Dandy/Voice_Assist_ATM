package com.bank.atm.voiceassistant;


import com.bank.Main;
import com.bank.atm.ui.MenuScreen;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import java.io.FileOutputStream;


public class OptionsVoiceRecognize {
    private static OptionsVoiceRecognize instance;
    private LiveSpeechRecognizer speechRecognizer;

    private OptionsVoiceRecognize() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("Logs/logs.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        PrintStream ps = new PrintStream(fos);

        //For Hiding the logs of sphinx....
        System.setErr(ps);

        Configuration configuration = new Configuration();
        configuration.setAcousticModelPath("lib/en-us/en-us");
        configuration.setDictionaryPath("src/main/resources/VoiceAssistantSources/Options.dic");
        configuration.setLanguageModelPath("src/main/resources/VoiceAssistantSources/Options.lm");

        try {
            speechRecognizer = new LiveSpeechRecognizer(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static synchronized OptionsVoiceRecognize getInstance() {
        if (instance == null) {
            instance = new OptionsVoiceRecognize();
        }
        return instance;
    }

    public  String listen() {
        if (Main.canRecongize()) {
            try {
                speechRecognizer.startRecognition(true);
                SpeechResult speechResult = speechRecognizer.getResult();
                String voiceCommand = null;
                if (speechResult != null) {
                    voiceCommand = speechResult.getHypothesis();
                    System.out.println("Voice Command is " + voiceCommand);
                }
                if (!voiceCommand.equals(""))
                    return voiceCommand;
            } finally {
                speechRecognizer.stopRecognition();
            }
        }
        else        return "";
        return null;
    }


    public static void main(String []args){
        OptionsVoiceRecognize op = new OptionsVoiceRecognize();
        while (true)
        op.listen();
    }
}
