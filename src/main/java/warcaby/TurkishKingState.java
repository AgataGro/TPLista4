package warcaby;

import java.util.ArrayList;
import java.util.List;

public class TurkishKingState implements State{
    @Override
    public List<Square> availibleMoves(Piece piece, Square[][] board) {
        List<Square> result = new ArrayList<>();
        int x = piece.getOldX()/70;
        int y = piece.getOldY()/70;
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
        for(int i=-1;i<=1;i=i+2)
        {
            if(y+i>=0 && y+i<=7) {
                if (board[x][y + i].hasPiece()) {
                    if (y + 2 * i >= 0 && y + 2 * i <= 7) {
                        if ((board[x][y + i].getPiece().getColor() != piece.getColor() && !board[x][y + 2 * i].hasPiece())) {
                            result.add(board[x][y + 2 * i]);
                        }
                    }
                } else result.add(board[x][y + i]);
            }
        }
        return result;
    }
}
