import javax.swing.*;

public class Hangman extends JFrame {
    // count the number of incorrect trials
    private int incorrectGuesses;

    //store the challenge from the WordDB here
    private String[] wordChallenge;

    private final WordDB wordDB;
    private JLabel hangmanImage;

    public Hangman() {
        super("Hangman Game (Java)");
        setSize(CommonConstants.FLAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        wordDB = new WordDB();
        wordChallenge = new wordDB.loadChallenge();

        addGuiComponents();
    }

    private void addGuidComponents() {
        // hangman image
        hangmanImage = CustomTools.loadImage(CommonConstants.IMAGE_PATH);
        hangmanImage.setBounds(0, 0, hangmanImage.getPreferredSize().width, hangmanImage.getPreferredSize().height);

        getContentPane().add(hangmanImage);
    }
}
