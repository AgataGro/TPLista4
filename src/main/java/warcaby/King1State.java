package warcaby;

import java.util.List;

public class King1State implements State{
    @Override
    public void Move(Piece piece, Board board, String sequence) {

    }

    @Override
    public int killablePieces(Piece piece, Board board, Square start, List<Square> jumped) {
        return 0;
    }

    @Override
    public boolean move(Piece piece, Board board, Square start, Direction d) {
        return false;
    }

    @Override
    public boolean jump(Piece piece, Board board, Square start, Direction d) {
        return false;
    }

    @Override
    public State crown(Piece piece, Square start) {
        return null;
    }
}
