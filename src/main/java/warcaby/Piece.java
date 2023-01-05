package warcaby;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.util.List;

public class Piece extends Circle {
    private double mouseX, mouseY;
    private int oldX, oldY;
    // moveDirection jest potrzebne, żeby potem zablokować ruchy pionków do tyłu
    private int moveDirection;
    private State state;

    Piece(int x, int y, int r, Color color, State state) {
        super(x,y,r);
        setFill(color);
        oldX = x-35;
        oldY = y-35;
        if(color == Color.WHITE)
            moveDirection = -1;
        else
            moveDirection = 1;

        this.state=state;

        setOnMousePressed(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
        });

        setOnMouseDragged(e -> {
            double xx = e.getSceneX()-mouseX;
            double yy = e.getSceneY()-mouseY;
            setCenterX(xx+getCenterX());
            setCenterY(yy+getCenterY());
            mouseX += xx;
            mouseY += yy;
        });
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

    public void move(int newX, int newY) {
        oldX = newX*70;
        oldY = newY*70;
        setCenterX(oldX+35);
        setCenterY(oldY+35);
    }

    public void notMove() {
        setCenterX(oldX+35);
        setCenterY(oldY+35);
    }

    public List<Square> getAvailibleMoves(Square[][] board) {
        return state.availibleMoves(this, board);
    }
}