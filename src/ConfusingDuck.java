import javax.swing.*;

public class ConfusingDuck extends Entity
{
    public ConfusingDuck(JFrame frame,Player player,int initialPosY,boolean onLeft)
    {
        super(frame, player,initialPosY,onLeft);
    }

    @Override
    protected void move(){
        if(onLeft)
            entity.setBounds(entity.getX()+4, (int)Math.round(Math.sin(entity.getX()/20)*25)+initialPosY,40,40);
        else
            entity.setBounds(entity.getX()-4, (int)Math.round(Math.sin(entity.getX()/20)*25)+initialPosY,40,40);
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
            return new ImageIcon("img/confusingduck-right.png");
        return new ImageIcon("img/confusingduck-left.png");
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
