/* 22 September 2019 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Othello extends JFrame implements ActionListener{
	public static final String levels[] = {"1","2","3"};
	public static final String optionsButtons[] = {"Okay", "Cancel"};
	public static final int boxCount = 8;

	State gameState;

	JLabel scoreWhiteLabel, scoreBlackLabel;
	int scoreWhite, scoreBlack;
	JPanel boardUI = new JPanel(); /*for game boardUI*/
	JPanel info = new JPanel(); /*for info about the game states*/
	JFrame title = new JFrame();
	JFrame over = new JFrame("It's Game Over !");
	JButton exitButton = new JButton("Exit");
	JPanel gameSelections = new JPanel();
	ButtonGroup bgmode = new ButtonGroup();
	JComboBox levelChoices = new JComboBox(levels);
	JButton buttons[][] = new JButton[boxCount+1][boxCount+1]; //button[0] tidak digunakan
	Icon iconBlackPiece = new ImageIcon(new ImageIcon("black_piece.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
	Icon iconWhitePiece = new ImageIcon(new ImageIcon("white_piece.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
	
	ImageIcon iconWhite = new ImageIcon(this.getClass().getResource("black_piece.png"));
	ImageIcon iconBlack = new ImageIcon(this.getClass().getResource("white_piece.png"));

	public static void main(String[] args){
		Othello othello = new Othello();
		othello.setTitle();
	}

	public void setTitle(){
		title.setSize(new Dimension(500,600));
		title.setResizable(false);
		title.setDefaultCloseOperation(EXIT_ON_CLOSE);
		title.setLayout(new BorderLayout());

		JLabel background = new JLabel(new ImageIcon(this.getClass().getResource("")));
		background.setPreferredSize(new Dimension(500,400));
		background.setLayout(new BorderLayout());

		JLabel titleLabel = new JLabel(new ImageIcon(this.getClass().getResource("background.jpg")));
		titleLabel.setPreferredSize(new Dimension(500, 200));
		background.add(titleLabel, BorderLayout.PAGE_START);

		JPanel titleContent = new JPanel();
		titleContent.add(background); // Halo beb // 

		JButton playButton = new JButton("Play !");
		playButton.setActionCommand("play");
		playButton.addActionListener(this);
		titleContent.add(playButton);
		
		title.add(titleContent);

		title.setVisible(true);
	}

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
	}

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

	public void actionPerformed(ActionEvent e) {
		String act = e.getActionCommand();
		if (act == "play"){
			setGameOptions();
			int x = JOptionPane.showOptionDialog(null, gameSelections,"Mode and Level" , JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null, optionsButtons, optionsButtons[0]);
			System.out.println(levelChoices.getSelectedItem());
			if (x == 0){
				title.dispose();
				setGameFrame();
				gameState = new State();
				updateBoardPiece(gameState.getBoard());
				if (bgmode.getSelection().getActionCommand() == "bots"){
					//fungsi bots vs bots
				}
			}
		}
		else 
		if (act.contains("button")){
			System.out.println(act);
			int boxNum = Integer.valueOf(act.substring(act.indexOf("n") +1));
			int iBox = boxNum / 10;
			int jBox = boxNum % 10;
			gameState.changeState(new tuple(iBox, jBox));
			updateBoardPiece(gameState.getBoard());
			System.out.println(gameState.isGameOver());
			if (!gameState.isGameOver()){
				tuple move;
				System.out.println("Mode nya : " + bgmode.getSelection().getActionCommand());
				switch (bgmode.getSelection().getActionCommand()){
					case ("minimax"):
						move = Algo.genMinimaxMove(gameState, gameState.getTurn() == 0);
						System.out.println(Integer.toString(move.i) + Integer.toString(move.j));
						gameState.changeState(move);
						updateBoardPiece(gameState.getBoard());
						break;
					case ("random"):
						move = Algo.genRandomMove(gameState);
						gameState.changeState(move);
						updateBoardPiece(gameState.getBoard());
						break;
				}
			}
			//unenable button gt ?
		}
	}

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

	public void mainBots(){
		//disable all buttons
	}



}