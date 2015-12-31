package game;

public class Settings
{
    private int sound, music, numEnemies, numPlayers, numNPCs;
    private boolean debug;
    public int getSound()
    {
        return sound;
    }
    public void setSound( int sound )
    {
        this.sound = sound;
    }
    public int getMusic()
    {
        return music;
    }
    public void setMusic( int music )
    {
        this.music = music;
    }
    public int getNumPlayers()
    {
        return numPlayers;
    }
    public void setNumPlayers( int numPlayers )
    {
        this.numPlayers = numPlayers;
    }
    public int getNumNPCs()
    {
        return numNPCs;
    }
    public void setNumNPCs( int numNPCs )
    {
        this.numNPCs = numNPCs;
    }
    public int getNumEnemies()
    {
        return numEnemies;
    }
    public void setNumEnemies( int numEnemies )
    {
        this.numEnemies = numEnemies;
    }
    public boolean isDebug()
    {
        return debug;
    }
    public void setDebug( boolean debug )
    {
        this.debug = debug;
    }
    
}
