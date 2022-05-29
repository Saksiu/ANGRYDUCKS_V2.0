import java.io.Serializable;

public class HighScore implements Serializable
{
        private String name;
        private String score;
        HighScore(String name, String score){
            this.name=name;
            this.score=score;
        }

    @Override
    public String toString()
    {
        return name.toUpperCase()+": "+score;
    }
    public String getScore()
    {
        return score;
    }
}