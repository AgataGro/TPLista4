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
    public List<Square> availibleMoves(Piece piece, Square[][] board);
    public List<List<Square>> moveSequence(Piece piece, Square[][] board, List<Square> steps, List<Square> jumped);
    public State changeState();
}
