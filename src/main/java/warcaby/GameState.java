package warcaby;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private int white;
    private int black;
    private final int size;
    private List<List<Square>> moves=new ArrayList<>();
    Square[][] tiles;
    public GameState(int size){
        this.size=size;
    }
    public void calculateCurrentState(Square[][] tiles){
        this.tiles=tiles;
        this.white=0;
        this.black=0;
        for(int i =0; i<size;i++){
            for(int j =0;j<size;j++){
                if(tiles[i][j].getPiece()!=null){
                    if(tiles[i][j].getPiece().getColor()== Color.WHITE)white++;
                    else black++;
                }
            }
        }
    }

    public int getBlack() {
        return black;
    }

    public int getWhite() {
        return white;
    }
    public void calculateCurrentMoves(Color color){
        moves.clear();
        for(int i =0; i<size;i++){
            for(int j =0;j<size;j++){
                if(tiles[i][j].getPiece()!=null){
                    if(tiles[i][j].getPiece().getColor()== color){
                        moves.addAll(tiles[i][j].getPiece().moveSequences(tiles));
                    }
                }
            }
        }
    }
}
