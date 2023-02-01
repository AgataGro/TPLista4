package warcaby;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mediator {
    List<Piece> white=new ArrayList<>();
    List<Piece> black=new ArrayList<>();
    boolean isWhiteTurn;
    boolean hasKillOption;
    Piece movedPiece =null;
    List<Square> killed=new ArrayList<>();
    List<List<SingleMove>> moveSequences=new ArrayList<>();
    boolean killMax;
    List<SingleMove> record = new ArrayList<>();

    /**
     * @param isWhiteTurn is true if white piece should move, false otherwise
     */
    Mediator(boolean isWhiteTurn, boolean killMax){
        this.isWhiteTurn=isWhiteTurn;
        this.killMax=killMax;
    }

    /**
     * @param board array with squares to check
     */
    public void changeTurn(Square[][] board){
        this.isWhiteTurn=!isWhiteTurn;
        movedPiece=null;
        killed.clear();
        hasKillOption(board);
        calculateMoves(board);
        if(killMax)moveSequences=getMoveSequencesMostKilled();
        else moveSequences=getMoveSequencesKillPriority();

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
                if (singleMove.getStart() == start&&singleMove.getEnd()==end){
                    square=singleMove.getKilled();
                    record.add(singleMove);
                }
            }
        }
        return square;
    }
    public List<Piece> pickRandomMove(){
        List<SingleMove> move = new ArrayList<>();
        if(!moveSequences.isEmpty()){
            Random random = new Random();
            int i = random.nextInt(0,moveSequences.size());
            move = moveSequences.get(i);
        }

        List<Piece> killed = new ArrayList<>();
        if(!move.isEmpty()) {
            Piece killedPiece;
            Piece piece;

            piece = move.get(0).getStart().getPiece();
            for (SingleMove singleMove : move) {
                if (singleMove.getKilled() != null) {
                    killedPiece = singleMove.getKilled().getPiece();
                    killPiece(killedPiece);
                    singleMove.getKilled().setPiece(null);
                    killed.add(killedPiece);
                }
                piece.move((int) (singleMove.getEnd().getX() / 70), (int) (singleMove.getEnd().getY() / 70));
                singleMove.getEnd().setPiece(piece);
                singleMove.getStart().setPiece(null);

            }
        }
        return killed;
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
    public void calculateMoves(Square[][] board){
        moveSequences.clear();
        List<List<SingleMove>> moves;
        if(isWhiteTurn){
            for (Piece piece : white) {
                moves = piece.moveSequences(board);
                for (List<SingleMove> move : moves) {
                    if (!move.isEmpty()) moveSequences.add(move);
                }

            }
        }
        else{
            for (Piece piece : black) {
                moves = piece.moveSequences(board);
                for (List<SingleMove> move : moves) {
                    if (!move.isEmpty()) moveSequences.add(move);
                }
            }
        }
    }
    public List<List<SingleMove>> getMoveSequencesKillPriority() {
        List<List<SingleMove>> result = new ArrayList<>();
        boolean kill, killed=false;
        for (List<SingleMove> moveSequence : moveSequences) {
            if (!moveSequence.isEmpty()) {
                kill = moveSequence.get(0).getKilled() != null;
                if (!killed && !kill) {
                    result.add(moveSequence);
                } else if (!killed) {
                    result.clear();
                    result.add(moveSequence);
                    killed = true;
                } else if (kill) {
                    result.add(moveSequence);
                }
            }
        }
        return result;
    }
    public List<List<SingleMove>> getMoveSequencesMostKilled() {
        List<List<SingleMove>> temp = getMoveSequencesKillPriority(), result = new ArrayList<>();
        for (List<SingleMove> singleMoves : temp) {
            if (result.isEmpty()) result.add(singleMoves);
            else if (result.get(0).size() == singleMoves.size()) result.add(singleMoves);
            else if (singleMoves.size() > result.get(0).size()) {
                result.clear();
                result.add(singleMoves);
            }
        }
        System.out.println(result);
        return result;
    }
}