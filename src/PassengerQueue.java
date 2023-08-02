public class PassengerQueue {
    private Passenger[] queueArray = new Passenger[42];
    private int last=0;

    public void add(Passenger passenger){
        queueArray[last++]=passenger;
    }

    public Passenger[] getQueueArray() {
        return queueArray;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }


}
