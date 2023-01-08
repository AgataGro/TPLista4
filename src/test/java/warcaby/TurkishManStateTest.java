package warcaby;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TurkishManStateTest {

    @Test
    void availibleMoves() {
        Square[][] tiles = initialSetting();
        Piece piece = tiles[3][5].getPiece();
        List<Square> actual = new ArrayList<>();
        actual.add(tiles[3][4]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));

        actual.clear();

        piece = tiles[5][6].getPiece();
        assertTrue(piece.getAvailibleMoves(tiles).isEmpty());

        piece = tiles[5][1].getPiece();
        assertEquals(piece.getAvailibleMoves(tiles).size(), 0);
        assertTrue(piece.getAvailibleMoves(tiles).isEmpty());

        piece = tiles[3][2].getPiece();
        actual = new ArrayList<>();
        actual.add(tiles[3][3]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));

        tiles = variousKillingSetup();
        piece=tiles[5][1].getPiece();
        actual.clear();
        actual.add(tiles[4][1]);
        actual.add(tiles[6][1]);
        actual.add(tiles[5][0]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));

        piece=tiles[2][2].getPiece();
        actual.clear();
        actual.add(tiles[1][2]);
        actual.add(tiles[3][2]);
        actual.add(tiles[2][4]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));

        piece=tiles[5][2].getPiece();
        actual.clear();
        actual.add(tiles[4][2]);
        actual.add(tiles[6][2]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));

        piece=tiles[5][4].getPiece();
        actual.clear();
        actual.add(tiles[4][4]);
        actual.add(tiles[6][4]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));

    }

    @Test
    void moveSequence() {
        Square[][] tiles = variousKillingSetup();
        Piece piece = tiles[2][2].getPiece();
        List<List<Square>> output = piece.moveSequences(tiles);
        List<List<Square>> result=new ArrayList<>();
        List<Square> current=new ArrayList<>();
        current.add(tiles[2][4]);
        current.add(tiles[4][4]);
        current.add(tiles[6][4]);
        result.add(current);
        assertEquals(output.size(), result.size());
        assertTrue(output.containsAll(result));

    }

    Square[][] initialSetting(){
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
                if(i == 1 || i == 2) {
                    Piece piece = new Piece(x+35,y+35,30,Color.BLACK, new TurkishManState());
                    square.setPiece(piece);

                }
                else if(i == 5 || i == 6) {
                    Piece piece = new Piece(x+35,y+35,30,Color.WHITE, new TurkishManState());
                    square.setPiece(piece);

                }
                x += 70;
            }
            x = 0;
            y += 70;
        }
        return tiles;
    }

    Square[][] variousKillingSetup(){
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
        tiles[5][1].setPiece(new Piece((int) (tiles[5][1].getX()+35), (int) (tiles[5][1].getY()+35),30, Color.WHITE, new TurkishManState()));
        tiles[2][2].setPiece(new Piece((int) (tiles[2][2].getX()+35), (int) (tiles[2][2].getY()+35),30, Color.BLACK, new TurkishManState()));
        tiles[5][2].setPiece(new Piece((int) (tiles[5][2].getX()+35), (int) (tiles[5][2].getY()+35),30, Color.BLACK, new TurkishManState()));
        tiles[2][3].setPiece(new Piece((int) (tiles[2][3].getX()+35), (int) (tiles[2][3].getY()+35),30, Color.WHITE, new TurkishManState()));
        tiles[3][3].setPiece(new Piece((int) (tiles[3][3].getX()+35), (int) (tiles[3][3].getY()+35),30, Color.BLACK, new TurkishManState()));
        tiles[5][3].setPiece(new Piece((int) (tiles[5][3].getX()+35), (int) (tiles[5][3].getY()+35),30, Color.BLACK, new TurkishManState()));
        tiles[3][4].setPiece(new Piece((int) (tiles[3][4].getX()+35), (int) (tiles[3][4].getY()+35),30, Color.WHITE, new TurkishManState()));
        tiles[5][4].setPiece(new Piece((int) (tiles[5][4].getX()+35), (int) (tiles[5][4].getY()+35),30, Color.WHITE, new TurkishManState()));

        return tiles;
    }
}