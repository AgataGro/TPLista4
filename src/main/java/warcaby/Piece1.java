package warcaby;
//

/**
 * Klasa pionka do gry w warcaby polskie(międzynarodowe)
 * Zachowanie pionka opisane z wykorzystaniem wzorca State
 * Zwykły pionek -> Man1State
 * Damka -> King1State
 */
public class Piece1 extends Piece {

    /**
     * konstruktor, domyślny State to Man1State
     * @param white true jeśli pionek jest biały, false jeśli czarny
     */
    public Piece1(boolean white) {
        super(white);
        this.setState(new Man1State());
    }

    /**
     * Funkcja zwracająca String z nazwą pola na które przeskakujemy pionkiem(zbiecie), jeżeli ruch jest możliwy
     * w przeciwnym przypadku zwraca pusty string
     * @param board wykorzystywana plansza
     * @param start pole, na którym znajduje się aktualnie pionek
     * @param d kierunek, w którym chcemy przesunąć pionek
     * @return string z nazwą pola
     */
    @Override
    public String jump(Board board, Square start, Direction d) {
        String tmp = "";
        if (this.getState().jump(this, board, start, d)) {
            switch (d) {
                case UpLeft -> {
                    tmp += (char) (start.getX() + (int) 'A' - 2);
                    tmp += (start.getY() + 2);
                }
                case UpRight -> {
                    tmp += (char) (start.getX() + (int) 'A' + 2);
                    tmp += (start.getY() + 2);
                }
                case DownLeft -> {
                    tmp += (char) (start.getX() + (int) 'A' - 2);
                    tmp += (start.getY() - 2);
                }
                case DownRight -> {
                    tmp += (char) (start.getX() + (int) 'A' + 2);
                    tmp += (start.getY() - 2);
                }
            }
        }
        return tmp;
    }

    /**
     * Funkcja zwracająca String z nazwą pola na które przesuwamy pionek, jeżeli ruch jest możliwy
     * w przeciwnym przypadku zwraca pusty string
     * @param board wykorzystywana plansza
     * @param start pole, na którym znajduje się aktualnie pionek
     * @param d kierunek, w którym chcemy przesunąć pionek
     * @return string z nazwą pola
     */
    @Override
    public String move(Board board, Square start, Direction d) {
        String tmp="";
        if(this.getState().move(this, board, start, d)) {
            switch (d) {
                case UpLeft -> {
                    tmp += (char) (start.getX() + (int) 'A' - 1);
                    tmp += (start.getY() + 1);
                }
                case UpRight -> {
                    tmp += (char) (start.getX() + (int) 'A' + 1);
                    tmp += (start.getY() + 1);
                }
                case DownLeft -> {
                    tmp += (char) (start.getX() + (int) 'A' - 1);
                    tmp += (start.getY() - 1);
                }
                case DownRight -> {
                    tmp += (char) (start.getX() + (int) 'A' + 1);
                    tmp += (start.getY() - 1);
                }
            }
        }
        return tmp;
    }

    /**
     * Funkcja zmieniająca stan pionka ze zwykłego na damkę
     * Jeżeli pionek może być ukoronowany zmiana stanu na King1State
     * w przeciwnym przypadku stan pozostaje bez zmian
     * Wywołać na początku rundy
     * @param start pole, którym znajduje się na starcie pionek
     */
    @Override
    public void crown(Square start) {
        this.setState(getState().crown(this, start));
    }
}
