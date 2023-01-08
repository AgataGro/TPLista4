package warcaby;

//import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

//import java.util.List;

public class PolishPiece extends Piece{
    //private int oldX, oldY;
    //private int moveDirection;
    //private State state;
    
    PolishPiece(int x, int y, int r, Color color, State state) {
        super(x,y,r);
        setFill(color);
        oldX = x-35;
        oldY = y-35;
        if(color == Color.WHITE)
            moveDirection = -1;
        else
            moveDirection = 1;

        this.state = state;
    }

    /*public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public int getMoveDirection() {
        return moveDirection;
    }

    public Color getColor() {
        return (Color) getFill();
    }

    public State getState() {
        return this.state;
    }

    public void setState(State newState) {
        this.state = newState;
    }*/

    @Override
    public void move(int newX, int newY) {
        oldX = newX*70;
        oldY = newY*70;
        setCenterX(oldX+35);
        setCenterY(oldY+35);
        if((this.getColor()==Color.WHITE && newY==0) || (this.getColor()==Color.BLACK && newY==9)) {
            setState(new PolishKingState());
            setStroke(Color.GOLD);
            setStrokeWidth(5);
        }
    }

    /*public void moveKing(int newX, int newY) {
        oldX = newX*70;
        oldY = newY*70;
        setCenterX(oldX+35);
        setCenterY(oldY+35);
    }

    public void notMove() {
        setCenterX(oldX+35);
        setCenterY(oldY+35);
    }

    public List<Square> getAvailableMoves(Square[][] board) {
        return state.availableMoves(this, board);
    }*/
}

// w pom było maven 1.7, a nie 1.8