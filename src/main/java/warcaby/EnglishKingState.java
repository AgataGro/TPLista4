package warcaby;

import java.util.ArrayList;
import java.util.List;

public class EnglishKingState implements State{
    @Override
    public List<Square> availibleMoves(Piece piece, Square[][] board) {
        List<Square> result = new ArrayList<>();
        int x = piece.getOldX()/70;
        int y = piece.getOldY()/70;
        int killed;
        for(int i=-1;i<=1;i=i+2)
        {
            for(int j=-1;j<=1;j=j+2){
                killed=0;
                boolean doLoop = true;
                int k=i, l=j;
                while(doLoop){
                    if(x+k>=0 && x+k<=7 && y+l<=7 && y+l>=0) {
                        if (board[x + k][y + l].hasPiece() && killed == 0) {
                            if (x + 2 * k >= 0 && x + 2 * k <= 7 && y + 2 * l <= 7 && y + 2 * l >= 0) {
                                if ((board[x + k][y + l].getPiece().getColor() != piece.getColor() && !board[x + 2 * k][y + 2 * l].hasPiece())) {
                                    result.add(board[x + 2 * k][y + 2 * l]);
                                    killed++;
                                }
                            }
                        } else if (board[x + k][y + l].hasPiece() && killed != 0) doLoop = false;
                        else result.add(board[x + k][y + l]);
                    } else doLoop = false;
                    k=k+i;
                    l=l+j;
                }
            }
        }
        return result;
    }
}
