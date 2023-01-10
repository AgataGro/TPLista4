package warcaby;

import javafx.scene.paint.Color;

import java.util.List;

public interface Checkers {
    private int getScenePlace(double x) {
        return (int)x/70;
    }

    private boolean checkMove(Piece piece, int x, int y) {
        return false;
    }

    private Piece createPiece(int x, int y, int r, Color color, State state) {
        return null;
    }

}
