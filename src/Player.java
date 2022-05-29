public class Player
{
    private int lives;
    private int points;

    Player(int lives){
        this.lives =lives;
        this.points=0;
    }

    public void loseLives(int lostLives){
        lives-=lostLives;
    }
    public void addPoints(int addedPoints){
        points+=addedPoints;
    }
    public int getPoints() {
        return points;
    }

    public int getLives(){
        return lives;
    }
}
