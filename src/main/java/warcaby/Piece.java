package warcaby;

/**
 * Klasa abstrakcyjna pionka
 */
public abstract class Piece {
    private boolean white = false;
    private State state;
    private int currentKillablePieces;

    /**
     * Konstruktor
     * @param white czy kolor jest biały
     */
    public Piece(boolean white) {
        this.white = white;
    }

    /**
     *
     * @return zwraca czy kolor pionka jest biały
     */
    public boolean isWhite() {
        return white;
    }

    /**
     * @return zwraca stan pionka(Man/King)
     */
    public State getState() {
        return state;
    }

    /**
     * Funkcja ustawiająca stan pionka
     * @param state stan pionka
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Funkcja ustalająca maksymalną ilość pionków, które jesteśmy w stanie zbić danym pionkiem w jednej turze
     * @param currentKillablePieces ilość pionków
     */
    public void setCurrentKillablePieces(int currentKillablePieces) {
        this.currentKillablePieces = currentKillablePieces;
    }

    /**
     * @return maksymalna ilość pionków, które jesteśmy w stanie zbić danym pionkiem w jednej turze
     */
    public int getCurrentKillablePieces() {
        return currentKillablePieces;
    }

    /**
     * Funkcja wykonująca właściwy ruch, przesunięcie pionka i zbicie napotkanych po drodze pionków
     * @param board wykorzystywana plansza
     * @param sequence sekwencja kroków w stylu A0:C2:E4:C6
     */
    public void doMove(Board board, String sequence){
        this.state.Move(this, board, sequence);
    }
    /**
     * Sprawdzenie czy zbicie jest możliwe
     * @param board wykorzystywana plansza
     * @param start pole, na którym aktualnie znajduje się pionek
     * @param d kierunek ruchu
     * @return String z nazwą pola np. A8
     */
    public abstract String jump(Board board, Square start, Direction d);
    /**
     * Sprawdzenie czy ruch jest możliwy
     * @param board wykorzystywana plansza
     * @param start pole, na którym aktualnie znajduje się pionek
     * @param d kierunek ruchu
     * @return String z nazwą pola np. A8
     */
    public abstract String move(Board board, Square start, Direction d);

    /**
     * Funkcja, która zmienia stan pionka z Man na King jeżeli spełnione są odpowiednie warunki
     * @param start pole na którym aktualnie znajduje się pionek
     */
    public abstract void crown(Square start);
}
