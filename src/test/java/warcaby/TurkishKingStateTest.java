package warcaby;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TurkishKingStateTest {

    @Test
    void availibleMoves() {
        Square[][] tiles = KillingSetup();
        Piece piece = tiles[3][5].getPiece();
        List<Square> actual = new ArrayList<>();
        actual.add(tiles[3][4]);
        actual.add(tiles[3][3]);
        actual.add(tiles[3][2]);
        actual.add(tiles[3][1]);
        actual.add(tiles[3][0]);
        actual.add(tiles[3][6]);
        actual.add(tiles[3][7]);
        actual.add(tiles[2][5]);
        actual.add(tiles[1][5]);
        actual.add(tiles[0][5]);
        actual.add(tiles[5][5]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));
    }

    @Test
    void moveSequence() {
        Square[][] tiles = KillingSetup();
        Piece piece = tiles[3][5].getPiece();
        List<List<SingleMove>> output = piece.moveSequences(tiles);
        List<List<SingleMove>> result=new ArrayList<>();
        List<SingleMove> current=new ArrayList<>();
        current.add(new SingleMove(tiles[3][5],tiles[5][5],tiles[4][5]));
        current.add(new SingleMove(tiles[5][5],tiles[5][1],tiles[5][2]));
        current.add(new SingleMove(tiles[5][1],tiles[1][1],tiles[2][1]));
        current.add(new SingleMove(tiles[1][1],tiles[1][5],tiles[1][4]));
        current.add(new SingleMove(tiles[1][5],tiles[7][5],tiles[6][5]));
        result.add(current);
        assertEquals(output.size(), result.size());
        List<List<String>> s1=new ArrayList<>(),s2=new ArrayList<>();
        List<String> ss1=new ArrayList<>(), ss2=new ArrayList<>();
        for(int i =0;i<output.size();i++) {
            ss1.clear();
            ss2.clear();
            for(int j=0;j<output.get(i).size();j++){
                ss1.add(result.get(i).get(j).getAsString());
                ss2.add(output.get(i).get(j).getAsString());
            }
            s1.add(ss1);
            s2.add(ss2);
        }
        assertTrue(s1.containsAll(s2));
    }

    Square[][] KillingSetup(){
        Square[][] tiles = new Square[8][8];
        int x = 0, y = 0;
        for(int i = 0; i<8; i++) {
            for(int j = 0; j<8; j++) {
                Square square = new Square(x,y,70,70,null);
                if((i+j)%2==0)
                    square.setFill(Color.WHEAT);
                else
                    square.setFill(Color.BROWN);
                tiles[j][i] = square;
                x += 70;
            }
            x = 0;
            y += 70;
        }
        tiles[3][5].setPiece(new TurkishPiece((int) (tiles[3][5].getX()+35), (int) (tiles[3][5].getY()+35),30, Color.WHITE, new TurkishKingState()));
        tiles[4][5].setPiece(new TurkishPiece((int) (tiles[4][5].getX()+35), (int) (tiles[4][5].getY()+35),30, Color.BLACK, new TurkishKingState()));
        tiles[6][5].setPiece(new TurkishPiece((int) (tiles[6][5].getX()+35), (int) (tiles[6][5].getY()+35),30, Color.BLACK, new TurkishKingState()));
        tiles[1][4].setPiece(new TurkishPiece((int) (tiles[1][4].getX()+35), (int) (tiles[1][4].getY()+35),30, Color.BLACK, new TurkishKingState()));
        tiles[2][1].setPiece(new TurkishPiece((int) (tiles[2][1].getX()+35), (int) (tiles[2][1].getY()+35),30, Color.BLACK, new TurkishKingState()));
        tiles[5][2].setPiece(new TurkishPiece((int) (tiles[5][2].getX()+35), (int) (tiles[5][2].getY()+35),30, Color.BLACK, new TurkishKingState()));


        return tiles;
    }
}