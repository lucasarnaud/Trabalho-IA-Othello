package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import model.Casa;
import controller.God;
import controller.StatusCasa;

public class Tabuleiro {

	private static Tabuleiro instance = new Tabuleiro();
	
	private JFrame frmReversi;
	private JPanel panel;
	private JLabel labelPontosPretas;
	private JLabel labelPontosBrancas;
	private JLabel labelJogador;
	private boolean podeComecarJogo;
	private final Action novoJogoAction = new NovoJogoAction();
	private final Action sairAction = new SairAction();

	

	private Tabuleiro() {
		initializeView();
	}
	
	public static Tabuleiro getInstance() {
		return instance;
	}

	public void start() {
		frmReversi.setVisible(true);
		God.getInstance().inicializaJogo();
		podeComecarJogo = false;
	}


	private void initializeView() {
	    try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	      } catch(Exception e) {
	        System.out.println("Error setting native LAF: " + e);
	      }
		
		frmReversi = new JFrame();
		frmReversi.setTitle("Reversi");
		frmReversi.setBounds(100, 100, 500, 550);
		frmReversi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmReversi.getContentPane().setLayout(null);

		JLabel lblBrancas = new JLabel("BRANCAS");
		lblBrancas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBrancas.setHorizontalAlignment(SwingConstants.CENTER);
		lblBrancas.setBounds(40, 21, 52, 14);
		frmReversi.getContentPane().add(lblBrancas);

		labelPontosBrancas = new JLabel();
		labelPontosBrancas.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelPontosBrancas.setHorizontalAlignment(SwingConstants.CENTER);
		labelPontosBrancas.setBounds(40, 39, 52, 14);
		frmReversi.getContentPane().add(labelPontosBrancas);

		JLabel lblPretas = new JLabel("PRETAS");
		lblPretas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPretas.setHorizontalAlignment(SwingConstants.CENTER);
		lblPretas.setBounds(388, 21, 52, 14);
		frmReversi.getContentPane().add(lblPretas);

		labelPontosPretas = new JLabel();
		labelPontosPretas.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelPontosPretas.setHorizontalAlignment(SwingConstants.CENTER);
		labelPontosPretas.setBounds(388, 39, 52, 14);
		frmReversi.getContentPane().add(labelPontosPretas);

		panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(new Color(107, 142, 35));
		panel.setBounds(40, 64, 400, 400);
		frmReversi.getContentPane().add(panel);
		panel.setLayout(new GridLayout(8, 8, 0, 0));
		
		JLabel lblJogadorVez = new JLabel("JOGADOR VEZ");
		lblJogadorVez.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblJogadorVez.setHorizontalAlignment(SwingConstants.CENTER);
		lblJogadorVez.setBounds(189, 21, 107, 14);
		frmReversi.getContentPane().add(lblJogadorVez);
		
		labelJogador = new JLabel("");
		labelJogador.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelJogador.setHorizontalAlignment(SwingConstants.CENTER);
		labelJogador.setBounds(189, 39, 107, 14);
		frmReversi.getContentPane().add(labelJogador);
		
		JMenuBar menuBar = new JMenuBar();
		frmReversi.setJMenuBar(menuBar);
		
		JMenu mnJogo = new JMenu("Jogo");
		menuBar.add(mnJogo);
		
		JMenuItem mntmReiniciar = new JMenuItem("Reiniciar");
		mntmReiniciar.setAction(novoJogoAction);
		mnJogo.add(mntmReiniciar);
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.setAction(sairAction);
		mnJogo.add(mntmSair);
		
		for (int i = 0; i < God.TAMANHO_TABULEIRO; i++) {
			for (int j = 0; j < God.TAMANHO_TABULEIRO; j++) {
				JLabel label = new JLabel();
				label.setSize(50, 50);
				label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				
				panel.add(label);	
			}
		}
	}


	public void atualiza(final Casa casas[][], StatusCasa jogador,
			int pontosBrancas, int pontosPretas) {
		panel.removeAll();

		for (int i = 0; i < casas.length; i++) {
			
			for (int j = 0; j < casas[i].length; j++) {
		
				JLabel labelImage;
				ImageIcon imageIcon;

				switch (casas[i][j].getStatus()) {
				case PECA_BRANCA:
					imageIcon = new ImageIcon("img/peca_branca.png");
					labelImage = new JLabel(imageIcon);
					break;
				case PECA_PRETA:
					imageIcon = new ImageIcon("img/peca_preta.png");
					labelImage = new JLabel(imageIcon);
					break;

				case JOGADA_POSSIVEL:
					imageIcon = new ImageIcon("img/jogada_possivel.png");
					labelImage = new JLabel(imageIcon);
										
					labelImage.addMouseListener(new JogadaDisponivelListener(i, j));

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
		
		labelJogador.setText("PEÇAS "
				+ (jogador == StatusCasa.PECA_BRANCA ? "BRANCAS" : "PRETAS"));
		
		labelPontosBrancas.setText(Integer.toString(pontosBrancas));
		labelPontosPretas.setText(Integer.toString(pontosPretas));

	}
	
	
	public void semJogadasPossiveis(StatusCasa jogador) {
		String mensagem =	"As peças " +
				(jogador == StatusCasa.PECA_BRANCA ? "brancas" : "pretas") +
				" não possuem movimentos!\n" +
				"As peças " + (jogador == StatusCasa.PECA_PRETA ? "brancas" : "pretas") +
				" jogarão novamente!";

		JOptionPane.showMessageDialog(frmReversi, mensagem, "Sem jogadas possíveis",
				JOptionPane.WARNING_MESSAGE);
	}
	
	
	public void fimDeJogo(StatusCasa jogadorVencedor) {
		String mensagem;
		
		switch (jogadorVencedor) {
		case PECA_BRANCA:
			mensagem = "As peças brancas venceram!";
			break;
		case PECA_PRETA:
			mensagem = "As peças pretas venceram!";
			break;
		default:
			mensagem = "O jogo terminou empatado!";
			break;
		}
		
		JOptionPane.showMessageDialog(frmReversi, mensagem, "Fim de jogo",
				JOptionPane.PLAIN_MESSAGE);
	}
	
	
	private class JogadaDisponivelListener implements MouseListener {
		private int i, j;
		public JogadaDisponivelListener(int i, int j) {
			this.i = i;
			this.j = j;
			podeComecarJogo = true;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			God.getInstance().jogar(i, j);
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}
	
	private class NovoJogoAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public NovoJogoAction() {
			putValue(NAME, "Novo jogo");
		}
		public void actionPerformed(ActionEvent e) {
			if (podeComecarJogo) {
				God.getInstance().inicializaJogo();
				podeComecarJogo = false;
			}
		}
	}
	private class SairAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public SairAction() {
			putValue(NAME, "Sair");
		}
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
}
