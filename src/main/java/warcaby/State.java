package warcaby;

import java.util.List;

public interface State {
    public void Move();
    public int killablePieces(Piece piece, Board board, Square start, List<Square> jumped);
    public boolean move(Piece piece, Board board, Square start, Direction d);
    public boolean jump(Piece piece, Board board, Square start, Direction d);
}
