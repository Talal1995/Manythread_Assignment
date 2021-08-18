package Assignment1;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class MusicPlayer extends Thread{
    private boolean keepRunning;
    private int pause;
    private GUIAssignment1 gui;
    private AudioInputStream ais;
    private File selectedFile;
    private File sound;
    private Clip clip;
    private JFileChooser browser;
    private int returnValue;
    private String[] musics;
    private int index;
    private JLabel lbl;
    private JButton btnPlay, btnStop;
    private JFrame frame;

    public MusicPlayer(JLabel label, JButton btnPlay, JButton btnStop, JFrame frame){
   //     gui = new GUIAssignment1();
        this.frame = frame;
        musics = new String[10];
        index = 0;
        keepRunning = true;
        this.lbl = label;
        this.btnPlay = btnPlay;
        this.btnStop = btnStop;
      //  actionPerformed();
    }
    @Override
    public void run()  {
        browser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV Files", "wav");
        browser.setFileFilter(filter);
        browser.setDialogTitle("Choose a wav music file");
        returnValue = browser.showOpenDialog(frame);

        if (returnValue == browser.APPROVE_OPTION){
            selectedFile = browser.getSelectedFile();
            musics[index] = selectedFile.toString();
            //In case we had a JCombo or JList component instead of JLabel,
            //we could use array to hold the music list
            //index++;
            lbl.setText(musics[index]);
            btnPlay.addActionListener(e -> play(musics[index]));
            btnStop.addActionListener(e -> stopMusic());
         ///   gui.getBtnPlay().addActionListener(e -> play(musics[index]));
        //    gui.getBtnStop().addActionListener(e -> stopMusic());
        }
    }
    private void play(String filename){
        try {
            sound = new File(filename);
            ais = AudioSystem.getAudioInputStream(sound);
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void stopMusic(){
        clip.stop();
    }
}
