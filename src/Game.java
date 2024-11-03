import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;

public class Game extends JFrame implements ActionListener {

    JPanel gamePanel = new JPanel();
    JPanel counterPanel = new JPanel();
    JLabel counterLabel = new JLabel();
    JPanel timerPanel = new JPanel();
    JLabel timerLabel = new JLabel();

    int moveCounter = 0;
    JButton[][] buttons = new JButton[4][4];
    GameTimer timer = new GameTimer(0,0,0);



    public Game() {
        setLayout(new BorderLayout());
        add(counterPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        add(timerPanel, BorderLayout.SOUTH);

        timerPanel.add(timerLabel);
        counterPanel.add(counterLabel);
        setCounterText();

        gamePanel.setLayout(new GridLayout(4, 4));

        ArrayList<Integer> num = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            num.add(i);
        }

        Collections.shuffle(num); //Kommentera för direktvinst och ändra i enum
        int k = 0;

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                String s = "";
                if (num.get(k) != 0) {
                    s = num.get(k) + "";
                }
                JButton b = new JButton(s);
                b.addActionListener(this);
                buttons[i][j] = b;
                gamePanel.add(b);
                k++;
            }
        }

        setSize(250, 270);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTimer();
    }

    public void switchPlaces(JButton b) {

        int row = 0;
        int col = 0;

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (b.equals(buttons[i][j]) ) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        int zeroRow = 0;
        int zeroCol = 0;

        for (int z = 0; z < buttons.length; z++) {
            for (int x = 0; x < buttons[z].length; x++) {
                if(buttons[z][x].getText().isEmpty()) {
                    zeroRow = z;
                    zeroCol = x;
                }
            }
        }

        if ((zeroRow == row && (zeroCol == col - 1 || zeroCol == col + 1)) ||
                zeroCol == col && (zeroRow == row - 1 || zeroRow == row + 1)) {
            String num = buttons[row][col].getText();
            buttons[zeroRow][zeroCol].setText(num);
            buttons[row][col].setText("");
            moveCounter++;
            setCounterText();
        }
    }

    public void setCounterText() {
        counterLabel.setText("Antal drag: " + moveCounter);
    }

    public void setTimer(){
        timer.start();
        while(!timer.isInterrupted()){
            timerLabel.setText("Timer: " + timer.getHours() +":"+ timer.getMinutes() + ":" + timer.getSeconds());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        switchPlaces(b);

        StringBuilder currentLine = new StringBuilder();

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if(buttons[i][j].getText().isEmpty()) {
                    currentLine.append(" ");
                }
                currentLine.append(buttons[i][j].getText());
            }
        }

        if(currentLine.toString().contentEquals(GameWin.WINNING_LINE.win)) {
            timer.interrupt();
            JOptionPane.showMessageDialog(null, "Congratulations! You won!\n" +
                    "Antal Drag: " + moveCounter + "\n" +
                    "Tid: " + timer.getHours() + ":" + timer.getMinutes() + ":" + timer.getSeconds());
            this.dispose();
            new Main().newGame();
        }
    }
}
