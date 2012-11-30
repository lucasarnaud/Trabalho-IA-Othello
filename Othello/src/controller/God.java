package controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import model.Casa;
import view.Tabuleiro;

public class God {

	private static final int TAMANHO_TABULEIRO = 8;

	private static final int DIRECAO_BAIXO = 1;

	private static final int DIRECAO_CIMA = -1;

	private static final int DIRECAO_MESMA = 0;

	private static final int DIRECAO_DIREITA = 1;

	private static final int DIRECAO_ESQUERDA = -1;

	private static God instance;
	private static Tabuleiro tabuleiro;

	private StatusCasa jogadorVez;
	private Casa[][] casas;
	
	private HashSet<Casa> jogadasPossiveis; //armazena o conjunto de casas disponiveis por rodada
	private HashMap<StatusCasa, HashSet<Casa>> pecasJogadores; //armazena o conjunto de pecas brancas e pretas no tabuleiro
	

	private God() {
		casas = new Casa[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
		jogadorVez = StatusCasa.PECA_PRETA;

		for (int i = 0; i < TAMANHO_TABULEIRO; i++) {

			for (int j = 0; j < TAMANHO_TABULEIRO; j++) {

				casas[i][j] = new Casa(i, j);

			}

		}
		
		jogadasPossiveis = new HashSet<Casa>();
		pecasJogadores = new HashMap<StatusCasa, HashSet<Casa>>();
		
		pecasJogadores.put(StatusCasa.PECA_BRANCA, new HashSet<Casa>());
		pecasJogadores.put(StatusCasa.PECA_PRETA, new HashSet<Casa>());
	
		casas[3][3].setStatus(StatusCasa.PECA_BRANCA);
		casas[4][4].setStatus(StatusCasa.PECA_BRANCA);
		
		casas[3][4].setStatus(StatusCasa.PECA_PRETA);
		casas[4][3].setStatus(StatusCasa.PECA_PRETA);
		
		casas[2][3].setStatus(StatusCasa.JOGADA_POSSIVEL);
		casas[3][2].setStatus(StatusCasa.JOGADA_POSSIVEL);
		casas[4][5].setStatus(StatusCasa.JOGADA_POSSIVEL);
		casas[5][4].setStatus(StatusCasa.JOGADA_POSSIVEL);

		pecasJogadores.get(StatusCasa.PECA_BRANCA).add(casas[3][3]);
		pecasJogadores.get(StatusCasa.PECA_BRANCA).add(casas[4][4]);
		
		pecasJogadores.get(StatusCasa.PECA_PRETA).add(casas[3][4]);
		pecasJogadores.get(StatusCasa.PECA_PRETA).add(casas[4][3]);
		
		jogadasPossiveis.add(casas[2][3]);
		jogadasPossiveis.add(casas[3][2]);
		jogadasPossiveis.add(casas[4][5]);
		jogadasPossiveis.add(casas[5][4]);
		
		tabuleiro = Tabuleiro.getInstance();
		tabuleiro.atualiza(casas, jogadorVez,
				pecasJogadores.get(StatusCasa.PECA_BRANCA).size(),
				pecasJogadores.get(StatusCasa.PECA_PRETA).size());
	}

	public static God getInstance() {
		if (instance == null) {
			instance = new God();
		}
		return instance;
	}

	public void jogar(int i, int j) {

		verificarValidadeJogada(i, j);

		colocarPeca(i, j);

		jogadorVez = jogadorVez == StatusCasa.PECA_BRANCA ? StatusCasa.PECA_PRETA
				: StatusCasa.PECA_BRANCA;

		atualizarJogadasPossiveis();

		tabuleiro.atualiza(casas, jogadorVez,
				pecasJogadores.get(StatusCasa.PECA_BRANCA).size(),
				pecasJogadores.get(StatusCasa.PECA_PRETA).size());
	}
	

	private void verificarValidadeJogada(int i, int j) {
		if (!jogadasPossiveis.contains(casas[i][j]))
			throw new RuntimeException(); // FIXME criar exception propria
	}

	
	private void colocarPeca(int i, int j) {
		Casa casa = casas[i][j];
		
		casa.setStatus(jogadorVez);
		
		pecasJogadores.get(jogadorVez).add(casa);
		jogadasPossiveis.remove(casa);
		
		atualizarPecas(i, j);
	}
	

	private void atualizarPecas(int i, int j) {

		atualizaCorEmUmaDasDirecoes(i, j, DIRECAO_BAIXO, DIRECAO_MESMA); //baixo
		atualizaCorEmUmaDasDirecoes(i, j, DIRECAO_CIMA, DIRECAO_MESMA); //cima
		atualizaCorEmUmaDasDirecoes(i, j, DIRECAO_MESMA, DIRECAO_DIREITA); //direita
		atualizaCorEmUmaDasDirecoes(i, j, DIRECAO_MESMA, DIRECAO_ESQUERDA); //esquerda
		atualizaCorEmUmaDasDirecoes(i, j, DIRECAO_BAIXO, DIRECAO_DIREITA); //diagonal baixo/direita
		atualizaCorEmUmaDasDirecoes(i, j, DIRECAO_BAIXO, DIRECAO_ESQUERDA); //diagonal baixo/esquerda
		atualizaCorEmUmaDasDirecoes(i, j, DIRECAO_CIMA, DIRECAO_DIREITA); //diagonal cima/direita
		atualizaCorEmUmaDasDirecoes(i, j, DIRECAO_CIMA, DIRECAO_ESQUERDA); //diagonal cima/esquerda
	}
	
	
	private void atualizaCorEmUmaDasDirecoes(int i, int j,
			int direcaoI, int direcaoJ) {

		Stack<Casa> casasAvaliadas = new Stack<Casa>();
		boolean repintar = false;

		for (int l = i + direcaoI, c = j + direcaoJ; l >= 0 && l < TAMANHO_TABULEIRO
				&& c >= 0 && c < TAMANHO_TABULEIRO; l += direcaoI, c += direcaoJ) {

			StatusCasa sc = casas[l][c].getStatus();

			if (sc == jogadorVez) {
				repintar = true;
				break;
			} else if (sc == StatusCasa.JOGADA_NAO_POSSIVEL
					|| sc == StatusCasa.JOGADA_POSSIVEL) {
				break;
			} else {
				casasAvaliadas.push(casas[l][c]);
			}

		}

		if (repintar) {
			while (!casasAvaliadas.isEmpty()) {
				Casa casa = casasAvaliadas.pop();
				casa.setStatus(jogadorVez);

				StatusCasa adversario = jogadorVez == StatusCasa.PECA_BRANCA ? StatusCasa.PECA_PRETA
						: StatusCasa.PECA_BRANCA;

				pecasJogadores.get(jogadorVez).add(casa);
				pecasJogadores.get(adversario).remove(casa);
			}
		}

	}	

	
	private void atualizarJogadasPossiveis() {
		
		for (Casa casa : jogadasPossiveis) {
			casa.setStatus(StatusCasa.JOGADA_NAO_POSSIVEL);
		}
		jogadasPossiveis.clear();

		HashSet<Casa> pecasJogadorVez = pecasJogadores.get(jogadorVez);
		
		for (Casa casa : pecasJogadorVez) {
			
			int i = casa.getI();
			int j = casa.getJ();
			
			atualizaPossibilidadeEmUmaDasDirecoes(i+1, j+0, +1, +0); //baixo
			atualizaPossibilidadeEmUmaDasDirecoes(i-1, j+0, -1, +0); //cima
			atualizaPossibilidadeEmUmaDasDirecoes(i-0, j+1, -0, +1); //direita
			atualizaPossibilidadeEmUmaDasDirecoes(i-0, j-1, -0, -1); //esquerda
			atualizaPossibilidadeEmUmaDasDirecoes(i+1, j+1, +1, +1); //diagonal baixo/direita
			atualizaPossibilidadeEmUmaDasDirecoes(i+1, j-1, +1, -1); //diagonal baixo/esquerda
			atualizaPossibilidadeEmUmaDasDirecoes(i-1, j+1, -1, +1); //diagonal cima/direita
			atualizaPossibilidadeEmUmaDasDirecoes(i-1, j-1, -1, -1); //diagonal cima/esquerda			
			
		}
		
	}
	
	
	private void atualizaPossibilidadeEmUmaDasDirecoes(int iInicial, int jInicial,
			int incrementoI, int incrementoJ) {

		boolean podeSerPossivel = false;
		
		StatusCasa sc = jogadorVez;
		
		int i, j;
		for (i = iInicial, j = jInicial; i >= 0 && i < TAMANHO_TABULEIRO
				&& j >= 0 && j < TAMANHO_TABULEIRO; i += incrementoI, j += incrementoJ) {

			sc = casas[i][j].getStatus();

			if (sc == jogadorVez || sc == StatusCasa.JOGADA_NAO_POSSIVEL || sc == StatusCasa.JOGADA_POSSIVEL) {
				break;
			} else {
				podeSerPossivel = true;
			}
		}
		
		if (podeSerPossivel && sc == StatusCasa.JOGADA_NAO_POSSIVEL) {
			Casa casa = casas[i][j];
			casa.setStatus(StatusCasa.JOGADA_POSSIVEL);
			jogadasPossiveis.add(casa);
		}

	}	


}
