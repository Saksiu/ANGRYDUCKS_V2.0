import javax.swing.*;
import java.awt.*;

public class Entity
{
    protected Player newPlayer;
    protected final JButton entity;
    public boolean isAlive =true;
    protected int initialPosY;
    protected boolean onLeft;
    protected int timesHit;
    protected JLayeredPane layeredPane;

    public Entity(JFrame frame, Player player, int initialPosY, boolean onLeft){
        this.initialPosY=initialPosY;
        this.onLeft=onLeft;
        layeredPane=frame.getLayeredPane();
        newPlayer=player;
        entity =new JButton(setImage());
        entity.setBorder(BorderFactory.createEmptyBorder());
        entity.setContentAreaFilled(false);

        timesHit=0;
        entity.addActionListener(e -> {
            timesHit++;
            onHit();
        });
        place();

        EntityThread entityThread = new EntityThread();
        entityThread.start();
    }

    public class EntityThread extends Thread{
        @Override
        public void run()
        {
            while(isAlive){
                try {Thread.sleep(20);}
                catch (InterruptedException e) {e.printStackTrace();}
                move();
                if(outOfScreen()||newPlayer.getLives()<=0){
                    entity.setVisible(false);
                    loseLives();
                    break;
                }
            }
        }
    }
    protected void move(){}
    protected void loseLives(){}
    protected void onHit(){}
    protected void place(){}
    protected boolean outOfScreen(){return false;}
    protected ImageIcon setImage(){return null;}
}
