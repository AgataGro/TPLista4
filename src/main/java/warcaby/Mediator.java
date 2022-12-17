package warcaby;

import java.util.ArrayList;
import java.util.List;

public class Mediator {
    Board board;
    List<Piece> movablePieces=new ArrayList<>();
    int maxKillable;
    public Mediator(Board board){
        this.board=board;
    }
    // wyliczenie maksymalnej liczby pionkow, ktore gracz jest w stanie zbic w danej turze
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
}
