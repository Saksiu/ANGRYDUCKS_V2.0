import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class ScoreBoard     //klasa odpowiedzialna za tworzenie listy wyników
{
    private final JFrame scoreFrame;

    private ArrayList<HighScore> highScores=new ArrayList<>();
    private final String filePath="src/score.txt";

    ScoreBoard(){
        loadBoard();

        scoreFrame=new JFrame("Wyniki");
        Container conPane = scoreFrame.getContentPane();
        scoreFrame.setLocation((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/10
                                ,(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/4);
        scoreFrame.setSize(300 ,400);
        scoreFrame.setResizable(false);
        scoreFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        scoreFrame.setLayout(new BorderLayout());

        JList<Object> scoreList = new JList<>(highScores.toArray());
        scoreList.setVisible(true);
        scoreList.setFont(new Font("Arial",Font.BOLD,36));
        scoreList.setLayoutOrientation(JList.VERTICAL);
        scoreList.setBackground(Color.red);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(scoreList);
        conPane.add(scrollPane,BorderLayout.CENTER);
    }

   public void updateBoard(String playerName,int playerScore)    //funkcja odpowiedzialna za aktualizacje tablicy wyników po zakonczeniu gry
    {
            highScores.add(new HighScore(playerName.trim(),String.valueOf(playerScore)));
            highScores.sort(Comparator.comparingInt(o -> Integer.parseInt(o.getScore())));
            Collections.reverse(highScores);
    }


    public void saveBoard() throws IOException
    {

        FileOutputStream file=new FileOutputStream(filePath);
        ObjectOutputStream output=new ObjectOutputStream(file);

        for(HighScore h:highScores)
        output.writeObject(h);

        file.close();
        output.close();
    }

    public void loadBoard()  //metoda zczytująca wyniki z pliku
    {
        try
        {
            if(new File(filePath).length()>0){
                FileInputStream file = new FileInputStream(filePath);
                ObjectInputStream input=new ObjectInputStream(file);
                while (file.available()>0)
                    highScores.add((HighScore) input.readObject());

                file.close();
                input.close();
            }
        } catch (Exception e)
        {e.printStackTrace();}
    }

    public void showBoard(){
        scoreFrame.setVisible(true);
    }

    public void hideBoard(){
        scoreFrame.setVisible(false);
    }
}
