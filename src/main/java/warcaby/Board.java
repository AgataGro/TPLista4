package warcaby;

public interface Board {
    public Square getTile(int x, int y);
    public void resetBoard();
    public int getTileNum();
}
