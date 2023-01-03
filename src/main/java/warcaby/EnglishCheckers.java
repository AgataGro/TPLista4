
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

    @Override
    public void start(Stage stage) {
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
                        Piece piece = createPiece(x+35,y+35,30,Color.BLACK);
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
                        Piece piece = createPiece(x+35,y+35,30,Color.WHITE);
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

    private MoveDetails checkMove(Piece piece, int x, int y) {
        if((x+y)%2==0 || tiles[x][y].hasPiece()) {
            return new MoveDetails(MoveType.STAY, null);
        }

        int startX = getScenePlace(piece.getOldX());
        int startY = getScenePlace(piece.getOldY());
        if(Math.abs(x-startX)==1 && y-startY==piece.getMoveDirection()) {
            return new MoveDetails(MoveType.STEP, null);
        }
        else if(Math.abs(x-startX)==2 && y-startY==piece.getMoveDirection()*2) {
            int middleX = startX + (x - startX)/2;
            int middleY = startY + (y - startY)/2;
            if(tiles[middleX][middleY].hasPiece() && tiles[middleX][middleY].getPiece().getColor()!=piece.getColor()) {
                return new MoveDetails(MoveType.KILL, tiles[middleX][middleY].getPiece());
            }
        }
        return new MoveDetails(MoveType.STAY, null);
    }

    private Piece createPiece(int x, int y, int r, Color color) {
        Piece piece = new Piece(x,y,r,color);

        /*
         * metoda aktywowana, kiedy puszczamy pionek
         * ona odpowiada za umieszczenie pionka w odpowiednim miejscu
         * zgodnie z wykonanym przez gracza ruchem
         */
        piece.setOnMouseReleased(e -> {
            int newX = getScenePlace(e.getSceneX());
            int newY = getScenePlace(e.getSceneY());

            MoveDetails details;

            if(newX<0 || newY<0 || newX>7 || newY>7)
                details = new MoveDetails(MoveType.STAY, null);
            else {
                details = checkMove(piece, newX, newY);
            }

            int startX = getScenePlace(piece.getOldX());
            int startY = getScenePlace(piece.getOldY());

            switch (details.getMoveType()) {
                case STAY:
                    piece.notMove();
                    break;
                case STEP:
                    piece.move(newX,newY);
                    tiles[startX][startY].setPiece(null);
                    tiles[newX][newY].setPiece(piece);
                    break;
                case KILL:
                    piece.move(newX,newY);
                    tiles[startX][startY].setPiece(null);
                    tiles[newX][newY].setPiece(piece);
                    Piece killedPiece = details.getPiece();
                    tiles[getScenePlace(killedPiece.getOldX())][getScenePlace(killedPiece.getOldY())].setPiece(null);
                    piecesGroup.getChildren().remove(killedPiece);
                    break;
            }
        });

        /*
         * to metoda do dopracowania, po kliknięciu na pionek mają podświatlać się
         * możliwe drogi zbicia
         * 
         * ewentualnie zrobić coś innego, bo to nie działa idealnie
         */
        piece.setOnMouseClicked(e -> {
            int x0 = getScenePlace(piece.getOldX());
            int y0 = getScenePlace(piece.getOldY());
            tiles[x0][y0].setStroke(Color.GREEN);
            tiles[x0][y0].setStrokeWidth(5);
        });

        /*
         * to metoda do dopracowania; kiedy myszka zjedzie z pionka pola, które
         * wcześniej się podświetliły na klliknięcie, mają wrócić do niepodświetlonego stanu
         */
        piece.setOnMouseExited(e -> {
            int x0 = getScenePlace(piece.getOldX());
            int y0 = getScenePlace(piece.getOldY());
            tiles[x0][y0].setStroke((null));
        });

        return piece;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
