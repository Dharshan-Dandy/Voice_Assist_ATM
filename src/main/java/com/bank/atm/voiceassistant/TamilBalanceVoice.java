package com.bank.atm.voiceassistant;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TamilBalanceVoice {

    private static final Map<Integer, String> tamilUnits = new HashMap<>();
    private static final Map<Integer, String> tamilTens = new HashMap<>();
    private static final Map<Integer, String> tamilHundreds = new HashMap<>();
    private static final String[] tamilThousands = {"", "ஆயிரத்து", "இரண்டாயிரத்து", "மூன்றாயிரத்து", "நான்காயிரத்து", "ஐந்தாயிரத்து", "ஆறாயிரத்து", "ஏழாயிரத்து", "எட்டாயிரத்து", "ஒன்பதாயிரத்து","பத்தாயிரத்து","பதினொன்றயிரத்து","இருபதாயிரத்து","முப்பதாயிரத்து","நான்பதாயிரத்து"};

    static {
        tamilUnits.put(0, "");
        tamilUnits.put(1, "ஒன்று");
        tamilUnits.put(2, "இரண்டு");
        tamilUnits.put(3, "மூன்று");
        tamilUnits.put(4, "நான்கு");
        tamilUnits.put(5, "ஐந்து");
        tamilUnits.put(6, "ஆறு");
        tamilUnits.put(7, "ஏழு");
        tamilUnits.put(8, "எட்டு");
        tamilUnits.put(9, "ஒன்பது");

        tamilTens.put(1, "பத்து");
        tamilTens.put(2, "இருபது");
        tamilTens.put(3, "முப்பது");
        tamilTens.put(4, "நாற்பது");
        tamilTens.put(5, "ஐம்பது");
        tamilTens.put(6, "அறுபது");
        tamilTens.put(7, "எழுபது");
        tamilTens.put(8, "எட்பது");
        tamilTens.put(9, "தொண்ணூறு");

        tamilHundreds.put(1, "நூறு");
        tamilHundreds.put(2, "இருநூறு");
        tamilHundreds.put(3, "முந்நூறு");
        tamilHundreds.put(4, "நான்நூறு");
        tamilHundreds.put(5, "ஐந்நூறு");
        tamilHundreds.put(6, "ஆறுநூறு");
        tamilHundreds.put(7, "ஏழுநூறு");
        tamilHundreds.put(8, "எட்டுநூறு");
        tamilHundreds.put(9, "தொத்யிரத்து");
    }

    public static String convertToTamilAmount(int amount) {
        if (amount == 0) {
            return "பூஜ்ஜியம்"; // Zero in Tamil
        }

        StringBuilder tamilText = new StringBuilder();

        // Convert thousands
        if (amount >= 1000) {
            tamilText.append(tamilThousands[amount / 1000]).append(" ");
            amount %= 1000;
        }

        // Convert hundreds
        if (amount >= 100) {
            tamilText.append(tamilHundreds.get(amount / 100)).append(" ");
            amount %= 100;
        }

        // Convert tens and units
        if (amount > 0) {
            if (tamilText.length() > 0) {
                tamilText.append(" ");
            }

            if (amount <= 9) {
                tamilText.append(tamilUnits.get(amount));
            } else if (amount <= 99) {
                tamilText.append(tamilTens.get((amount / 10)));
                if (amount % 10 != 0) {
                    tamilText.append(tamilUnits.get(amount % 10));
                }
            }
        }

        return tamilText.toString();
    }

    public static void main(String[] args) {
        int amount = 10100; // Change this amount to test
        balance2Voice("0");
//        System.out.println(convertToTamilAmount(Integer.valueOf("10101")));
    }

    public static void balance2Voice(String number) {
        try {
            String audioFilePath = "src/main/resources/AudioSource/speech.wav";

            // Check if the output file already exists
            File audioFile = new File(audioFilePath);
            if (audioFile.exists()) {
                // If it exists, delete it
                try {
                    audioFile.delete();
                    System.out.println("Existing file deleted successfully.");
                } catch (SecurityException e) {
                    System.err.println("Unable to delete existing file: " + e.getMessage());
                }
            }else{
                System.out.println("No Such File Exists : " + audioFilePath);
            }
            String[] command = {
                    "/bin/bash",
                    "-c",
                    "echo \"" + convertToTamilAmount(Integer.valueOf(number)) + "\" | ./lib/kuttyTTs/src/KuttyTTS -m ./lib/kuttyTTs/voices/naveen_tamil.htsvoice -o ./src/main/resources/AudioSource/speech.wav"
            };


            // Start the process
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            // Get the output of the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // Wait for the command to finish and get the exit code
            int exitCode = process.waitFor();
            System.out.println("Command exited with code: " + exitCode);

            audioFilePath = "./src/main/resources/AudioSource/speech1.wav";

            // Check if the output file already exists
            audioFile = new File(audioFilePath);
            if (audioFile.exists()) {
                // If it exists, delete it
                try {
                    audioFile.delete();
                    System.out.println("file deleted successfully.");
                } catch (SecurityException e) {
                    System.err.println("Unable to delete existing file: " + e.getMessage());
                }
            }

            // Command to execute for convering to playable file
            String[] command1 = {"ffmpeg", "-i", "./src/main/resources/AudioSource/speech.wav", "-acodec", "pcm_s16le", "-ar", "44100", "-ac", "2", "./src/main/resources/AudioSource/speech1.wav"};

            // Create ProcessBuilder instance with the command
            ProcessBuilder pb = new ProcessBuilder(command1);

            // Redirect the error stream to the output stream
            pb.redirectErrorStream(true);

            // Start the process
            Process process1 = pb.start();

            // Read the output of the process (if needed)
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(process1.getInputStream()));

            int exitCode1 = process.waitFor();

            // Check if the process exited successfully
            if (exitCode1 == 0) {
                System.out.println("Converted successfully");
            } else {
                System.err.println("Not Converted with exit code: " + exitCode1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(2400);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Thread amount = new Thread(() -> AudioVoice.voiceOfFile("speech1"));
        amount.start();
    }

}
