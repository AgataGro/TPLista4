package warcaby;

import java.util.ArrayList;
import java.util.List;

public class EnglishKingState implements State{
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
        //zmienna do sprawdzenia czy przeskoczyliśmy nad danym pionkiem
        boolean contained;
        if(current.size()>0){
            for (SingleMove square : current) {
                contained=false;
                list = new ArrayList<>(steps);
                if (square.getKilled()!=null) {
                    list.add(square);
                    Piece p = new PolishPiece((int) square.getEnd().getX()+35,(int) square.getEnd().getY()+35,30, piece.getColor(), new EnglishKingState());
                    for(SingleMove jump : steps){
                        if (square.getKilled() == jump.getKilled()) {
                            contained = true;
                            break;
                        }
                    }
                    if (!contained) {
                        result.addAll(moveSequence(p, board, list));
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
            else if(result.get(0).get(0).getKilled()==null&&squares.get(0).getKilled()!=null){
                result.clear();
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
        switch (direction){
            case UpLeft -> {
                if(x-1>=0&&y-1>=0){
                    Piece p = tiles[x-1][y-1].getPiece();
                    if(p==null){
                        result.add(new SingleMove(tiles[x][y],tiles[x-1][y-1],null));
                    }
                    else if(piece.getColor()!=p.getColor()&&x-2>=0&&y-2>=0)
                    {
                        if(tiles[x-2][y-2].getPiece()==null)result.add(new SingleMove(tiles[x][y],tiles[x-2][y-2],tiles[x-1][y-1]));
                    }
                }
            }
            case UpRight -> {
                if(x+1<=7&&y-1>=0){
                    Piece p = tiles[x+1][y-1].getPiece();
                    if(p==null){
                        result.add(new SingleMove(tiles[x][y],tiles[x+1][y-1],null));
                    }
                    else if(piece.getColor()!=p.getColor()&&x+2<=7&&y-2>=0){
                        if(tiles[x+2][y-2].getPiece()==null)result.add(new SingleMove(tiles[x][y],tiles[x+2][y-2],tiles[x+1][y-1]));
                    }
                }
            }
            case DownLeft -> {
                if(x-1>=0&&y+1<=7){
                    Piece p = tiles[x-1][y+1].getPiece();
                    if(p==null){
                        result.add(new SingleMove(tiles[x][y],tiles[x-1][y+1],null));
                    }
                    else if(piece.getColor()!=p.getColor()&&x-2>=0&&y+2<=7){
                        if(tiles[x-2][y+2].getPiece()==null)result.add(new SingleMove(tiles[x][y],tiles[x-2][y+2],tiles[x-1][y+1]));
                    }
                }
            }
            case DownRight -> {
                if(x+1<=7&&y+1<=7){
                    Piece p = tiles[x+1][y+1].getPiece();
                    if(p==null){
                        result.add(new SingleMove(tiles[x][y],tiles[x+1][y+1],null));
                    }
                    else if(piece.getColor()!=p.getColor()&&x+2<=7&&y+2<=7){
                        if(tiles[x+2][y+2].getPiece()==null)result.add(new SingleMove(tiles[x][y],tiles[x+2][y+2],tiles[x+1][y+1]));
                    }
                }
            }
        }
        return result;
    }
}
