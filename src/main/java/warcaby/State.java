package warcaby;


import java.util.List;

/**
 * Interfejs do wykorzystania wzorca State
 * Z założeniem, że dla każdego typu pionka (rodzaju gry) stworzone będą dwa stany
 * Man - zachowanie zwykłego pionka
 * King - zachowanie damki
 */
public interface State {
    /**
     * Funkcja sprawdzająca, na które pola można przesunąć pionek w zależności od jego stanu i układu pionków na planszy
     * @param piece zaznaczony pionek
     * @param board pola na wykorzystywanej planszy
     * @return lista pól, na które można przesunąć pionek
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
