package warcaby;

import java.util.ArrayList;
import java.util.List;

public class TurkishManState implements State{
    @Override
    public List<Square> availibleMoves(Piece piece, Square[][] board) {
        List<Square> result = new ArrayList<>();
        int x = piece.getOldX()/70;
        int y = piece.getOldY()/70;
        int moveDirection = piece.getMoveDirection();
        for(int i=-1;i<=1;i=i+2)
        {
            if(x+i>=0 && x+i<=7) {
                if (board[x + i][y].hasPiece()) {
                    if (x + 2 * i >= 0 && x + 2 * i <= 7) {
                        if ((board[x + i][y].getPiece().getColor() != piece.getColor() && !board[x + 2 * i][y].hasPiece())) {
                            result.add(board[x + 2 * i][y]);
                        }
                    }
                } else result.add(board[x + i][y]);
            }
        }
        if(y+moveDirection>=0 && y+moveDirection<=7) {
            if (board[x][y+moveDirection].hasPiece()) {
                if (y + 2 * moveDirection >= 0 && y + 2 * moveDirection <= 7) {
                    if ((board[x][y=moveDirection].getPiece().getColor() != piece.getColor() && !board[x][y + 2 * moveDirection].hasPiece())) {
                        result.add(board[x][y+2*moveDirection]);
                    }
                }
            } else result.add(board[x][y+moveDirection]);
        }
        return result;
    }
}
