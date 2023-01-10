package warcaby;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class TurkishKingState implements State{
    @Override
    public List<SingleMove> availibleMoves(Piece piece, Square[][] board) {
        List<SingleMove> result= new ArrayList<>();

        for (Direction direction : Direction.values()) {
            result.addAll(move(piece, board, direction));
        }
        return result;
    }

    @Override
    public List<List<SingleMove>> moveSequence(Piece piece, Square[][] board, List<SingleMove> steps) {
        //Inicjalizacja zmiennych
        List<SingleMove> current, list;
        List<List<SingleMove>> result = new ArrayList<>(), endResult = new ArrayList<>();
        current = availibleMoves(piece, board);
        //usunięcie pionka z pola startowego
        board[piece.getOldX()/70][piece.getOldY()/70].setPiece(null);
        //zmienne do sprawdzenia czy nie robimy zwory o 180 stopni
        Direction dir1, dir2;
        int x, y;
        boolean directionOK=true;
        if(current.size()>0){
            for (SingleMove square : current) {
                x = (int) (square.getEnd().getX()/70 - square.getStart().getX()/70);
                y = (int) (square.getEnd().getY()/70 - square.getStart().getY()/70);
                if(x == 0){
                    if(y>0)dir1=Direction.Down;
                    else dir1=Direction.Up;
                }
                else{
                    if(x>0)dir1=Direction.Right;
                    else dir1=Direction.Left;
                }
                list = new ArrayList<>(steps);
                if (square.getKilled()!=null) {
                    if(!steps.isEmpty()){
                        x = (int) (steps.get(steps.size()-1).getEnd().getX()/70 - steps.get(steps.size()-1).getStart().getX()/70);
                        y = (int) (steps.get(steps.size()-1).getEnd().getY()/70 - steps.get(steps.size()-1).getStart().getY()/70);

                        if(x == 0){
                            if(y>0)dir2=Direction.Down;
                            else dir2=Direction.Up;
                        }
                        else{
                            if(x>0)dir2=Direction.Right;
                            else dir2=Direction.Left;
                        }
                        if((dir1==Direction.Up&&dir2==Direction.Down)||
                                (dir1==Direction.Down&&dir2==Direction.Up)||
                                (dir1==Direction.Right&&dir2==Direction.Left)||
                                (dir1==Direction.Left&&dir2==Direction.Right)){
                            directionOK=false;
                        }
                        if(directionOK){
                        list.add(square);
                        Piece p = new PolishPiece((int) square.getEnd().getX()+35,(int) square.getEnd().getY()+35,30, piece.getColor(), new TurkishManState());
                        board[(int) (square.getKilled().getX()/70)][(int) (square.getKilled().getY()/70)].setPiece(null);

                        result.addAll(moveSequence(p, board, list));
                        }

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
        for (List<SingleMove> squares : result) {
            if (!endResult.contains(squares)) {
                {
                    endResult.add(squares);
                }
            }
        }
        result.clear();
        for (List<SingleMove> squares : endResult) {
            if(result.isEmpty())result.add(squares);
            else if(result.get(0).size()<squares.size()){
                result.clear();
                result.add(squares);
            }
            else if(result.get(0).size()==squares.size()){
                if(result.get(0).get(0).getKilled()==null&&squares.get(0).getKilled()!=null){
                    result.clear();
                }
                result.add(squares);
            }
        }
        return result;
    }

    @Override
    public State changeState() {
        return this;
    }

    @Override
    public List<SingleMove> move(Piece piece, Square[][] tiles, Direction direction) {
        List<SingleMove> result=new ArrayList<>();
        int x = piece.getOldX()/70;
        int y = piece.getOldY()/70;
        int i=0, j=0;
        Square killed=null;
        boolean end=false;
        switch (direction){
            case Up -> {
                j=-1;
            }
            case Down -> {
                j=1;
            }
            case Left -> {
                i=-1;
            }
            case Right -> {
                i=1;
            }
        }
        if(i!=0||j!=0){
            while(true){
                if(x+i<=7&&x+i>=0&&y+j<=7&&y+j>=0){
                    Piece p = tiles[x+i][y+j].getPiece();
                    if(p==null){
                        result.add(new SingleMove(tiles[x][y],tiles[x+i][y+j],killed));
                    }
                    else if(piece.getColor()!=p.getColor()&&killed==null)killed=tiles[x+i][y+j];
                    else break;
                }
                else break;
                if(i>0)i++;
                else if(i<0)i--;
                if(j>0)j++;
                else if(j<0)j--;
            }
        }
        return result;
    }
}
