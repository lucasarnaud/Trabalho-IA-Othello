package controller;

import java.util.Stack;

import model.Casa;
import view.Tabuleiro;

public class God {

	private static final int TAMANHO_TABULEIRO = 8;

	private static God instance;
	private static Tabuleiro tabuleiro;

	private StatusCasa jogadorVez;
	private Casa[][] casas;

	private God() {
		casas = new Casa[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];
		jogadorVez = StatusCasa.PECA_PRETA;

		for (int i = 0; i < TAMANHO_TABULEIRO; i++) {

			for (int j = 0; j < TAMANHO_TABULEIRO; j++) {

				casas[i][j] = new Casa();

			}

		}

		casas[3][3].setStatus(StatusCasa.PECA_BRANCA);
		casas[4][4].setStatus(StatusCasa.PECA_BRANCA);
		casas[3][4].setStatus(StatusCasa.PECA_PRETA);
		casas[4][3].setStatus(StatusCasa.PECA_PRETA);
		casas[2][3].setStatus(StatusCasa.JOGADA_POSSIVEL);
		casas[3][2].setStatus(StatusCasa.JOGADA_POSSIVEL);
		casas[4][5].setStatus(StatusCasa.JOGADA_POSSIVEL);
		casas[5][4].setStatus(StatusCasa.JOGADA_POSSIVEL);

		tabuleiro = Tabuleiro.getInstance();
		tabuleiro.atualiza(casas, jogadorVez);
	}

	public static God getInstance() {
		if (instance == null) {
			instance = new God();
		}
		return instance;
	}

	public void jogar(int i, int j) {

		verificarValidadeJogada();

		executarJogada(i, j);

		atualizarJogadasDisponiveis();

		jogadorVez = jogadorVez == StatusCasa.PECA_BRANCA ? StatusCasa.PECA_PRETA
				: StatusCasa.PECA_BRANCA;

		tabuleiro.atualiza(casas, jogadorVez);
	}

	private void atualizarJogadasDisponiveis() {
		// TODO Auto-generated method stub

	}

	private void executarJogada(int i, int j) {
		casas[i][j].setStatus(jogadorVez);

		atualizarPecas(i, j);
	}

	private void atualizarPecas(int i, int j) {

		atualizaUmaDasDirecoes(i+1, j+0, +1, +0); //baixo
		atualizaUmaDasDirecoes(i-1, j+0, -1, +0); //cima
		atualizaUmaDasDirecoes(i-0, j+1, -0, +1); //direita
		atualizaUmaDasDirecoes(i-0, j-1, -0, -1); //esquerda
		atualizaUmaDasDirecoes(i+1, j+1, +1, +1); //diagonal baixo/direita
		atualizaUmaDasDirecoes(i+1, j-1, +1, -1); //diagonal baixo/esquerda
		atualizaUmaDasDirecoes(i-1, j+1, -1, +1); //diagonal cima/direita
		atualizaUmaDasDirecoes(i-1, j-1, -1, -1); //diagonal cima/esquerda
	}
	
	
	private void atualizaUmaDasDirecoes(int iInicial, int jInicial, int incrementoI,
			int incrementoJ) {

		Stack<Casa> casasAvaliadas = new Stack<Casa>();
		boolean repintar = false;

		for (int i = iInicial, j = jInicial; i >= 0 && i < TAMANHO_TABULEIRO
				&& j >= 0 && j < TAMANHO_TABULEIRO; i += incrementoI, j += incrementoJ) {

			StatusCasa sc = casas[i][j].getStatus();

			if (sc == jogadorVez) {
				repintar = true;
				break;
			} else if (sc == StatusCasa.JOGADA_NAO_POSSIVEL
					|| sc == StatusCasa.JOGADA_POSSIVEL) {
				break;
			} else {
				casasAvaliadas.push(casas[i][j]);
			}

		}

		if (repintar) {
			while (!casasAvaliadas.isEmpty()) {
				casasAvaliadas.pop().setStatus(jogadorVez);
			}
		}

	}	

	private void verificarValidadeJogada() {
		// TODO Auto-generated method stub

	}

}
