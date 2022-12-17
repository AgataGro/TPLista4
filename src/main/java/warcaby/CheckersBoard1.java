package warcaby;

public class CheckersBoard1 implements Board{
    Square[][] tiles;
    public CheckersBoard1(){
        this.resetBoard();
    }
    public Square getTile(int x, int y){
        return tiles[x][y];
    }

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
