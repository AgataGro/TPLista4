package warcaby;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

import java.util.List;

public class PolishCheckers extends Application {

    private Group tilesGroup = new Group();
    private Group piecesGroup = new Group();
    private Square[][] tiles = new Square[10][10];
    
    // to pole jest wykorzystywane przy uruchamianiu aplikacji z poziomu klasy LaunchingCheckers
    static Stage classStage = new Stage();

    /*
     * tutaj tworzy się plansza do gry
     */
    @Override
    public void start(Stage stage) {
        classStage = stage;

        AnchorPane pane = new AnchorPane();
        Button button = new Button("Opponent's turn");
        button.setFont(Font.font("Times New Roman", 20));
        pane.getChildren().addAll(tilesGroup, piecesGroup, button);
        AnchorPane.setBottomAnchor(button, 0.0);

        int x = 0, y = 0;
        for(int i = 0; i<10; i++) {
            for(int j = 0; j<10; j++) {
                if((i+j)%2==0) {
                    Square square = new Square(x,y,70,70,Color.WHEAT);
                    tiles[j][i] = square;
                    tilesGroup.getChildren().add(square);
                    x += 70;
                }
                else {
                    if(i<4) {
                        Piece piece = createPiece(x+35, y+35, 30, Color.BLACK);
                        Square square = new Square(x,y,70,70,Color.BROWN);
                        square.setPiece(piece);
                        tiles[j][i] = square;
                        tilesGroup.getChildren().add(square);
                        piecesGroup.getChildren().add(piece);
                    }
                    else if(i == 4 || i == 5) {
                        Square square = new Square(x,y,70,70,Color.BROWN);
                        tiles[j][i] = square;
                        tilesGroup.getChildren().add(square);
                    }
                    else if(i>5) {
                        Piece piece = createPiece(x+35, y+35, 30, Color.WHITE);
                        Square square = new Square(x,y,70,70,Color.BROWN);
                        square.setPiece(piece);
                        tiles[j][i] = square;
                        tilesGroup.getChildren().add(square);
                        piecesGroup.getChildren().add(piece);
                    }
                    x += 70;
                }
            }
            x = 0;
            y += 70;
        }

        Scene scene = new Scene(pane,700,736);
        stage.setTitle("Polish checkers");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * metoda, która wylicza, w którym kwadracie znajduje się podana liczba
     * @param x współrzędna punktu z planszy
     * @return numer kwadratu, w którym znajduje się podana liczba
     */
    private int getScenePlace(double x) {
        return (int)x/70;
    }

    /**
     * metoda sprawdzająca, jaki rodzaj ruchu wykonaliśmy
     * @param piece pionek, który ruszyliśmy
     * @param x współrzędna x lewego górnego rogu kwadratu, na którym upuściliśmy pionek
     * @param y współrzędna y lewego górnego rogu kwadratu, na którym upuściliśmy pionek
     * @return typ ruchu, który wykonaliśmy i pionek, który zbilliśmy albo null, jeżeli nie zbiliśmy żadnego
     */
    private MoveDetails checkMove(Piece piece, int x, int y) {
        if((x+y)%2==0 || tiles[x][y].hasPiece()) {
            return new MoveDetails(MoveType.STAY, null);
        }

        int startX = getScenePlace(piece.getOldX());
        int startY = getScenePlace(piece.getOldY());
        if(Math.abs(x-startX)==1 && y-startY==piece.getMoveDirection()) {
            return new MoveDetails(MoveType.STEP, null);
        }
        else if(Math.abs(x-startX)==2 && Math.abs(y-startY)==2) {
            int middleX = startX + (x - startX)/2;
            int middleY = startY + (y - startY)/2;
            if(tiles[middleX][middleY].hasPiece() && tiles[middleX][middleY].getPiece().getColor()!=piece.getColor()) {
                return new MoveDetails(MoveType.KILL, tiles[middleX][middleY].getPiece());
            }
        }
        return new MoveDetails(MoveType.STAY, null);
    }

    /**
     * metoda tworząca pionek i dodająca pewne funkcjonalności
     * @param x współrzędna x środka koła
     * @param y współrzędna x środka koła
     * @param r długość promienia koła
     * @param color kolor wypełnienia koła
     * @return utworzony pionek
     */
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

            if(newX<0 || newY<0 || newX>9 || newY>9)
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
         */
        piece.setOnMouseClicked(e -> {
            int x0 = getScenePlace(piece.getOldX());
            int y0 = getScenePlace(piece.getOldY());
            tiles[x0][y0].setStroke(Color.GREEN);
            tiles[x0][y0].setStrokeWidth(5);
            List<Square> availibleMoves = piece.getAvailibleMoves(tiles);
            Square temp;
            for(int i=0;i< availibleMoves.size();i++)
            {
                temp=availibleMoves.get(i);
                temp.setStroke(Color.BLUE);
                temp.setStrokeWidth(5);
            }
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
