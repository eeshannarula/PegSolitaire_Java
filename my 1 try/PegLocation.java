package peggame;

public class PegLocation {
    public int[] locToGo;
    public int[] locToRemove;

    public String dir;

    public void Update(int[][] dirRatio,String direction){
        locToRemove = dirRatio[0];
        locToGo = dirRatio[1];
        dir = direction;
    }
}
