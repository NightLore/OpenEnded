package game;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Assets
{
    private String[] biomes;
    private HashMap<String, String[]> floorFiles;
    private HashMap<String, String[]> blockFiles;
    private HashMap<String, BufferedImage[]> floors;
    private HashMap<String, BufferedImage[]> blocks;
    
    private String[] charSkins;
    private HashMap<String, String> charFiles;
    private HashMap<String, BufferedImage> skins;
    
    public void loadFiles()
    {
        floorFiles = new HashMap<String,String[]>();
        blockFiles = new HashMap<String,String[]>();
        biomes = new String[]{ "PLAINS" };
        floorFiles.put( biomes[0], new String[]{ "background.png" } );
        blockFiles.put( biomes[0], new String[]{ "DarkGreen.png" } );
        
        charFiles = new HashMap<String,String>();
        charSkins = new String[]{ "greyCircle", "redCircle", "smallCircle" };
        charFiles.put( charSkins[0], "player.png" );
        charFiles.put( charSkins[1], "redcircle.png" );
        charFiles.put( charSkins[2], "smallcircle.png" );
    }
    
    public void loadAssets()
    {
        loadMapAssets();
        loadSpriteAssets();
    }
    
    private void loadMapAssets()
    {
        floors = new HashMap<String, BufferedImage[]>();
        blocks = new HashMap<String, BufferedImage[]>();
        for ( String biome : biomes )
        {
            String[] fFiles = floorFiles.get( biome );
            String[] bFiles = floorFiles.get( biome );
            int fLength = fFiles.length;
            int bLength = bFiles.length;
            BufferedImage[] floor = new BufferedImage[fLength];
            BufferedImage[] block = new BufferedImage[bLength];
            for ( int i = 0; i < fLength; i++ ) 
                floor[i] = toImage( "/imgs/" + fFiles[i] );// TODO image packages
            for ( int i = 0; i < bLength; i++ ) 
                block[i] = toImage( "/imgs/" + bFiles[i] );
            floors.put( biome, floor );
            blocks.put( biome, block );
        }
    }
    
    private void loadSpriteAssets()
    {
        skins = new HashMap<String,BufferedImage>();
        for ( String s : charSkins )
        {
            skins.put( s, toImage( "/imgs/" + charFiles.get( s ) ) );
        }
    }
    
    /**
     * Return BufferedImage object of a picture file, starts at this project's path
     * @param fileName
     * @return buffered image
     */
    public static BufferedImage toImage( String fileName )
    {
        try { 
            return ImageIO.read( Assets.class.getResource( fileName ) );
        } catch ( java.io.IOException e ) { 
            System.out.println( "Cannot find: " + fileName );
            e.printStackTrace(); 
            @SuppressWarnings("resource")
            Scanner scanIn = new Scanner( System.in );
            System.out.print( "Input file: " );
            return toImage( scanIn.nextLine() );
        }
    }
}
