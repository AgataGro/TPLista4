package warcaby;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class EnglishCheckers extends Application {

    private Group tilesGroup = new Group();
    private Group piecesGroup = new Group();
    private Square[][] tiles = new Square[8][8];
    
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
                if((i+j)%2==0) {
                    Square square = new Square(x,y,70,70,Color.WHEAT);
                    tiles[j][i] = square;
                    tilesGroup.getChildren().add(square);
                    x += 70;
                }
                else {
                    if(i<3) {
                        Piece piece = createPiece(x+35,y+35,30,Color.BLACK, new EnglishManState());
                        Square square = new Square(x,y,70,70,Color.BROWN);
                        tiles[j][i] = square;
                        square.setPiece(piece);
                        tilesGroup.getChildren().add(square);
                        piecesGroup.getChildren().add(piece);
                    }
                    else if(i == 3 || i == 4) {
                        Square square = new Square(x,y,70,70,Color.BROWN);
                        tiles[j][i] = square;
                        tilesGroup.getChildren().add(square);
                    }
                    else if(i>4) {
                        Piece piece = createPiece(x+35,y+35,30,Color.WHITE, new EnglishManState());
                        Square square = new Square(x,y,70,70,Color.BROWN);
                        tiles[j][i] = square;
                        square.setPiece(piece);
                        tilesGroup.getChildren().add(square);
                        piecesGroup.getChildren().add(piece);
                    }
                    x += 70;
                }
            }
            x = 0;
            y += 70;
        }

        Scene scene = new Scene(pane,560,596);
        stage.setTitle("English checkers");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private int getScenePlace(double x) {
        return (int)x/70;
    }

    private boolean checkMove(Piece piece, int x, int y) {
        List<Square> availableMoves = piece.getAvailableMoves(tiles);
        if(availableMoves.contains(tiles[x][y]))
            return true;
        else
            return false;
    }

    private Piece createPiece(int x, int y, int r, Color color, State state) {
        Piece piece = new EnglishPiece(x,y,r,color,state);

        /*
         * metoda aktywowana, kiedy puszczamy pionek
         * ona odpowiada za umieszczenie pionka w odpowiednim miejscu
         * zgodnie z wykonanym przez gracza ruchem
         */
        piece.setOnMouseReleased(e -> {
            int newX = getScenePlace(e.getSceneX());
            int newY = getScenePlace(e.getSceneY());

            MoveDetails details;

            if(newX<0 || newY<0 || newX>7 || newY>7 || !checkMove(piece,newX,newY))
                piece.notMove();
            else {
                int startX = getScenePlace(piece.getOldX());
                int startY = getScenePlace(piece.getOldY());
                if(checkMove(piece,newX,newY) && piece.getState() instanceof EnglishManState) {
                    if(Math.abs(newX-startX)==1) {
                        piece.move(newX,newY);
                        tiles[startX][startY].setPiece(null);
                        tiles[newX][newY].setPiece(piece);
                    }
                    else {
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
                else if(checkMove(piece,newX,newY) && piece.getState() instanceof EnglishKingState) {
                    piece.move(newX,newY);
                    tiles[startX][startY].setPiece(null);
                    tiles[newX][newY].setPiece(piece);
                }
            }
        });

        piece.setOnMouseClicked(e -> {
            List<Square> availibleMoves = piece.getAvailableMoves(tiles);
            Square temp;
            for(int i=0;i< availibleMoves.size();i++)
            {
                temp=availibleMoves.get(i);
                temp.setStroke(Color.BLUE);
                temp.setStrokeWidth(5);
            }
        });

        piece.setOnMouseExited(e -> {
            List<Square> availibleMoves = piece.getAvailableMoves(tiles);
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
