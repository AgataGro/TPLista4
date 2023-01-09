package warcaby;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece extends Circle {
    private double mouseX, mouseY;
    private int oldX, oldY;
    // moveDirection jest potrzebne, żeby potem zablokować ruchy pionków do tyłu
    private int moveDirection;
    private State state;

    Piece(int x, int y, int r) {
        super(x,y,r);
    }

    public int getOldX() {
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

    public abstract void move(int newX, int newY);

    public void notMove() {
        setCenterX(oldX+35);
        setCenterY(oldY+35);
    }

    public List<Square> getAvailibleMoves(Square[][] board) {
        return state.availibleMoves(this, board);
    }
    public List<List<Square>> moveSequences(Square[][] board){return  state.moveSequence(this, board, new ArrayList<>(), new ArrayList<>());}
    public void changeState(){
        state.changeState();
    }

    public State getState() {return this.state;}
}
