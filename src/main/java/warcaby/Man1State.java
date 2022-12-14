package warcaby;

import java.util.List;

public class Man1State implements State {
    @Override
    public void Move() {

    }

    /**
     * Funkcja zliczjąca ile maksymalnie pionków można zbić danym pionkiem w jedntm ruchu/turze
     * Pionek może bić do tyłu i do przodu po przekątnej
     * Pionek nie może przeskoczyć dwa razy nad tym samym pionkiem
     * @param piece pionek, którego możliwości zbijania sprawdzamy
     * @param board wykorzystywana w grze plansza
     * @param start pole startowe, pole, na którym nasz pionek znajduje się na starcie rundy
     * @param jumped lista pól nad, którymi przeskoczyliśmy
     * @return maksymalna liczba pionków, które mozna zbić w jednej turze
     */
    @Override
    public int killablePieces(Piece piece, Board board, Square start, List<Square> jumped) {
        int d1 = 0, d2 = 0, d3 = 0, d4 = 0;
        /* żeby nie zmienić oryginalnych pól/pionków nad którymi przeskoczyliśmy,
           stworzenie list odpowiednio dla każdego kierunku ruchu*/
        List<Square> jumpedUpLeft, jumpedUpRight, jumpedDownLeft, jumpedDownRight;
        if (jump(piece, board, start, Direction.UpLeft) && !jumped.contains(board.getTile(start.getX()-1, start.getY()+1))){
            jumpedUpLeft = jumped;
            jumpedUpLeft.add(board.getTile(start.getX()-1, start.getY()+1));
            d1 = killablePieces(piece, board, board.getTile(start.getX()-2, start.getY()+2), jumpedUpLeft);
        }
        if (jump(piece, board, start, Direction.UpRight) && !jumped.contains(board.getTile(start.getX()+1, start.getY()+1))){
            jumpedUpRight = jumped;
            jumpedUpRight.add(board.getTile(start.getX()+1, start.getY()+1));
            d2 = killablePieces(piece, board, board.getTile(start.getX()+2, start.getY()+2), jumpedUpRight);
        }
        if (jump(piece, board, start, Direction.DownLeft) && !jumped.contains(board.getTile(start.getX()-1, start.getY()-1))){
            jumpedDownLeft = jumped;
            jumpedDownLeft.add(board.getTile(start.getX()-1, start.getY()-1));
            d3 = killablePieces(piece, board, board.getTile(start.getX()-2, start.getY()-2), jumpedDownLeft);
        }
        if (jump(piece, board, start, Direction.DownRight) && !jumped.contains(board.getTile(start.getX()+1, start.getY()-1))){
            jumpedDownRight = jumped;
            jumpedDownRight.add(board.getTile(start.getX()+1, start.getY()-1));
            d4 = killablePieces(piece, board, board.getTile(start.getX()+2, start.getY()-2), jumpedDownRight);
        }
        return Math.max(jumped.size(), Math.max(d1, Math.max(d2, Math.max(d3, d4))));
    }

    @Override
    public boolean move(Piece piece, Board board, Square start, Direction d) {
        Square temp;
        int i = 0, j = 0;
        switch (d){
            case UpLeft -> {
                i = -1;
                j = 1;
            }
            case UpRight -> {
                i = 1;
                j = 1;
            }
        }
        temp = board.getTile(start.getX() + i, start.getY() + j);
        return temp.getPiece() == null;
    }

    @Override
    public boolean jump(Piece piece, Board board, Square start, Direction d) {
        Square temp;
        int i = 0, j = 0;
        switch (d){
            case UpLeft -> {
                i = -1;
                j = 1;
            }
            case UpRight -> {
                i = 1;
                j = 1;
            }
            case DownLeft -> {
                i = -1;
                j = -1;
            }
            case DownRight -> {
                i = 1;
                j = -1;
            }
        }
        temp = board.getTile(start.getX() + i, start.getY() + j);
        if(temp.getPiece() != null && temp.getPiece().isWhite() != piece.isWhite()){
            temp = board.getTile(start.getX() + 2 * i, start.getY() + 2 * j);
            return temp.getPiece() == null;
        }
        else return false;
    }
}
