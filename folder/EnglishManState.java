package warcaby;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import java.util.Objects;

public class EnglishManState implements State {
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
        List<SingleMove> current, list;
        List<List<SingleMove>> result = new ArrayList<>(), endResult = new ArrayList<>();
        current = availibleMoves(piece, board);
        board[piece.getOldX()/70][piece.getOldY()/70].setPiece(null);
        //zmienna do sprawdzenia czy przeskoczyliśmy nad danym pionkiem
        boolean contained;
        if(current.size()>0){
            for (SingleMove square : current) {
                contained=false;
                list = new ArrayList<>(steps);
                if (square.getKilled()!=null) {
                    list.add(square);
                    Piece p = new EnglishPiece((int) square.getEnd().getX()+35,(int) square.getEnd().getY()+35,30, piece.getColor(), new EnglishManState());
                    for(SingleMove jump : steps){
                        if (square.getKilled() == jump.getKilled()) {
                            contained = true;
                            break;
                        }
                    }
                    if (!contained) {
                        if(p.getOldY()/70==7&&p.getColor()==Color.BLACK){
                            result.add(list);
                        }
                        else if(p.getOldY()/70==0&&p.getColor()==Color.WHITE){
                            result.add(list);
                        }
                        else result.addAll(moveSequence(p, board, list));
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
            else if(squares.get(0).getKilled()!=null){
                if(result.get(0).get(0).getKilled()==null){
                    result.clear();
                    result.add(squares);
                }
                else result.add(squares);
            }
            else if(result.get(0).get(0).getKilled()==null)result.add(squares);
        }
        endResult.clear();
        for (List<SingleMove> singleMoves : result) {
            if (endResult.isEmpty()) endResult.add(singleMoves);
            else {
                for (List<SingleMove> moves : endResult) {
                    contained=true;
                    if (singleMoves.size() > moves.size()) {
                        for (int j = 0; j < moves.size(); j++) {
                            if(!Objects.equals(singleMoves.get(j).getAsString(), moves.get(j).getAsString())){
                                contained=false;
                            }
                        }
                    }
                    if(contained){
                        endResult.remove(moves);
                        endResult.add(singleMoves);

                    }
                }

            }
        }
        return endResult;
    }

    @Override
    public State changeState() {
        return new EnglishKingState();
    }
    
    @Override
    public List<SingleMove> move(Piece piece, Square[][] tiles, Direction direction) {
        List<SingleMove> result=new ArrayList<>();
        int x = piece.getOldX()/70;
        int y = piece.getOldY()/70;
        int moveDirection = piece.getMoveDirection();
        switch (direction){
            case UpLeft : {
                if(x-1>=0&&y-1>=0){
                    if(moveDirection==-1) {
                        Piece p = tiles[x - 1][y - 1].getPiece();
                        if (p == null) {
                            result.add(new SingleMove(tiles[x][y], tiles[x - 1][y - 1], null));
                        } else if (piece.getColor() != p.getColor() && x - 2 >= 0 && y - 2 >= 0) {
                            if (tiles[x - 2][y - 2].getPiece() == null)
                                result.add(new SingleMove(tiles[x][y], tiles[x - 2][y - 2], tiles[x - 1][y - 1]));
                        }
                    }
                }
                break;
            }
            case UpRight : {
                if(x+1<=7&&y-1>=0){
                    if(moveDirection==-1) {
                        Piece p = tiles[x + 1][y - 1].getPiece();
                        if (p == null) {
                            result.add(new SingleMove(tiles[x][y], tiles[x + 1][y - 1], null));
                        } else if (piece.getColor() != p.getColor() && x + 2 <= 7 && y - 2 >= 0) {
                            if (tiles[x + 2][y - 2].getPiece() == null)
                                result.add(new SingleMove(tiles[x][y], tiles[x + 2][y - 2], tiles[x + 1][y - 1]));
                        }
                    }
                }
                break;
            }
            case DownLeft : {
                if(x-1>=0&&y+1<=7){
                    if(moveDirection==1) {
                        Piece p = tiles[x - 1][y + 1].getPiece();
                        if (p == null) {
                            result.add(new SingleMove(tiles[x][y], tiles[x - 1][y + 1], null));
                        } else if (piece.getColor() != p.getColor() && x - 2 >= 0 && y + 2 <= 7) {
                            if (tiles[x - 2][y + 2].getPiece() == null)
                                result.add(new SingleMove(tiles[x][y], tiles[x - 2][y + 2], tiles[x - 1][y + 1]));
                        }
                    }
                }
                break;
            }
            case DownRight : {
                if(x+1<=7&&y+1<=7){
                    if(moveDirection==1) {
                        Piece p = tiles[x + 1][y + 1].getPiece();
                        if (p == null) {
                            result.add(new SingleMove(tiles[x][y], tiles[x + 1][y + 1], null));
                        } else if (piece.getColor() != p.getColor() && x + 2 <= 7 && y + 2 <= 7) {
                            if (tiles[x + 2][y + 2].getPiece() == null)
                                result.add(new SingleMove(tiles[x][y], tiles[x + 2][y + 2], tiles[x + 1][y + 1]));
                        }
                    }
                }
                break;
            }
            default:
                break;
        }
        return result;
    }
}