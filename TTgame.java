import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TTgame extends JFrame implements ActionListener {
    private JButton[][] board;
    private JButton resetButton;
    private boolean isPlayerX;
    private boolean isGameWon;

    public TTgame() {
        setTitle("TIK-TOK");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 3));

        board = new JButton[3][3];
        isPlayerX = true;
        isGameWon = false;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = new JButton();
                board[row][col].setFont(new Font("Consolas", Font.PLAIN, 50));
                board[row][col].addActionListener(this);
                add(board[row][col]);
            }
        }

        resetButton = new JButton("AGAIN ??");
        resetButton.addActionListener(this);
        add(resetButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        JButton button = (JButton) event.getSource();

        if (button.equals(resetButton)) {
            resetGame();
        } else if (!isGameWon && button.getText().isEmpty()) {
            if (isPlayerX) {
                button.setText("X");
            } else {
                button.setText("O");
            }

            isPlayerX = !isPlayerX;
            checkGameStatus();
        }
    }

    private void resetGame() {
        isPlayerX = true;
        isGameWon = false;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col].setText("");
            }
        }
    }

    private void checkGameStatus() {
        String[][] currentState = new String[3][3];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                currentState[row][col] = board[row][col].getText();
            }
        }

        // Check rows
        for (int row = 0; row < 3; row++) {
            if (!currentState[row][0].isEmpty() &&
                currentState[row][0].equals(currentState[row][1]) &&
                currentState[row][0].equals(currentState[row][2])) {
                displayWinner(currentState[row][0]);
                return;
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            if (!currentState[0][col].isEmpty() &&
                currentState[0][col].equals(currentState[1][col]) &&
                currentState[0][col].equals(currentState[2][col])) {
                displayWinner(currentState[0][col]);
                return;
            }
        }

        // Check diagonals
        if (!currentState[0][0].isEmpty() &&
            currentState[0][0].equals(currentState[1][1]) &&
            currentState[0][0].equals(currentState[2][2])) {
            displayWinner(currentState[0][0]);
            return;
        }

        if (!currentState[0][2].isEmpty() &&
            currentState[0][2].equals(currentState[1][1]) &&
            currentState[0][2].equals(currentState[2][0])) {
            displayWinner(currentState[0][2]);
            return;
        }

        // Check for a draw
        boolean isDraw = true;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (currentState[row][col].isEmpty()) {
                    isDraw = false;
                    break;
                }
            }
        }

        if (isDraw) {
            JOptionPane.showMessageDialog(this, "DRAW", "GAME FINISH", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        }
    }

    private void displayWinner(String player) {
        String message = "Player " + player + " WON";
        JOptionPane.showMessageDialog(this, message, "FINISHED", JOptionPane.INFORMATION_MESSAGE);
        isGameWon = true;
        resetGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TTgame());
    }
}
