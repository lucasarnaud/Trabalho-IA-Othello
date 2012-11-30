package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Casa;
import controller.God;
import controller.StatusCasa;

public class Tabuleiro {

	private static Tabuleiro instance = new Tabuleiro();
	
	private JFrame frmReversi;
	private JPanel panel;
	private JLabel labelPontosPretas;
	private JLabel labelPontosBrancas;
	private JLabel lblJogadorVez;
	private JLabel labelJogador;

	

	private Tabuleiro() {
		initializeView();
	}
	
	public static Tabuleiro getInstance() {
		if (instance == null) {
			instance = new Tabuleiro();
		}
		return instance;
	}

	public void start() {
		frmReversi.setVisible(true);
	}


	private void initializeView() {
		frmReversi = new JFrame();
		frmReversi.setTitle("Reversi");
		frmReversi.setBounds(100, 100, 600, 600);
		frmReversi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmReversi.getContentPane().setLayout(null);

		JLabel lblBrancas = new JLabel("BRANCAS");
		lblBrancas.setBounds(90, 35, 61, 14);
		frmReversi.getContentPane().add(lblBrancas);

		labelPontosBrancas = new JLabel();
		labelPontosBrancas.setBounds(90, 60, 46, 14);
		frmReversi.getContentPane().add(labelPontosBrancas);

		JLabel lblPretas = new JLabel("PRETAS");
		lblPretas.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPretas.setBounds(429, 35, 61, 14);
		frmReversi.getContentPane().add(lblPretas);

		labelPontosPretas = new JLabel();
		labelPontosPretas.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPontosPretas.setBounds(444, 60, 46, 14);
		frmReversi.getContentPane().add(labelPontosPretas);

		panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(new Color(107, 142, 35));
		panel.setBounds(90, 114, 400, 400);
		frmReversi.getContentPane().add(panel);
		panel.setLayout(new GridLayout(8, 8, 0, 0));
		
		lblJogadorVez = new JLabel("JOGADOR VEZ");
		lblJogadorVez.setHorizontalAlignment(SwingConstants.CENTER);
		lblJogadorVez.setBounds(226, 35, 107, 14);
		frmReversi.getContentPane().add(lblJogadorVez);
		
		labelJogador = new JLabel("");
		labelJogador.setHorizontalAlignment(SwingConstants.CENTER);
		labelJogador.setBounds(226, 60, 107, 14);
		frmReversi.getContentPane().add(labelJogador);
	}


	public void atualiza(final Casa casas[][], StatusCasa jogador) {
		panel.removeAll();
		int pontosBrancas = 0;
		int pontosPretas= 0;

		for (int i = 0; i < casas.length; i++) {
			final int iFinal = i; 
			
			for (int j = 0; j < casas.length; j++) {
				final int jFinal = j; 
				
				JLabel labelImage;
				ImageIcon imageIcon;

				switch (casas[i][j].getStatus()) {
				case PECA_BRANCA:
					imageIcon = new ImageIcon("img/peca_branca.png");
					labelImage = new JLabel(imageIcon);
					pontosBrancas++;
					break;
				case PECA_PRETA:
					imageIcon = new ImageIcon("img/peca_preta.png");
					labelImage = new JLabel(imageIcon);
					pontosPretas++;
					break;

				case JOGADA_POSSIVEL:
					imageIcon = new ImageIcon("img/jogada_possivel.png");
					labelImage = new JLabel(imageIcon);
					
					labelImage.addMouseListener(new MouseListener() {
						
						@Override
						public void mouseReleased(MouseEvent arg0) {
						}
						
						@Override
						public void mousePressed(MouseEvent arg0) {
						}
						
						@Override
						public void mouseExited(MouseEvent arg0) {
						}
						
						@Override
						public void mouseEntered(MouseEvent arg0) {
						}
						
						@Override
						public void mouseClicked(MouseEvent arg0) {
							God.getInstance().jogar(iFinal, jFinal);
						}
					});
					
					break;
					
				default:
					labelImage = new JLabel();
					break;
				}
				labelImage.setSize(50, 50);
				labelImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
				panel.add(labelImage);	
			}
		}
		
		labelJogador.setText(jogador == StatusCasa.PECA_PRETA ? "PEÇA PRETA" : "PEÇA BRANCA");
		
		labelPontosBrancas.setText(String.valueOf(pontosBrancas));
		labelPontosPretas.setText(String.valueOf(pontosPretas));

	}

}
