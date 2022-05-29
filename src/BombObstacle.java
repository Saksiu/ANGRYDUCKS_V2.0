import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class BombObstacle extends Entity
{
    public BombObstacle(JFrame frame, Player player, int initialPosY, boolean onLeft)
    {
        super(frame, player,initialPosY,onLeft);
    }
    @Override
    protected void move(){
            entity.setBounds(entity.getX(),entity.getY()+2,30,40);
    }
    @Override
    protected ImageIcon setImage(){
            return new ImageIcon("img/bomb.png");
    }
    @Override
    protected void place(){
        layeredPane.add(entity,JLayeredPane.POPUP_LAYER);
        int rand= ThreadLocalRandom.current().nextInt(400)+100;
        entity.setBounds(rand,0,30,40);
    }
    @Override
    protected void onHit(){
        newPlayer.loseLives(1);
        entity.setVisible(false);
    }
    @Override
    protected boolean outOfScreen(){
        return entity.getY() >= 790;
    }
}
