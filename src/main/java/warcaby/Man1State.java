package warcaby;

import java.util.List;

public class Man1State implements State {
    /**
     * Funkcja wykonująca ruch, usuwa zbite pionki i przesuwa wybrany pionek
     * @param piece
     * @param board
     * @param sequence
     */
    @Override
    public void Move(Piece piece, Board board, String sequence) {
        String[] seq= sequence.split(":");
        int x1,x2,y1,y2;
        for(int i=0;i< seq.length-1;i++){
            x1=(int)seq[i].charAt(0)-(int)'A';
            x2=(int)seq[i+1].charAt(0)-(int)'A';
            y1=(int)seq[i].charAt(1)-(int)'0';
            y2=(int)seq[i+1].charAt(1)-(int)'0';
            board.getTile(x1,y1).setPiece(null);
            if(Math.abs(x1-x2)==2&&Math.abs(y1-y2)==2){
                if(x1>x2){
                    if(y1>y2)board.getTile(x1-1,y1-1).setPiece(null);
                    else board.getTile(x1-1,y1=1).setPiece(null);
                }
                else {
                    if(y1>y2)board.getTile(x1+1,y1-1).setPiece(null);
                    else board.getTile(x1+1,y1=1).setPiece(null);
                }
            }
            board.getTile(x2,y2).setPiece(piece);
        }
    }

    /**
     * Funkcja zliczająca ile maksymalnie pionków można zbić danym pionkiem w jedntm ruchu/turze
     * Pionek może bić do tyłu i do przodu po przekątnej
     * Pionek nie może przeskoczyć dwa razy nad tym samym pionkiem
     * @see State
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

    /**
     * @see State
     * Zwraca true jak można wykonać ruch
     * Czarne w kierunku DownLeft lub DownRight
     * Białe w kierunku UpLeft lub UpRight
     * Jak pole, na które przesuwamy pionek jest puste
     * W przeciwnym wypadku false
     */
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
            case DownLeft -> {
                i = -1;
                j = -1;
            }
            case DownRight -> {
                i = 1;
                j = -1;
            }
        }
        if((piece.isWhite()&&(d==Direction.UpLeft||d==Direction.UpRight))
                || (!piece.isWhite()&&(d==Direction.DownLeft||d==Direction.DownRight))) {
            if (start.getX() + i < 10 && start.getX() + i >= 0 && start.getY() + j < 10 && start.getY() + j >= 0) {
                temp = board.getTile(start.getX() + i, start.getY() + j);
                return temp.getPiece() == null;
            } else return false;
        }
        else return false;
    }

    /**
     * @see State
     * Pionek może bić zarówno do przodu jak i do tyłu
     */
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
        if(start.getX()+i<10&&start.getX()+i>=0&&start.getY()+j<10&&start.getY()+j>=0) {
            temp = board.getTile(start.getX() + i, start.getY() + j);
            if (temp.getPiece() != null && temp.getPiece().isWhite() != piece.isWhite()) {
                if(start.getX()+2*i<10&&start.getX()+2*i>=0&&start.getY()+2*j<10&&start.getY()+2*j>=0) {
                    temp = board.getTile(start.getX() + 2 * i, start.getY() + 2 * j);
                    return temp.getPiece() == null;
                }
                else return false;
            }
            else return false;
        }
        else return false;
    }

    /**
     * @see State
     * Pionek jest koronowany jeżeli na końcu/starcie tury znajduje się w rzędzie najbliżej gracza przeciwnego
     */
    @Override
    public State crown(Piece piece, Square start) {
        if((piece.isWhite()&&start.getY()==9)||(!piece.isWhite()&&start.getY()==0)){
            return new King1State();
        }
        else return this;
    }
}
