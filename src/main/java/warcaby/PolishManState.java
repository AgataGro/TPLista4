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
                    } else if(j==moveDirection){
                        result.add(board[x + i][y + j]);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<List<Square>> moveSequence(Piece piece, Square[][] board, List<Square> steps, List<Square> jumped) {
        List<Square> current;
        List<List<Square>> result = new ArrayList<>(), endResult = new ArrayList<>();
        current = availibleMoves(piece, board);
        List<Square> list;
        board[piece.getOldX()/70][piece.getOldY()/70].setPiece(null);
        if(current.size()>0){
            for (Square square : current) {
                list = new ArrayList<>(steps);
                int xdif = (int) (square.getX()/70-piece.getOldX()/70);
                int ydif = (int) (square.getY()/70-piece.getOldY()/70);
                if (Math.abs(xdif) > 1) {
                    list.add(square);
                    Piece p = new Piece((int) square.getX()+35,(int) square.getY()+35,30, piece.getColor(), new PolishManState());
                    List<Square> j = new ArrayList<>(jumped);
                    xdif=xdif/2;
                    ydif=ydif/2;
                    if (!j.contains(board[piece.getOldX()/70 + xdif][piece.getOldY()/70 + ydif])) {
                        j.add(board[piece.getOldX()/70 + xdif][piece.getOldY()/70 + ydif]);
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
        return new PolishKingState();
    }
}
