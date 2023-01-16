package warcaby;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece extends Circle {
    protected double mouseX, mouseY;
    protected int oldX, oldY;
    // moveDirection jest potrzebne, żeby potem zablokować ruchy pionków do tyłu
    protected int moveDirection;
    protected State state;

    /**
     * @param x one of the coordinates of the middle of a piece
     * @param y one of the coordinates of the middle of a piece
     * @param r value of circle radius
     */
    Piece(int x, int y, int r) {
        super(x,y,r);
    }

    /**
     * @return x coordinate of the previous middle of a piece
     */
    public int getOldX() {
        return oldX;
    }

    /**
     * @return y coordinate of the previous middle of a piece
     */
    public int getOldY() {
        return oldY;
    }

    /**
     * @return into which direction the piece can move - -1 or 1
     */
    public int getMoveDirection() {
        return moveDirection;
    }

    /**
     * @return color of the piece
     */
    public Color getColor() {
        return (Color) getFill();
    }

    /**
     * @param newX x coordinate of the place to which we want to move a piece
     * @param newY y coordinate of the place to which we want to move a piece
     */
    public abstract void move(int newX, int newY);

    /**
     * this method alllows programme to place a piece in its old place after an illegal move
     */
    public void notMove() {
        setCenterX(oldX+35);
        setCenterY(oldY+35);
    }

    /**
     * @param board table with all the squares of the board we are using to play checkers
     * @return list of squares where the piece can move
     */
    public List<Square> getAvailibleMoves(Square[][] board) {
        List<SingleMove> moves=state.availibleMoves(this, board);
        List<Square> result=new ArrayList<>();
        boolean killed=false;
        for (SingleMove move : moves) {
            if(!killed) {
                if(move.getKilled()!=null){
                    result.clear();
                    result.add(move.getEnd());
                }
                else result.add(move.getEnd());
            } else {
                if(move.getKilled()!=null){
                    result.add(move.getEnd());
                }
            }
        }
        return result;
    }
    
    /**
     * @param board array of squares
     * @return list with al squares where a piece can move
     */
    public List<SingleMove> getSingleMoves(Square[][] board){
        List<SingleMove> moves=state.availibleMoves(this, board);
        List<SingleMove> result=new ArrayList<>();
        boolean killed=false;
        for (SingleMove move : moves) {
            if(!killed) {
                if(move.getKilled()!=null){
                    result.clear();
                    result.add(move);
                }
                else result.add(move);
            } else {
                if(move.getKilled()!=null){
                    result.add(move);
                }
            }
        }
        return result;
    }
    
    /**
     * @param board table with squares from the board
     * @return sequence of moves we can do with the piece
     */
    public List<List<SingleMove>> moveSequences(Square[][] board){
        List<List<SingleMove>> result;
        result = state.moveSequence(this, board, new ArrayList<>());
        return result;
    }
    
    /**
     * method to call when a piece should change its state
     */
    public void changeState(){
        state.changeState();
    }

    /**
     * @return state of the piece
     */
    public State getState() {return this.state;}
}
