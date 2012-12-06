package controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Stack;

import jogadores.Humano;
import jogadores.Jogador;
import model.Casa;
import view.Tabuleiro;

public class God implements Board, Cloneable {

	public static final int TAMANHO_TABULEIRO = 8;

	private static final int DIRECAO_BAIXO = 1;
	private static final int DIRECAO_CIMA = -1;
	private static final int DIRECAO_MESMA = 0;
	private static final int DIRECAO_DIREITA = 1;
	private static final int DIRECAO_ESQUERDA = -1;

	private static final God instance = new God();
	private Tabuleiro tabuleiro;

	private Jogador jogadorVez;
	private Jogador peca_preta;
	private Jogador peca_branca;
	
	private int numeroJogadas;
	
	private boolean gameOver;
	
	private Casa[][] casas;

	private HashSet<Casa> jogadasPossiveis; // armazena o conjunto de casas disponiveis por rodada
	private HashMap<StatusCasa, HashSet<Casa>> pecasJogadores; // armazena o conjunto de pecas brancas e pretas no tabuleiro


	private God() {
		casas = new Casa[TAMANHO_TABULEIRO][TAMANHO_TABULEIRO];

		for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
			for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
				casas[i][j] = new Casa(i, j);
			}
		}

		jogadasPossiveis = new HashSet<Casa>();
		pecasJogadores = new HashMap<StatusCasa, HashSet<Casa>>();

		pecasJogadores.put(StatusCasa.PECA_BRANCA, new HashSet<Casa>());
		pecasJogadores.put(StatusCasa.PECA_PRETA, new HashSet<Casa>());
	}

	public void inicializaJogo(Jogador peca_preta, Jogador peca_branca) {
		tabuleiro = Tabuleiro.getInstance();
		
		pecasJogadores.get(StatusCasa.PECA_BRANCA).clear();
		pecasJogadores.get(StatusCasa.PECA_PRETA).clear();
		jogadasPossiveis.clear();
		
		gameOver = false;
		numeroJogadas = 0;
		
		this.peca_preta = peca_preta;
		this.peca_branca = peca_branca;
		
		peca_preta.cor = StatusCasa.PECA_PRETA;
		peca_branca.cor = StatusCasa.PECA_BRANCA;
		
		jogadorVez = peca_preta;

		for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
			for (int j = 0; j < TAMANHO_TABULEIRO; j++) {
				casas[i][j].setStatus(StatusCasa.JOGADA_NAO_POSSIVEL);
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

		pecasJogadores.get(StatusCasa.PECA_BRANCA).add(casas[3][3]);
		pecasJogadores.get(StatusCasa.PECA_BRANCA).add(casas[4][4]);

		pecasJogadores.get(StatusCasa.PECA_PRETA).add(casas[3][4]);
		pecasJogadores.get(StatusCasa.PECA_PRETA).add(casas[4][3]);

		jogadasPossiveis.add(casas[2][3]);
		jogadasPossiveis.add(casas[3][2]);
		jogadasPossiveis.add(casas[4][5]);
		jogadasPossiveis.add(casas[5][4]);

		tabuleiro.atualiza(casas, jogadorVez,
				pecasJogadores.get(StatusCasa.PECA_BRANCA).size(),
				pecasJogadores.get(StatusCasa.PECA_PRETA).size());
	}

	public static God getInstance() {
		return instance;
	}

	public void jogar(int i, int j) {
		verificaValidadeJogada(i, j);
		
		numeroJogadas++;

		colocaPeca(i, j); // posiciona a nova peca no tabuleiro e reverte as pecas da outra cor

		atualizaProximaJogada();

		tabuleiro.atualiza(casas, jogadorVez,
				pecasJogadores.get(StatusCasa.PECA_BRANCA).size(),
				pecasJogadores.get(StatusCasa.PECA_PRETA).size());

		if (jogadasPossiveis.isEmpty()) {

			Jogador adversario = jogadorVez;

			atualizaProximaJogada();

			if (jogadasPossiveis.isEmpty()) {
				fimJogo();
				gameOver = true;
				return;
			}

			tabuleiro.semJogadasPossiveis(adversario);
			tabuleiro.atualiza(casas, jogadorVez,
					pecasJogadores.get(StatusCasa.PECA_BRANCA).size(),
					pecasJogadores.get(StatusCasa.PECA_PRETA).size());
		}
		
	}

	private void atualizaProximaJogada() {
		jogadorVez = (jogadorVez.cor == StatusCasa.PECA_BRANCA ? peca_preta
				: peca_branca);

		atualizaJogadasPossiveis();
	}

	private void fimJogo() {
		int nPretas = pecasJogadores.get(StatusCasa.PECA_PRETA).size();
		int nBrancas = pecasJogadores.get(StatusCasa.PECA_BRANCA).size();

		if (nBrancas > nPretas)
			tabuleiro.fimDeJogo(StatusCasa.PECA_BRANCA);
		else if (nPretas > nBrancas)
			tabuleiro.fimDeJogo(StatusCasa.PECA_PRETA);
		else
			tabuleiro.fimDeJogo(StatusCasa.JOGADA_NAO_POSSIVEL);
	}

	private void verificaValidadeJogada(int i, int j) {
		if (!jogadasPossiveis.contains(casas[i][j]))
			throw new RuntimeException("Jogada inv‡lida");
	}

	private void colocaPeca(int i, int j) {
		Casa casa = casas[i][j];

		casa.setStatus(jogadorVez.cor);

		pecasJogadores.get(jogadorVez.cor).add(casa);
		jogadasPossiveis.remove(casa);

		atualizaPecas(i, j);
	}

	private void atualizaPecas(int i, int j) {

		atualizaCorEmUmaDasDirecoes(i, j, DIRECAO_BAIXO, DIRECAO_MESMA); // baixo
		atualizaCorEmUmaDasDirecoes(i, j, DIRECAO_CIMA, DIRECAO_MESMA); // cima
		atualizaCorEmUmaDasDirecoes(i, j, DIRECAO_MESMA, DIRECAO_DIREITA); // direita
		atualizaCorEmUmaDasDirecoes(i, j, DIRECAO_MESMA, DIRECAO_ESQUERDA); // esquerda
		atualizaCorEmUmaDasDirecoes(i, j, DIRECAO_BAIXO, DIRECAO_DIREITA); // diagonal baixo/direita
		atualizaCorEmUmaDasDirecoes(i, j, DIRECAO_BAIXO, DIRECAO_ESQUERDA); // diagonal baixo/esquerda
		atualizaCorEmUmaDasDirecoes(i, j, DIRECAO_CIMA, DIRECAO_DIREITA); // diagonal cima/direita
		atualizaCorEmUmaDasDirecoes(i, j, DIRECAO_CIMA, DIRECAO_ESQUERDA); // diagonal cima/esquerda
	}

	private void atualizaCorEmUmaDasDirecoes(int i, int j, int direcaoI,
			int direcaoJ) {

		Stack<Casa> casasAvaliadas = new Stack<Casa>();
		boolean repintar = false;

		for (int l = i + direcaoI, c = j + direcaoJ; l >= 0
				&& l < TAMANHO_TABULEIRO && c >= 0 && c < TAMANHO_TABULEIRO; l += direcaoI, c += direcaoJ) {

			StatusCasa sc = casas[l][c].getStatus();

			if (sc == jogadorVez.cor) {
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

				pecasJogadores.get(casa.getStatus()).remove(casa);

				casa.setStatus(jogadorVez.cor);

				pecasJogadores.get(jogadorVez.cor).add(casa);
			}
		}

	}

	private void atualizaJogadasPossiveis() {

		for (Casa casa : jogadasPossiveis) {
			casa.setStatus(StatusCasa.JOGADA_NAO_POSSIVEL);
		}
		jogadasPossiveis.clear();

		HashSet<Casa> pecasJogadorVez = pecasJogadores.get(jogadorVez.cor);

		for (Casa casa : pecasJogadorVez) {

			int i = casa.getI();
			int j = casa.getJ();

			atualizaPossibilidadeEmUmaDasDirecoes(i, j, DIRECAO_BAIXO, DIRECAO_MESMA); // baixo
			atualizaPossibilidadeEmUmaDasDirecoes(i, j, DIRECAO_CIMA, DIRECAO_MESMA); // cima
			atualizaPossibilidadeEmUmaDasDirecoes(i, j, DIRECAO_MESMA, DIRECAO_DIREITA); // direita
			atualizaPossibilidadeEmUmaDasDirecoes(i, j, DIRECAO_MESMA, DIRECAO_ESQUERDA); // esquerda
			atualizaPossibilidadeEmUmaDasDirecoes(i, j, DIRECAO_BAIXO, DIRECAO_DIREITA); // diagonal baixo/direita
			atualizaPossibilidadeEmUmaDasDirecoes(i, j, DIRECAO_BAIXO, DIRECAO_ESQUERDA); // diagonal baixo/esquerda
			atualizaPossibilidadeEmUmaDasDirecoes(i, j, DIRECAO_CIMA, DIRECAO_DIREITA); // diagonal cima/direita
			atualizaPossibilidadeEmUmaDasDirecoes(i, j, DIRECAO_CIMA, DIRECAO_ESQUERDA); // diagonal cima/esquerda

		}

	}

	private void atualizaPossibilidadeEmUmaDasDirecoes(int i, int j,
			int direcaoI, int direcaoJ) {

		boolean podeSerPossivel = false;

		StatusCasa sc = jogadorVez.cor;

		int iInicial = i + direcaoI, jInicial = j + direcaoJ;

		int l, c;
		for (l = iInicial, c = jInicial; l >= 0 && l < casas.length && c >= 0
				&& c < casas[i].length; l += direcaoI, c += direcaoJ) {

			sc = casas[l][c].getStatus();

			if (sc == jogadorVez.cor || sc == StatusCasa.JOGADA_NAO_POSSIVEL
					|| sc == StatusCasa.JOGADA_POSSIVEL) {
				break;
			} else {
				podeSerPossivel = true;
			}
		}

		if (podeSerPossivel && sc == StatusCasa.JOGADA_NAO_POSSIVEL) {
			Casa casa = casas[l][c];
			casa.setStatus(StatusCasa.JOGADA_POSSIVEL);
			jogadasPossiveis.add(casa);
		}

	}

	@Override
	public HashSet<Casa> getMoves() {		
		return jogadasPossiveis;
	}

	@Override
	public Board makeMove(Casa casa) {
		God god;
		
		god = (God) getClone();
		
		int i = casa.getI();
		int j = casa.getJ();
		
		god.verificaValidadeJogada(i, j);

		god.numeroJogadas++;
		
		god.colocaPeca(i, j); // posiciona a nova peca no tabuleiro e reverte as pecas da outra cor

		god.atualizaProximaJogada();

		if (god.jogadasPossiveis.isEmpty()) {

			god.atualizaProximaJogada();

			if (god.jogadasPossiveis.isEmpty()) {
				god.gameOver = true;
			}
		}
		
		return god;
	}

	@Override
	public Integer evaluate(StatusCasa jogador) {
		StatusCasa adversario = jogador == StatusCasa.PECA_BRANCA ? StatusCasa.PECA_BRANCA
				: StatusCasa.PECA_PRETA;
		return pecasJogadores.get(jogador).size()-pecasJogadores.get(adversario).size();
	}

	@Override
	public Jogador currentPlayer() {
		return jogadorVez;
	}

	@Override
	public boolean isGameOver() {
		return gameOver;
	}
	
	@Override
	public int getNumeroJogadas() {
		return numeroJogadas;
	}
	
	

	@Override
	public Board getClone(){
		God clone = new God();
		
		clone.jogadorVez = this.jogadorVez;
		clone.peca_preta = this.peca_preta;
		clone.peca_branca = this.peca_branca;
		
		for (Casa casa : this.jogadasPossiveis) {
			int i = casa.getI();
			int j = casa.getJ();

			clone.casas[i][j].setStatus(StatusCasa.JOGADA_POSSIVEL);
			clone.jogadasPossiveis.add(clone.casas[i][j]);
		}
		
		for (Entry<StatusCasa, HashSet<Casa>> entry : this.pecasJogadores.entrySet()) {
			for (Casa casa : entry.getValue()) {
				Casa casa_clone = clone.casas[casa.getI()][casa.getJ()];
				casa_clone.setStatus(casa.getStatus());
				clone.pecasJogadores.get(entry.getKey()).add(casa_clone);
			}
		}
		
		clone.numeroJogadas = numeroJogadas;
		clone.gameOver = gameOver;
		clone.tabuleiro = null;
		return clone;
	}
	
	@Override
	public HashMap<StatusCasa, HashSet<Casa>> getPecasJogadores() {
		return pecasJogadores;
	}

	public void jogarMaquina() {
		if(jogadorVez.getClass() != Humano.class){
			jogadorVez.jogar();
		}
	}

}
