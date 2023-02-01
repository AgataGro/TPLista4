package warcaby;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class Game {
    private int id;
    private boolean isWhiteTurn;
    private Set<Move> moveSet;

    public Set<Move> getMoveSet() {
        return moveSet;
    }

    public void setMoveSet(Set<Move> moveSet) {
        this.moveSet = moveSet;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Game(boolean isWhiteTurn, String name){
        this.isWhiteTurn=isWhiteTurn;
        this.name = name;
    }

    public Object getIsWhiteTurn() {
        return isWhiteTurn;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


}
