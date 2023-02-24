public class VaseGuest extends Thread {

    int guestId;
    public VaseGuest(int guestId) {
        this.guestId = guestId;
    }

    @Override
    public void run() {

        while (MinotuarVase.count < MinotuarVase.totalGuests) {
            MinotuarVase.lookAtVase(guestId);
           
        }
    }
}