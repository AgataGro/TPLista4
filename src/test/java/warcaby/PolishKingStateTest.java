package warcaby;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PolishKingStateTest {

    @Test
    void availibleMoves() {
        Square[][] tiles = simpleSetup();
        Piece piece = tiles[6][7].getPiece();
        List<Square> actual = new ArrayList<>();
        actual.add(tiles[4][5]);
        actual.add(tiles[3][4]);
        actual.add(tiles[2][3]);
        actual.add(tiles[1][2]);
        actual.add(tiles[0][1]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));

    }

    @Test
    void moveSequence() {
        Square[][] tiles = simpleSetup();
        Piece piece = tiles[6][7].getPiece();
        List<List<SingleMove>> output = piece.moveSequences(tiles);
        List<List<SingleMove>> result=new ArrayList<>();
        List<SingleMove> current=new ArrayList<>();
        current.add(new SingleMove(tiles[6][7],tiles[4][5],tiles[5][6]));
        current.add(new SingleMove(tiles[5][6],tiles[7][2], tiles[6][3]));
        current.add(new SingleMove(tiles[7][2],tiles[5][0], tiles[6][1]));
        current.add(new SingleMove(tiles[5][0],tiles[2][3], tiles[3][2]));
        result.add(current);
        current.clear();
        current.add(new SingleMove(tiles[6][7],tiles[4][5],tiles[5][6]));
        current.add(new SingleMove(tiles[5][6],tiles[7][2], tiles[6][3]));
        current.add(new SingleMove(tiles[7][2],tiles[5][0], tiles[6][1]));
        current.add(new SingleMove(tiles[5][0],tiles[1][4], tiles[3][2]));
        result.add(current);
        current.clear();
        current.add(new SingleMove(tiles[6][7],tiles[4][5],tiles[5][6]));
        current.add(new SingleMove(tiles[5][6],tiles[7][2], tiles[6][3]));
        current.add(new SingleMove(tiles[7][2],tiles[5][0], tiles[6][1]));
        current.add(new SingleMove(tiles[5][0],tiles[0][5], tiles[3][2]));
        result.add(current);
        current.clear();
        current.add(new SingleMove(tiles[6][7],tiles[2][3],tiles[5][6]));
        current.add(new SingleMove(tiles[2][3],tiles[5][0], tiles[3][2]));
        current.add(new SingleMove(tiles[5][0],tiles[7][2], tiles[6][1]));
        current.add(new SingleMove(tiles[7][2],tiles[5][4], tiles[6][3]));
        result.add(current);
        current.clear();
        current.add(new SingleMove(tiles[6][7],tiles[2][3],tiles[5][6]));
        current.add(new SingleMove(tiles[2][3],tiles[5][0], tiles[3][2]));
        current.add(new SingleMove(tiles[5][0],tiles[7][2], tiles[6][1]));
        current.add(new SingleMove(tiles[7][2],tiles[4][5], tiles[6][3]));
        result.add(current);
        current.clear();
        current.add(new SingleMove(tiles[6][7],tiles[2][3],tiles[5][6]));
        current.add(new SingleMove(tiles[2][3],tiles[5][0], tiles[3][2]));
        current.add(new SingleMove(tiles[5][0],tiles[7][2], tiles[6][1]));
        current.add(new SingleMove(tiles[7][2],tiles[3][6], tiles[6][3]));
        result.add(current);
        current.clear();
        current.add(new SingleMove(tiles[6][7],tiles[2][3],tiles[5][6]));
        current.add(new SingleMove(tiles[2][3],tiles[5][0], tiles[3][2]));
        current.add(new SingleMove(tiles[5][0],tiles[7][2], tiles[6][1]));
        current.add(new SingleMove(tiles[7][2],tiles[2][7], tiles[6][3]));
        result.add(current);
        current.clear();
        current.add(new SingleMove(tiles[6][7],tiles[2][3],tiles[5][6]));
        current.add(new SingleMove(tiles[2][3],tiles[5][0], tiles[3][2]));
        current.add(new SingleMove(tiles[5][0],tiles[7][2], tiles[6][1]));
        current.add(new SingleMove(tiles[7][2],tiles[1][8], tiles[6][3]));
        result.add(current);
        current.clear();
        current.add(new SingleMove(tiles[6][7],tiles[2][3],tiles[5][6]));
        current.add(new SingleMove(tiles[2][3],tiles[5][0], tiles[3][2]));
        current.add(new SingleMove(tiles[5][0],tiles[7][2], tiles[6][1]));
        current.add(new SingleMove(tiles[7][2],tiles[0][9], tiles[6][3]));
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
        tiles[6][7].setPiece(new PolishPiece((int) (tiles[6][7].getX()+35), (int) (tiles[6][7].getY()+35),30, Color.WHITE, new PolishKingState()));
        tiles[5][6].setPiece(new PolishPiece((int) (tiles[5][6].getX()+35), (int) (tiles[5][6].getY()+35),30, Color.BLACK, new PolishKingState()));
        tiles[3][2].setPiece(new PolishPiece((int) (tiles[3][2].getX()+35), (int) (tiles[3][2].getY()+35),30, Color.BLACK, new PolishKingState()));
        tiles[6][1].setPiece(new PolishPiece((int) (tiles[6][1].getX()+35), (int) (tiles[6][1].getY()+35),30, Color.BLACK, new PolishKingState()));
        tiles[6][3].setPiece(new PolishPiece((int) (tiles[6][3].getX()+35), (int) (tiles[6][3].getY()+35),30, Color.BLACK, new PolishKingState()));

        return tiles;
    }

}