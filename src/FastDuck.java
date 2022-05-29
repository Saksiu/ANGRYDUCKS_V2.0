import javax.swing.*;

public class FastDuck extends Entity
{
    public FastDuck(JFrame frame,Player player,int initialPosY,boolean onLeft)
    {
        super(frame, player,initialPosY,onLeft);
    }

    @Override
    protected void move(){
        if(onLeft)
            entity.setBounds(entity.getX()+6,initialPosY,40,40);
        else
            entity.setBounds(entity.getX()-6,initialPosY,40,40);
    }
    @Override
    protected void loseLives(){newPlayer.loseLives(1);}
    @Override
    protected void onHit(){
        newPlayer.addPoints(10);
        isAlive=false;
        entity.setVisible(false);
    }
    @Override
    protected ImageIcon setImage(){
        if(onLeft)
            return new ImageIcon("img/fastduck-right.png");
        return new ImageIcon("img/fastduck-left.png");
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
