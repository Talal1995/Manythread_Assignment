package Assignment1;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

/**
 * The GUI for assignment 1, DualThreads
 */
public class GUIAssignment1 implements Runnable
{


    /**
     * These are the components you need to handle.
     * You have to add listeners and/or code
     */
    private JFrame frame;		// The Main window
    private JButton btnDisplay;	// Start thread moving display
    private JButton btnDStop;	// Stop moving display thread
    private JButton btnTriangle;// Start moving graphics thread
    private JButton btnTStop;	// Stop moving graphics thread
    private JButton btnOpen;	// Open audio file
    private JButton btnPlay;	// Start playing audio
    private JButton btnStop;	// Stop playing
    private JButton btnGo;		// Start game catch me
    private JPanel pnlMove;		// The panel to move display in
    private JPanel pnlRotate;	// The panel to move graphics in
    private JPanel pnlGame;		// The panel to play in
    private JLabel lblPlaying;	// Playing text
    private JLabel lblAudio;	// Audio file
    private JTextArea txtHits;	// Dispaly hits
    private JComboBox cmbSkill;	// Skill combo box, needs to be filled in
    private JLabel moveStringLbl = new JLabel("DisplayThread");
    /**
     * Constructor
     */
    public GUIAssignment1()
    {
        Start();
    }

    /**
     * Starts the application
     */
    public void Start()
    {
        frame = new JFrame();
        frame.setBounds(0, 0, 819, 438);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("Multiple Thread Demonstrator");
        InitializeGUI();					// Fill in components
        frame.setVisible(true);
        frame.setResizable(false);			// Prevent user from change size
        frame.setLocationRelativeTo(null);	// Start middle screen
    }

    /**
     * Sets up the GUI with components
     */
    private void InitializeGUI()
    {
        // The music player outer panel
        JPanel pnlSound = new JPanel();
        Border b1 = BorderFactory.createTitledBorder("Music Player");
        pnlSound.setBorder(b1);
        pnlSound.setBounds(12, 12, 450, 100);
        pnlSound.setLayout(null);

        // Add labels and buttons to this panel
        lblPlaying = new JLabel("Now Playing: ");	// Needs to be alteraed
        lblPlaying.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblPlaying.setBounds(128, 16, 300, 20);
        pnlSound.add(lblPlaying);
        JLabel lbl1 = new JLabel("Loaded Audio File:");
        lbl1.setBounds(10, 44, 130, 13);
        pnlSound.add(lbl1);
        lblAudio = new JLabel("");				// Needs to be altered
        lblAudio.setBounds(125, 44, 300, 13);
        pnlSound.add(lblAudio);
        btnOpen = new JButton("Open");
        btnOpen.setBounds(6, 71, 75, 23);
        pnlSound.add(btnOpen);
        btnPlay = new JButton("Play");
        btnPlay.setBounds(88, 71, 75, 23);
        pnlSound.add(btnPlay);
        btnStop = new JButton("Stop");
        btnStop.setBounds(169, 71, 75, 23);
        pnlSound.add(btnStop);
        frame.add(pnlSound);

        // The moving display outer panel
        JPanel pnlDisplay = new JPanel();
        Border b2 = BorderFactory.createTitledBorder("Display Thread");
        pnlDisplay.setBorder(b2);
        pnlDisplay.setBounds(12, 118, 222, 269);
        pnlDisplay.setLayout(null);

        // Add buttons and drawing panel to this panel
        btnDisplay = new JButton("Start Display");
        btnDisplay.setBounds(10, 226, 121, 23);
        pnlDisplay.add(btnDisplay);
        btnDStop = new JButton("Stop");
        btnDStop.setBounds(135, 226, 75, 23);
        pnlDisplay.add(btnDStop);
        pnlMove = new JPanel();
        pnlMove.setBounds(10,  19,  200,  200);
        Border b21 = BorderFactory.createLineBorder(Color.black);
        pnlMove.setBorder(b21);
        pnlDisplay.add(pnlMove);
        frame.add(pnlDisplay);

        // The moving graphics outer panel
        JPanel pnlTriangle = new JPanel();
        Border b3 = BorderFactory.createTitledBorder("Triangle Thread");
        pnlTriangle.setBorder(b3);
        pnlTriangle.setBounds(240, 118, 222, 269);
        pnlTriangle.setLayout(null);

        // Add buttons and drawing panel to this panel
        btnTriangle = new JButton("Start Rotate");
        btnTriangle.setBounds(10, 226, 121, 23);
        pnlTriangle.add(btnTriangle);
        btnTStop = new JButton("Stop");
        btnTStop.setBounds(135, 226, 75, 23);
        pnlTriangle.add(btnTStop);
        pnlRotate = new JPanel();
        pnlRotate.setBounds(10,  19,  200,  200);
        Border b31 = BorderFactory.createLineBorder(Color.black);
        pnlRotate.setBorder(b31);
        pnlTriangle.add(pnlRotate);
        // Add this to main window
        frame.add(pnlTriangle);

        // The game outer panel
        JPanel pnlCatchMe = new JPanel();
        Border b4 = BorderFactory.createTitledBorder("Catch Me");
        pnlCatchMe.setBorder(b4);
        pnlCatchMe.setBounds(468, 12, 323, 375);
        pnlCatchMe.setLayout(null);

        // Add controls to this panel
        JLabel lblSkill = new JLabel("Skill:");
        lblSkill.setBounds(26, 20, 50, 13);
        pnlCatchMe.add(lblSkill);
        JLabel lblInfo = new JLabel("Hit Image with Mouse");
        lblInfo.setBounds(107, 13, 150, 13);
        pnlCatchMe.add(lblInfo);
        JLabel lblHits = new JLabel("Hits:");
        lblHits.setBounds(240, 20, 50, 13);
        pnlCatchMe.add(lblHits);
        cmbSkill = new JComboBox();			// Need to be filled in with data
        cmbSkill.setBounds(19, 41, 61, 23);
        pnlCatchMe.add(cmbSkill);
        btnGo = new JButton("GO");
        btnGo.setBounds(129, 41, 75, 23);
        pnlCatchMe.add(btnGo);
        txtHits = new JTextArea();			// Needs to be updated
        txtHits.setBounds(233, 41, 71, 23);
        Border b40 = BorderFactory.createLineBorder(Color.black);
        txtHits.setBorder(b40);
        pnlCatchMe.add(txtHits);
        pnlGame = new JPanel();
        pnlGame.setBounds(19, 71, 285, 283);
        Border b41 = BorderFactory.createLineBorder(Color.black);
        pnlGame.setBorder(b41);
        pnlCatchMe.add(pnlGame);
        frame.add(pnlCatchMe);
    }

    @Override
    public void run() {
        btnOpen.addActionListener(e -> {
            MusicPlayer t1 = new MusicPlayer(lblAudio, btnPlay, btnStop, frame);
            t1.run();
        });
        btnDisplay.addActionListener(e -> {
            DisplayThread t2 = new DisplayThread(pnlMove, moveStringLbl);
            t2.start();
            btnDStop.addActionListener(ex -> {
                t2.stopDisplay();
            });
        });
        btnTriangle.addActionListener(e -> {
            GraphicsThread t3 = new GraphicsThread(pnlRotate);
            t3.start();
            btnTStop.addActionListener(ex-> {
                t3.stopRotating();
            });
        });



    }
}