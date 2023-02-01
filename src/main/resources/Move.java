package warcaby;

public class Move {
    private int id;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int killX;
    private int killY;
    private Game game;

    public Move() {

    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    Move(SingleMove singleMove, Game game){
        startX=(int)singleMove.getStart().getX()/70;
        startY=(int)singleMove.getStart().getY()/70;
        endX=(int)singleMove.getEnd().getX()/70;
        endY=(int)singleMove.getEnd().getY()/70;
        if(singleMove.getKilled()==null){
            killX=-1;
            killY=-1;
        }
        else{
            killX=(int)singleMove.getKilled().getX()/70;
            killY=(int)singleMove.getKilled().getY()/70;
        }
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public int getId() {
        return id;
    }


    public int getKillX() {
        return killX;
    }

    public int getKillY() {
        return killY;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public void setId(int id) {
        this.id = id;
    }
}
