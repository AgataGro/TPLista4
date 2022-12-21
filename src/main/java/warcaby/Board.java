package warcaby;

/**
 * Interfejs planszy do gry
 */
public interface Board {
    /**
     * zwraca pole o zadanym x, y
     * @param x współrzędna x pola
     * @param y współrzędna y pola
     * @return pole
     */
    public Square getTile(int x, int y);

    /**
     * funkcja resetująca planszę do pozycji wyjściowej
     */
    public void resetBoard();

    /**
     * @return liczba pól na planszy
     */
    public int getTileNum();
}
