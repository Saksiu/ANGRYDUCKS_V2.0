import javax.swing.*;

public class ShipObstacle extends Entity
{
    public ShipObstacle(JFrame frame,Player player,int initialPosY,boolean onLeft)
    {
        super(frame, player,initialPosY,onLeft);
    }
    @Override
    protected void move(){
        if(onLeft)
            entity.setBounds(entity.getX()+2,initialPosY,80,80);
        else
            entity.setBounds(entity.getX()-2,initialPosY,80,80);
    }
    @Override
    protected ImageIcon setImage(){
        if(onLeft)
            return new ImageIcon("img/boat-right.png");
        return new ImageIcon("img/boat-left.png");
    }
    @Override
    protected void place(){
        layeredPane.add(entity,JLayeredPane.POPUP_LAYER);
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
