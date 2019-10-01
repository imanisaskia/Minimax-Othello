/* 22 September 2019 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Othello extends JFrame implements ActionListener{
	public static final String levels[] = {"1","2","3"};
	public static final String optionsButtons[] = {"Okay", "Cancel"};
	public static final int boxCount = 8;

	State gameState;
	tuple[] legalMoves;

	JLabel scoreWhiteLabel, scoreBlackLabel;
	int scoreWhite, scoreBlack;
	JPanel boardUI = new JPanel(); /** for game boardUI */
	JPanel info = new JPanel(); /** for info about the game states */
	JFrame title = new JFrame();
	JFrame over = new JFrame("It's Game Over !");
	JButton exitButton = new JButton("Exit");
	JPanel gameSelections = new JPanel();
	ButtonGroup bgmode = new ButtonGroup();
	JComboBox levelChoices = new JComboBox(levels);
	JButton buttons[][] = new JButton[boxCount+1][boxCount+1]; //button[0] tidak digunakan
	Icon iconBlackPiece = new ImageIcon(new ImageIcon("black_piece.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
	Icon iconWhitePiece = new ImageIcon(new ImageIcon("white_piece.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
	Icon iconShadow = new ImageIcon(new ImageIcon("shadow.png").getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT));

	public static void main(String[] args){
		Othello othello = new Othello();
		othello.setTitle();
	}

	/** set and show title frame */
	public void setTitle(){
		title.setSize(new Dimension(500,600));
		title.setResizable(false);
		title.setDefaultCloseOperation(EXIT_ON_CLOSE);
		title.setLayout(new BorderLayout());

		JLabel background = new JLabel(new ImageIcon(this.getClass().getResource("")));
		background.setPreferredSize(new Dimension(500,400));
		background.setLayout(new BorderLayout());

		JLabel titleLabel = new JLabel(new ImageIcon(this.getClass().getResource("othello title.png")));
		titleLabel.setPreferredSize(new Dimension(500, 100));
		background.add(titleLabel, BorderLayout.PAGE_START);

		JLabel titleLogo = new JLabel(new ImageIcon(this.getClass().getResource("background.jpg")));
		titleLogo.setPreferredSize(new Dimension(500, 200));
		background.add(titleLogo, BorderLayout.CENTER);		

		JPanel titleContent = new JPanel();
		titleContent.add(background); // Halo beb // 

		JButton playButton = new JButton("Play !");
		playButton.setActionCommand("play");
		playButton.addActionListener(this);
		titleContent.add(playButton);
		
		title.add(titleContent);

		title.setVisible(true);
	}

	/** set and show the game frame (board and score) */
	public void setGameFrame(){
		setSize(new Dimension (500,600));
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		info.setSize(new Dimension(500,100));
		info.setLayout(new BorderLayout());
		scoreWhiteLabel = new JLabel("White pieces count : 2");
		scoreWhiteLabel.setPreferredSize(new Dimension(500, 50));
		scoreBlackLabel = new JLabel("Black pieces count : 2");
		scoreBlackLabel.setPreferredSize(new Dimension(500, 50));
		info.add(scoreWhiteLabel, BorderLayout.PAGE_START);
		info.add(scoreBlackLabel, BorderLayout.LINE_START);
		add(info, BorderLayout.PAGE_START);

		boardUI.setSize(new Dimension(500,500));
		boardUI.setLayout(new GridLayout(boxCount, boxCount));
		for (int i =1; i <= boxCount; i++){
			for (int j = 1; j <= boxCount; j++){
				buttons[i][j] = new JButton();
				buttons[i][j].setActionCommand("button" + Integer.toString(i) + Integer.toString(j));
				buttons[i][j].addActionListener(this);
				boardUI.add(buttons[i][j]);
			}
		}
		
		add(boardUI, BorderLayout.CENTER);
		setVisible(true);
	}

	/** set and show the game over frame */
	public void setOver(){
		over.setSize(new Dimension(500,600));
		over.setResizable(false);
		over.setDefaultCloseOperation(EXIT_ON_CLOSE);
		over.setLayout(new BorderLayout());

		//ganti tulisan/gambar game over harusnya
		JLabel overLabel = new JLabel(new ImageIcon(this.getClass().getResource("background.jpg")));
		overLabel.setPreferredSize(new Dimension(500, 200));
		over.add(overLabel, BorderLayout.PAGE_START);

		JLabel endGameInfo = new JLabel("endGameInfo",SwingConstants.CENTER);
		//itung spa yang menang trus pake if 
		endGameInfo.setPreferredSize(new Dimension(500,100));
		endGameInfo.setText("YOU WON !");
		over.add(endGameInfo, BorderLayout.CENTER);
		setVisible(true);
	}

	/** show dialog box with radio button to decide which game play mode */
	public void setGameOptions(){
		gameSelections.setLayout(new BorderLayout());

		JLabel modeQuestions = new JLabel("Please select a game mode:", JLabel.LEFT);
		gameSelections.add(modeQuestions, BorderLayout.PAGE_START);

		JPanel modeOptions = new JPanel();
		modeOptions.setLayout(new BorderLayout());
		JRadioButton minimaxButton = new JRadioButton("VS Minimax");
		modeOptions.add(minimaxButton, BorderLayout.PAGE_START);
		minimaxButton.setSelected(true);
		minimaxButton.setActionCommand("minimax");
		JRadioButton randomButton = new JRadioButton("VS Random");
		modeOptions.add(randomButton, BorderLayout.CENTER);
		randomButton.setActionCommand("random");
		JRadioButton botsButton = new JRadioButton("MiniMax VS Random");
		modeOptions.add(botsButton, BorderLayout.PAGE_END);
		botsButton.setActionCommand("bots");
		gameSelections.add(modeOptions, BorderLayout.CENTER);
		bgmode.add(minimaxButton);
		bgmode.add(randomButton);
		bgmode.add(botsButton);

		JPanel levelOptions = new JPanel();
		levelOptions.setLayout(new BorderLayout());
		JLabel levelQuestions = new JLabel("Select a Level of Opponent:", JLabel.LEFT);
		levelOptions.add(levelQuestions, BorderLayout.LINE_START);
		
		
		levelChoices.setPreferredSize(new Dimension(50,30));
		levelOptions.add(levelChoices, BorderLayout.LINE_END);
		gameSelections.add(levelOptions, BorderLayout.PAGE_END);
	}

	/** action performed when button click */
	public void actionPerformed(ActionEvent e) {
		String act = e.getActionCommand();
		if (act == "play"){ /** Play button at the title frame get clicked */
			setGameOptions();
			int x = JOptionPane.showOptionDialog(null, gameSelections,"Mode and Level" , JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null, optionsButtons, optionsButtons[0]);
			System.out.println(levelChoices.getSelectedItem());
			if (x == 0){
				title.dispose();
				setGameFrame();
				gameState = new State();
				updateBoardPiece(gameState.getBoard());
				legalMoves = Algo.genLegalMoves(gameState);
				if (bgmode.getSelection().getActionCommand() == "bots"){
					mainBots();
				} else {
					nextMoveHint(legalMoves);
				}
			}
		}
		else 
		if (act.contains("button")){ /** boardUI button get clicked */
			System.out.println(act);
			int boxNum = Integer.valueOf(act.substring(act.indexOf("n") +1));
			int iBox = boxNum / 10;
			int jBox = boxNum % 10;
			tuple chosen = new tuple(iBox, jBox);
			if (isMoveAllowed(chosen, legalMoves)){
				gameState.changeState(new tuple(iBox, jBox));  /** update state from user input */
				updateBoardPiece(gameState.getBoard()); /** update boardUI */
				System.out.println(gameState.isGameOver());
				if (!gameState.isGameOver()){ /** not game over = bot's turn */
					tuple move;
					System.out.println("Mode nya : " + bgmode.getSelection().getActionCommand());
					switch (bgmode.getSelection().getActionCommand()){
						case ("minimax"):
							move = Algo.genMinimaxMove(gameState, gameState.getTurn() == 0);
							System.out.println(Integer.toString(move.i) + Integer.toString(move.j));
							gameState.changeState(move);
							updateBoardPiece(gameState.getBoard());
							legalMoves = Algo.genLegalMoves(gameState);
							nextMoveHint(legalMoves);
							if (legalMoves.length == 0 ){
								move = new tuple(9,9);
								gameState.changeState(move);
								updateBoardPiece(gameState.getBoard());
								legalMoves = Algo.genLegalMoves(gameState);
								nextMoveHint(legalMoves);
							}
							break;
						case ("random"):
							move = Algo.genRandomMove(gameState);
							gameState.changeState(move);
							updateBoardPiece(gameState.getBoard());
							legalMoves = Algo.genLegalMoves(gameState);
							nextMoveHint(legalMoves);
							if (legalMoves.length == 0 ){
								move = new tuple(9,9);
								gameState.changeState(move);
								updateBoardPiece(gameState.getBoard());
								legalMoves = Algo.genLegalMoves(gameState);
								nextMoveHint(legalMoves);
							}
							break;
					}
				} else { /** game over */
					setOver();
				}
			}
			//unenable button gt ?
		}
	}

	/** update boardUI and score */
	public void updateBoardPiece(String[][] state){
		for (int i = 1; i <= boxCount; i++){
			for (int j = 1; j <= boxCount; j++){
				//System.out.println(Integer.toString(i) + " " + Integer.toString(j) + " " + state[i][j]);
				System.out.print(state[i][j] + " ");
				if (state[i][j].equals("1")){
					buttons[i][j].setIcon(iconWhitePiece);
				} else 
				if (state[i][j].equals("0")){
					buttons[i][j].setIcon(iconBlackPiece);
				}
			}
			System.out.println();
		}
		updateScore(state);
		System.out.println();
		System.out.println();
	}

	/**update score */
	public void updateScore(String[][] state){
		scoreWhite = 0;
		scoreBlack = 0;
		for (int i = 1; i <= boxCount; i++){
			for (int j = 1; j <= boxCount; j++){
				if (state[i][j].equals("1")){
					scoreWhite++;
				} else 
				if (state[i][j].equals("0")){
					scoreBlack++;
				}
			}
			scoreWhiteLabel.setText("White pieces count : " + Integer.toString(scoreWhite));
			scoreBlackLabel.setText("Black pieces count : " + Integer.toString(scoreBlack));
		}
	}

	public void nextMoveHint(tuple[] legalMoves){
		for (int i = 0; i < legalMoves.length; i++ ){
			tuple box = legalMoves[i];
			buttons[box.i][box.j].setIcon(iconShadow);
		}
	}

	public boolean isMoveAllowed(tuple chosen, tuple[] legalMoves){
		int i = 0;
		boolean found = false;
		while (i < legalMoves.length && !found){
			if ((chosen.i == legalMoves[i].i) && (chosen.j == legalMoves[i].j)){
				found = true;
			} else {
				i++;
			}
		}
		return found;
	}

	public void mainBots(){
		try {
			while (!gameState.isGameOver()){
				Thread.sleep(5000);
				tuple move;
				move = Algo.genMinimaxMove(gameState, gameState.getTurn() == 0);
				gameState.changeState(move);
				updateBoardPiece(gameState.getBoard());
				Thread.sleep(2000);
				move = Algo.genRandomMove(gameState);
				gameState.changeState(move);
				updateBoardPiece(gameState.getBoard());
			}
		} catch(InterruptedException ex){
			Thread.currentThread().interrupt();
		}
	}



}