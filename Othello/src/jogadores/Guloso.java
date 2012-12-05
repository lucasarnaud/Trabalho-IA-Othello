package jogadores;

import model.Casa;
import controller.Board;
import controller.God;

public class Guloso extends Jogador {
	@Override
	public void jogar() {
		Board board = God.getInstance().getClone();
		Casa jogada = getBestMove(board);
		God.getInstance().jogar(jogada.getI(), jogada.getJ());
	}

	private Casa getBestMove(Board board) {
		
		int pontuacao = -1;
		Casa jogada = null;
		for (Casa movimento : board.getMoves()) {
			Board newBoard = board.makeMove(movimento);
			int currentScore = newBoard.evaluate(this.cor);
			
			if(currentScore > pontuacao){
				pontuacao = currentScore;
				jogada = movimento;
			}
			
		}
		
		return jogada;
	}
}
