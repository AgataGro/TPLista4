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
     * Wykonanie ruchu
     * Do zmiany??? Stworzenie klasy, która przechowuje wszystkie możliwe sekwencje ruchów dla danego pionka
     * @param piece przesuwany pionek
     * @param board wykorzystywana plansza
     * @param sequence sekwencja kolejnych ruchów/przesunięć pionka
     */
    public void Move(Piece piece, Board board, String sequence);

    /**
     * Funkcja sprawdzjąca ile maksymalnie pionków jesteśmy w stanie zbić danym pionkien
     * @param piece przesuwany pionek
     * @param board wykorzystywana plansza
     * @param start pole, na którym aktualnie znajduje się pionek
     * @param jumped lista pól nad którymi do tej pory przeskoczyliśmy
     * @return int maksymalna liczba pionków, które dany pionek może zbić
     */
    public int killablePieces(Piece piece, Board board, Square start, List<Square> jumped);

    /**
     * Funkcja sprawdzająca czy pionek może wykonać dany ruch
     * @param piece przesuwany pionek
     * @param board wykorzystywana plansza
     * @param start pole, na którym aktualnie znajduje się pionek
     * @param d kierunek ruchu
     * @return true można wykonać ruch, false jak nie można
     */
    public boolean move(Piece piece, Board board, Square start, Direction d);

    /**
     * Funkcja sprawdzająca czy pionek może wykonać dane zbicie
     * @param piece przesuwany pionek
     * @param board wykorzystywana plansza
     * @param start pole, na którym aktualnie znajduje się pionek
     * @param d kierunek ruchu
     * @return true można wykonać ruch, false jak nie można
     */
    public boolean jump(Piece piece, Board board, Square start, Direction d);

    /**
     * Funkcja do zmiany stanu z Man na King
     * @param piece przesuwany pionek
     * @param start pole, na którym aktualnie znajduje się pionek
     * @return stan Man jak nie można ukoronować pionka, King jak można
     */
    public State crown(Piece piece, Square start);
}
