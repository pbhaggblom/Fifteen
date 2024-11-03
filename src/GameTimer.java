public class GameTimer extends Thread {

    private int seconds;
    private int minutes;
    private int hours;

    public GameTimer(int seconds, int minutes, int hours) {
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
    }

    public int getSeconds() {
        return seconds;
    }
    public int getMinutes() {
        return minutes;
    }
    public int getHours() {
        return hours;
    }


    public void run() {

        while(!Thread.interrupted()) {
            try{
                Thread.sleep(1000);
                seconds++;
                if(seconds >= 60) {
                    seconds = 0;
                    minutes++;
                }
                if(minutes >= 60) {
                    minutes = 0;
                    hours++;
                }

            }catch(InterruptedException e){
                break;
            }
        }


    }
}
