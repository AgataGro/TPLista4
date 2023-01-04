package warcaby;

import com.almasb.fxgl.scene.Scene;

import java.util.List;

/**
 * Interfejs do wykorzystania wzorca State
 * Z założeniem, że dla każdego typu pionka (rodzaju gry) stworzone będą dwa stany
 * Man - zachowanie zwykłego pionka
 * King - zachowanie damki
 */
public interface State {

    public List<Square> availibleMoves(Piece piece, Square[][] board);
}
