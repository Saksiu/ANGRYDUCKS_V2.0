import javax.swing.*;

public class TankDuck extends Entity
{

    public TankDuck(JFrame frame, Player player, int initialPosY, boolean onLeft)
    {
        super(frame, player, initialPosY, onLeft);
    }

    @Override
    protected void move(){
        if(onLeft)
            entity.setBounds(entity.getX()+2,initialPosY,40,40);
        else
            entity.setBounds(entity.getX()-2,initialPosY,40,40);
        }
    @Override
    protected void loseLives(){newPlayer.loseLives(2);}
    @Override
    protected void onHit(){
        if(timesHit>=4){
            newPlayer.addPoints(20);
            isAlive=false;
            entity.setVisible(false);
        }
        if(timesHit==2)
            if(onLeft)
                entity.setIcon(new ImageIcon("img/tankduckdamaged-right.png"));
            else
                entity.setIcon(new ImageIcon("img/tankduckdamaged-left.png"));

    }
    @Override
    protected ImageIcon setImage(){
        if(onLeft)
            return new ImageIcon("img/tankduck-right.png");
        return new ImageIcon("img/tankduck-left.png");
    }
    @Override
    protected void place(){
        layeredPane.add(entity,JLayeredPane.MODAL_LAYER);
        if(onLeft)
            entity.setBounds(-40, initialPosY,0,0);
        else
            entity.setBounds(800,initialPosY,0,0);
    }
    @Override
    protected boolean outOfScreen(){
        return (entity.getX() >= 790 && onLeft) || (entity.getX() <= -30 && !onLeft);
    }

}
