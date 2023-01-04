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
        if(x>0 && y + moveDirection<=9&&y + moveDirection>=0) {
            if (board[x - 1][y + moveDirection].hasPiece()) {
                if(x>1&& y + 2*moveDirection<=9&&y + 2*moveDirection>=0) {
                    if ((board[x - 1][y + moveDirection].getPiece().getColor() != piece.getColor() && !board[x - 2][y + 2 * moveDirection].hasPiece()))
                        result.add(board[x - 2][y + 2 * moveDirection]);
                }
            } else result.add(board[x - 1][y + moveDirection]);
        }
        if(x<9 && y + moveDirection<=9&&y + moveDirection>=0) {
            if (board[x + 1][y + moveDirection].hasPiece()) {
                if(x<8&& y + 2*moveDirection<=9&&y + 2*moveDirection>=0) {
                    if ((board[x + 1][y + moveDirection].getPiece().getColor() != piece.getColor() && !board[x + 2][y + 2 * moveDirection].hasPiece()))
                        result.add(board[x + 2][y + 2 * moveDirection]);
                }
            } else result.add(board[x + 1][y + moveDirection]);
        }
        return result;
    }
}
