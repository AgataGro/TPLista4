package warcaby;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PolishManStateTest {

    @Test
    void availibleMoves() {
        //Testowanie gdy pionki w pozycji startowej
        //biały środek
        Square[][] tiles = initialSetting();
        Piece piece = tiles[3][6].getPiece();
        List<Square> actual = new ArrayList<>();
        actual.add(tiles[4][5]);
        actual.add(tiles[2][5]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));
        //biały brzeg
        piece = tiles[9][6].getPiece();
        actual = new ArrayList<>();
        actual.add(tiles[8][5]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));
        //biały bez mozliwosci ruchu
        piece = tiles[8][7].getPiece();
        assertTrue(piece.getAvailibleMoves(tiles).isEmpty());

        //czarny środek
        piece = tiles[2][3].getPiece();
        actual = new ArrayList<>();
        actual.add(tiles[1][4]);
        actual.add(tiles[3][4]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));
        //biały brzeg
        piece = tiles[0][3].getPiece();
        actual = new ArrayList<>();
        actual.add(tiles[1][4]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));
        //biały bez mozliwosci ruchu
        piece = tiles[1][2].getPiece();
        assertTrue(piece.getAvailibleMoves(tiles).isEmpty());

        //Testowanie zbijania
        tiles=variousKillingSetup();
        piece=tiles[2][1].getPiece();
        actual = new ArrayList<>();
        actual.add(tiles[1][0]);
        actual.add(tiles[3][0]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));

        piece=tiles[3][2].getPiece();
        actual = new ArrayList<>();
        actual.add(tiles[1][0]);
        actual.add(tiles[2][3]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));

        piece=tiles[5][2].getPiece();
        actual = new ArrayList<>();
        actual.add(tiles[7][4]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));

        piece=tiles[4][3].getPiece();
        actual = new ArrayList<>();
        actual.add(tiles[6][1]);
        actual.add(tiles[6][5]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));

        piece=tiles[6][3].getPiece();
        actual = new ArrayList<>();
        actual.add(tiles[4][1]);
        actual.add(tiles[7][2]);
        actual.add(tiles[4][5]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));

        piece=tiles[3][4].getPiece();
        actual = new ArrayList<>();
        actual.add(tiles[2][3]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));

        piece=tiles[5][4].getPiece();
        actual = new ArrayList<>();
        actual.add(tiles[7][2]);
        actual.add(tiles[4][5]);
        actual.add(tiles[6][5]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));

    }
    @Test
    void moveSequence() {
        Square[][] tiles = variousKillingSetup();
        Piece piece = tiles[6][3].getPiece();
        List<List<Square>> output = piece.moveSequences(tiles);
        List<List<Square>> result=new ArrayList<>();
        List<Square> current=new ArrayList<>();
        current.add(tiles[4][1]);
        current.add(tiles[2][3]);
        result.add(current);
        assertEquals(output.size(), result.size());
        assertTrue(output.containsAll(result));

        tiles = singleKillingSetup();
        piece = tiles[0][3].getPiece();
        output = piece.moveSequences(tiles);
        result.clear();
        current.clear();
        current.add(tiles[2][1]);
        current.add(tiles[4][3]);
        current.add(tiles[6][5]);
        result.add(current);

        assertEquals(output.size(), result.size());
        assertTrue(output.containsAll(result));

    }
    Square[][] initialSetting(){
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
                    if(i<4) {
                        Piece piece = new Piece(x+35, y+35, 30, Color.BLACK, new PolishManState());
                        Square square = new Square(x,y,70,70,Color.BROWN);
                        square.setPiece(piece);
                        tiles[j][i] = square;
                    }
                    else if(i == 4 || i == 5) {
                        Square square = new Square(x,y,70,70,Color.BROWN);
                        tiles[j][i] = square;

                    }
                    else if(i>5) {
                        Piece piece = new Piece(x+35, y+35, 30, Color.WHITE, new PolishManState());
                        Square square = new Square(x,y,70,70,Color.BROWN);
                        square.setPiece(piece);
                        tiles[j][i] = square;
                    }
                    x += 70;
                }
            }
            x = 0;
            y += 70;
        }
        return tiles;
    }
    Square[][] variousKillingSetup(){
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
        tiles[2][1].setPiece(new Piece((int) (tiles[2][1].getX()+35), (int) (tiles[2][1].getY()+35),30, Color.WHITE, new PolishManState()));
        tiles[3][2].setPiece(new Piece((int) (tiles[3][2].getX()+35), (int) (tiles[3][2].getY()+35),30, Color.BLACK, new PolishManState()));
        tiles[5][2].setPiece(new Piece((int) (tiles[5][2].getX()+35), (int) (tiles[5][2].getY()+35),30, Color.BLACK, new PolishManState()));
        tiles[4][3].setPiece(new Piece((int) (tiles[4][3].getX()+35), (int) (tiles[4][3].getY()+35),30, Color.WHITE, new PolishManState()));
        tiles[6][3].setPiece(new Piece((int) (tiles[6][3].getX()+35), (int) (tiles[6][3].getY()+35),30, Color.WHITE, new PolishManState()));
        tiles[3][4].setPiece(new Piece((int) (tiles[3][4].getX()+35), (int) (tiles[3][4].getY()+35),30, Color.WHITE, new PolishManState()));
        tiles[5][4].setPiece(new Piece((int) (tiles[5][4].getX()+35), (int) (tiles[5][4].getY()+35),30, Color.BLACK, new PolishManState()));
        return tiles;
    }
    Square[][] singleKillingSetup(){
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
        tiles[1][2].setPiece(new Piece((int) (tiles[1][2].getX()+35), (int) (tiles[1][2].getY()+35),30, Color.BLACK, new PolishManState()));
        tiles[3][2].setPiece(new Piece((int) (tiles[3][2].getX()+35), (int) (tiles[3][2].getY()+35),30, Color.BLACK, new PolishManState()));
        tiles[0][3].setPiece(new Piece((int) (tiles[0][3].getX()+35), (int) (tiles[0][3].getY()+35),30, Color.WHITE, new PolishManState()));
        tiles[5][4].setPiece(new Piece((int) (tiles[3][4].getX()+35), (int) (tiles[3][4].getY()+35),30, Color.BLACK, new PolishManState()));
        return tiles;
    }
}