package warcaby;

public class SingleMove {
    Square start;
    Square end;
    Square killed;

    /**
     * @param start a square where the move started
     * @param end a square where the piece was released
     * @param killed a square where the piece to kill is placed
     */
    public SingleMove(Square start, Square end, Square killed){
        this.start=start;
        this.end=end;
        this.killed=killed;
    }
    /**
     * @return a square where the move started
     */
    public Square getStart() {
        return start;
    }
    /**
     * @return a square where the move ended
     */
    public Square getEnd() {
        return end;
    }
    /**
     * @return a square with killed piece
     */
    public Square getKilled() {
        return killed;
    }
    /**
     * @return start, end and killed values as a string
     */
    public String getAsString(){
        String a="";
        a+=start;
        a+=end;
        a+=killed;
        return a;
    }
}