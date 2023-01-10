package warcaby;

public class SingleMove {
    Square start;
    Square end;
    Square killed;
    public SingleMove(Square start, Square end, Square killed){
        this.start=start;
        this.end=end;
        this.killed=killed;
    }

    public Square getStart() {
        return start;
    }

    public Square getEnd() {
        return end;
    }

    public Square getKilled() {
        return killed;
    }
}
