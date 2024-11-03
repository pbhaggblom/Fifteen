import javax.swing.*;

public class Main {

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

    public static void main(String[] args) {
        new Main().newGame();
    }
}