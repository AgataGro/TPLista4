package warcaby;

import java.util.ArrayList;
import java.util.List;

/**
 * Wykorzystanie wzorca mediator do komunikacji między klasami Board i Piece
 */
public class Mediator {
    Board board;
    List<Piece> movablePieces=new ArrayList<>();
    int maxKillable;
    public Mediator(Board board){
        this.board=board;
    }

    /**
     * wyliczenie maksymalnej liczby pionkow, ktore gracz jest w stanie zbic w danej turze
     * @param isWhite czy ruch białych
     */
    public void setMaxKillable(boolean isWhite) {
        int temp=0, num=board.getTileNum();
        Piece tile;
        if(num==100)num=10;
        else if(num==64)num=8;
        for(int i=0;i<num;i++){
            for(int j=0;j<num;j++){
                tile=board.getTile(j,i).getPiece();
                if(tile.isWhite()==isWhite){
                    if(tile.getCurrentKillablePieces()>temp) temp= tile.getCurrentKillablePieces();
                }
            }
        }
        this.maxKillable = temp;
    }

    /**
     * Funkcja ustalająca, które pionki w danej turze mogą wykonać ruch
     * @param isWhite czy ruch białych
     */
    public void setMovablePieces(boolean isWhite) {
        Piece[] temp;
        int num=board.getTileNum();
        movablePieces.clear();
        setMaxKillable(isWhite);
        Piece tile;
        if(num==100)num=10;
        else if(num==64)num=8;
        for(int i=0;i<num;i++){
            for(int j=0;j<num;j++){
                tile=board.getTile(j,i).getPiece();
                if(tile.isWhite()==isWhite){
                    if(tile.getCurrentKillablePieces()==maxKillable) movablePieces.add(tile);
                }
            }
        }
    }

    /**
     * Funkcja wykonująca ruch
     * @param piece przesuwany pionek
     * @param steps kolejne przesunięcia pionka
     * @return true jeśli ruch był poprawny
     */
    public boolean doMove(Piece piece, String steps){
        //TO-DO sprawdzenie czy ruchy zbijają wszystkie niezbędne pionki
        if(movablePieces.contains(piece)) {
            piece.doMove(board, steps);
            return true;
        }
        else return false;
    }
}
