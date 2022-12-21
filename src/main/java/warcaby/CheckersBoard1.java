package warcaby;

/**
 * Plansza do gry w warcaby międzynarodowe(polskie)
 */
public class CheckersBoard1 implements Board{
    Square[][] tiles;

    /**
     * Konstruktor
     */
    public CheckersBoard1(){
        this.resetBoard();
    }

    /**
     * @see Board
     * @param x współrzędna x pola
     * @param y współrzędna y pola
     * @return pole
     */
    public Square getTile(int x, int y){
        return tiles[x][y];
    }

    /**
     * @see Board
     * Plansza ma wymiary 10x10, w grzy wykorzystujemy tylko czarne pola zaczynając od lewego dolnego rogu
     */
    public void resetBoard() {
        for(int i =0; i<10;i++){
            for(int j=0; j<10; j++){
                if(i+j%2==0){
                    if(i<=3){
                        tiles[i][j]=new Square(j, i, new Piece1(true), true);
                    }
                    else if(i>=6){
                        tiles[i][j]=new Square(j, i, new Piece1(false), true);
                    }
                    else{
                        tiles[i][j]=new Square(j, i, null, true);
                    }
                }
                else{
                    tiles[i][j]=new Square(j, i, null, false);
                }
            }
        }
    }

    public int getTileNum() {
        return 100;
    }
}
