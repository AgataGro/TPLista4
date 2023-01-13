package warcaby;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

import java.util.List;

public class TurkishCheckers extends Application implements Checkers{

    Square[][] tiles = new Square[8][8];
    Group tilesGroup = new Group();
    Group piecesGroup = new Group();
    
    static Stage classStage = new Stage();

    @Override
    public void start(Stage stage) {
        classStage = stage;
        AnchorPane pane = new AnchorPane();
        Button button = new Button("Opponent's turn");
        button.setFont(Font.font("Times New Roman", 20));
        pane.getChildren().addAll(tilesGroup, piecesGroup, button);
        AnchorPane.setBottomAnchor(button, 0.0);

        int x = 0, y = 0;
        for(int i = 0; i<8; i++) {
            for(int j = 0; j<8; j++) {
                Square square = new Square(x,y,70,70,null);
                if((i+j)%2==0)
                    square.setFill(Color.WHEAT);
                else
                    square.setFill(Color.BROWN);
                tiles[j][i] = square;
                tilesGroup.getChildren().add(square);
                if(i == 1 || i == 2) {
                    Piece piece = createPiece(x+35,y+35,30,Color.BLACK, new TurkishManState());
                    square.setPiece(piece);
                    piecesGroup.getChildren().add(piece);
                }
                else if(i == 5 || i == 6) {
                    Piece piece = createPiece(x+35,y+35,30,Color.WHITE, new TurkishManState());
                    square.setPiece(piece);
                    piecesGroup.getChildren().add(piece);
                }
                x += 70;
            }
            x = 0;
            y += 70;
        }

        Scene scene = new Scene(pane,560,596);
        stage.setTitle("Turkish checkers");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    private int getScenePlace(double x) {
        return (int)x/70;
    }

    /**
     * @param piece a piece whose move legality we want to check
     * @param x a coordinate of the left top corner of a square where we want to place the piece
     * @param y a coordinate of the left top corner of a square where we want to place the piece
     * @return true if we can move or false in opposite case
     */
    private boolean checkMove(Piece piece, int x, int y) {
        List<Square> availableMoves = piece.getAvailibleMoves(tiles);
        if(availableMoves.contains(tiles[x][y]))
            return true;
        else
            return false;
    }

    /**
     * in this method a new piece is created with requested features
     * and some actions are set to this piece
     * @param x a x coordinate of the middle of the piece
     * @param y a y coordinate of the middle of the piece
     * @param r a value of piece radius
     * @param color a color of a piece
     * @param state a state of a piece
     * @return created piece
     */
    private Piece createPiece(int x, int y, int r, Color color, State state) {
        Piece piece = new TurkishPiece(x,y,r,color,state);

        /*
         * metoda aktywowana, kiedy puszczamy pionek
         * ona odpowiada za umieszczenie pionka w odpowiednim miejscu
         * zgodnie z wykonanym przez gracza ruchem
         */
        piece.setOnMouseReleased(e -> {
            int newX = getScenePlace(e.getSceneX());
            int newY = getScenePlace(e.getSceneY());

            if(newX<0 || newY<0 || newX>7 || newY>7 || !checkMove(piece,newX,newY))
                piece.notMove();
            else {
                int startX = getScenePlace(piece.getOldX());
                int startY = getScenePlace(piece.getOldY());
                if(checkMove(piece,newX,newY) && piece.getState() instanceof TurkishManState) {
                    if((Math.abs(newX-startX)==1 && newY-startY==0) || (newX-startX==0 && newY-startY==piece.getMoveDirection())) {
                        piece.move(newX,newY);
                        tiles[startX][startY].setPiece(null);
                        tiles[newX][newY].setPiece(piece);
                    }
                    else if((Math.abs(newX-startX)==2 && newY-startY==0) || (newX-startX==0 && newY-startY==piece.getMoveDirection()*2)) {
                        piece.move(newX,newY);
                        tiles[startX][startY].setPiece(null);
                        tiles[newX][newY].setPiece(piece);
                        int middleX = startX + (newX - startX)/2;
                        int middleY = startY + (newY - startY)/2;
                        Piece killedPiece = tiles[middleX][middleY].getPiece();
                        tiles[getScenePlace(killedPiece.getOldX())][getScenePlace(killedPiece.getOldY())].setPiece(null);
                        piecesGroup.getChildren().remove(killedPiece);
                    }
                }
                else if(checkMove(piece,newX,newY) && piece.getState() instanceof TurkishKingState) {
                    piece.move(newX,newY);
                    tiles[startX][startY].setPiece(null);
                    tiles[newX][newY].setPiece(piece);
                }
            }
        });

        piece.setOnMouseClicked(e -> {
            List<Square> availibleMoves = piece.getAvailibleMoves(tiles);
            Square temp;
            for(int i=0;i< availibleMoves.size();i++)
            {
                temp=availibleMoves.get(i);
                temp.setStroke(Color.BLUE);
                temp.setStrokeWidth(5);
            }
        });

        piece.setOnMouseExited(e -> {
            List<Square> availibleMoves = piece.getAvailibleMoves(tiles);
            Square temp;
            for(int i=0;i< availibleMoves.size();i++)
            {
                temp=availibleMoves.get(i);
                temp.setStroke(null);
            }
        });
        
        piece.setOnMouseDragged(e -> {
            piece.toFront();
        });

        return piece;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
