import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class Game extends JFrame implements ActionListener {

    JPanel p = new JPanel();
    JButton[][] buttons = new JButton[4][4];

    public Game() {
        add(p);

        p.setLayout(new GridLayout(4, 4));

        ArrayList<Integer> num = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            num.add(i);
        }

        Collections.shuffle(num);
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
                p.add(b);
                k++;
            }
        }

        setSize(250, 250);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void switchPlaces(JButton b) {

        int row = 0;
        int col = 0;
        int i = 0;
        int j = 0;
        for (i = 0; i < buttons.length; i++) {
            for (j = 0; j < buttons[i].length; j++) {
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
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        switchPlaces(b);
    }
}
