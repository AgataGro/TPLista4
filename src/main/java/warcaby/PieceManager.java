package warcaby;

import java.util.ArrayList;
import java.util.List;

public class PieceManager {
    List<Piece> white=new ArrayList<>();
    List<Piece> black=new ArrayList<>();
    boolean isWhiteTurn;
    boolean hasKillOption;
    Piece movedPiece =null;
    List<Square> killed=new ArrayList<>();
    List<List<SingleMove>> availibleMoves=new ArrayList<>();
    
    /**
     * @param isWhiteTurn is true if white piece should move, false otherwise
     */
    PieceManager(boolean isWhiteTurn){
        this.isWhiteTurn=isWhiteTurn;
    }
    
    /**
     * @param board array with squares to check
     */
    public void changeTurn(Square[][] board){
        this.isWhiteTurn=!isWhiteTurn;
        movedPiece=null;
        killed.clear();
        hasKillOption(board);
        calculateAvailibleMoves(board);
    }
    
    /**
     * @param square square where we killed a piece
     */
    public void addKilled(Square square){
        killed.add(square);
    }
    
    /**
     * @return true if list killed is not empty, false otherwise
     */
    public boolean hasKilled(){
        return !killed.isEmpty();
    }
    public List<SingleMove> pickMove(int startX, int startY, int endX, int endY){
        List<SingleMove> result=new ArrayList<>();
        Square start, end;
        for (List<SingleMove> availibleMove : availibleMoves) {
            if (!availibleMove.isEmpty()) {
                start = availibleMove.get(0).getStart();
                end = availibleMove.get(0).getEnd();
                if (start.getX() / 70 == startX && start.getY() / 70 == startY && end.getX() / 70 == endX && end.getY() / 70 == endY)
                    result = availibleMove;
            }
        }
        return result;
    }
    public void calculateAvailibleMoves(Square[][] board){
        List<List<SingleMove>> temp = new ArrayList<>();
        if (isWhiteTurn) {
            for (Piece piece : white) {
                temp.addAll(piece.moveSequences(board));
            }
        } else{
            for (Piece piece : black) {
                temp.addAll(piece.moveSequences(board));
            }
        }

        availibleMoves.clear();
        for (List<SingleMove> singleMoves : temp) {
            if(!singleMoves.isEmpty()) {
                System.out.println(singleMoves.get(0).getAsString());
                if (hasKillOption) {
                    if (singleMoves.get(0).getKilled() != null) {
                        System.out.println(singleMoves.get(0).getAsString());
                        if (availibleMoves.isEmpty()) availibleMoves.add(singleMoves);
                        else if (singleMoves.size() > availibleMoves.get(0).size()) {
                            availibleMoves.clear();
                            availibleMoves.add(singleMoves);
                        } else if (singleMoves.size() == availibleMoves.get(0).size()) availibleMoves.add(singleMoves);
                    }
                } else {
                    availibleMoves.add(singleMoves);
                }
            }
        }
        // System.out.println(availibleMoves);
    }

    /**
     * @param piece black piece to add to the list of black pieces
     */
    public void addBlack(Piece piece){
        black.add(piece);
    }
    
    /**
     * @param piece white piece to add to the list of white pieces
     */
    public void addWhite(Piece piece){
        white.add(piece);
    }

    /**
     * @param start square where the move started
     * @param end square where the move ended
     * @param board array with squares from the board
     * @return square where killed piece is placed
     */
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

    /**
     * @return piece which has just been moved
     */
    public Piece getMovedPiece() {
        return movedPiece;
    }

    /**
     * @param movedPiece piece which has just been moved
     */
    public void setMovedPiece(Piece movedPiece) {
        this.movedPiece = movedPiece;
    }

    /**
     * @return size of the array with black pieces
     */
    public int getBlackNum(){return black.size();}
    
    /**
     * @return size of the array with white pieces
     */
    public int getWhiteNum(){return white.size();}
    
    /**
     * @param piece piece which we killed
     */
    public void killPiece(Piece piece){
        if(isWhiteTurn)black.remove(piece);
        else white.remove(piece);
    }
    
    /**
     * @param piece piece to check if it can make more moves
     * @param board array with squares from the board
     * @return true if the piece still has available moves
     */
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
    
    /**
     * @param currentBoard array with squares from the board
     */
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
    
    /**
     * @param piece piece to check available tiles to move to
     * @param board array of squares from the board
     * @return list of squares where the piece can move
     */
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
                    if (singleMove.getKilled() != null&& !killed.contains(singleMove.getKilled())){
                        result.add(singleMove.getEnd());
                    }
                }
            }
        }
        return result;
    }


}
