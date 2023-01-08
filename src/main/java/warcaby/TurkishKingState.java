package warcaby;

import javafx.scene.paint.Color;

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

    @Override
    public List<List<Square>> moveSequence(Piece piece, Square[][] board, List<Square> steps, List<Square> jumped) {
        List<Square> current;
        List<List<Square>> result = new ArrayList<>(), endResult = new ArrayList<>();
        current = availibleMoves(piece, board);
        List<Square> list;
        board[piece.getOldX()/70][piece.getOldY()/70].setPiece(null);
        Square killed=null;
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
                while(xdif!=0||ydif!=0){
                    if(board[(int) (square.getX()/70-x)][(int) (square.getY()/70-y)].hasPiece()){
                        if(board[(int) (square.getX()/70-x)][(int) (square.getY()/70-y)].getPiece().getColor()!=piece.getColor()){
                            killed=board[(int) (square.getX()/70-x)][(int) (square.getY()/70-y)];
                            break;
                        }
                    }
                    if(xdif!=0)xdif=xdif-x;
                    if(ydif!=0)ydif=ydif-y;
                    if(x<0)x--;
                    else x++;
                    if(y<0)y--;
                    else y++;
                }
                if (killed!=null) {
                    list.add(square);
                    Piece p = new Piece((int) square.getX()+35,(int) square.getY()+35,30, piece.getColor(), new TurkishManState());
                    List<Square> j = new ArrayList<>(jumped);
                    xdif=xdif/2;
                    ydif=ydif/2;
                    //sprawdzenie czy nie przeskakujemy nad ostanio zbitym pionkiem
                    if (j.lastIndexOf(board[piece.getOldX()/70 + xdif][piece.getOldY()/70 + ydif])!=j.size()-1) {
                        j.add(board[piece.getOldX()/70 + xdif][piece.getOldY()/70 + ydif]);
                        board[piece.getOldX()/70 + xdif][piece.getOldY()/70 + ydif].setPiece(null);
                        //zakończenie ruchu jeśli pionek przechodzi na wiersz najbliżej przeciwnika
                        if((square.getY()/70==7&&piece.getColor()== Color.BLACK)||(square.getY()/70==0&&piece.getColor()== Color.WHITE)){
                            result.add(list);
                        }
                        else result.addAll(moveSequence(p, board, list, j));
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
                endResult.add(squares);
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
