package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import jogadores.Aleatorio;
import jogadores.Guloso;
import jogadores.Humano;
import jogadores.Jogador;
import jogadores.Minimax;
import jogadores.Minimax2;
import jogadores.Minimax3;
import model.Casa;
import controller.God;
import controller.StatusCasa;

public class Tabuleiro {


	private static final int HUMANO = 0;
	private static final int ALEATORIO = 1;
	private static final int GULOSO = 2;
	private static final int MINIMAX = 3;
	private static final int MINIMAX2 = 4;
	private static final int MINIMAX3 = 5;

	
	private static Tabuleiro instance = new Tabuleiro();
	
	private JButton btnPrximaJogada;
	
	private JFrame frmReversi;
	private JPanel panel;
	
	private JLabel labelPontosPretas;
	private JLabel labelPontosBrancas;
	private JLabel labelJogador;

	private final Action sairAction = new SairAction();
	private final Action novoJogoAction = new NovoJogoAction();
	
	private final Action tipoJogadorPretasHumanoSelecionado = new TipoJogadorPretasHumanoSelecionadoAction();
	private final Action tipoJogadorPretasAleatorioSelecionado = new TipoJogadorPretasAleatorioSelecionadoAction();
	private final Action tipoJogadorPretasGulosoSelecionado = new TipoJogadorPretasGulosoSelecionadoAction();
	private final Action tipoJogadorPretasMinimaxSelecionado = new TipoJogadorPretasMinimaxSelecionadoAction();
	private final Action tipoJogadorPretasMinimax2Selecionado = new TipoJogadorPretasMinimax2SelecionadoAction();

	private final Action tipoJogadorBrancasHumanoSelecionado = new TipoJogadorBrancasHumanoSelecionadoAction();
	private final Action tipoJogadorBrancasAleatorioSelecionado = new TipoJogadorBrancasAleatorioSelecionadoAction();
	private final Action tipoJogadorBrancasGulosoSelecionado = new TipoJogadorBrancasGulosoSelecionadoAction();
	private final Action tipoJogadorBrancasMinimaxSelecionado = new TipoJogadorBrancasMinimaxSelecionadoAction();
	private final Action tipoJogadorBrancasMinimax2Selecionado = new TipoJogadorBrancasMinimax2SelecionadoAction();
	private Action tipoJogadorBrancasMinimax3Selecionado = new TipoJogadorBrancasMinimax3SelecionadoAction();
	private Action tipoJogadorPretasMinimax3Selecionado = new TipoJogadorPretasMinimax3SelecionadoAction();
	
	private JLabel lbTipoJogadorPretas;
	private JLabel lbTipoJogadorBrancas;
	
	private int tipoJogadorPretas=0;
	private int tipoJogadorBrancas=0;

	private Tabuleiro() {
		initializeView();
	}
	
	public static Tabuleiro getInstance() {
		return instance;
	}

	public void start() {
		frmReversi.setVisible(true);
		God.getInstance().inicializaJogo(new Humano(), new Guloso());
	}


	private void initializeView() {
	    try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	      } catch(Exception e) {
	        System.out.println("Error setting native LAF: " + e);
	      }
		
		frmReversi = new JFrame();
		frmReversi.setResizable(false);
		frmReversi.setTitle("Reversi");
		frmReversi.setBounds(100, 100, 500, 600);
		frmReversi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmReversi.getContentPane().setLayout(null);

		JLabel lblBrancas = new JLabel("BRANCAS");
		lblBrancas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBrancas.setHorizontalAlignment(SwingConstants.CENTER);
		lblBrancas.setBounds(362, 46, 83, 14);
		frmReversi.getContentPane().add(lblBrancas);

		labelPontosBrancas = new JLabel();
		labelPontosBrancas.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelPontosBrancas.setHorizontalAlignment(SwingConstants.CENTER);
		labelPontosBrancas.setBounds(362, 64, 83, 14);
		frmReversi.getContentPane().add(labelPontosBrancas);

		JLabel lblPretas = new JLabel("PRETAS");
		lblPretas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPretas.setHorizontalAlignment(SwingConstants.CENTER);
		lblPretas.setBounds(45, 46, 83, 14);
		frmReversi.getContentPane().add(lblPretas);

		labelPontosPretas = new JLabel();
		labelPontosPretas.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelPontosPretas.setHorizontalAlignment(SwingConstants.CENTER);
		labelPontosPretas.setBounds(45, 64, 83, 14);
		frmReversi.getContentPane().add(labelPontosPretas);

		panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(new Color(107, 142, 35));
		panel.setBounds(45, 100, 400, 400);
		frmReversi.getContentPane().add(panel);
		panel.setLayout(new GridLayout(8, 8, 0, 0));
		
		JLabel lblJogadorVez = new JLabel("JOGADOR VEZ");
		lblJogadorVez.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblJogadorVez.setHorizontalAlignment(SwingConstants.CENTER);
		lblJogadorVez.setBounds(189, 46, 107, 14);
		frmReversi.getContentPane().add(lblJogadorVez);
		
		labelJogador = new JLabel("");
		labelJogador.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelJogador.setHorizontalAlignment(SwingConstants.CENTER);
		labelJogador.setBounds(189, 64, 107, 14);
		frmReversi.getContentPane().add(labelJogador);
		
		btnPrximaJogada = new JButton("Pr\u00F3xima jogada");
		btnPrximaJogada.setEnabled(false);
		btnPrximaJogada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				God.getInstance().jogarMaquina();
			}
		});
		btnPrximaJogada.setBounds(160, 511, 136, 29);
		frmReversi.getContentPane().add(btnPrximaJogada);
		
		lbTipoJogadorPretas = new JLabel();
		lbTipoJogadorPretas.setText("HUMANO");
		lbTipoJogadorPretas.setHorizontalAlignment(SwingConstants.CENTER);
		lbTipoJogadorPretas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbTipoJogadorPretas.setBounds(45, 21, 83, 14);
		frmReversi.getContentPane().add(lbTipoJogadorPretas);
		
		lbTipoJogadorBrancas = new JLabel();
		lbTipoJogadorBrancas.setText("HUMANO");
		lbTipoJogadorBrancas.setHorizontalAlignment(SwingConstants.CENTER);
		lbTipoJogadorBrancas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbTipoJogadorBrancas.setBounds(362, 21, 83, 14);
		frmReversi.getContentPane().add(lbTipoJogadorBrancas);
		
		JMenuBar menuBar = new JMenuBar();
		frmReversi.setJMenuBar(menuBar);
		
		JMenu mnJogo = new JMenu("Jogo");
		menuBar.add(mnJogo);
		
		JMenuItem mnNovoJogo = new JMenuItem("Novo jogo");
		mnNovoJogo.setAction(novoJogoAction);
		mnJogo.add(mnNovoJogo);
			
		JMenu mnTipoJogadorPretas = new JMenu("Pe\u00E7as pretas");
		mnJogo.add(mnTipoJogadorPretas);
		
		ButtonGroup buttonGroupJogadorPretas = new ButtonGroup();
		JRadioButtonMenuItem radioJogadorPretasHumano = new JRadioButtonMenuItem("Humano");
		buttonGroupJogadorPretas.add(radioJogadorPretasHumano);
		radioJogadorPretasHumano.setSelected(true);
		radioJogadorPretasHumano.setAction( tipoJogadorPretasHumanoSelecionado );
		mnTipoJogadorPretas.add(radioJogadorPretasHumano);
		
		JRadioButtonMenuItem radioJogadorPretasAleatorio = new JRadioButtonMenuItem("Aleat\u00F3rio");
		radioJogadorPretasAleatorio.setAction( tipoJogadorPretasAleatorioSelecionado );
		buttonGroupJogadorPretas.add(radioJogadorPretasAleatorio);
		mnTipoJogadorPretas.add(radioJogadorPretasAleatorio);
		
		JRadioButtonMenuItem radioJogadorPretasGuloso = new JRadioButtonMenuItem("Guloso");
		radioJogadorPretasGuloso.setAction(tipoJogadorPretasGulosoSelecionado);
		buttonGroupJogadorPretas.add(radioJogadorPretasGuloso);
		mnTipoJogadorPretas.add(radioJogadorPretasGuloso);
		
		JRadioButtonMenuItem radioJogadorPretasMinimax = new JRadioButtonMenuItem("Minimax");
		radioJogadorPretasMinimax.setAction(tipoJogadorPretasMinimaxSelecionado);
		buttonGroupJogadorPretas.add(radioJogadorPretasMinimax);
		mnTipoJogadorPretas.add(radioJogadorPretasMinimax);
		
		JRadioButtonMenuItem radioJogadorPretasMinimax2 = new JRadioButtonMenuItem("Minimax2");
		radioJogadorPretasMinimax2.setAction(tipoJogadorPretasMinimax2Selecionado);
		buttonGroupJogadorPretas.add(radioJogadorPretasMinimax2);
		mnTipoJogadorPretas.add(radioJogadorPretasMinimax2);
		
		JRadioButtonMenuItem radioJogadorPretasMinimax3 = new JRadioButtonMenuItem("Minimax3");
		radioJogadorPretasMinimax3.setAction(tipoJogadorPretasMinimax3Selecionado);
		buttonGroupJogadorPretas.add(radioJogadorPretasMinimax3);
		mnTipoJogadorPretas.add(radioJogadorPretasMinimax3);
		
		JMenu mnTipoJogadorBrancas = new JMenu("Pe\u00E7as brancas");
		mnJogo.add(mnTipoJogadorBrancas);
		
		ButtonGroup buttonGroupJogadorBrancas = new ButtonGroup();
		JRadioButtonMenuItem radioJogadorBrancasHumano = new JRadioButtonMenuItem("Humano");
		radioJogadorBrancasHumano.setAction( tipoJogadorBrancasHumanoSelecionado );
		buttonGroupJogadorBrancas .add(radioJogadorBrancasHumano);
		radioJogadorBrancasHumano.setSelected(true);
		mnTipoJogadorBrancas.add(radioJogadorBrancasHumano);
		
		JRadioButtonMenuItem radioJogadorBrancasAleatorio = new JRadioButtonMenuItem("Aleat\u00F3rio");
		radioJogadorBrancasAleatorio.setAction(tipoJogadorBrancasAleatorioSelecionado);
		buttonGroupJogadorBrancas.add(radioJogadorBrancasAleatorio);
		mnTipoJogadorBrancas.add(radioJogadorBrancasAleatorio);
		
		JRadioButtonMenuItem radioJogadorBrancasGuloso = new JRadioButtonMenuItem("Guloso");
		radioJogadorBrancasGuloso.setAction(tipoJogadorBrancasGulosoSelecionado);
		buttonGroupJogadorBrancas.add(radioJogadorBrancasGuloso);
		mnTipoJogadorBrancas.add(radioJogadorBrancasGuloso);
		
		JRadioButtonMenuItem radioJogadorBrancasMinimax = new JRadioButtonMenuItem("Minimax");
		radioJogadorBrancasMinimax.setAction(tipoJogadorBrancasMinimaxSelecionado);
		buttonGroupJogadorBrancas.add(radioJogadorBrancasMinimax);
		mnTipoJogadorBrancas.add(radioJogadorBrancasMinimax);
		
		JRadioButtonMenuItem radioJogadorBrancasMinimax2 = new JRadioButtonMenuItem("Minimax2");
		radioJogadorBrancasMinimax2.setAction(tipoJogadorBrancasMinimax2Selecionado);
		buttonGroupJogadorBrancas.add(radioJogadorBrancasMinimax2);
		mnTipoJogadorBrancas.add(radioJogadorBrancasMinimax2);

		JRadioButtonMenuItem radioJogadorBrancasMinimax3 = new JRadioButtonMenuItem("Minimax3");
		radioJogadorBrancasMinimax3.setAction(tipoJogadorBrancasMinimax3Selecionado);
		buttonGroupJogadorBrancas.add(radioJogadorBrancasMinimax3);
		mnTipoJogadorBrancas.add(radioJogadorBrancasMinimax3);
		

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


	public void atualiza(final Casa casas[][], Jogador jogadorVez, int pontosBrancas, int pontosPretas) {		
		panel.removeAll();
		btnPrximaJogada.setEnabled(false);

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
					
					if(jogadorVez.getClass() == Humano.class){
						labelImage.addMouseListener(new JogadaDisponivelListener(i, j));
					}
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
		
		if(jogadorVez.getClass() != Humano.class){
			btnPrximaJogada.setEnabled(true);
		}
		
		labelJogador.setText("PEÇAS "
				+ (jogadorVez.cor == StatusCasa.PECA_BRANCA ? "BRANCAS" : "PRETAS"));
		
		labelPontosBrancas.setText(Integer.toString(pontosBrancas));
		labelPontosPretas.setText(Integer.toString(pontosPretas));
	}
	
	
	public void semJogadasPossiveis(Jogador adversario) {
		String mensagem =	"As peças " +
				(adversario.cor == StatusCasa.PECA_BRANCA ? "brancas" : "pretas") +
				" não possuem movimentos!\n" +
				"As peças " + (adversario.cor == StatusCasa.PECA_PRETA ? "brancas" : "pretas") +
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

			Jogador jogadorPretas, jogadorBrancas;
			
			switch (tipoJogadorPretas) {
			case HUMANO:
				jogadorPretas = new Humano();
				lbTipoJogadorPretas.setText("HUMANO");
				break;
			case ALEATORIO:
				jogadorPretas = new Aleatorio();
				lbTipoJogadorPretas.setText("ALEAT\u00D3RIO");
				break;
			case GULOSO:
				jogadorPretas = new Guloso();
				lbTipoJogadorPretas.setText("GULOSO");
				break;
			case MINIMAX:
				jogadorPretas = new Minimax();
				lbTipoJogadorPretas.setText("MINIMAX");
				break;
			case MINIMAX2:
				jogadorPretas = new Minimax2();
				lbTipoJogadorPretas.setText("MINIMAX2");
				break;
			case MINIMAX3:
				jogadorPretas = new Minimax3();
				lbTipoJogadorPretas.setText("MINIMAX3");
				break;

			default:
				jogadorPretas = new Jogador();
				break;
			}
			
			switch (tipoJogadorBrancas) {
			case HUMANO:
				jogadorBrancas = new Humano();
				lbTipoJogadorBrancas.setText("HUMANO");
				break;
			case ALEATORIO:
				jogadorBrancas = new Aleatorio();
				lbTipoJogadorBrancas.setText("ALEAT\u00D3RIO");
				break;
			case GULOSO:
				jogadorBrancas = new Guloso();
				lbTipoJogadorBrancas.setText("GULOSO");
				break;
			case MINIMAX:
				jogadorBrancas = new Minimax();
				lbTipoJogadorBrancas.setText("MINIMAX");
				break;
			case MINIMAX2:
				jogadorBrancas = new Minimax2();
				lbTipoJogadorBrancas.setText("MINIMAX2");
				break;
			case MINIMAX3:
				jogadorBrancas = new Minimax3();
				lbTipoJogadorBrancas.setText("MINIMAX3");
				break;
			default:
				jogadorBrancas = new Jogador();
				break;
			}
			
			God.getInstance().inicializaJogo(jogadorPretas, jogadorBrancas);

			panel.revalidate();
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

	private class TipoJogadorPretasHumanoSelecionadoAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public TipoJogadorPretasHumanoSelecionadoAction() {
			putValue(NAME, "Humano");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			tipoJogadorPretas = HUMANO;
		}
	}

	private class TipoJogadorPretasAleatorioSelecionadoAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public TipoJogadorPretasAleatorioSelecionadoAction() {
			putValue(NAME, "Aleat\u00F3rio");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			tipoJogadorPretas = ALEATORIO;
		}
	}

	private class TipoJogadorPretasGulosoSelecionadoAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public TipoJogadorPretasGulosoSelecionadoAction() {
			putValue(NAME, "Guloso");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			tipoJogadorPretas = GULOSO;
		}
	}

	private class TipoJogadorPretasMinimaxSelecionadoAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public TipoJogadorPretasMinimaxSelecionadoAction() {
			putValue(NAME, "Minimax");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			tipoJogadorPretas = MINIMAX;
		}
	}
	private class TipoJogadorPretasMinimax2SelecionadoAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public TipoJogadorPretasMinimax2SelecionadoAction() {
			putValue(NAME, "Minimax2");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			tipoJogadorPretas = MINIMAX2;
		}
	}
	private class TipoJogadorPretasMinimax3SelecionadoAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public TipoJogadorPretasMinimax3SelecionadoAction() {
			putValue(NAME, "Minimax3");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			tipoJogadorPretas = MINIMAX3;
		}
	}
	private class TipoJogadorBrancasHumanoSelecionadoAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public TipoJogadorBrancasHumanoSelecionadoAction() {
			putValue(NAME, "Humano");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			tipoJogadorBrancas = HUMANO;
		}
	}

	private class TipoJogadorBrancasAleatorioSelecionadoAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public TipoJogadorBrancasAleatorioSelecionadoAction() {
			putValue(NAME, "Aleat\u00F3rio");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			tipoJogadorBrancas = ALEATORIO;
		}
	}

	private class TipoJogadorBrancasGulosoSelecionadoAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public TipoJogadorBrancasGulosoSelecionadoAction() {
			putValue(NAME, "Guloso");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			tipoJogadorBrancas = GULOSO;
		}
	}

	private class TipoJogadorBrancasMinimaxSelecionadoAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public TipoJogadorBrancasMinimaxSelecionadoAction() {
			putValue(NAME, "Minimax");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			tipoJogadorBrancas = MINIMAX;
		}
	}

	private class TipoJogadorBrancasMinimax2SelecionadoAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public TipoJogadorBrancasMinimax2SelecionadoAction() {
			putValue(NAME, "Minimax2");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			tipoJogadorBrancas = MINIMAX2;
		}
	}
	private class TipoJogadorBrancasMinimax3SelecionadoAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		public TipoJogadorBrancasMinimax3SelecionadoAction() {
			putValue(NAME, "Minimax3");
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			tipoJogadorBrancas = MINIMAX3;
		}
	}

}
