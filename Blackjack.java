/** 
 *
 * @author mcarl2017
 */

// Michael Mullings
// mcm14n
// COP3252 - Java Programming
// Game Project - Blackjack

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;



public class Blackjack implements java.io.Serializable{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
		      System.out.println("Welcome To Tally Blackjack!");
                BlackjackUI UI = new BlackjackUI();
		      String name = JOptionPane.showInputDialog(null, "Enter Your GamerTag!","Welcome To Tally Blackjack", JOptionPane.QUESTION_MESSAGE);
		      UI.setGamerTag(name);
		      JOptionPane.showMessageDialog(null, "Place Your Bet!");

            }
        });
    }

}

class BlackjackUI extends JFrame  implements java.io.Serializable{

    private Image bg;
    private BlackjackBGPanel bgpanel;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu helpMenu;
    private JMenuItem openMenuItem;
    private JMenuItem newMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem exitMenuItem;
    private JMenuItem contentsMenuItem;
    private JMenuItem aboutMenuItem;
    private JMenuItem viewMenuItem;
    private GridBagConstraints c;
    private String gamertag;
    private Leaderboard leaderboard;

    public BlackjackUI() {
        super();

        bgpanel = new BlackjackBGPanel();
        bgpanel.setVisible(true);
        getContentPane().add(bgpanel);

        setBackground(new Color(0, 0, 0));
        setMinimumSize(new Dimension(825,645));
        setPreferredSize(new Dimension(825, 645));
        setMaximumSize(new Dimension(825, 645));
        setResizable(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Blackjack");
        setLocationRelativeTo(null);
        initMenuBar();
        setVisible(true);
        pack();

	    leaderboard = new Leaderboard();
	
	
    }

    public void initMenuBar() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        helpMenu = new JMenu();
        openMenuItem = new JMenuItem();
        newMenuItem = new JMenuItem();
        saveMenuItem = new JMenuItem();
        exitMenuItem = new JMenuItem();
        contentsMenuItem = new JMenuItem();
        aboutMenuItem = new JMenuItem();
	    viewMenuItem = new JMenuItem();
        setMenuItems();

    }

    public void setMenuItems() {
        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        newMenuItem.setMnemonic('n');
        newMenuItem.setText("New");
        fileMenu.add(newMenuItem);

        openMenuItem.setMnemonic('l');
        openMenuItem.setText("Load");
        fileMenu.add(openMenuItem);

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setMnemonic('l');
        helpMenu.setText("Leaderboard");

        contentsMenuItem.setMnemonic('s');
        contentsMenuItem.setText("Stats");
        helpMenu.add(contentsMenuItem);

        aboutMenuItem.setMnemonic('p');
        aboutMenuItem.setText("Post");
        helpMenu.add(aboutMenuItem);
	
    	viewMenuItem.setMnemonic('v');
    	viewMenuItem.setText("View");
    	helpMenu.add(viewMenuItem);

        menuBar.add(helpMenu);

        setMenuActions();

        setJMenuBar(menuBar);

    }

    public void setMenuActions() {

        newMenuItem.addActionListener(new MenuAction1());
        openMenuItem.addActionListener(new MenuAction2());
        saveMenuItem.addActionListener(new MenuAction3());
        exitMenuItem.addActionListener(new MenuAction4());
        contentsMenuItem.addActionListener(new MenuAction5());
        aboutMenuItem.addActionListener(new MenuAction6());
	    viewMenuItem.addActionListener(new MenuAction7());

    }

    class MenuAction1 implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent evt) {
            newGame();
        }
    }
    class MenuAction2 implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent evt) {
            loadGameAction();
        }
    }
    class MenuAction3 implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent evt) {
            saveGame();
        }
    }
    class MenuAction4 implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent evt) {
                System.exit(0);
        }
    }
    class MenuAction5 implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent evt) {
            displayStats();
        }
    }
    class MenuAction6 implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent evt) {
            postLeaderboard();
         }
    }
    class MenuAction7 implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent evt) {
            viewLeaderboard();
        }
    }


    public void setGamerTag(String gt){
       gamertag = gt;
       if(gamertag == null)
            gamertag = "";
	   bgpanel.getActionPanel().setGamerId(gamertag);
    }

    public void newGame(){
    	bgpanel.getActionPanel().newGame();
    
    }

    public void saveGame(){
	    Date date = new Date();
	    String file = bgpanel.getActionPanel().gamertag + " " + date.toString();
	    bgpanel.saveGame(file);
    
    }


    public void loadGameAction(){
    
    	java.util.Vector<String> fileNames = new java.util.Vector<String>();

	File [] files = new File("./gameData/savedData").listFiles();

	for(File file : files){
	    if(file.isFile()){
	    	fileNames.add(file.getName());
	    }
	
	}
	
	if(fileNames.size() > 0){
		String [] fn = new String[fileNames.size()];
		for(int i =0; i < fileNames.size(); i++)
			fn[i] = fileNames.get(i);
	
		String loadFile = (String)JOptionPane.showInputDialog(null, 
				"Select a Saved File!",
				"Load Game",
				JOptionPane.INFORMATION_MESSAGE,
				null,
				fn,
				fn[0]);
        if(loadFile != null)
		  loadGame(loadFile);
	}else{
		JOptionPane.showMessageDialog(null, "No Saved Games");
	
	}
    }


    public void loadGame(String filename){
    	   bgpanel.loadGame(filename);
    
    }

    public void displayStats(){
    	bgpanel.getActionPanel().displayStats();
    }

    
    public void postLeaderboard(){
    	java.io.File lb = new File("./gameData/leaderboardData/leaderboard.ser");
	if(lb.exists() && !lb.isDirectory()){
		Leaderboard lbmap = null;
		try{
		    java.io.FileInputStream fileIn = new java.io.FileInputStream("./gameData/leaderboardData/leaderboard.ser");
		    java.io.ObjectInputStream in = new java.io.ObjectInputStream(fileIn);
		     lbmap = (Leaderboard) in.readObject();
		     in.close();
		     fileIn.close();

		     leaderboard.leaderboard.putAll(lbmap.leaderboard);
		}catch(Exception ex){
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable to Post to Leaderboard.");
		
		}	
	}

	leaderboard.leaderboard.put(bgpanel.getActionPanel().getPoints(), bgpanel.getActionPanel().getGamerTag());

	try{
		java.io.FileOutputStream fileOut = new java.io.FileOutputStream("./gameData/leaderboardData/leaderboard.ser");
		java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(fileOut);
		out.writeObject(leaderboard);
        out.flush();
		out.close();
		fileOut.close();
	
	}catch(Exception ex){
		ex.printStackTrace();
		JOptionPane.showMessageDialog(null, "Sorry, Unable to Post to Leaderboard.");
	}
    
    }

    public void viewLeaderboard(){
    	java.io.File lb = new File("./gameData/leaderboardData/leaderboard.ser");
	if(lb.exists() && !lb.isDirectory()){
		Leaderboard lbmap = null;
		try{
		    java.io.FileInputStream fileIn = new java.io.FileInputStream("./gameData/leaderboardData/leaderboard.ser");
		    java.io.ObjectInputStream in = new java.io.ObjectInputStream(fileIn);
		     lbmap = (Leaderboard) in.readObject();
		     in.close();
		     fileIn.close();

		     leaderboard.leaderboard.putAll(lbmap.leaderboard);
		
		     int i = 0;
		     String scores = "";
		     for(Map.Entry<Integer, String> mapData : leaderboard.leaderboard.entrySet()) {

                         scores = scores + (i+1) + ". " + mapData.getValue() + "     " + mapData.getKey() + "\n";
        		 i++;
			if(i > 4)
			  break;
       		      }
			

		     JOptionPane.showMessageDialog(null, 
			 scores,
			" Blackjack Leaderboard", 
			JOptionPane.PLAIN_MESSAGE);
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, "No Leaderboard Views Available.");
		
		}	
	}else{
		JOptionPane.showMessageDialog(null, "No Leaderboard Scores Available.");
	
	}
    
    }

}

class Leaderboard implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	public TreeMap<Integer,String> leaderboard;

	public Leaderboard(){
		leaderboard = new TreeMap<>(
		(Comparator<Integer> & java.io.Serializable)(num1, num2) ->{
			return num2.compareTo(num1);
		});

	}
}


class BlackjackBGPanel extends JPanel implements java.io.Serializable{

    private transient BufferedImage bg;
    private BlackjackActionPanel ap;

    public BlackjackBGPanel() {
        super();
        setMinimumSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800,600));
        setSize(new Dimension(800, 600));
        setMaximumSize(new Dimension(800, 600));
        try{
            ap = new BlackjackActionPanel();
            bg = ImageIO.read(new File("assets/table/BlackJackTable.png"));
            bgpaint();
            this.add(ap);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public BlackjackActionPanel getActionPanel(){
    
    	return ap;
    }

    public void saveGame(String filename){
    	

    	try{
            ap.saveBlackjack(filename);
            JOptionPane.showMessageDialog(null, "Game Saved.");
    	}catch(Exception e){
            e.printStackTrace();
    		JOptionPane.showMessageDialog(null, "Unable to Save Game.");
    		
    	}
    }

    public void loadGame(String filename){
        try{
            ap.loadBlackjack(filename);
            JOptionPane.showMessageDialog(null, "Load Game Successful.");
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Unable to Load Game.");
            
        }
    
    }

    public void bgpaint() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 10, 10, 800, 600, this);

    }

}

class BlackjackActionPanel extends JPanel implements java.io.Serializable {
    private static final long serialVersionUID = 2L;
	
    private BlackjackGame game;
    private int wins, losses, draws, counter, winner, points, gains, lost, pointsAdded;
    public String gamertag;
    private transient JButton hitButton;
    private transient JButton standButton;
    private transient JButton doubleButton;
    private transient JButton splitButton;

    private transient JButton betButton;
    private transient JButton clearButton;
    private transient JButton surButton;

    private transient JButton [] wagerButton;

    private transient JLabel balanceLabel;
    private transient JLabel betLabel;
    private transient JLabel value1, value2, value3;

    private transient JToggleButton hitToggle;

    private transient CardDrawAnimation [] playerCards;
    private transient CardDrawAnimation [] dealerCards;

    public transient BufferedImage cardFaceDown;
    private int playerHand1CC, dealerCC;
    private transient JPanel self, me, my, myself;
    private int pxplot, pyplot, dxplot, dyplot, p1xplot, p1yplot;
    private int pxdx, pydy, dxdx, dydy, p1dx, p1dy;
    private boolean bool;
    private transient java.util.Timer time;

    public BlackjackActionPanel(){
        super();
        setLayout(null);
        init();
        setButtons();
        setButtonActions();
        setLabels();
		initDisableBTNS();

        setMinimumSize(new Dimension(800,600));
        setPreferredSize(new Dimension(800,600));
        setMaximumSize(new Dimension(800, 600));

        setVisible(true);
        setOpaque(false);
    }

    public void init(){
		game = new BlackjackGame();

        hitButton = new JButton();
        standButton = new JButton();
        doubleButton = new JButton();
        splitButton = new JButton();

        betButton = new JButton();
        clearButton = new JButton();
        surButton = new JButton();

        wagerButton = new JButton[4];
        wagerButton[0] = new JButton();
        wagerButton[1] = new JButton();
        wagerButton[2] = new JButton();
        wagerButton[3] = new JButton();

        balanceLabel = new JLabel();
        betLabel = new JLabel();
        value1 = new JLabel();
        value2 = new JLabel();
        value3 = new JLabel();

        hitToggle = new JToggleButton();

        playerHand1CC = 0;
	    dealerCC = 0;
        self = this;
        me = this;
        my = this;
	    myself = this;
        playerCards = new CardDrawAnimation[30];
		dealerCards = new CardDrawAnimation[30];
        pxplot = 150;
        pyplot = 250;
        pxdx = 8;
        pydy = 4;
        dxplot = 440;
        dyplot = 20;
        dxdx = 8;
        dydy = 0;
        p1xplot = 280;
        p1yplot = 260;
        p1dx = 4;
        p1dy = 8;
	    counter = 0;
    	wins = 0;
    	losses = 0;
    	draws = 0;
    	bool = false;
    	points = 0;
    	gains = 0;
    	lost = 0;
    	pointsAdded = 0;
    	time = new java.util.Timer();
    }

    public void setGamerId(String gt){
    	gamertag = gt;
        game.setPlayerName(gamertag);
    }

    public void setButtons(){
      // hitToggle
        hitToggle.setBackground(new java.awt.Color(0, 0, 0));
        hitToggle.setFont(new java.awt.Font("Times New Roman", 1, 14));
        hitToggle.setForeground(new java.awt.Color(255, 255, 255));
        hitToggle.setText("Hand 1");
        hitToggle.setToolTipText("<html>\n<pre>\nPress to toggle between hands\n</pre>\n<html>");
        this.add(hitToggle);
        hitToggle.setSelected(false);
        hitToggle.setBounds(680, 440, 110, 30);

        // hit button
        hitButton.setBackground(new java.awt.Color(0, 0, 0));
        hitButton.setFont(new java.awt.Font("SansSerif", 1, 14));
        hitButton.setForeground(new java.awt.Color(255, 255, 255));
        hitButton.setText("HIT");
        hitButton.setToolTipText("<html><pre>Request a card from \nthe Dealer</pre></html>");
        this.add(hitButton);
        hitButton.setBounds(680, 477, 110, 40);

        // double button
        doubleButton.setBackground(new java.awt.Color(0, 0, 0));
        doubleButton.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        doubleButton.setForeground(new java.awt.Color(255, 255, 255));
        doubleButton.setText("DOUBLE");
        doubleButton.setToolTipText("<html>\n<pre>Double Your Orginal Bet. \nPlayers can only down double \non an initial hand value \nof 9 or higher. </pre>\n</html>");
        this.add(doubleButton);
        doubleButton.setBounds(560, 477, 110, 40);

        // stand button
        standButton.setBackground(new java.awt.Color(0, 0, 0));
        standButton.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        standButton.setForeground(new java.awt.Color(255, 255, 255));
        standButton.setText("STAND");
        standButton.setToolTipText("<html>\n<pre>Keep Your Hand Value.\nEnd your Turn</pre>\n</html>");
        this.add(standButton);
        standButton.setBounds(680, 527, 110, 40);

        // split button
        splitButton.setBackground(new java.awt.Color(0, 0, 0));
        splitButton.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        splitButton.setForeground(new java.awt.Color(255, 255, 255));
        splitButton.setText("SPLIT");
        splitButton.setToolTipText("<html>\n<pre>\nSplit Your Initial Hand Into\nTwo Distinct Hands. You may\nonly split your hand on the initial\ndraw only if both of your cards \nhave the same face value. If either\nhand has a higher value than the\ndealer's hand, you win!\n</pre>\n</html>");
        this.add(splitButton);
        splitButton.setBounds(560, 527, 110, 40);

        // surrender button
        surButton.setBackground(new java.awt.Color(0, 0, 0));
        surButton.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        surButton.setForeground(new java.awt.Color(255, 255, 255));
        surButton.setText("SURRENDER");
        surButton.setToolTipText("<html>\n<pre>\nYou May Surrender And\nLose Your Half Your Bet But\nOnly On Your Initial Hand.\nTip: It may be a good idea to \nsurrender if you think the dealer \nhas blackjack.\n</pre>\n</html>");
        this.add(surButton);
        surButton.setBounds(200, 480, 130, 40);

        // bet button
        betButton.setBackground(new java.awt.Color(0, 0, 0));
        betButton.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        betButton.setForeground(new java.awt.Color(255, 255, 255));
        betButton.setText("BET");
        betButton.setToolTipText("<html>\n<pre>\nSubmit Your Bet\n</pre>\n<html>");
        this.add(betButton);
        betButton.setBounds(200, 530, 130, 40);

        // clear Button
        clearButton.setBackground(new java.awt.Color(0, 0, 0));
        clearButton.setFont(new java.awt.Font("SansSerif", 1, 12)); 
        clearButton.setForeground(new java.awt.Color(255, 255, 255));
        clearButton.setText("CLEAR");
        clearButton.setToolTipText("<html>\n<pre>\nClear Your Bet\n</pre>\n</html>");
        clearButton.setMaximumSize(new java.awt.Dimension(41, 29));
        clearButton.setMinimumSize(new java.awt.Dimension(41, 29));
        clearButton.setPreferredSize(new java.awt.Dimension(41, 29));
        this.add(clearButton);
        clearButton.setBounds(360, 530, 130, 40);

        // $50 wager button
        wagerButton[0].setFont(new java.awt.Font("Times New Roman", 1, 14));
        wagerButton[0].setText("$50");
        wagerButton[0].setToolTipText("<html>\n<pre>Add $50 to your bet</pre>\n</html>");
        wagerButton[0].setContentAreaFilled(false);
        wagerButton[0].setBorder(null);
        this.add(wagerButton[0]);
        wagerButton[0].setBounds(40, 440, 79, 29);

        // $100 wager button
        wagerButton[1].setFont(new java.awt.Font("Times New Roman", 1, 14));
        wagerButton[1].setText("$100");
        wagerButton[1].setToolTipText("<html>\n<pre>Add $100 to your bet</pre>\n</html>");
        wagerButton[1].setContentAreaFilled(false);
        wagerButton[1].setBorder(null);
        this.add(wagerButton[1]);
        wagerButton[1].setBounds(100, 480, 79, 29);

        // $500 wager button
        wagerButton[2].setFont(new java.awt.Font("Times New Roman", 1, 14));
        wagerButton[2].setText("$500");
        wagerButton[2].setToolTipText("<html>\n<pre>Add $500 to your bet</pre>\n</html>");
        wagerButton[2].setContentAreaFilled(false);
        wagerButton[2].setBorder(null);
        this.add(wagerButton[2]);
        wagerButton[2].setBounds(50, 510, 69, 27);

        wagerButton[3].setFont(new java.awt.Font("Times New Roman", 1, 14));
        wagerButton[3].setText("$1000");
        wagerButton[3].setToolTipText("<html>\n<pre>Add $1000 to your bet</pre>\n</html>");
        wagerButton[3].setContentAreaFilled(false);
        wagerButton[3].setBorder(null);
        this.add(wagerButton[3]);
        wagerButton[3].setBounds(110, 534, 79, 31);



    }

    public void initDisableBTNS(){
    		hitButton.setEnabled(false);
    		standButton.setEnabled(false);
    		doubleButton.setEnabled(false);
    		splitButton.setEnabled(false);
    		surButton.setEnabled(false);
    		clearButton.setEnabled(false);
            hitToggle.setEnabled(false);
	}

    public boolean checkToggle(){
            return hitToggle.isSelected();
    }

    public void enableClearBTN(){
		    clearButton.setEnabled(true);
	  }

    public void enableToggle(){
            hitToggle.setEnabled(true);
    }

    public void enableSurrenderBTN(){
		    surButton.setEnabled(true);

	  }

    public void disableClearBTN(){
		    clearButton.setEnabled(false);
	  }

    public void disableSurrenderBTN(){
		    surButton.setEnabled(false);
	  }

    public void disbleSplitBTN(){
		    splitButton.setEnabled(false);
	  }

    public void disableDoubleDownBTN(){
		    doubleButton.setEnabled(false);
	  }

    public void disableDoubleDownSplitBTN(){
		    doubleButton.setEnabled(false);
		    splitButton.setEnabled(false);
	  }


    public void disableHitBTN(){
		    hitButton.setEnabled(false);
	  }

    public void disableStandBTN(){
		    standButton.setEnabled(false);
	  }

	  public void disableBetBTN(){
		    betButton.setEnabled(false);
	  }

	  public void enableBetBTN(){
		    betButton.setEnabled(true);
	  }

    public void disableBettingChipButtons(){
    		wagerButton[0].setEnabled(false);
    		wagerButton[1].setEnabled(false);
    		wagerButton[2].setEnabled(false);
    		wagerButton[3].setEnabled(false);
	  }

    public void initGameButtons(){
    		hitButton.setEnabled(true);
    		standButton.setEnabled(true);
    		if(game.getPlayerHand1Value() > 8 && (game.getPlayerBalance() > game.getPlayerBet()*2))
    			doubleButton.setEnabled(true);
    		if(game.canSplit() && (game.getPlayerBalance() > game.getPlayerBet()*2))
    			splitButton.setEnabled(true);
	   }

    public void setButtonActions(){
	   hitToggle.addActionListener(new GameButtonAction1());
       hitButton.addActionListener(new GameButtonAction2());
       standButton.addActionListener(new GameButtonAction3());
       doubleButton.addActionListener(new GameButtonAction4());
       splitButton.addActionListener(new GameButtonAction5());
       betButton.addActionListener(new GameButtonAction6());
       surButton.addActionListener(new GameButtonAction7());
       clearButton.addActionListener(new GameButtonAction8());
       wagerButton[0].addActionListener(new GameButtonAction9());
       wagerButton[1].addActionListener(new GameButtonAction10());
       wagerButton[2].addActionListener(new GameButtonAction11());
       wagerButton[3].addActionListener(new GameButtonAction12());
    }

    class GameButtonAction1 implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent e){
            if(checkToggle()){
                hitToggle.setText("Hand 2");    
            }else{
                hitToggle.setText("Hand 1");    
            }
        }
    }

    class GameButtonAction2 implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent e) {
            hit();
        }
    }


    class GameButtonAction3 implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent e) {
            endGame();
        }
    }

    class GameButtonAction4 implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent e) {
            doubleDown();
        }
    }

    class GameButtonAction5 implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent e) {
             split();
        }
    }

    class GameButtonAction6 implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent e) {
            if(game.getPlayerBet() > 0){
                disableBetBTN();
                startGame();

            }else{
                JOptionPane.showMessageDialog(null, "You Must Place a Bet Greater Than $0.");
            }
        }
    }


    class GameButtonAction7 implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent e) {
            game.surrender();
            losses++;
            resetGame(game.getPlayerBalance());

        }
    }

    class GameButtonAction8 implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent e) {
            game.clearBet();
            betLabel.setText("Bet: $" + game.getPlayerBet());
            disableClearBTN();
        }
    }

    class GameButtonAction9 implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent e) {
            if(game.placeBlueWager()){
                betLabel.setText("Bet: $" + game.getPlayerBet());
                enableClearBTN();
            }
            else
                JOptionPane.showMessageDialog(null, "You Cannot Bet Higher than Your Balance.");
            }
    }

    class GameButtonAction10 implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent e) {
            if(game.placeYellowWager()){
                betLabel.setText("Bet: $" + game.getPlayerBet());
                enableClearBTN();
            }
            else
                JOptionPane.showMessageDialog(null, "You Cannot Bet Higher than Your Balance.");
        }
    }

    class GameButtonAction11 implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent e) {
            if(game.placeRedWager()){
                betLabel.setText("Bet: $" + game.getPlayerBet());
                enableClearBTN();
            }
            else
                JOptionPane.showMessageDialog(null, "You Cannot Bet Higher than Your Balance.");
            }
    }

    class GameButtonAction12 implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent e) {
            if(game.placeGreenWager()){
                betLabel.setText("Bet: $" + game.getPlayerBet());
                enableClearBTN();
            }
            else
                JOptionPane.showMessageDialog(null, "You Cannot Bet Higher than Your Balance.");
        }
    }    


    public void setLabels(){
        betLabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); 
        betLabel.setForeground(new java.awt.Color(255, 255, 51));
        betLabel.setText("Bet: " + "$0");
        this.add(betLabel);
        betLabel.setBounds(360, 500, 170, 29);

        balanceLabel.setFont(new java.awt.Font("Times New Roman", 1, 18)); 
        balanceLabel.setForeground(new java.awt.Color(255, 255, 51));
        balanceLabel.setText("Balance: " + "$1500");
        balanceLabel.setToolTipText("");
        this.add(balanceLabel);
        balanceLabel.setBounds(360, 470, 190, 22);

        value1.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        value1.setForeground(new java.awt.Color(255, 255, 51));
        value1.setText("Player's Hand:\n " + game.getPlayerHand1Value());
        this.add(value1);
        value1.setBounds(200, 570, 150, 40);

        value2.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        value2.setForeground(new java.awt.Color(255, 255, 51));
        value2.setText("Player's Hand:\n " + game.getPlayerHand2Value());
        this.add(value2);
        value2.setVisible(false);
        value2.setBounds(370, 570, 150, 40);


        value3.setFont(new java.awt.Font("Times New Roman", 1, 14));
        value3.setForeground(new java.awt.Color(255, 255, 51));
        value3.setText("Dealer's Hand:\n " + game.getDealerHandValue());
        this.add(value3);
	    value3.setVisible(true);
        value3.setBounds(10, 30, 150, 40);
    }


	public void dealCardToPlayerHand1(BufferedImage img){
        playerCards[playerHand1CC] = new CardDrawAnimation(img, pxplot, pyplot, pxdx, pydy);
        self.add(playerCards[playerHand1CC]);
        self = playerCards[playerHand1CC];
        self.validate();
        playerCards[playerHand1CC].setBounds(0, 0, 800, 600);
        playerCards[playerHand1CC++].paintCard();
		if(!game.isSplit())
        	pxplot += 20;
		else
			pxplot -= 20;
	
	}

    public void putCardToPlayerHand1(BufferedImage img, int num){
        playerCards[playerHand1CC] = new CardDrawAnimation(img, pxplot, pyplot, pxdx, pydy, num);
        self.add(playerCards[playerHand1CC]);
        self = playerCards[playerHand1CC];
        self.validate();
        playerCards[playerHand1CC].setBounds(0, 0, 800, 600);
        playerCards[playerHand1CC].setLayout(null);
        playerCards[playerHand1CC++].paintCard();
        if(!game.isSplit())
            pxplot += 20;
        else
            pxplot -= 20;
    }

  	public void dealCardToPlayerHand2(BufferedImage img){ // must edit varibles
      	playerCards[playerHand1CC] = new CardDrawAnimation(img, p1xplot, p1yplot, p1dx, p1dy);
      	self.add(playerCards[playerHand1CC]);
      	self = playerCards[playerHand1CC];
      	self.validate();
      	playerCards[playerHand1CC].setBounds(0, 0, 800, 600);
      	playerCards[playerHand1CC++].paintCard();
      	p1xplot += 20;
  	}

    public void putCardToPlayerHand2(BufferedImage img, int num){
        playerCards[playerHand1CC] = new CardDrawAnimation(img, p1xplot, p1yplot, p1dx, p1dy, num);
        self.add(playerCards[playerHand1CC]);
        self = playerCards[playerHand1CC];
        self.validate();
        playerCards[playerHand1CC].setBounds(0, 0, 800, 600);
        playerCards[playerHand1CC].setLayout(null);
        playerCards[playerHand1CC++].paintCard();
        p1xplot += 20;
    }

	public void dealCardToDealer(BufferedImage img){
        dealerCards[dealerCC] = new CardDrawAnimation(img, dxplot, dyplot, dxdx, dydy);
        me.add(dealerCards[dealerCC]);
        me = dealerCards[dealerCC];
        me.validate();
        dealerCards[dealerCC].setBounds(0, 0, 800, 600);
        dealerCards[dealerCC++].paintCard();
        dxplot -= 20;
	}

    public void putCardToDealer(BufferedImage img, int num){
        dealerCards[dealerCC] = new CardDrawAnimation(img, dxplot, dyplot, dxdx, dydy, num);
        me.add(dealerCards[dealerCC]);
        me = dealerCards[dealerCC];
        me.validate();
        dealerCards[dealerCC].setBounds(0, 0, 800, 600);
        dealerCards[dealerCC].setLayout(null);
        dealerCards[dealerCC++].paintCard();
        dxplot -= 20;
    }

    public void startGame(){
        System.out.println("\nNew Round!"); 
        try{
            cardFaceDown = ImageIO.read(new File("assets/cards/cardback.png"));
        }catch(Exception exc){
           exc.printStackTrace();
	       System.exit(0);
        }
        pointsAdded = 200;
    	enableSurrenderBTN();
    	disableClearBTN();
    	disableBettingChipButtons();
    	game.deal();
    	dealCardToPlayerHand1(game.getPlayerHand1CardImg(0));
    	time.schedule(new StartGameTimerTask1(), 700);
        time.schedule(new StartGameTimerTask2(), 2000);
    	time.schedule(new StartGameTimerTask3(), 2750);

        winner = game.Blackjack();
		if(winner != 4){
		    time.schedule(new StartGameTimerTask4(), 3800);
			time.schedule(new StartGameTimerTask5(), 4100);
			time.schedule(new StartGameTimerTask6(), 4500);
			time.schedule(new StartGameTimerTask7(), 5000);
			
		}else{
			time.schedule(new StartGameTimerTask8(), 3000);
		
		}

	}


  public void hit(){
  	if (checkToggle()){
		hitToggle.setEnabled(false);
		hitButton.setEnabled(false);
		splitButton.setEnabled(false);
		doubleButton.setEnabled(false);
		standButton.setEnabled(false);
		time.schedule(new HitTimerTask1(), 500);
		time.schedule(new HitTimerTask2(), 1000);

	}else{
		 
		 hitToggle.setEnabled(false);
         hitButton.setEnabled(false);
		 splitButton.setEnabled(false);
		 doubleButton.setEnabled(false);
		 standButton.setEnabled(false);
		 time.schedule(new HitTimerTask3(), 500);
		 time.schedule(new HitTimerTask4(), 1000);

	
	
	}
	time.schedule(new HitTimerTask5(), 1500);
  
  }



   public void split(){
     if(!game.isSplit()){
        game.Split();
        pointsAdded = 100;
        betLabel.setText("Bet: $" + game.getPlayerBet());
        for(int i = 0; i < playerHand1CC; i++)
           myself.remove(playerCards[i]);
        playerHand1CC = 0;
        pxplot = 120;
        pyplot = 230;
        self = this;
        my = this;
        doubleButton.setEnabled(false);
        dealCardToPlayerHand1(game.getPlayerHand1CardImg(0));
        splitButton.setEnabled(false);
        time.schedule(new SplitTimerTask1(), 700);
        time.schedule(new SplitTimerTask2(), 1200);
        time.schedule(new SplitTimerTask3(), 2000);

     }
   }



    public void doubleDown(){
       bool = game.doubleDown();
	   doubleButton.setEnabled(false);
	   betLabel.setText("Bet: $" + game.getPlayerBet());
	   dealCardToPlayerHand1(game.getPlayerHand1CardImg(game.getPlayerHand1NumCards()-1));
	   if(game.getPlayerHand1Value() < 22){
		  value1.setText("Player's Hand: " + game.getPlayerHand1Value());}
	   else{
		  value1.setText("Busted");
		  JOptionPane.showMessageDialog(null,"Your First Hand is Busted");
	   }
	    pointsAdded = 250;
        endGame();
   }


	public void endGame(){
	    int numCards, secs;
	    numCards = 0;
	    if(bool){
	       bool = false;
	       secs = 550;
        }
	    else{
	       secs = 50;
        }
        game.stand();
	    numCards = game.getDealerHandNumCards();
        initDisableBTNS();
	    for(int i = 0; i < dealerCC; i++)
		    remove(dealerCards[i]);
        dealerCC = 0;
        dxplot = 440;
        dyplot = 20;
	    me = this;
      	for (int i = 0; i < numCards; i++){

      		time.schedule(new EndGameTimerTask1(), secs);
      		secs += 750;
      	}
	      time.schedule(new EndGameTimerTask2(), secs);

	}

   public void resetGame(int bal){
	    game = new BlackjackGame();
	    game.newPlayerBalance(bal);
	    for(int i = 0; i < playerHand1CC; i++)
            remove(playerCards[i]);
	    playerHand1CC = 0;
	    pxplot = 150;
	    pyplot = 250;
	    for(int i = 0; i < dealerCC; i++)
	       remove(dealerCards[i]);
	    dealerCC = 0;
	    dxplot = 440;
	    dyplot = 20;
	    me = this;
	    self = this;
        my = this;
        p1xplot = 280;
        p1yplot = 260;
	    repaint();
	    for(int i = 0; i < 4; i++)
	       wagerButton[i].setEnabled(true);
	    initDisableBTNS();
	    enableBetBTN();
	    betLabel.setText("Bet: $" + game.getPlayerBet());
	    balanceLabel.setText("Balance: $" + game.getPlayerBalance());
        hitToggle.setSelected(false);
        hitToggle.setEnabled(false);
        value1.setText("Player's Hand:\n" + game.getPlayerHand1Value());
        value2.setVisible(false);
        value2.setText("Player's Hand:\n" + game.getPlayerHand1Value());
        value3.setText("Dealer's Hand:\n" + game.getPlayerHand1Value());
     }


   public void newGame(){
   	resetGame(0);
	wins = 0;
	losses = 0;
	draws = 0;
	points = 0;
	lost = 0;
	gains = 0;

   }

   public void displayStats(){

	int lo = 1;
        if(losses > 0)
	lo = losses;
	Double wlratio = new Double((double)wins/lo);
	wlratio = java.math.BigDecimal.valueOf(wlratio).setScale(3, java.math.RoundingMode.HALF_UP).doubleValue();

   	JOptionPane.showMessageDialog(null, 
			"Wins: " + wins + "\n"
			+ "Losses: " + losses + "\n"
			+ "Draws: " + draws + "\n"
			+ "W/L Ratio: " + wlratio + "\n"
			+  "Money Won: " + gains + "\n"
			+ "Money Lost: " + lost + "\n" 
			+ "Points: " + points,
			gamertag + " Statistics", 
			JOptionPane.PLAIN_MESSAGE);
   
   }

   public int getPoints(){
   	return points;
   }

   public String getGamerTag(){
   	return gamertag;
   
   }

    class StartGameTimerTask1 extends TimerTask implements java.io.Serializable{
        public void run(){
            dealCardToPlayerHand1(game.getPlayerHand1CardImg(1));
            value1.setText("Player's Hand:\n" + game.getPlayerHand1Value());
        }
    }

    class StartGameTimerTask2 extends TimerTask implements java.io.Serializable{
        public void run(){
            
            dealCardToDealer(cardFaceDown);
        }
    }

    class StartGameTimerTask3 extends TimerTask implements java.io.Serializable{
        public void run(){
            dealCardToDealer(game.getDealerHandCardImg(1));
        }

    }

    class StartGameTimerTask4 extends TimerTask implements java.io.Serializable{
        public void run(){
            for(int i = 0; i<dealerCC; i++){
                   myself.remove(dealerCards[i]);
            }
            dealerCC = 0;
            dxplot = 440;
            dyplot = 20;    
        }
    }

    class StartGameTimerTask5 extends TimerTask implements java.io.Serializable{
        public void run(){
            me = myself;
            dealCardToDealer(game.getDealerHandCardImg(0));
        }
    }

    class StartGameTimerTask6 extends TimerTask implements java.io.Serializable{
        public void run(){
            dealCardToDealer(game.getDealerHandCardImg(1));
        }
    }

    class StartGameTimerTask7 extends TimerTask implements java.io.Serializable{
        public void run(){
            if(winner == 1){
                wins++;
                gains = gains + game.getPlayerBet()*2;
                points = points + (gains/1000*250);
                value3.setText("Dealer's Hand:\n " + game.getDealerHandValue());
                game.printDealerHand();
                System.out.println("Dealers Val: "+ game.getDealerHandValue());
                System.out.println("Player's First Hand: ");
                game.printPlayerHand1();
                System.out.println("Players Val: "+ game.getPlayerHand1Value());
                points = points + 500;
     
                JOptionPane.showMessageDialog(null, "You Have Blackjack!!.");
                resetGame(game.getPlayerBalance()+(game.getPlayerBet()*2));
            }
            else if (winner == 2){
                losses++;
                lost = lost + game.getPlayerBet();
                game.collectFromPlayer();
                value3.setText("Dealer's Hand:\n " + game.getDealerHandValue());
                game.printDealerHand();
                System.out.println("Dealers Val: "+ game.getDealerHandValue());
                System.out.println("Player's First Hand: "); 
                game.printPlayerHand1(); 
                System.out.println("Players Val: "+ game.getPlayerHand1Value());
                JOptionPane.showMessageDialog(null, "The Dealer has Blackjack!!.");
                if(game.getPlayerBalance() > 49){
                        resetGame(game.getPlayerBalance());}
                else{
                        JOptionPane.showMessageDialog(null, "Insufficient Funds! Game Over!!");
                        displayStats();
                        System.exit(0);
                    }
                }           
            else{
                draws++;
                value3.setText("Dealer's Hand:\n" + game.getDealerHandValue());
                game.printDealerHand();
                System.out.println("Dealers Val: "+ game.getDealerHandValue());
                System.out.println("Player's First Hand: "); 
                game.printPlayerHand1(); 
                System.out.println("Players Val: "+ game.getPlayerHand1Value());
                JOptionPane.showMessageDialog(null, "Both Player Have Blackjack!!.\nIt's a Draw!!");
                resetGame(game.getPlayerBalance());
            }
        }
    }

    class StartGameTimerTask8 extends TimerTask implements java.io.Serializable{
        public void run(){
            initGameButtons();
                        
        }
    }


   class HitTimerTask1 extends TimerTask implements java.io.Serializable{
        public void run(){
            game.dealCardToPlayerHand2();
            dealCardToPlayerHand2(game.getPlayerHand2CardImg(game.getPlayerHand2NumCards()-1));
            value2.setText("Player's Hand: "+ game.getPlayerHand2Value());
            
        }
    }

    class HitTimerTask2 extends TimerTask implements java.io.Serializable{
        public void run(){
            if(game.getPlayerHand2Value() > 21){
                value2.setText("Busted");
                JOptionPane.showMessageDialog(null, "Your Second Hand is Busted!!");
                    
            }
                        
        }
    }

    class HitTimerTask3 extends TimerTask implements java.io.Serializable{
        public void run(){
            game.dealCardToPlayerHand1();
            dealCardToPlayerHand1(game.getPlayerHand1CardImg(game.getPlayerHand1NumCards()-1));
            value1.setText("Player's Hand: " + game.getPlayerHand1Value());
        }
    }

    class HitTimerTask4 extends TimerTask implements java.io.Serializable{
        public void run(){
            if(game.getPlayerHand1Value() > 21){
                value1.setText("Busted");
                JOptionPane.showMessageDialog(null, "Your First Hand is Busted!");
                       
            }       
        }
    }

    class HitTimerTask5 extends TimerTask implements java.io.Serializable{
        public void run(){
            if(game.isSplit() && (game.getPlayerHand1Value() > 21 && game.getPlayerHand2Value() > 21)){
                endGame();
            }
            else if(!game.isSplit() && game.getPlayerHand1Value() > 21){
                endGame();
            }else{
                hitButton.setEnabled(true);
                standButton.setEnabled(true);
                if(game.isSplit()){
                    if(game.getPlayerHand1Value() > 21){
                        hitToggle.setText("Hand 2");
                        hitToggle.setSelected(true);
                    }else if(game.getPlayerHand2Value() > 21){
                        hitToggle.setText("Hand 1");
                        hitToggle.setSelected(false);
                    }else{
                        hitToggle.setEnabled(true);
                    }
                }

            }
        }
    }

   class SplitTimerTask1 extends TimerTask implements java.io.Serializable{
        public void run(){
           dealCardToPlayerHand1(game.getPlayerHand1CardImg(1));
           value1.setText("Player's Hand:\n" + game.getPlayerHand1Value());
        }


    }

    class SplitTimerTask2 extends TimerTask implements java.io.Serializable{
        public void run(){
            dealCardToPlayerHand2(game.getPlayerHand2CardImg(0));
            value1.setText("Player's Hand:\n" + game.getPlayerHand1Value());
        }


    }

    class SplitTimerTask3 extends TimerTask implements java.io.Serializable{
        public void run(){
           dealCardToPlayerHand2(game.getPlayerHand2CardImg(1));
           enableToggle();
           value2.setVisible(true);
           value2.setText("Player's Hand:\n" + game.getPlayerHand2Value());
        }


    }

   class EndGameTimerTask1 extends TimerTask implements java.io.Serializable{
        public void run(){
            dealCardToDealer(game.getDealerHandCardImg(counter));
            counter++;
        }


    }

   class EndGameTimerTask2 extends TimerTask implements java.io.Serializable{
        public void run(){
            System.out.println("Dealer Hand: ");
            game.printDealerHand();
            System.out.println("Dealers Val: "+ game.getDealerHandValue());
            System.out.println("Player's First Hand: ");
            game.printPlayerHand1(); 
            System.out.println("Players Val: "+ game.getPlayerHand1Value());
            if(game.isSplit()){
                System.out.println("Player's Second Hand: ");
                game.printPlayerHand2();
                System.out.println("Players Val: "+ game.getPlayerHand2Value());
                if(game.getPlayerHand2Value() < 22)
                  value2.setText("Player's Hand:\n" + game.getPlayerHand2Value());
                else
                    value2.setText("Busted");
                }
            if(game.getPlayerHand1Value() < 22)
                value1.setText("Player's Hand:\n" + game.getPlayerHand1Value());
            else
                value1.setText("Busted");
            if(game.getDealerHandValue() < 22)
                value3.setText("Dealer's Hand:\n" + game.getDealerHandValue());
            else
                value3.setText("Busted");
            winner = game.getWinner();
            bool = false;
            if(winner == 1){
                wins++;
                points = points + pointsAdded;
                gains = gains + game.getPlayerBet();
                points = points + (gains/1000*250);
                game.payPlayer();
                JOptionPane.showMessageDialog(null, "You Win!!.");

            }else if(winner == 2){
                losses++;
                lost = lost + game.getPlayerBet();
                game.collectFromPlayer();
                JOptionPane.showMessageDialog(null, "You Lose!!.");
            }else{
                draws++;
                JOptionPane.showMessageDialog(null, "It's a Draw!!.");
            }
            counter = 0;
            if(game.getPlayerBalance() > 49){
                resetGame(game.getPlayerBalance());}
            else{
                balanceLabel.setText("Balance: $0");
                betLabel.setText("Bet: $0");   
                JOptionPane.showMessageDialog(null, "Insufficient Funds! Game Over!!");
                displayStats();
                System.exit(0);
            }

    }


}

    public void saveBlackjack(String file){
        try {
                java.io.FileOutputStream fileOut = new java.io.FileOutputStream("./gameData/savedData/"+file+".ser");
                java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(fileOut);
                out.writeObject(new BlackjackSaveObject(game, wins, losses, draws, counter, winner, points, gains, lost,
                                    pointsAdded, gamertag, hitButton, standButton, doubleButton, splitButton,
                                     betButton, clearButton, surButton, hitToggle, wagerButton, balanceLabel,
                                     betLabel, value1, value2, value3, playerHand1CC, dealerCC, playerCards, dealerCards));

                out.close();
                fileOut.close();
            }catch(java.io.IOException i) {
                i.printStackTrace();
            }
    }

    public void loadBlackjack(String file){
        newGame();
        BlackjackLoadObject b1 = new BlackjackLoadObject(file);
        BlackjackSaveObject b2 = b1.getSaveObject();
        game = b2.savedGame;
        game.restoreIMG();
        wins = b2.savedWins;
        losses = b2.savedLosses;
        draws = b2.savedDraws;
        counter = b2.savedCounter;
        winner = b2.savedWinner;
        points = b2.savedPoints;
        gains = b2.savedGains;
        lost = b2.savedLost;
        pointsAdded = b2.savedPointsAdded;
        gamertag = b2.savedGamertag;
        hitButton.setEnabled(b2.hitButtonEnabled);
        standButton.setEnabled(b2.standButtonEnabled);
        doubleButton.setEnabled(b2.doubleButtonEnabled);
        splitButton.setEnabled(b2.splitButtonEnabled);
        betButton.setEnabled(b2.betButtonEnabled);
        clearButton.setEnabled(b2.clearButtonEnabled);
        surButton.setEnabled(b2.surButtonEnabled);
        hitToggle.setEnabled(b2.hitToggleEnabled);
        hitToggle.setText(b2.hitToggleText);
        hitToggle.setSelected(b2.hitToggleSelected);
        for(int i = 0; i < 4; i++)
                wagerButton[i].setEnabled(b2.wagerButtonEnabled[i]);
        balanceLabel.setEnabled(b2.balanceLabelEnabled);
        balanceLabel.setText(b2.balanceLabelText);
        betLabel.setEnabled(b2.betLabelEnabled);
        betLabel.setText(b2.betLabelText);
        value1.setEnabled(b2.value1Enabled);
        value1.setText(b2.value1Text);
        value2.setEnabled(b2.value2Enabled);
        value2.setText(b2.value2Text);
        value2.setVisible(b2.value2Visible);
        value3.setEnabled(b2.value3Enabled);
        value3.setText(b2.value3Text);

        
        playerHand1CC = 0;
        dealerCC = 0;
        
        if(game.isSplit()){
            p1xplot = 280;
            p1yplot = 260;
            for(int i = 0 ; i < game.getPlayerHand2NumCards(); i++){
                putCardToPlayerHand2(game.getPlayerHand2CardImg(i), 0);
            }
            pxplot = 120;
            pyplot = 230;
            for(int i = 0; i < game.getPlayerHand1NumCards(); i++){
                putCardToPlayerHand1(game.getPlayerHand1CardImg(i), 0);
            }
        }else{
            pxplot = b2.playerPlots[0];
            pyplot = b2.playerPlots[1];
            for(int i = 0; i < game.getPlayerHand1NumCards(); i++){
                putCardToPlayerHand1(game.getPlayerHand1CardImg(i), 0);
            }
        }
        dxplot = b2.dealerPlot[0];
        dyplot = b2.dealerPlot[1];
        for(int i = 0; i < game.getDealerHandNumCards(); i++){
                if(i == 0){
                    try{
                        cardFaceDown = ImageIO.read(new File("assets/cards/cardback.png"));
                        
                        putCardToDealer(cardFaceDown, 0);
                     }catch(Exception exc){
                          exc.printStackTrace();
                          System.exit(0);
                     }
                }else{   
                    putCardToDealer(game.getDealerHandCardImg(i), 0);
                }
        }

    }

}

class BlackjackSaveObject implements java.io.Serializable{
        private static final long serialVersionUID = 2L;

        public BlackjackGame savedGame;
        public int savedWins, savedLosses, savedDraws;
        public int savedCounter, savedWinner, savedPoints, savedGains, savedLost, savedPointsAdded;
        // Buttons
        public String savedGamertag;
        public Boolean hitButtonEnabled;
        public Boolean standButtonEnabled;
        public Boolean doubleButtonEnabled;
        public Boolean splitButtonEnabled;
        public Boolean betButtonEnabled;
        public Boolean clearButtonEnabled;
        public Boolean surButtonEnabled;
        public Boolean [] wagerButtonEnabled;
        public Boolean hitToggleEnabled;
        public Boolean hitToggleSelected;
        // Labels
        public boolean balanceLabelEnabled;
        public String balanceLabelText;
        public Boolean betLabelEnabled;
        public String betLabelText;
        public Boolean value1Enabled, value2Enabled, value2Visible, value3Enabled;
        public String value1Text, value2Text, value3Text, hitToggleText;
        // Card panels
        int [] playerPlots;
        int [] dealerPlot;
        public int savedPlayerHand1CC, savedDealerCC;

        public BlackjackSaveObject(BlackjackGame game, int wins, int losses, int draws, int counter, int winner, int points, int gains, int lost,
                                    int pointsAdded, String gamertag, JButton hitButton, JButton standButton, JButton doubleButton, JButton splitButton,
                                     JButton betButton, JButton clearButton, JButton surButton, JToggleButton hitToggle, JButton [] wagerButton, JLabel balanceLabel,
                                     JLabel betLabel, JLabel value1, JLabel value2, JLabel value3, int playerHand1CC, int dealerCC, CardDrawAnimation [] playerCards,
                                     CardDrawAnimation [] dealerCards){
            savedGame = game;
            savedWins = wins;
            savedLosses = losses;
            savedDraws = draws;
            savedCounter = counter;
            savedWinner = winner;
            savedPoints = points;
            savedGains = gains;
            savedLost = lost;
            savedPointsAdded = pointsAdded;
            savedGamertag = gamertag;
            hitButtonEnabled = hitButton.isEnabled();
            standButtonEnabled = standButton.isEnabled();
            doubleButtonEnabled = doubleButton.isEnabled();
            splitButtonEnabled = splitButton.isEnabled();
            betButtonEnabled = betButton.isEnabled();
            clearButtonEnabled = clearButton.isEnabled();
            surButtonEnabled = surButton.isEnabled();
            hitToggleEnabled = hitToggle.isEnabled();
            hitToggleSelected = hitToggle.isSelected();
            hitToggleText = hitToggle.getText();
            wagerButtonEnabled = new Boolean[4];
            for(int i = 0; i < 4; i++)
                wagerButtonEnabled[i] = wagerButton[i].isEnabled();
            balanceLabelEnabled = balanceLabel.isEnabled();
            balanceLabelText = balanceLabel.getText();
            betLabelEnabled = betLabel.isEnabled();
            betLabelText = betLabel.getText();
            value1Enabled = value1.isEnabled();
            value1Text = value1.getText();
            value2Enabled = value2.isEnabled();
            value2Text = value2.getText();
            value3Enabled = value3.isEnabled();
            value3Text = value3.getText();
            value2Visible = value2.isVisible();
            savedPlayerHand1CC = playerHand1CC; 
            savedDealerCC = dealerCC;
            playerPlots = new int[playerHand1CC*2];
            dealerPlot = new int[dealerCC*2];
            for(int i = 0, j = 0; i < playerHand1CC; i++, j++){
                playerPlots[i++] = playerCards[j].getxendPlot();
                playerPlots[i] = playerCards[j].getyendPlot();
            }
            for(int i = 0, j = 0; i < dealerCC; i++, j++){
                dealerPlot[i++] = dealerCards[j].getxendPlot();
                dealerPlot[i] = dealerCards[j].getyendPlot();;
            }
                

        }

    }

class BlackjackLoadObject implements java.io.Serializable{
        private static final long serialVersionUID = 3L;

        BlackjackSaveObject b; 

        public BlackjackLoadObject(String file){
            b = null;
            try {
                java.io.FileInputStream fileIn = new java.io.FileInputStream("./gameData/savedData/"+file);
                java.io.ObjectInputStream in = new java.io.ObjectInputStream(fileIn);
                b = (BlackjackSaveObject) in.readObject();
                in.close();
                fileIn.close();
            }catch(java.io.IOException i) {
                i.printStackTrace();
                return;
            }catch(ClassNotFoundException c) {
                System.out.println("BlackjackSaveObject class not found");
                c.printStackTrace();
                return;
            }
            

        }

        public BlackjackSaveObject getSaveObject(){
            return b;
        }

}

class CardDrawAnimation extends JPanel implements java.io.Serializable{

    private final static int xorigin = 605, yorigin = 30;
    private final static int cardHeight = 200, cardWidth = 150;
    private int flag;
    private int xendpoint, yendpoint;
    private int x, y, xvel, yvel;
    private BufferedImage card;
    private javax.swing.Timer timer;
    private int draw;

    public CardDrawAnimation(BufferedImage img, int xplot, int yplot, int xv, int yv, int d){
        super();
        card = img;
        x = xorigin;
        y = yorigin;
        xendpoint = xplot;
        yendpoint = yplot;
        xvel = xv;
        yvel = yv;
        draw = d;

        setMinimumSize(new Dimension(800,600));
        setPreferredSize(new Dimension(800,600));
        setMaximumSize(new Dimension(800, 600));

        setVisible(true);
        setOpaque(false);
	    setLayout(null);

    }

    public CardDrawAnimation(BufferedImage img, int xplot, int yplot, int xv, int yv){
        this(img, xplot, yplot, xv, yv, 2000);

    }

    public int getxendPlot(){
        return xendpoint;
    }

    public int getyendPlot(){
        return yendpoint;
    }

    public void paintCard(){
        if(draw == 0){
            paintCardPostion();
        }else{
            flag = 3;
            repaint();
            flag = 1;
            timer =  new javax.swing.Timer(3, new CardDrawTimerTask());
            timer.start();
        }
    }

    public void paintCardPostion(){
        flag = 200;
        repaint();
    }

    class CardDrawTimerTask implements ActionListener, java.io.Serializable{
        public void actionPerformed(ActionEvent e){
            repaint();
            x -= xvel;
            y += yvel;
            if(x <= xendpoint && y >= yendpoint){
                flag = 2;
                x = xendpoint;
                y = yendpoint;
                repaint();
            }
        }
    }

    protected void paintComponent(Graphics g){

        super.paintComponent(g);
        switch(flag){
            case 1:
                if(x > xendpoint && y <= yendpoint){
                    g.drawImage(card, x, y, cardWidth, cardHeight, this);

                }
                break;
            case 2:
                g.drawImage(card, x, y, cardWidth, cardHeight, this);
                break;
            case 200:
                g.drawImage(card, xendpoint, yendpoint, cardWidth, cardHeight, this);
                break;
            default:
                g.drawImage(card, xorigin, yorigin, cardWidth, cardHeight, this);
                break;
        }
    }

}



// Game Mechanics

class Card implements java.io.Serializable{
	private final static String []
	NAMES = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

	private final static String []
	SUITS = {"spades", "clubs", "hearts", "diamonds"};

	private final static String [] IMG = new String[52];

	private String cardName, cardSuit;
	private int cardValue;
	private transient BufferedImage cardImg;
    private String imgPath;

	public Card(int name, int suit, int img, String game){
		cardName = NAMES[name];
		cardSuit = SUITS[suit];
		if(name <= 7)
			cardValue = name+2; // check
		else if(name > 7 && game.equals("blackjack"))
			cardValue = 10;
    if(name == 12 && game.equals("blackjack"))
      cardValue = 11;
		try{
			cardImg = ImageIO.read(new File("assets/cards/" + IMG[img]));
            imgPath = "assets/cards/" + IMG[img]; 
		}catch (Exception ex){
			ex.printStackTrace();
		}

	}

    public void restoreIMG(){
        try{
            cardImg = ImageIO.read(new File(imgPath));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

	// Accessor Functions
	public String toString(){
		return cardName + " of " + cardSuit;
	}

	public int getValue(){
		return cardValue;
	}

	public String getName(){
		return cardName;
	}

	public String getSuit(){
		return cardSuit;
	}

	public BufferedImage getImg(){
		return cardImg;
	}

	// Mutator Functions

	public void setValue(int value, String game){
		if((value == 1 || value == 11) && game.equals("blackjack"))
			if(cardName.equals("A"))
				cardValue = value;
		else
			cardValue = value;
	}

	// Initializer function
	public static void initIMG(){
		for(int i = 0, j = 0, k = 0; i < 52; i++, j++){
			if(j%13 == 0 && j != 0){
				j = 0;
				if (k < 4)
					k++;
			}
			IMG[i] = NAMES[j] + "_of_" + SUITS[k] + ".png";

		}
	}
}




class Deck implements java.io.Serializable{
	final static int initNumCards = 52;

	private Card [] deck;
	private int numCards;

	public Deck(){
		Card.initIMG();
		numCards = initNumCards-1;
		deck = new Card[initNumCards];

		for(int i = 0, j = 0, k = 0; i < 52; i++, j++){
			if(j%13 == 0 && j != 0){
				j = 0;
				if(k < 4)
					k++;
			}
			deck[i] = new Card(j, k, i, "blackjack");
		}

		this.shuffle();
	}

	public void shuffle(){
		Random rand = new Random();
    int card;
		for(int i = 0; i < 4; i++){
			for (int j = 0; j < 52; j++){
				card  = rand.nextInt(52);

				Card c = deck[j];
				deck[j] = deck[card];
				deck[card] = c;
			}
		}
	}

    public int getNumCards(){
        return numCards;
    }

    public void restoreIMG(){
        for(int i = 0; i < initNumCards; i++){
            deck[i].restoreIMG();
        }
    }

	public Card dealCard(){
		if(numCards > -1)
			return deck[numCards--];
		return null;
	}

	public void returnCardToDeck(Card c){
		if(numCards < 51){
			deck[++numCards] = c;
		}
	}

	public void displayDeck(){
		for(Card card: deck)
			System.out.println(card);
	}

}


class Hand implements java.io.Serializable{
    final static int handSize = 30;

    private Card [] bufferHand;
    protected Card [] hand1;
    protected Card [] hand2;
    protected int [] numCards;
    protected boolean split;

    public Hand(){
            split = false;
            hand1 = new Card[handSize];
            hand2 = new Card[handSize];
            numCards = new int[2];
            for(int i = 0; i < 2; i++){
				          numCards[i] = 0;
        	}

	}

    public void restoreIMG(){
        for(int i = 0; i < numCards[0]; i++){
            hand1[i].restoreIMG();
        }
        if(isSplit()){
           for(int i = 0; i < numCards[1]; i++){
                hand2[i].restoreIMG();
            } 
        }
    }

	public int getNumCards(int i){
		return numCards[i];
	}

	public void resetNumCards(int i){
		numCards[i] = 0;
	}


    public int getHandValue(int i){
  		int pos = i-1;
  		int value = 0;
  		switch(i){
  			case 2:
  				bufferHand = hand2;
  				break;
  			default:
  				bufferHand = hand1;
  				break;
  		}

  		for (int index = 0; index < numCards[pos]; index++){
            value = value + bufferHand[index].getValue();

		}

		return value;

    }

    

    public boolean isSplit(){
            return split;
	}

    public void setSplit(boolean b){
            split = b;
    }

    public void changeSplit(){
            split = !split;
    }

    public void printHand(int i){
    		int p = i-1;
    		switch(i){
    			case 2:
    				bufferHand = hand2;
    				break;
    			default:
    				bufferHand = hand1;
    				break;
    		}

    		for(int index = 0; index < numCards[p]; index++){
    			System.out.println(bufferHand[index] + " : " + bufferHand[index].getValue());
    		}
	}

    public Card getCard(int j, int i){
    		int pos = i-1;
    		switch(i){
    			case 1:
    				bufferHand = hand2;
    				break;
    			default:
    				bufferHand = hand1;
    				break;
    		}

    		if(j < numCards[pos])
    			return bufferHand[j];
    		return bufferHand[0];
	}

    public int getValue(int j, int i){
    		int pos = i-1;
    		switch(i){
    			case 2:
    				bufferHand = hand2;
    				break;
    			default:
    				bufferHand = hand1;
    				break;
    		}

    		if(j < numCards[pos])
    			return bufferHand[j].getValue();
    		return bufferHand[0].getValue();
	}

    public String getName(int j, int i){
    		int pos = i-1;
    		switch(i){
    			case 2:
    				bufferHand = hand2;
    				break;
    			default:
    				bufferHand = hand1;
    				break;
    		}

    		if(j < numCards[pos])
    			return bufferHand[j].getName();
    		return bufferHand[0].getName();
	}

    public void printHandNames(int i){
    		int pos = i-1;
    		switch(i){
    			case 2:
    				bufferHand = hand2;
    				break;
    			default:
    				bufferHand = hand1;
    				break;
    		}

  		for(int index = 0; index < numCards[pos]; index++)
  			System.out.println(bufferHand[index].getName());
	}

    public String getSuit(int j, int i){
    		int pos = i-1;
    		switch(i){
    			case 2:
    				bufferHand = hand2;
    				break;
    			default:
    				bufferHand = hand1;
    				break;
    		}

    		if(j < numCards[pos])
    			return bufferHand[j].getSuit();
    		return bufferHand[0].getSuit();
	}

    public void printHandSuits(int i){
    		int pos = i-1;
    		switch(i){
    			case 2:
    				bufferHand = hand2;
            break;
    			default:
    				bufferHand = hand1;
    				break;
    		}

    		for(int index = 0; index < numCards[pos]; index++){
    			System.out.println(bufferHand[index].getSuit());
    		}
	}

    public BufferedImage getCardImg(int j, int i){
    		int pos = i-1;
    		switch(i){
    			case 2:
    				bufferHand = hand2;
    				break;
    			default:
    				bufferHand = hand1;
    				break;
    		}

    		if(j < numCards[pos])
    		   return bufferHand[j].getImg();
    		return bufferHand[0].getImg();
	 }

    public void addCard(Card c, int i){
    		int pos = i-1;
    		switch(i){
    			case 2:
    				bufferHand = hand2;
    				break;
    			default:
    				bufferHand = hand1;
    				break;
    		}

  		if(numCards[pos] < handSize){
  			bufferHand[numCards[pos]++] = c;
  		}
	}


    public void splitHand(){
        if( (hand1[0].getName()).equals(hand1[1].getName())  ){
            hand2[0] = hand1[1];
            numCards[0] = 1;
            numCards[1] = 1;
			      split = true;
        }
    }

    public void setValue(int j, int i, int val, String game){
    		int pos = i-1;
    		switch(i){
    			case 2:
    				bufferHand = hand2;
    				break;
    			default:
    				bufferHand = hand1;
    				break;
    		}

    		if(j < numCards[pos]){
    			bufferHand[j].setValue(val, game);
    		}
    	}

}


class Player extends Hand implements java.io.Serializable{
	private int bet;
	private int balance;
    private String name;

	public Player(){
		super();
		bet = 0;
		balance = 1500;
	}

	public String getPlayerName(){
		return name;
	}

    public void setPlayerName(String n){
        name = n;
    }

	public int getBet(){
		return bet;
	}

	public int getBalance(){
		return balance;
	}

	public void setBet(int b){
		if(b > 49 && b < balance+1)
			bet = bet + b;
	}

	public void resetBet(){
		bet = 0;
	}

	public void payBet(){
		balance = balance + bet;
	}

	public void setBalance(int b){
		balance = balance + b;
		if (balance < 0)
			balance = 0;
	}

	public void newBalance(int b){
		if(b > 0)
			balance = b;
	}

}

class Dealer extends Hand implements java.io.Serializable{
	private Deck cardDeck;

	public Dealer(){
		super();
		cardDeck = new Deck();
	}

    public void restoreDeckIMG(){
        cardDeck.restoreIMG();
    }

	public void shuffle(){
		cardDeck.shuffle();
	}

	public Card dealCard(){
		return cardDeck.dealCard();
	}

	public void returnCardToDeck(Card c){
		cardDeck.returnCardToDeck(c);
	}

	public void displayDeck(){
		cardDeck.displayDeck();
	}

	public Deck getCardDeck(){
		return cardDeck;
	}

	public void setCardDeck(Deck d){
		cardDeck = d;
	}

	public Deck returnNewDeck(){
		return new Deck();
	}


}

class BlackjackGame implements java.io.Serializable{
    private Dealer dealer;
    private Player player;
	private boolean doubledown;

    public BlackjackGame(){
      dealer = new Dealer();
      player = new Player();
	  doubledown = false;
    }

    public void setPlayerName(String n){
        player.setPlayerName(n);
    }

	public boolean placeBlueWager(){
	   if(player.getBalance() > 49+player.getBet()){
			    player.setBet(50);
			    return true;
	   }

	   return false;
	}

	public boolean placeYellowWager(){
	   if(player.getBalance() > 99+player.getBet()){
			    player.setBet(100);
			    return true;
		 }
     return false;
	}

	public boolean placeRedWager(){
	   if(player.getBalance() > 499+player.getBet()){
			    player.setBet(500);
			    return true;
		 }
	    return false;
	}

	public boolean placeGreenWager(){
	    if(player.getBalance() > 999+player.getBet()){
			     player.setBet(1000);
			     return true;
		  }
	    return false;
	}

    public int getPlayerBet(){
		  return player.getBet();
	}

	public int getPlayerBalance(){
		return player.getBalance();
	}

	public void newPlayerBalance(int bal){
		player.newBalance(bal);
	}


	public void clearBet(){
		  player.resetBet();
	}

	public void surrender(){
		  player.setBalance(-1*player.getBet()/2);
	}

	public void deal(){
			dealCardToPlayerHand1();
			dealCardToPlayerHand1();
			dealCardToDealer();
			dealCardToDealer();
	}

	public void dealCardToPlayerHand1(){
		player.addCard(dealer.dealCard(), 1);
		if (player.getHandValue(1) > 21){
			for (int i = 0; i < player.getNumCards(0); i++){
				if(player.getName(i,1).equals("A"))
					player.setValue(i, 1, 1, "blackjack");
				if(player.getHandValue(1) < 22)
					break;
			}
		}

	}

	public void dealCardToPlayerHand2(){
		if(player.isSplit() == true){
			player.addCard(dealer.dealCard(), 2);
			if(player.getHandValue(2) > 21){
        for(int i = 0; i < player.getNumCards(1); i++){
			     if(player.getName(i,2).equals("A"))
              player.setValue(i, 2, 1, "blackjack");
			     if(player.getHandValue(2) < 22)
              break;
        }
      }
		}
	}

    public void dealCardToDealer(){
        dealer.addCard(dealer.dealCard(), 1);
        if (dealer.getHandValue(1) > 21 || (player.getHandValue(1) > dealer.getHandValue(1)) || (player.getHandValue(2) > dealer.getHandValue(1) && isSplit()) ){
          for (int i = 0; i < dealer.getNumCards(0); i++){
            if(dealer.getName(i,1).equals("A"))
              dealer.setValue(i, 1, 1, "blackjack");
            if(dealer.getHandValue(1) < 22)
              break;
          }
        }
    }

    public void stand(){
		DealerStrategy();
	}

	public boolean doubleDown(){
		if(player.getBalance() > player.getBet()*2){
			  player.setBet(player.getBet());
			  dealCardToPlayerHand1();
			  doubledown = true;
        return doubledown;
		 }
     return false;
	}

	public boolean canSplit(){
		if(player.getBalance() > player.getBet()*2){
			if (player.getName(0,1).equals(player.getName(1,1)))
				return true;
		}
		return false;
	}

    public boolean isSplit(){
        return player.isSplit();
    }

	public void Split(){
		player.splitHand();
		dealCardToPlayerHand1();
		dealCardToPlayerHand2();
		player.setBet(player.getBet());
	}

	public void DealerStrategy(){
		while(getPlayerHand1Value() >= getDealerHandValue() || getPlayerHand2Value() >= getDealerHandValue()){
			if(isSplit() && (getPlayerHand1Value() > 21 && getPlayerHand2Value() > 21))
				break;
			else if(!isSplit() && getPlayerHand1Value() > 21)
				break;
			if(getDealerHandValue() < 18)
				dealCardToDealer();
			else
				break;
		
		} 
	}

	public int getPlayerHand1Value(){
		return player.getHandValue(1);
	}

	public int getPlayerHand2Value(){
		return player.getHandValue(2);
	}

	public int getPlayerHand1NumCards(){
		return player.getNumCards(0);
	}

    public int getPlayerHand2NumCards(){
		return player.getNumCards(1);
	}

	public void resetPlayerHand1NumCards(){
		player.resetNumCards(0);
	}

	public void resetPlayerHand2NumCards(){
		player.resetNumCards(1);
	}

	public int getDealerHandValue(){
		return dealer.getHandValue(1);
	}

	public int getDealerHandNumCards(){
		return dealer.getNumCards(0);
	}

	public void resetDealerHandNumCards(){
		dealer.resetNumCards(0);
	}

    public void printDealerHand(){
     dealer.printHand(1);
    }

    public void printPlayerHand1(){
     player.printHand(1);
    }

    public void printPlayerHand2(){
        player.printHand(2);
    }

	public BufferedImage getPlayerHand1CardImg(int j){
		return player.getCardImg(j, 1);
	}

	public BufferedImage getPlayerHand2CardImg(int j){
			return player.getCardImg(j , 2);
	}

	public BufferedImage getDealerHandCardImg(int j){
			return dealer.getCardImg(j , 1);
	}

    public void restoreIMG(){
        player.restoreIMG();
        dealer.restoreIMG();
        dealer.restoreDeckIMG();
    }

	public void payPlayer(){
		player.payBet();
	}

	public void collectFromPlayer(){
		if (doubledown == true){
			player.setBalance((int)(player.getBet()* -0.75));
			doubledown = false;
		}else{
			player.setBalance(-1*player.getBet());
		}

	}

	public int Blackjack(){
		if(player.getHandValue(1) == 21 && dealer.getHandValue(1) != 21)
			return 1;
		else if(dealer.getHandValue(1) == 21 && player.getHandValue(1) != 21)
			return 2;
    else if(dealer.getHandValue(1) == 21 && player.getHandValue(1) == 21)
      return 3;
		return 4;
	}

	public int getWinner(){
		if(!player.isSplit()){
			if( player.getHandValue(1) > dealer.getHandValue(1) ){
				if (player.getHandValue(1) < 22)
					return 1;
				else if(dealer.getHandValue(1) < 22)
					return 2;
        }
			else if(player.getHandValue(1) < dealer.getHandValue(1)){
				if (dealer.getHandValue(1) < 22)
					return 2;
				else if (player.getHandValue(1) < 22)
					return 1;
      }
		}else{
			if( (player.getHandValue(1) > dealer.getHandValue(1)) )
				if (player.getHandValue(1) < 22)
					return 1;
			if(player.getHandValue(2) > dealer.getHandValue(1))
				if (player.getHandValue(2) < 22)
					return 1;
			if( player.getHandValue(1) < dealer.getHandValue(1) && player.getHandValue(2) < dealer.getHandValue(1))
				if (dealer.getHandValue(1) < 22)
					return 2;
				else if (player.getHandValue(1) < 22 || player.getHandValue(2) < 22)
					return 1;
			if( player.getHandValue(1) > 21 && player.getHandValue(2) > 21)
				if(dealer.getHandValue(1) < 22)
					return 2;
            if( player.getHandValue(1) > 21 && player.getHandValue(2) < 22 && dealer.getHandValue(1) > 21)
                return 1;
            if ( player.getHandValue(1) < 22 && player.getHandValue(2) > 21 && dealer.getHandValue(1) > 21)
                return 1;

		}

		return 3;
	}



}
