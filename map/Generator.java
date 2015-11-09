package map;

import java.util.Random;
import java.util.Scanner;

/**
 *  This class randomly generates 2D-boolean arrays with random content intended
 *  to simulate random generation of a tiled game where "false" is a wall and 
 *  "true" is a space.<br>
 *  Current types of generation:
 *  @see map.Generator#generate(int)
 *
 *  @author  Nathan Man-ho Lui
 *  @version Nov 6, 2015
 *  @author  Assignment: OpenEnded
 */
public class Generator
{
    private static final Random RAND = new Random();

    public enum Generation {
        DEFAULT, ROOM, MAZE;
        
        private static final Generation[] TYPES = values();
        private static final int NUMTYPES = TYPES.length;
        public static Generation randomType()
        {
            return TYPES[RAND.nextInt( NUMTYPES )];
        }
    }
//    private static final List<Generation> TYPES = Collections.unmodifiableList(Arrays.asList(values()));
    
    /**
     * Randomly picks a type of generation and returns a 2D-boolean 
     * representation of that generation
     * @param size
     * @return
     */
    public static boolean[][] generate( int size )
    {
        return generate( Generation.randomType(), size );
    }
    public static boolean[][] generate( Generation g, int size )
    {
        return generate( RAND, g, size );
    }
    private static boolean[][] generate( Random rand, Generation g, int size )
    {
        boolean[][] map = new boolean[size][size];
        switch( g )
        {
            case DEFAULT:
                fill( map, true );
                generate( rand, map );
                break;
            case MAZE:
                fill( map, false );
                generateRoom( rand, map );
                generateMaze( rand, map );
                break;
            case ROOM:
                fill( map, true );
                generateRoom( rand, map );
                generate( rand, map );
                break;
            default:
                break;
        }
        return map;
    }
    
    private static void fill( boolean[][] map, boolean fill )
    {
        for ( boolean[] row : map )
        {
            for ( int i = 0; i < row.length; i++ )
            {
                row[i] = fill;
            }
        }
    }
    
//    private static void generate( boolean[][] map )
//    {
//        generate( RAND, map );
//    }
    private static void generate( Random rand, boolean[][] map )
    {
        int size = map.length;
        int amt = rand.nextInt( size / 4 ) + size / 3;
        for ( int i = 0; i < amt; i++ )
        {
            int x = rand.nextInt( size );
            int y = rand.nextInt( size );
            if ( map[x][y] == false )
                i--;
            map[x][y] = false;
        }
    }
    
//    private static void generateMaze( boolean[][] map )
//    {
//        generateMaze( RAND, map );
//    }
    private static void generateMaze( Random rand, boolean[][] map )
    {
        int size = map.length;
        int left = rand.nextInt( size - 2 ) + 1;
        int top = rand.nextInt( size - 2 ) + 1;
        int right = rand.nextInt( size - 2 ) + 1;
        int down = rand.nextInt( size - 2 ) + 1;
        randomPath( rand, map, 0, left );
        randomPath( rand, map, top, 0 );
        randomPath( rand, map, size - 1, right );
        randomPath( rand, map, down, size - 1 );
    }
    private static void randomPath( Random rand, boolean[][] map, int x, int y )
    {
        int dx = ( x == 0 ) ? 1 : ( x == map.length-1 ) ? -1 : 0;
        int dy = ( y == 0 ) ? 1 : ( y == map.length-1 ) ? -1 : 0;
        while ( inBounds( map, x, y ) )
        {
            map[x][y] = true;
            x += dx;
            y += dy;
            if ( rand.nextBoolean() && rand.nextBoolean() )
            {
                if ( dx != 0 )
                {
                    dy = rand.nextBoolean() ? 1 : -1 ;
                    dx = 0;
                }
                else if ( dy != 0 )
                {
                    dx = rand.nextBoolean() ? 1 : -1 ;
                    dy = 0;
                }
            }
        }
        
        
    }
    private static boolean inBounds( boolean[][] map, int x, int y )
    {
        return x >= 0 && x < map.length && y >= 0 && y < map[0].length;
    }
    
    /**
     * Assumes map is a square. Randomly generates walls at the edge of the map
     * @param map
     */
//    private static void generateRoom( boolean[][] map )
//    {
//        generateRoom( RAND, map );
//    }
    private static void generateRoom( Random rand, boolean[][] map )
    {
        int length = map.length;
        for ( int i = 0; i < length; i++ )
        {
            map[i][0] = rand.nextBoolean();
            map[0][i] = rand.nextBoolean();
            map[i][length - 1] = rand.nextBoolean();
            map[length - 1][i] = rand.nextBoolean();
        }
    }
    
    public static int randInt( int limit )
    {
        return RAND.nextInt( limit );
    }
    
    public static void main( String[] args )
    {
        @SuppressWarnings("resource")
        Scanner scanUser = new Scanner( System.in );
        System.out.println( "Enter seed: " );
        long seed = scanUser.nextLong();
        Random rand = seed>=0?new Random( seed ):new Random();
        System.out.println( "Random seed accepted: " + (seed>=0?seed:"random" ));
        
        System.out.println( "Enter size: " );
        int size = scanUser.nextInt();
        boolean[][] map = new boolean[size][size];
        System.out.println( "Map created of size " + size );
        
        while ( true )
        {
            System.out.println( printMap( map ) );
            String s = scanUser.next();
            switch ( s.charAt( 0 ) )
            {
                case 'f':
                    System.out.println( "Input Boolean: " );
                    Generator.fill( map, scanUser.nextBoolean() );
                    System.out.println( "Filled" );
                    break;
                case 'g':
                    Generator.generate( rand, map );
                    System.out.println( "Generated default" );
                    break;
                case 'm':
                    Generator.generateMaze( rand, map );
                    System.out.println( "Generated maze" );
                    break;
                case 'r':
                    Generator.generateRoom( rand, map );
                    System.out.println( "Generated room" );
                    break;
                    
            }
        }
    }
    
    private static String printMap( boolean[][] map )
    {
        String s = "";
        for ( int i = 0; i < map.length; i++ ) {
            for ( int j = 0; j < map[0].length; j++ )
                s += ( map[i][j] ? ' ' : 'X' ) + " ";
            s += "\n";
        }
        return s;
    }

}
