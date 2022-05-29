import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


public class GameFrame
{
    private JFrame gameFrame;
    private ScoreBoard scoreBoard;
    private JLayeredPane gamePanes;

    private JLabel timeCounter=new JLabel();    //liczniki czasu, punktów i żyć
    private JLabel pointCounter=new JLabel();
    private JLabel livesCounter=new JLabel();

    private GameThread gameThread=new GameThread();     //wątki czasowe
    private SecondCounter secondCounter=new SecondCounter();

    private boolean gameOver=false;
    private Player newPlayer;

    GameFrame(JFrame frame,ScoreBoard scores) {
        gameFrame=frame;
        scoreBoard=scores;
        difficultySetting();    //rozpoczecie od wybrania poziomu trudnosci
    }

    private class GameThread extends Thread{
        @Override
        public void run()
        {
            while(!gameOver){
                try {Thread.sleep(entityInterval);}
                catch (InterruptedException e) {e.printStackTrace();}
                makeEntity();
                if(newPlayer.getLives()<=0){
                    gameOver=true;
                    gameFrame.setVisible(false);
                    askForName();
                }
            }
        }
    }
    private class SecondCounter extends Thread{
        private int secondsPassed=0;
        @Override
        public void run()
        {
            while (!gameOver){
                try {Thread.sleep(1000);}
                catch (InterruptedException e) {e.printStackTrace();}
                secondsPassed++;
                timeCounter.setText(String.valueOf(secondsPassed));
                pointCounter.setText(String.valueOf(newPlayer.getPoints()));
                livesCounter.setText(String.valueOf(newPlayer.getLives()));
                if (secondsPassed%5==0&&secondsPassed>0)
                    entityInterval -= entityInterval * entitySpeeder;
            }
        }
    }

    private void setupGUI(){
        gameFrame.setSize(800,800);
        gameFrame.setLayout(null);
        JLabel gameBackground =new JLabel(new ImageIcon("img/backgroundA.png"));
        gamePanes=new JLayeredPane();
        gameFrame.setLayeredPane(gamePanes);
        gameFrame.getRootPane().setBorder(BorderFactory.createLineBorder(Color.ORANGE,10));
        gamePanes.add(gameBackground,JLayeredPane.DEFAULT_LAYER);
        gameBackground.setBounds(0,0,800,800);

        int distanceBetweenLabels=630;
        for(JLabel label:new JLabel[]{timeCounter, pointCounter, livesCounter}){
            gamePanes.add(label,JLayeredPane.PALETTE_LAYER);
            label.setBounds(distanceBetweenLabels,0,80,40);
            label.setBackground(Color.WHITE);
            label.setForeground(Color.BLACK);
            label.setFont(new Font("Arial",Font.BOLD,20));
            label.setOpaque(true);
            distanceBetweenLabels-=90;
        }
        timeCounter.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
        pointCounter.setBorder(BorderFactory.createLineBorder(Color.BLUE,5));
        livesCounter.setBorder(BorderFactory.createLineBorder(Color.RED,5));
        gameFrame.setVisible(true);
    }

    private int entityInterval;
    private float entitySpeeder;
    private int pointMultiplier;

    private void startGame(difficulty difficulty){
        setupGUI();

        switch (difficulty){

            case EASY -> {
                entityInterval =1500;
                entitySpeeder =0.015f;
                newPlayer=new Player(30);
                pointMultiplier=1;
            }
            case NORMAL -> {
                entityInterval =1000;
                entitySpeeder =0.035f;
                newPlayer= new Player(20);
                pointMultiplier=2;
            }
            case HARD -> {
                entityInterval =700;
                entitySpeeder =0.055f;
                newPlayer= new Player(10);
                pointMultiplier=3;
            }
            case EXTREME -> {
                entityInterval =600;
                entitySpeeder =0.095f;
                newPlayer= new Player(5);
                pointMultiplier=5;
            }
        }
        gameThread.start();
        secondCounter.start();
    }
    private void difficultySetting(){
        JFrame difficultyFrame= new JFrame();
        SpringLayout layout=new SpringLayout();
        Container conPane=difficultyFrame.getContentPane();

        difficultyFrame.setDefaultCloseOperation(difficultyFrame.DO_NOTHING_ON_CLOSE);
        difficultyFrame.setLocation(gameFrame.getLocation().x+300,gameFrame.getLocation().y+100);
        difficultyFrame.getRootPane().setBorder(BorderFactory.createLineBorder(Color.RED,10));
        difficultyFrame.setSize(200,300);
        difficultyFrame.setResizable(false);
        difficultyFrame.setUndecorated(true);
        difficultyFrame.setLayout(layout);

        JLabel headText=new JLabel("WYBIERZ!");
        headText.setFont(new Font("Arial",Font.BOLD,20));

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER,headText,0,SpringLayout.HORIZONTAL_CENTER,conPane);
        layout.putConstraint(SpringLayout.NORTH,headText,10,SpringLayout.NORTH,conPane);

        conPane.add(headText);

        JButton easyButton=new JButton("LATWY");
        easyButton.addActionListener(e -> {
            difficultyFrame.setVisible(false);
            startGame(difficulty.EASY);
        });

        JButton casualButton=new JButton("NORMALNY");
        casualButton.addActionListener(e-> {
            difficultyFrame.setVisible(false);
            startGame(difficulty.NORMAL);
        });

        JButton hardButton=new JButton("TRUDNY");
        hardButton.addActionListener(e-> {
            difficultyFrame.setVisible(false);
            startGame(difficulty.HARD);
        });

        JButton extremeButton=new JButton("EXTREME");
        extremeButton.addActionListener(e-> {
            difficultyFrame.setVisible(false);
            startGame(difficulty.EXTREME);
        });

        int heightBetweenButtons=40;
        for (JButton button:new JButton[]{easyButton,casualButton,hardButton,extremeButton})
        {
            conPane.add(button);
            button.setFont(new Font("Arial",Font.BOLD,15));
            button.setPreferredSize(new Dimension(120,40));
            layout.putConstraint(SpringLayout.HORIZONTAL_CENTER,button,0,SpringLayout.HORIZONTAL_CENTER,conPane);
            layout.putConstraint(SpringLayout.NORTH,button,heightBetweenButtons,SpringLayout.NORTH,conPane);
            heightBetweenButtons+=50;
        }

        difficultyFrame.setVisible(true);
    }

    private String playerName;
    private void restartGame() {
        scoreBoard.updateBoard(playerName,newPlayer.getPoints()+ secondCounter.secondsPassed*pointMultiplier);
        try {scoreBoard.saveBoard();}
        catch (IOException e) {e.printStackTrace();}
        gameFrame.dispose();
        new MainFrame();
    }
    private void askForName(){
        JFrame nameInputFrame=new JFrame();
        nameInputFrame.setUndecorated(true);
        SpringLayout layout=new SpringLayout();
        Container conPane=nameInputFrame.getContentPane();
        nameInputFrame.setSize(360,100);
        nameInputFrame.setDefaultCloseOperation(nameInputFrame.DO_NOTHING_ON_CLOSE);
        nameInputFrame.setLocation(gameFrame.getLocation().x+300,gameFrame.getLocation().y+100);
        nameInputFrame.setLayout(layout);
        nameInputFrame.getRootPane().setBorder(BorderFactory.createLineBorder(Color.RED,10));

        JTextField nameInput=new JTextField();
        nameInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(nameInput.getText().length()>5)
                    e.consume();
                if(nameInput.getText().length()==5&& !Objects.equals(nameInput.getText(), "     ")){
                    playerName=nameInput.getText().toUpperCase();
                    nameInputFrame.dispose();
                    restartGame();
                }
            }});
        nameInput.setFont(new Font("Arial",Font.BOLD,50));
        nameInput.setPreferredSize(new Dimension(180,90));

        JLabel scoreLabel=new JLabel(": " + secondCounter.secondsPassed*pointMultiplier);
        scoreLabel.setFont(new Font("Arial",Font.BOLD,50));

        conPane.add(nameInput);
        conPane.add(scoreLabel);

        layout.putConstraint(SpringLayout.WEST,nameInput,0,SpringLayout.WEST,conPane);
        layout.putConstraint(SpringLayout.NORTH,nameInput,0,SpringLayout.NORTH,conPane);

        layout.putConstraint(SpringLayout.WEST,scoreLabel,10,SpringLayout.EAST,nameInput);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,scoreLabel,0,SpringLayout.VERTICAL_CENTER,conPane);

        nameInputFrame.setVisible(true);
    }
    private void makeEntity(){
        boolean onLeft;
        int entityRandomizer=ThreadLocalRandom.current().nextInt(750) + 1;
        int sideRandomizer=ThreadLocalRandom.current().nextInt(2);
        onLeft= sideRandomizer == 1;
        int entityPos=ThreadLocalRandom.current().nextInt(220)+450;

        if(entityRandomizer<=100)
            new NormalDuck(gameFrame,newPlayer,entityPos,onLeft);
        else if(entityRandomizer<=250)
            new FastDuck(gameFrame,newPlayer,entityPos,onLeft);
        else if (entityRandomizer<=350)
            new TankDuck(gameFrame,newPlayer,entityPos,onLeft);
        else if(entityRandomizer<=500)
            new ConfusingDuck(gameFrame,newPlayer,entityPos,onLeft);
        else if(entityRandomizer<=600)
            new ShipObstacle(gameFrame,newPlayer,entityPos,onLeft);
        else
            new BombObstacle(gameFrame,newPlayer,entityPos,onLeft);
    }
    enum difficulty{EASY,NORMAL,HARD,EXTREME}
}
