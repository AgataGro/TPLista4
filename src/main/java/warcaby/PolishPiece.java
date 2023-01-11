package warcaby;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class PolishPiece extends Piece{
    
    PolishPiece(int x, int y, int r, Color color, State state) {
        super(x,y,r);
        setFill(color);
        oldX = x-35;
        oldY = y-35;
        if(color == Color.WHITE)
            moveDirection = -1;
        else
            moveDirection = 1;

        this.state = state;
    }
    @Override
    public void move(int newX, int newY) {
        oldX = newX*70;
        oldY = newY*70;
        setCenterX(oldX+35);
        setCenterY(oldY+35);
        if((this.getColor()==Color.WHITE && newY==0) || (this.getColor()==Color.BLACK && newY==9)) {
            state = state.changeState();
            //Image im = new Image("https://image.pngaaa.com/500/1998500-middle.png",false);
            //setFill(new ImagePattern(im));
            setStroke(Color.GOLD);
            setStrokeWidth(5);
        }
    }
}
