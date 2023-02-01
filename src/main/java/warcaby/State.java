package warcaby;

//import javafx.scene.Scene;

import java.util.List;

/**
 * Interfejs do wykorzystania wzorca State
 * Z założeniem, że dla każdego typu pionka (rodzaju gry) stworzone będą dwa stany
 * Man - zachowanie zwykłego pionka
 * King - zachowanie damki
 */
public interface State {

    /**
     * @param piece piece which available moves we want to get
     * @param board array of squares
     * @return
     */
    public List<SingleMove> availibleMoves(Piece piece, Square[][] board);

    /**
     * @param piece piece which sequence of available moves is returned
     * @param board array of aquares
     * @param steps list of move sequences
     * @return list of lists of move sequences
     */
    public List<List<SingleMove>> moveSequence(Piece piece, Square[][] board, List<SingleMove> steps);

    /**
     * @return state after change
     */
    public State changeState();

    /**
     * @param piece piece to move
     * @param tiles array of squares
     * @param direction direction in which the piece wants to move
     * @return list of available move sequences
     */
    public List<SingleMove> move(Piece piece, Square[][] tiles, Direction direction);
}