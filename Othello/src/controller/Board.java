package controller;

import java.util.HashMap;
import java.util.HashSet;

import jogadores.Jogador;

import model.Casa;

public interface Board {
	public HashSet<Casa> getMoves();
	public Board makeMove(Casa casa);
	public Integer evaluate(StatusCasa jogador);
	public Jogador currentPlayer();
	public boolean isGameOver();
	public Board getClone();
	public int getNumeroJogadas();
	public HashMap<StatusCasa, HashSet<Casa>> getPecasJogadores();
}
