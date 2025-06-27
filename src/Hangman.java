import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hangman extends JFrame implements ActionListener {
    // count the number of incorrect trials
    private int incorrectGuesses;

    //store the challenge from the WordDB here
    private String[] wordChallenge;

    private final WordDB wordDB;
    private JLabel hangmanImage, categoryLabel, hiddenWordLabel;
    private JButton[] letterButtons;

    public Hangman() {
        super("Hangman Game (Java)");
        setSize(CommonConstants.FLAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);

        wordDB = new WordDB();
        letterButtons = new JButton[26];
        wordChallenge = wordDB.loadChallenge();

        addGuiComponents();
    }

    private void addGuiComponents() {
        // hangman image
        hangmanImage = CustomTools.loadImage(CommonConstants.IMAGE_PATH);
        hangmanImage.setBounds(0, 0, hangmanImage.getPreferredSize().width, hangmanImage.getPreferredSize().height);

        // category display
        categoryLabel = new JLabel(wordChallenge[0]);
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        categoryLabel.setOpaque(true);
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setBackground(CommonConstants.SECONDARY_COLOR);
        categoryLabel.setBorder(BorderFactory.createLineBorder(CommonConstants.SECONDARY_COLOR));
        categoryLabel.setBounds(
                0,
                hangmanImage.getPreferredSize().height - 28,
                CommonConstants.FLAME_SIZE.width,
                categoryLabel.getPreferredSize().height
        );

        // hidden word
        hiddenWordLabel = new JLabel(CustomTools.hideWords(wordChallenge[1]));
        hiddenWordLabel.setForeground(Color.WHITE);
        hiddenWordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hiddenWordLabel.setBounds(
                0,
                categoryLabel.getY() + categoryLabel.getPreferredSize().height + 50,
                CommonConstants.FLAME_SIZE.width,
                hiddenWordLabel.getPreferredSize().height
        );

        // letter buttons
        GridLayout gridLayout = new GridLayout(4,7);
        JPanel buttonPanel = new JPanel(gridLayout);
        buttonPanel.setBounds(
                -5,
                hiddenWordLabel.getY() + hiddenWordLabel.getPreferredSize().height,
                CommonConstants.BUTTON_PANEL_SIZE.width,
                CommonConstants.BUTTON_PANEL_SIZE.height
        );

        buttonPanel.setLayout(gridLayout);

        // buttonPanel.setBackground(CommonConstants.BACKGROUND_COLOR);
        buttonPanel.setOpaque(true);

        // creates letter buttons
        for (char c = 'A'; c <= 'Z'; c++ ) {
            JButton button = new JButton(Character.toString(c));
            button.setFocusPainted(false);
            // button.setForeground(Color.WHITE);
            button.setBackground(CommonConstants.PRIMARY_COLOR);
            button.addActionListener(this);

            // using ASCII value to calculate the current index
            int currentIndex = c - 'A';

            letterButtons[currentIndex] = button;
            buttonPanel.add(letterButtons[currentIndex]);
        }

        // reset button
        JButton resetButton = new JButton("Reset");
        resetButton.setBackground(CommonConstants.SECONDARY_COLOR);
        resetButton.setForeground(Color.WHITE);
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);

        // quit button
        JButton quitButton = new JButton("Quit");
        quitButton.setForeground(Color.WHITE);
        quitButton.setBackground(CommonConstants.SECONDARY_COLOR);
        quitButton.addActionListener(this);
        buttonPanel.add(quitButton);

        getContentPane().add(buttonPanel);
        getContentPane().add(categoryLabel);
        getContentPane().add(hangmanImage);
        getContentPane().add(hiddenWordLabel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Reset")) {
            resetGame();
        } else if (command.equals("Quit")) {
            dispose();
            return;
        } else {
            // letter buttons

            // disable button
            JButton button = (JButton) e.getSource();
            button.setEnabled(false);

            // check if the word contains users guess
            if (wordChallenge[1].contains(command)) {
                // indicate that user got
                button.setBackground(Color.GREEN);

                // store the hidden word in a char array, so update the hidden text
                char[] hiddenWord = hiddenWordLabel.getText().toCharArray();

                for (int i = 0; i < wordChallenge[1].length(); i++) {
                    //update _ to correct letter
                    if (wordChallenge[1].charAt(i) == command.charAt(0)) {
                        hiddenWord[i] = command.charAt(0);
                    }
                }

                // update hiddenWordLabel
                hiddenWordLabel.setText(String.valueOf(hiddenWord));

                // the user guessed the word right
            } else {
                // indicate the user chose the wrong letter
                button.setBackground(Color.RED);

                // increase the incorrect letter
                ++incorrectGuesses;

                // update the hangman image
                CustomTools.updateImage(hangmanImage, "resources/" + (incorrectGuesses + 1) + ".png");

                // user failed to guess word right
            }

        }
    }

    private void resetGame() {
        // load new challenge
        wordChallenge = wordDB.loadChallenge();
        incorrectGuesses = 0;

        // load starting image
        CustomTools.updateImage(hangmanImage, CommonConstants.IMAGE_PATH);

        // update category
        categoryLabel.setText(wordChallenge[0]);

        // update hiddenWord
        String hiddenWord = CustomTools.hideWords(wordChallenge[1]);
        hiddenWordLabel.setText(hiddenWord);

        // enable all buttons
        for (int i = 0; i < letterButtons.length; i++) {
            letterButtons[i].setEnabled(true);
            letterButtons[i].setBackground(CommonConstants.PRIMARY_COLOR);
        }

    }
}
