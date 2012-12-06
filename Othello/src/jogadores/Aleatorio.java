package jogadores;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Casa;
import controller.Board;
import controller.God;

public class Aleatorio extends Jogador {
	@Override
	public void jogar() {
		Board board = God.getInstance().getClone();
		Casa jogada = getBestMove(board);
		God.getInstance().jogar(jogada.getI(), jogada.getJ());
	}

	private Casa getBestMove(Board board) {
		
		List<Casa> movimentos = new ArrayList<Casa>(board.getMoves());
		
		Random r = new Random(System.currentTimeMillis());
		
		return movimentos.get(r.nextInt(movimentos.size()));
	}
}
