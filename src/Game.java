import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

public class Game extends JFrame implements ActionListener {

    JPanel gamePanel = new JPanel();
    JPanel counterPanel = new JPanel();
    JLabel counterLabel = new JLabel();
    JPanel timerPanel = new JPanel();
    JLabel timerLabel = new JLabel();
    JButton restart = new JButton("Restart");

    int moveCounter = 0;
    JButton[][] buttons = new JButton[4][4];
    GameTimer timer;
    ArrayList<Integer> num = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0));


    public Game() {
        setLayout(new BorderLayout());
        add(counterPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        add(timerPanel, BorderLayout.SOUTH);

        timerPanel.add(timerLabel);
        counterPanel.add(restart);
        counterPanel.add(counterLabel);

        restart.addActionListener(e -> start(buttons));

        setCounterText();

        gamePanel.setLayout(new GridLayout(4, 4));


//        Collections.shuffle(num); //Kommentera för direktvinst och ändra i enum

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                JButton b = new JButton();
                b.addActionListener(this);
                buttons[i][j] = b;
                gamePanel.add(b);
            }
        }

        start(buttons);

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

        while(!timer.isInterrupted()){
            timerLabel.setText("Timer: " + timer.getHours() +":"+ timer.getMinutes() + ":" + timer.getSeconds());
        }
    }

    public void start(JButton[][] buttons) {
        //        Collections.shuffle(num); //Kommentera för direktvinst och ändra i enum
        int k = 0;

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                String s = "";
                if (num.get(k) != 0) {
                    s = num.get(k) + "";
                }
                buttons[i][j].setText(s);
                k++;
            }
        }

        moveCounter = 0;
        setCounterText();
        timer = new GameTimer(0, 0, 0);
        timer.start();
    }

    public void newGame(){
        int startNew = JOptionPane.showOptionDialog(null,
                "Do you want to start a new game?",
                "New Game", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,null,null);

        if(startNew == JOptionPane.YES_OPTION){
            new Game();
        }else{
            System.exit(0);
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
            start(buttons);
        }
    }
}
