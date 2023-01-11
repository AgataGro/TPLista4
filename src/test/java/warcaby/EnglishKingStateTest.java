package warcaby;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnglishKingStateTest {

    @Test
    void availibleMoves() {
        Square[][] tiles = simpleSetup();
        Piece piece = tiles[4][3].getPiece();
        List<Square> actual = new ArrayList<>();
        actual.add(tiles[2][5]);
        actual.add(tiles[6][1]);
        actual.add(tiles[3][2]);
        actual.add(tiles[5][4]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));

        actual.clear();
        piece=tiles[5][2].getPiece();
        actual.add(tiles[4][1]);
        actual.add(tiles[6][1]);
        actual.add(tiles[6][3]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));
    }

    @Test
    void moveSequence() {
        Square[][] tiles = simpleSetup();
        Piece piece = tiles[4][3].getPiece();
        List<List<SingleMove>> output = piece.moveSequences(tiles);
        List<List<SingleMove>> result=new ArrayList<>();
        List<SingleMove> current=new ArrayList<>();
        current.add(new SingleMove(tiles[4][3], tiles[6][1], tiles[5][2]));
        result.add(current);
        current.clear();
        current.add(new SingleMove(tiles[4][3], tiles[2][5], tiles[3][4]));
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

    Square[][] simpleSetup(){
        Square[][] tiles = new Square[10][10];
        int x = 0, y = 0;
        for(int i = 0; i<10; i++) {
            for(int j = 0; j<10; j++) {
                if((i+j)%2==0) {
                    Square square = new Square(x,y,70,70, Color.WHEAT);
                    tiles[j][i] = square;
                    x += 70;
                }
                else {
                    Square square = new Square(x,y,70,70,Color.BROWN);
                    tiles[j][i] = square;

                    x += 70;
                }
            }
            x = 0;
            y += 70;
        }
        tiles[4][3].setPiece(new PolishPiece((int) (tiles[4][3].getX()+35), (int) (tiles[4][3].getY()+35),30, Color.WHITE, new EnglishKingState()));
        tiles[2][1].setPiece(new PolishPiece((int) (tiles[2][1].getX()+35), (int) (tiles[2][1].getY()+35),30, Color.BLACK, new EnglishKingState()));
        tiles[5][2].setPiece(new PolishPiece((int) (tiles[5][2].getX()+35), (int) (tiles[5][2].getY()+35),30, Color.BLACK, new EnglishKingState()));
        tiles[3][4].setPiece(new PolishPiece((int) (tiles[3][4].getX()+35), (int) (tiles[3][4].getY()+35),30, Color.BLACK, new EnglishKingState()));
        tiles[6][5].setPiece(new PolishPiece((int) (tiles[6][5].getX()+35), (int) (tiles[6][5].getY()+35),30, Color.BLACK, new EnglishKingState()));

        return tiles;
    }
}