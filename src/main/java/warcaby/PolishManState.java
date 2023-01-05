package warcaby;

import java.util.ArrayList;
import java.util.List;

public class PolishManState implements State{
    @Override
    public List<Square> availibleMoves(Piece piece, Square[][] board) {
        List<Square> result = new ArrayList<>();
        int x = piece.getOldX()/70;
        int y = piece.getOldY()/70;
        int moveDirection = piece.getMoveDirection();
        for(int i=-1;i<=1;i=i+2)
        {
            for(int j=-1;j<=1;j=j+2){

                if(x+i>=0 && x+i<=9 && y+j<=9 && y+j>=0) {
                    if (board[x + i][y + j].hasPiece()) {
                        if (x + 2 * i >= 0 && x + 2 * i <= 9 && y + 2 * j <= 9 && y + 2 * j >= 0) {
                            if ((board[x + i][y + j].getPiece().getColor() != piece.getColor() && !board[x + 2 * i][y + 2 * j].hasPiece())) {
                                result.add(board[x + 2 * i][y + 2 * j]);
                            }
                        }
                    } else if(j==moveDirection)result.add(board[x + i][y + j]);
                }
            }
        }
        return result;
    }
}
