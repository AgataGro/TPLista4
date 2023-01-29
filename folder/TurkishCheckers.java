package warcaby;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.util.List;
import java.util.ArrayList;

public class TurkishCheckers extends Application {

    Square[][] tiles = new Square[8][8];
    Group tilesGroup = new Group();
    Group piecesGroup = new Group();
    Mediator mediator;
    List<Square> killed=new ArrayList<>();

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
                    Piece piece = createPiece(x+35,y+35,30,Color.BLACK,new TurkishManState());
                    square.setPiece(piece);
                    piecesGroup.getChildren().add(piece);
                }
                else if(i == 5 || i == 6) {
                    Piece piece = createPiece(x+35,y+35,30,Color.WHITE,new TurkishManState());
                    square.setPiece(piece);
                    piecesGroup.getChildren().add(piece);
                }
                x += 70;
            }
            x = 0;
            y += 70;
        }

        mediator=new Mediator(true);
        List<Piece> pieceList = getPieces(true);
        for (Piece piece : pieceList) {
            mediator.addWhite(piece);
        }
        pieceList=getPieces(false);
        for (Piece piece : pieceList) {
            mediator.addBlack(piece);
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
     * @param isWhite true if white pieces are requested, false otherwise
     * @return list of white or black pieces
     */
    private List<Piece> getPieces(boolean isWhite){
        List<Piece> temp=new ArrayList<>();
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++){
                if(tiles[j][i].hasPiece()){
                    if(tiles[j][i].getPiece().getColor()==Color.WHITE&&isWhite)temp.add(tiles[j][i].getPiece());
                    else if(tiles[j][i].getPiece().getColor()==Color.BLACK&&!isWhite)temp.add(tiles[j][i].getPiece());
                }
            }
        }
        return temp;
    }

    /**
     * @param piece a piece whose move's legality we want to check
     * @param x a coordinate of the left top corner of a square where we want to place the piece
     * @param y a coordinate of the left top corner of a square where we want to place the piece
     * @return true if we can move or false in opposite case
     */
    private boolean checkMove(Piece piece, int x, int y) {
        List<Square> availableMoves = mediator.getAvailibleTiles(piece,tiles);
        return availableMoves.contains(tiles[x][y]);
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
        TurkishPiece piece = new TurkishPiece(x,y,r,color,state);

        piece.setOnMouseReleased(e -> {
            int newX = getScenePlace(e.getSceneX());
            int newY = getScenePlace(e.getSceneY());

            if(newX<0 || newY<0 || newX>7 || newY>7 || !checkMove(piece, newX, newY))
                piece.notMove();
            else {
                int startX = getScenePlace(piece.getOldX());
                int startY = getScenePlace(piece.getOldY());
                if(checkMove(piece,newX,newY)){
                    if(mediator.getMovedPiece()==null) {
                        mediator.setMovedPiece(piece);
                        if(mediator.getKilled(tiles[startX][startY], tiles[newX][newY], tiles)!=null) {
                            killed.add(mediator.getKilled(tiles[startX][startY], tiles[newX][newY], tiles));
                            mediator.addKilled(mediator.getKilled(tiles[startX][startY], tiles[newX][newY], tiles));
                        }
                        piece.move(newX,newY);
                        tiles[startX][startY].setPiece(null);
                        tiles[newX][newY].setPiece(piece);
                        if(!mediator.hasKilled()){
                            mediator.changeTurn(tiles);
                            System.out.println("endTurn, not killed");
                        }
                        else {
                            if (!mediator.continueTurn(piece,tiles)) {
                                System.out.println("endTurn, not moves");
                                Piece killedPiece;
                                for (Square square : killed) {
                                    if (square != null) {
                                        killedPiece = tiles[getScenePlace(square.getX())][getScenePlace(square.getY())].getPiece();
                                        mediator.killPiece(killedPiece);
                                        tiles[getScenePlace(square.getX())][getScenePlace(square.getY())].setPiece(null);
                                        piecesGroup.getChildren().remove(killedPiece);
                                    }
                                }
                                mediator.changeTurn(tiles);
                                killed.clear();
                            }
                        }
                    }
                    else if(mediator.getMovedPiece()==piece){
                        if(mediator.getKilled(tiles[startX][startY], tiles[newX][newY], tiles)!=null) {
                            killed.add(mediator.getKilled(tiles[startX][startY], tiles[newX][newY], tiles));
                            mediator.addKilled(mediator.getKilled(tiles[startX][startY], tiles[newX][newY], tiles));
                        }                        piece.move(newX,newY);
                        tiles[startX][startY].setPiece(null);
                        tiles[newX][newY].setPiece(piece);
                        if(!mediator.hasKilled()){
                            mediator.changeTurn(tiles);
                            System.out.println("endTurn, not killed");
                        }
                        else {
                            if (!mediator.continueTurn(piece,tiles)) {
                                System.out.println("endTurn, not moves");
                                Piece killedPiece;
                                for (Square square : killed) {
                                    if (square != null) {
                                        killedPiece = tiles[getScenePlace(square.getX())][getScenePlace(square.getY())].getPiece();
                                        mediator.killPiece(killedPiece);
                                        tiles[getScenePlace(square.getX())][getScenePlace(square.getY())].setPiece(null);
                                        piecesGroup.getChildren().remove(killedPiece);
                                    }
                                }
                                mediator.changeTurn(tiles);
                                killed.clear();
                            }
                        }
                    }
                }
            }
            if(mediator.getWhiteNum()==0) {
                System.out.println("Black wins!");
                BlackWin win = new BlackWin();
                win.start(BlackWin.classStage);
                classStage.hide();
                return;
            }
            else if(mediator.getBlackNum()==0) {
                System.out.println("White wins!");
                WhiteWin win = new WhiteWin();
                win.start(WhiteWin.classStage);
                classStage.hide();
                return;
            }
        });

        piece.setOnMouseClicked(e -> {
            List<Square> availibleMoves = mediator.getAvailibleTiles(piece,tiles);
            Square temp;
            for(int i=0;i< availibleMoves.size();i++)
            {
                temp=availibleMoves.get(i);
                temp.setStroke(Color.BLUE);
                temp.setStrokeWidth(5);
            }
        });

        piece.setOnMouseExited(e -> {
            List<Square> availibleMoves = mediator.getAvailibleTiles(piece,tiles);
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