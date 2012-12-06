package jogadores;

import model.Casa;
import controller.Board;
import controller.God;


public class Minimax2 extends Jogador {
	
	int cont = 0;

	@Override
	public void jogar() {
		cont = 0;
		God god = God.getInstance();
		Board board = god.getClone();
		System.out.print("Minimax2 >>> ");
		long t0 = System.currentTimeMillis();
		Casa jogada = getBestMove(board , this, 5);
		god.jogar(jogada.getI(), jogada.getJ());
		System.out.println( cont + " >>> " + (System.currentTimeMillis()-t0)/1000);
	}

	private Casa getBestMove(Board board, Jogador jogador, int maxDepth) {
		Object[] resultado = new Object[2];
		resultado = minimax(board, jogador, maxDepth, 0);
		return (Casa) resultado[1];
	}

	private Object[] minimax(Board board, Jogador jogador, int maxDepth, int currentDepth) {
		cont++;
		Object[] resultado = new Object[2];
		if(board.isGameOver() || currentDepth == maxDepth){
			int nMoves = board.getMoves().size();
			nMoves = board.currentPlayer().cor==jogador.cor?nMoves:-nMoves;
			
			resultado[0] = board.evaluate(jogador.cor)+nMoves;
			resultado[1] = null;
		}else{

			
			if(board.currentPlayer().cor==jogador.cor){
				resultado[0] = -1000;
			}else{
				resultado[0] = 1000;
			}
			for (Casa movimento : board.getMoves()) {
				Board newBoard = board.makeMove(movimento);
				
				Object[] retornoRecursivo = new Object[2];
				retornoRecursivo = minimax(newBoard, jogador, maxDepth, currentDepth+1);
				
				int currentScore = (Integer) retornoRecursivo[0];
				
				if(board.currentPlayer().cor.equals(jogador.cor)){
					if(currentScore > (Integer) resultado[0]) {
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
