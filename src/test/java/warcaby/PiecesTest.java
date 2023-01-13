package warcaby;

import org.junit.Test;
import org.junit.Before;
import javafx.scene.paint.Color;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PiecesTest {
    private Piece engBlackPiece, engWhitePiece, polBlackPiece, polWhitePiece, turBlackPiece, turWhitePiece;

    @Before
    public void setUp() {
        engBlackPiece = new EnglishPiece(35, 35, 30, Color.BLACK, new EnglishManState());
        engWhitePiece = new EnglishPiece(35,525,30,Color.WHITE, new EnglishManState());
        polBlackPiece = new PolishPiece(35,35,30,Color.BLACK,new PolishManState());
        polWhitePiece = new PolishPiece(35,665,30,Color.WHITE,new PolishManState());
        turBlackPiece = new TurkishPiece(35,105,30,Color.BLACK,new TurkishManState());
        turWhitePiece = new TurkishPiece(35,455,30,Color.WHITE,new TurkishManState());
    }

    @Test
    public void MoveDirectionTest() {
        assertEquals(engBlackPiece.getMoveDirection(),1);
        assertEquals(engWhitePiece.getMoveDirection(),-1);
        assertEquals(polBlackPiece.getMoveDirection(),1);
        assertEquals(polWhitePiece.getMoveDirection(),-1);
        assertEquals(turBlackPiece.getMoveDirection(),1);
        assertEquals(turWhitePiece.getMoveDirection(),-1);
    }

    @Test
    public void notMoveTest() {
        Integer engBX = engBlackPiece.getOldX();
        Integer engBY = engBlackPiece.getOldY();
        Integer engWX = engWhitePiece.getOldX();
        Integer engWY = engWhitePiece.getOldY();
        Integer polBX = polBlackPiece.getOldX();
        Integer polBY = polBlackPiece.getOldY();
        Integer polWX = polWhitePiece.getOldX();
        Integer polWY = polWhitePiece.getOldY();
        Integer turBX = turBlackPiece.getOldX();
        Integer turBY = turBlackPiece.getOldY();
        Integer turWX = turWhitePiece.getOldX();
        Integer turWY = turWhitePiece.getOldY();

        notMoveForTest();
        
        assertTrue(engBlackPiece.getOldX()==engBX);
        assertTrue(engBlackPiece.getOldY()==engBY);
        assertTrue(engWhitePiece.getOldX()==engWX);
        assertTrue(engWhitePiece.getOldY()==engWY);
        assertTrue(polBlackPiece.getOldX()==polBX);
        assertTrue(polBlackPiece.getOldY()==polBY);
        assertTrue(polWhitePiece.getOldX()==polWX);
        assertTrue(polWhitePiece.getOldY()==polWY);
        assertTrue(turBlackPiece.getOldX()==turBX);
        assertTrue(turBlackPiece.getOldY()==turBY);
        assertTrue(turWhitePiece.getOldX()==turWX);
        assertTrue(turWhitePiece.getOldY()==turWY);
    }

    @Test
    public void stateTest() {
        moveForTest();
        assertTrue(engBlackPiece.getState() instanceof EnglishKingState);
        assertTrue(engWhitePiece.getState() instanceof EnglishKingState);
        assertTrue(polWhitePiece.getState() instanceof PolishKingState);
        assertTrue(polWhitePiece.getState() instanceof PolishKingState);
        assertTrue(turWhitePiece.getState() instanceof TurkishKingState);
        assertTrue(turWhitePiece.getState() instanceof TurkishKingState);
    }

    void notMoveForTest() {
        engBlackPiece.notMove();
        engWhitePiece.notMove();
        polBlackPiece.notMove();
        polWhitePiece.notMove();
        turBlackPiece.notMove();
        turWhitePiece.notMove();
    }

    void moveForTest() {
        engBlackPiece.move(0,7);
        engWhitePiece.move(0,0);
        polBlackPiece.move(0,9);
        polWhitePiece.move(0,0);
        turBlackPiece.move(0,7);
        turWhitePiece.move(0,0);
    }
}