package game;

/**
 *  A holder class for the Game settings
 *
 *  @author  Nathan Man-ho Lui
 *  @version Dec 31, 2015
 */
public class Settings
{
    // note: with getters and setters, can be replaced with array
    public int sound, music, numEnemies, numNPCs;
    public boolean debug, playerFriendlyFire, enemyFriendlyFire;
    
    public Settings()
    {
        sound = 100;
        music = 100;
        numEnemies = 20;
        numNPCs = 4;
        debug = false;
        playerFriendlyFire = false;
        enemyFriendlyFire = false;
    }
    
    public Settings( Settings settings )
    {
        this.copy( settings );
    }
    
    public void copy( Settings settings ) // basically same as clone()
    {
        this.sound = settings.sound;
        this.music = settings.music;
        this.numEnemies = settings.numEnemies;
        this.numNPCs = settings.numNPCs;
        this.debug = settings.debug;
        this.playerFriendlyFire = settings.playerFriendlyFire;
        this.enemyFriendlyFire = settings.enemyFriendlyFire;
    }
}
