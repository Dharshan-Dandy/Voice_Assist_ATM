package com.bank.atm.voiceassistant;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioVoice {
    private static volatile boolean continuePlayback = true;
    private static volatile Thread execuingThread = null;
    private static final Object lock = new Object();

    public static void voiceOfFile(String filename) {
        if (execuingThread != null && execuingThread.isAlive()) {
            stopPlayback();
            execuingThread = null;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        execuingThread = Thread.currentThread();
        synchronized (lock) {
            // Create a new thread to execute the audio playback
            try {
                // Create an AudioInputStream to read the audio file
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/main/resources/AudioSource/" + filename + ".wav"));

                // Get the format of the audio
                AudioFormat format = audioInputStream.getFormat();

                // Create a new AudioFormat with modified sample rate
                float newSampleRate = format.getSampleRate() * 8.7f;
                AudioFormat newFormat = new AudioFormat(format.getEncoding(), newSampleRate,
                        format.getSampleSizeInBits(), format.getChannels(),
                        format.getFrameSize(), newSampleRate, format.isBigEndian());

                // Create a new AudioInputStream with the modified format
                AudioInputStream newInputStream = AudioSystem.getAudioInputStream(newFormat, audioInputStream);

                // Get the DataLine.Info object for the SourceDataLine
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, newFormat);

                // Open the line
                SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
                line.open(newFormat);

                // Start playing the audio
                line.start();

                // Create a buffer for reading the audio data
                byte[] buffer = new byte[4096];
                int bytesRead;

                //For continuePlayback to becomes true
                continuePlayback = true;

                // Read and play the audio data
                while (continuePlayback && (bytesRead = newInputStream.read(buffer)) != -1) {
                    line.write(buffer, 0, bytesRead);
                }
                execuingThread = null;

                // Close the line and input stream
                line.drain();
                line.close();
                newInputStream.close();

            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            } finally {
                execuingThread = null;
            }
        }
    }

    // Method to stop the playback
    public static void stopPlayback() {
        continuePlayback = false;
    }


    public static void main(String[] args) {
        voiceOfFile("welcome_nicole");
    }
}
