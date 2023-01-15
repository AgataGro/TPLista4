package warcaby;

import java.util.ArrayList;
import java.util.List;

public class Mediator {
    List<Piece> white=new ArrayList<>();
    List<Piece> black=new ArrayList<>();
    boolean isWhiteTurn;
    boolean hasKillOption;
    Piece movedPiece =null;
    List<Square> killed=new ArrayList<>();
    Mediator(boolean isWhiteTurn){
        this.isWhiteTurn=isWhiteTurn;
    }
    public void changeTurn(Square[][] board){
        this.isWhiteTurn=!isWhiteTurn;
        movedPiece=null;
        killed.clear();
        hasKillOption(board);
    }
    public void addKilled(Square square){
        killed.add(square);
    }
    public boolean hasKilled(){
        return !killed.isEmpty();
    }

    public void addBlack(Piece piece){
        black.add(piece);
    }
    public void addWhite(Piece piece){
        white.add(piece);
    }

    public Square getKilled(Square start, Square end, Square[][] board){
        List<SingleMove> temp = new ArrayList<>();
        Square square=null;
        if (isWhiteTurn && white.contains(movedPiece)) {
            temp = movedPiece.getSingleMoves(board);
        } else if (!isWhiteTurn && black.contains(movedPiece)) {
            temp = movedPiece.getSingleMoves(board);
        }
        if (!temp.isEmpty()) {
            for (SingleMove singleMove : temp) {
                if (singleMove.getStart() == start&&singleMove.getEnd()==end) square=singleMove.getKilled();
            }
        }

        return square;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public void setMovedPiece(Piece movedPiece) {
        this.movedPiece = movedPiece;
    }

    public int getBlackNum(){return black.size();}
    public int getWhiteNum(){return white.size();}
    public void killPiece(Piece piece){
        if(isWhiteTurn)black.remove(piece);
        else white.remove(piece);
    }
    public boolean continueTurn(Piece piece, Square[][] board){
        if(movedPiece==null){
            System.out.println("no moved piece");
            return true;
        }
        else if(!getAvailibleTiles(piece, board).isEmpty()){
            System.out.println("has moves");
            return true;
        }
        else{
            System.out.println("has no moves");
            return false;
        }

    }
    public void hasKillOption(Square[][] currentBoard){
        List<SingleMove> temp;
        boolean kill=false;
        if(isWhiteTurn){
            for (Piece piece : white) {
                temp = piece.getSingleMoves(currentBoard);
                if (!temp.isEmpty()) {
                    for (SingleMove singleMove : temp) {
                        if (singleMove.getKilled() != null) {
                            kill = true;
                            break;
                        }
                    }
                }
            }
        }
        else {
            for (Piece piece : black) {
                temp = piece.getSingleMoves(currentBoard);
                if (!temp.isEmpty()) {
                    for (SingleMove singleMove : temp) {
                        if (singleMove.getKilled() != null) {
                            kill = true;
                            break;
                        }
                    }
                }
            }
        }
        hasKillOption=kill;
    }
    public List<Square> getAvailibleTiles(Piece piece, Square[][] board){
        List<Square> result=new ArrayList<>();
        List<SingleMove> temp = new ArrayList<>();
        if(movedPiece==null) {
            if (isWhiteTurn && white.contains(piece)) {
                temp = piece.getSingleMoves(board);
            } else if (!isWhiteTurn && black.contains(piece)) {
                temp = piece.getSingleMoves(board);
            }
            if (!temp.isEmpty()) {
                for (SingleMove singleMove : temp) {
                    if (singleMove.getKilled() != null) result.add(singleMove.getEnd());
                    else if (!hasKillOption) result.add(singleMove.getEnd());
                }
            }
        }
        else if(movedPiece==piece){
            if (isWhiteTurn && white.contains(piece)) {
                temp = piece.getSingleMoves(board);
            } else if (!isWhiteTurn && black.contains(piece)) {
                temp = piece.getSingleMoves(board);
            }
            if (!temp.isEmpty()) {
                for (SingleMove singleMove : temp) {
                    System.out.println(singleMove.getAsString());
                    if (singleMove.getKilled() != null&& !killed.contains(singleMove.getKilled())){
                        result.add(singleMove.getEnd());
                        System.out.println(singleMove.getAsString());
                    }
                }
            }
        }
        return result;
    }


}
