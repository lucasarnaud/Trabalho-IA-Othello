package jogadores;

import model.Casa;
import controller.Board;
import controller.God;


public class Minimax3 extends Jogador {
	

	private static final int pesos[][];
	
	
	int cont = 0;

	@Override
	public void jogar() {
		cont = 0;
		God god = God.getInstance();
		Board board = god.getClone();
		System.out.print("Minimax3 >>> ");
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

/*		if (board.isGameOver()) {
			if (board.currentPlayer().cor==jogador.cor) {
				resultado[0] = PONTO_FIM_JOGO;
			}
			else {
				resultado[0] = -PONTO_FIM_JOGO;
			}
			resultado[1] = null;
		}
		else if (currentDepth == maxDepth) {
			int nMoves = board.getMoves().size();
			nMoves = board.currentPlayer().cor==jogador.cor?nMoves:-nMoves;

			int peso = 0;
			for (Casa casa : board.getPecasJogadores().get(jogador.cor)) {
				peso += pesos[casa.getI()][casa.getJ()];
			}
			
			resultado[0] = board.evaluate(jogador.cor)+peso+nMoves;
			resultado[1] = null;
		}*/
		
		if(board.isGameOver() || currentDepth == maxDepth){
			int nMoves = board.getMoves().size();
			nMoves = board.currentPlayer().cor==jogador.cor?nMoves:-nMoves;

			int peso = 0;
			for (Casa casa : board.getPecasJogadores().get(jogador.cor)) {
				peso += pesos[casa.getI()][casa.getJ()];
			}

			resultado[0] = board.evaluate(jogador.cor)+peso+nMoves;
			resultado[1] = null;
		}
		else{

			
			if(board.currentPlayer().cor==jogador.cor){
				resultado[0] = -10000;
			}else{
				resultado[0] = 10000;
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
	
	
	
	static {
		pesos = new int[God.TAMANHO_TABULEIRO][God.TAMANHO_TABULEIRO];
		
		pesos[0][0] = 100; pesos[0][1] = 2; pesos[0][2] = 8; pesos[0][3] = 3; pesos[0][4] = 3; pesos[0][5] = 8; pesos[0][6] = 2; pesos[0][7] = 100;
		pesos[1][0] = 2; pesos[1][1] = 1; pesos[1][2] = 7; pesos[1][3] = 4; pesos[1][4] = 4; pesos[1][5] = 7; pesos[1][6] = 1; pesos[1][7] = 2;
		pesos[2][0] = 8; pesos[2][1] = 7; pesos[2][2] = 9; pesos[2][3] = 5; pesos[2][4] = 5; pesos[2][5] = 9; pesos[2][6] = 7; pesos[2][7] = 8;
		pesos[3][0] = 3; pesos[3][1] = 4; pesos[3][2] = 5; pesos[3][3] = 0; pesos[3][4] = 0; pesos[3][5] = 5; pesos[3][6] = 4; pesos[3][7] = 3;
		pesos[4][0] = 3; pesos[4][1] = 4; pesos[4][2] = 5; pesos[4][3] = 0; pesos[4][4] = 0; pesos[4][5] = 5; pesos[4][6] = 4; pesos[4][7] = 3;
		pesos[5][0] = 8; pesos[5][1] = 7; pesos[5][2] = 9; pesos[5][3] = 5; pesos[5][4] = 5; pesos[5][5] = 9; pesos[5][6] = 7; pesos[5][7] = 8;
		pesos[6][0] = 2; pesos[6][1] = 1; pesos[6][2] = 7; pesos[6][3] = 4; pesos[6][4] = 4; pesos[6][5] = 7; pesos[6][6] = 1; pesos[6][7] = 2;
		pesos[7][0] = 100; pesos[7][1] = 2; pesos[7][2] = 8; pesos[7][3] = 3; pesos[7][4] = 3; pesos[7][5] = 8; pesos[7][6] = 2; pesos[7][7] = 100;


/*		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.print(String.format("%3d", pesos[i][j]));		
			}
			System.out.println();
		}
*/	}
	
}











/*		if(board.isGameOver() || currentDepth == maxDepth){

int nMoves = board.getMoves().size();
nMoves = board.currentPlayer().cor==jogador.cor?nMoves:-nMoves;

int peso = 0;
for (Casa casa : board.getPecasJogadores().get(jogador.cor)) {
	peso += pesos[casa.getI()][casa.getJ()];
}

resultado[0] = board.evaluate(jogador.cor)+peso+nMoves;
resultado[1] = null;
}
*/		

