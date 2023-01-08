package warcaby;

import java.util.ArrayList;
import java.util.List;

public class PolishKingState implements State{
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
                    if(x+k>=0 && x+k<=9 && y+l<=9 && y+l>=0) {
                        if (board[x + k][y + l].hasPiece() && killed == 0) {
                            if (x + 2 * k >= 0 && x + 2 * k <= 9 && y + 2 * l <= 9 && y + 2 * l >= 0) {
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

    public List<List<Square>> moveSequence(Piece piece, Square[][] board, List<Square> steps, List<Square> jumped) {
        List<Square> current;
        List<List<Square>> result = new ArrayList<>(), endResult = new ArrayList<>();
        current = availibleMoves(piece, board);
        List<Square> list;
        Square killed=null;
        board[piece.getOldX()/70][piece.getOldY()/70].setPiece(null);
        int x, y;
        if(current.size()>0){
            for (Square square : current) {
                list = new ArrayList<>(steps);
                int xdif = (int) (square.getX()/70-piece.getOldX()/70);
                int ydif = (int) (square.getY()/70-piece.getOldY()/70);
                if(xdif<0)x=-1;
                else x=1;
                if(ydif<0)y=-1;
                else y=1;
                while(xdif!=0){
                    if(board[(int) (square.getX()/70-x)][(int) (square.getY()/70-y)].hasPiece()){
                        if(board[(int) (square.getX()/70-x)][(int) (square.getY()/70-y)].getPiece().getColor()!=piece.getColor()){
                            killed=board[(int) (square.getX()/70-x)][(int) (square.getY()/70-y)];
                            break;
                        }
                    }
                    xdif=xdif-x;
                    ydif=ydif-y;
                    if(x<0)x--;
                    else x++;
                    if(y<0)y--;
                    else y++;
                }
                if (killed!=null) {
                    list.add(square);
                    Piece p = new Piece((int) square.getX()+35,(int) square.getY()+35,30, piece.getColor(), new PolishKingState());
                    List<Square> j = new ArrayList<>(jumped);
                    if (!j.contains(killed)) {
                        j.add(killed);
                        System.out.println(list);
                        result.addAll(moveSequence(p, board, list, j));
                    }
                } else {
                    if (steps.isEmpty()) {
                        list.add(square);
                        result.add(list);
                    }
                    else result.add(list);
                }
            }
        }
        else result.add(steps);
        for (List<Square> squares : result) {
            if (!endResult.contains(squares)) {
                {
                    endResult.add(squares);
                }
            }
        }
        result.clear();
        for (List<Square> squares : endResult) {
            if(result.isEmpty())result.add(squares);
            else if(result.get(0).size()<squares.size()){
                result.clear();
                result.add(squares);
            }
            else if(result.get(0).size()==squares.size()){
                result.add(squares);
            }
        }
        return result;
    }

    @Override
    public State changeState() {
        return this;
    }
}
