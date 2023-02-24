public class Guest extends Thread {

    int guestId;
    public Guest(int guestId) {
        this.guestId = guestId;
    }

    @Override
    public void run() {

        while (MinotuarMaze.count < MinotuarMaze.totalGuests) {
            MinotuarMaze.enterMaze(guestId);
        }
    }
}
