package warcaby;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnglishManStateTest {

    @Test
    void availibleMoves() {
        //Testowanie gdy pionki w pozycji startowej
        //biały środek
        Square[][] tiles = initialSetting();
        Piece piece = tiles[2][5].getPiece();
        List<Square> actual = new ArrayList<>();
        actual.add(tiles[1][4]);
        actual.add(tiles[3][4]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));
        //biały brzeg
        piece = tiles[0][5].getPiece();
        actual = new ArrayList<>();
        actual.add(tiles[1][4]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));
        //biały bez mozliwosci ruchu
        piece = tiles[3][6].getPiece();
        assertTrue(piece.getAvailibleMoves(tiles).isEmpty());

        //czarny środek
        piece = tiles[3][2].getPiece();
        actual = new ArrayList<>();
        actual.add(tiles[2][3]);
        actual.add(tiles[4][3]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));
        //czarny brzeg
        piece = tiles[7][2].getPiece();
        actual = new ArrayList<>();
        actual.add(tiles[6][3]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));
        //czarny bez mozliwosci ruchu
        piece = tiles[2][1].getPiece();
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
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));

        piece=tiles[6][3].getPiece();
        actual = new ArrayList<>();
        actual.add(tiles[4][1]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));

        piece=tiles[3][4].getPiece();
        actual = new ArrayList<>();
        actual.add(tiles[2][3]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));

        piece=tiles[5][4].getPiece();
        actual = new ArrayList<>();
        actual.add(tiles[4][5]);
        actual.add(tiles[6][5]);
        assertEquals(piece.getAvailibleMoves(tiles).size(), actual.size());
        assertTrue(piece.getAvailibleMoves(tiles).containsAll(actual));
    }

    @Test
    void moveSequence() {
        Square[][] tiles = variousKillingSetup();
        Piece piece = tiles[6][3].getPiece();
        List<List<SingleMove>> output = piece.moveSequences(tiles);
        List<List<SingleMove>> result=new ArrayList<>();
        List<SingleMove> current=new ArrayList<>();
        current.add(new SingleMove(tiles[6][3],tiles[4][1], tiles[5][2]));
        result.add(current);
        assertEquals(output.size(), result.size());
        assertSame(output.get(0).get(0).getStart(), result.get(0).get(0).getStart());
        assertSame(output.get(0).get(0).getEnd(), result.get(0).get(0).getEnd());
        assertSame(output.get(0).get(0).getKilled(), result.get(0).get(0).getKilled());


        tiles = singleKillingSetup();
        piece = tiles[1][4].getPiece();
        output=piece.moveSequences(tiles);
        result.clear();
        current.clear();
        current.add(new SingleMove(tiles[1][4],tiles[3][2],tiles[2][3]));
        current.add(new SingleMove(tiles[3][2],tiles[5][0],tiles[4][1]));
        result.add(current);

        assertEquals(output.size(), result.size());
        assertSame(output.get(0).get(0).getStart(), result.get(0).get(0).getStart());
        assertSame(output.get(0).get(0).getEnd(), result.get(0).get(0).getEnd());
        assertSame(output.get(0).get(0).getKilled(), result.get(0).get(0).getKilled());
        assertSame(output.get(0).get(1).getStart(), result.get(0).get(1).getStart());
        assertSame(output.get(0).get(1).getEnd(), result.get(0).get(1).getEnd());
        assertSame(output.get(0).get(1).getKilled(), result.get(0).get(1).getKilled());
    }

    Square[][] initialSetting(){
        Square[][] tiles = new Square[8][8];
        int x = 0, y = 0;
        for(int i = 0; i<8; i++) {
            for(int j = 0; j<8; j++) {
                if((i+j)%2==0) {
                    Square square = new Square(x,y,70,70, Color.WHEAT);
                    tiles[j][i] = square;
                    x += 70;
                }
                else {
                    if(i<3) {
                        Piece piece = new EnglishPiece(x+35, y+35, 30, Color.BLACK, new EnglishManState());
                        Square square = new Square(x,y,70,70,Color.BROWN);
                        square.setPiece(piece);
                        tiles[j][i] = square;
                    }
                    else if(i == 3 || i == 4) {
                        Square square = new Square(x,y,70,70,Color.BROWN);
                        tiles[j][i] = square;

                    }
                    else {
                        Piece piece = new EnglishPiece(x+35, y+35, 30, Color.WHITE, new EnglishManState());
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
        Square[][] tiles = new Square[8][8];
        int x = 0, y = 0;
        for(int i = 0; i<8; i++) {
            for(int j = 0; j<8; j++) {
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
        tiles[2][1].setPiece(new EnglishPiece((int) (tiles[2][1].getX()+35), (int) (tiles[2][1].getY()+35),30, Color.WHITE, new EnglishManState()));
        tiles[3][2].setPiece(new EnglishPiece((int) (tiles[3][2].getX()+35), (int) (tiles[3][2].getY()+35),30, Color.BLACK, new EnglishManState()));
        tiles[5][2].setPiece(new EnglishPiece((int) (tiles[5][2].getX()+35), (int) (tiles[5][2].getY()+35),30, Color.BLACK, new EnglishManState()));
        tiles[4][3].setPiece(new EnglishPiece((int) (tiles[4][3].getX()+35), (int) (tiles[4][3].getY()+35),30, Color.WHITE, new EnglishManState()));
        tiles[6][3].setPiece(new EnglishPiece((int) (tiles[6][3].getX()+35), (int) (tiles[6][3].getY()+35),30, Color.WHITE, new EnglishManState()));
        tiles[3][4].setPiece(new EnglishPiece((int) (tiles[3][4].getX()+35), (int) (tiles[3][4].getY()+35),30, Color.WHITE, new EnglishManState()));
        tiles[5][4].setPiece(new EnglishPiece((int) (tiles[5][4].getX()+35), (int) (tiles[5][4].getY()+35),30, Color.BLACK, new EnglishManState()));
        return tiles;
    }
    Square[][] singleKillingSetup(){
        Square[][] tiles = new Square[8][8];
        int x = 0, y = 0;
        for(int i = 0; i<8; i++) {
            for(int j = 0; j<8; j++) {
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
        tiles[1][4].setPiece(new EnglishPiece((int) (tiles[1][4].getX()+35), (int) (tiles[1][4].getY()+35),30, Color.WHITE, new EnglishManState()));
        tiles[2][3].setPiece(new EnglishPiece((int) (tiles[2][3].getX()+35), (int) (tiles[2][3].getY()+35),30, Color.BLACK, new EnglishManState()));
        tiles[4][1].setPiece(new EnglishPiece((int) (tiles[4][1].getX()+35), (int) (tiles[4][1].getY()+35),30, Color.BLACK, new EnglishManState()));
        tiles[6][1].setPiece(new EnglishPiece((int) (tiles[6][1].getX()+35), (int) (tiles[6][1].getY()+35),30, Color.BLACK, new EnglishManState()));
        return tiles;
    }
}