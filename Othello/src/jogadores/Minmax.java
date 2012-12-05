package jogadores;

import model.Casa;
import controller.Board;
import controller.God;


public class Minmax extends Jogador {

	@Override
	public void jogar() {
		Board board = God.getInstance().getClone();
		Casa jogada = getBestMove(board , this, 60);
		God.getInstance().jogar(jogada.getI(), jogada.getJ());
	}

	private Casa getBestMove(Board board, Jogador jogador, int maxDepth) {
		Object[] resultado = new Object[2];
		resultado = minimax(board, jogador, maxDepth, 0);
		return (Casa) resultado[1];
	}

	private Object[] minimax(Board board, Jogador jogador, int maxDepth, int currentDepth) {
		System.out.println(currentDepth);
		Object[] resultado = new Object[2];
		if(board.isGameOver() || currentDepth == maxDepth){
			resultado[0] = board.evaluate(jogador.cor);
			resultado[1] = null;		
		}else{

			
			if(board.currentPlayer().cor.equals(jogador.cor)){
				resultado[0] = -1;
			}else{
				resultado[0] = 65;
			}
			for (Casa movimento : board.getMoves()) {
				Board newBoard = board.makeMove(movimento);
				
				Object[] retornoRecursivo = new Object[2];
				retornoRecursivo = minimax(newBoard, jogador, maxDepth, currentDepth+1);
				
				int currentScore = (Integer) retornoRecursivo[0];
				
				if(board.currentPlayer().cor.equals(jogador.cor)){
					if(currentScore > (Integer) resultado[0]) {
						System.out.println(resultado[0]);

						resultado[0] = currentScore;
						resultado[1] =  movimento;
					}
				}else{
					if(currentScore < (Integer) resultado[0]) {
						resultado[0] =  currentScore;
						resultado[1] =  movimento;
					}
				}
			}
		}
		return resultado;
	}

}
