package warcaby;

import java.util.ArrayList;
import java.util.List;

public class EnglishManState implements State{
    @Override
    public List<Square> availibleMoves(Piece piece, Square[][] board) {
        List<Square> result = new ArrayList<>();
        int x = piece.getOldX()/70;
        int y = piece.getOldY()/70;
        int moveDirection = piece.getMoveDirection();
        for(int i=-1;i<=1;i=i+2)
        {
            if(x+i>=0 && x+i<=7 && y+moveDirection<=7 && y+moveDirection>=0) {
                if (board[x + i][y + moveDirection].hasPiece()) {
                    if (x + 2 * i >= 0 && x + 2 * i <= 7 && y + 2 * moveDirection <= 7 && y + 2 * moveDirection >= 0) {
                        if ((board[x + i][y + moveDirection].getPiece().getColor() != piece.getColor() && !board[x + 2 * i][y + 2 * moveDirection].hasPiece())) {
                            result.add(board[x + 2 * i][y + 2 * moveDirection]);
                        }
                    }
                } else result.add(board[x + i][y + moveDirection]);
            }
        }

        return result;
    }
}
