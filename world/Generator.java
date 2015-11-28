package world;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *  This class randomly generates 2D-boolean arrays with random content intended
 *  to simulate random generation of a tiled game where "false" is a wall and 
 *  "true" is a space. All code assumes the 2D arrays are squares.<br>
 *  Current types of generation:
 *  @see world.Generator#generate(int)
 *
 *  @author  Nathan Man-ho Lui
 *  @version Nov 6, 2015
 *  @author  Assignment: OpenEnded
 */
public class Generator
{
    private static final Random RAND = new Random();

    public enum Generation {
        DEFAULT, RUIN, MAZE;
        
        private static final Generation[] TYPES = values();
        private static final int NUMTYPES = TYPES.length;
        public static Generation randomType()
        {
            return TYPES[RAND.nextInt( NUMTYPES )];
        }
        public static class Properties
        {
            private List<Point> points;
            private boolean[][] map;
            private Generation g;
            public Properties( Generation g, int size )
            {
                this.g = g;
                this.map = new boolean[size][size];
                this.points = new ArrayList<Point>();
            }
            
            public boolean[][] getMap()
            {
                return map;
            }
            public List<Point> getPoints()
            {
                return points;
            }
            public Generation getGeneration()
            {
                return g;
            }
            public int getSize()
            {
                return map.length;
            }
        }
    }
//    private static final List<Generation> TYPES = Collections.unmodifiableList(Arrays.asList(values()));
    private static Generation lastGeneration = Generation.DEFAULT;
    
    public static Generation lastGeneration()
    {
        return lastGeneration;
    }
    
    public static int randInt( int limit )
    {
        return RAND.nextInt( limit );
    }
    public static boolean nextBoolean()
    {
        return RAND.nextBoolean();
    }
    
    /**
     * Randomly picks a type of generation and returns a 2D-boolean 
     * representation of that generation
     * @param size
     * @return
     */
    public static Generation.Properties generate( Generation g, int size )
    {
        return generate( g, size, 0, 0, 0, 0 );
    }
    public static Generation.Properties generate( Generation g, int size, int x, int y, int w, int h )
    {
        return generate( RAND, g, size, x, y, w, h );
    }
    private static Generation.Properties generate( Random rand, Generation g, int size )
    {
        return generate( rand, g, size, 0, 0, 0, 0 );
    }
    /**
     * Generates a map with a restricted rectangular area to remain as a space
     * @param g
     * @param size
     * @param x coordinate to avoid
     * @param y coordinate to avoid 
     * @param w total distance of avoidance in x direction
     * @param h total distance of avoidance in y direction
     * @return
     */
    public static Generation.Properties generate( Random rand, Generation g, int size, int x, int y, int w, int h )
    {
        Generation.Properties prop = new Generation.Properties( g, size );
        boolean[][] map = prop.getMap();
        switch( g )
        {
            case DEFAULT:
                fill( map, true );
                generateDefault( rand, map );
                break;
            case MAZE:
                fill( map, false );
                generateRuinEdge( rand, map );
                generateMaze( rand, map );
                int size1 = map.length - 2;
                int min = size1 / 4;
                int x1 = rand.nextInt( size1 - min ) + 1;
                int y1 = rand.nextInt( size1 - min ) + 1;
                int w1 = rand.nextInt( size1 - x1 ) + min;
                int h1 = rand.nextInt( size1 - y1 ) + min;
                generateRoom( map, x1, y1, w1, h1, true );
                randomPathMid( rand, map, x1 + w1 / 2, y1 + h1 / 2 );
                break;
            case RUIN:
                fill( map, true );
                generateRuin( rand, map );
                generateDefault( rand, map );
                break;
            default:
                break;
        }
        generateRoom( map, x - w / 2, y - h / 2, w, h, true );
        findPoints( prop );
        return prop;
    }
//    private static boolean[][] generate( Random rand, Generation g, int size )
//    {
//        boolean[][] map = new boolean[size][size];
//        switch( g )
//        {
//            case DEFAULT:
//                fill( map, true );
//                generateDefault( rand, map );
//                break;
//            case MAZE:
//                fill( map, false );
//                generateRuin( rand, map );
//                generateMaze( rand, map );
//                int min = size / 5;
//                int x = rand.nextInt( size );
//                int y = rand.nextInt( size );
//                int w = rand.nextInt( size / 4 ) + min;
//                int h = rand.nextInt( size / 4 ) + min;
//                generateRoom( map, x, y, w, h, true );
//                randomPathMid( rand, map, x + w / 2, y + w / 2 );
//                break;
//            case RUIN:
//                fill( map, true );
//                generateRuin( rand, map );
//                generateDefault( rand, map );
//                break;
//            default:
//                break;
//        }
//        return map;
//    }
    
    // ------------------------ generation methods ------------------------ //
    private static void findPoints( Generation.Properties prop )
    {
        List<Point> points = prop.getPoints();
        boolean[][] map = prop.getMap();
        for ( int i = 0; i < map.length; i++ )
        {
            for ( int j = 0; j < map[i].length; j++ )
            {
                if ( map[i][j]/*isValid( map, i, j )*/ )
                    points.add( new Point( i, j ) );
            }
        }
    }
//    private static boolean isValid( boolean[][] map, int x, int y )
//    {
//        return !inBounds( map, x, y ) || map[x][y] 
//                     && ( isValid( map, x + 1, y )
//                       || isValid( map, x, y + 1 ) 
//                       || isValid( map, x - 1, y )
//                       || isValid( map, x, y - 1 ) );
//    }
    
    private static void fill( boolean[][] map, boolean fill )
    {
        generateRoom( map, 0, 0, map.length, map[0].length, fill );
    }
    private static void generateRoom( boolean[][] map, int x, int y, int w, int h, boolean fill )
    {
        for ( int i = 0; i < w; i++ )
        {
            for ( int j = 0; j < h; j++ )
            {
                if ( inBounds( map, x+i, y+j ) )
                    map[x+i][y+j] = fill;
            }
        }
            
    }
    
    private static void generateDefault( Random rand, boolean[][] map )
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
    
    private static void generateMaze( Random rand, boolean[][] map )
    {
        int size = map.length;
        int left = rand.nextInt( size - 2 ) + 1;
        int top = rand.nextInt( size - 2 ) + 1;
        int right = rand.nextInt( size - 2 ) + 1;
        int down = rand.nextInt( size - 2 ) + 1;
        randomPathEdge( rand, map, 0, left );
        randomPathEdge( rand, map, top, 0 );
        randomPathEdge( rand, map, size - 1, right );
        randomPathEdge( rand, map, down, size - 1 );
    }
    private static void randomPathEdge( Random rand, boolean[][] map, int x, int y )
    {
        int dx = ( x == 0 ) ? 1 : ( x == map.length-1 ) ? -1 : 0;
        int dy = ( y == 0 ) ? 1 : ( y == map.length-1 ) ? -1 : 0;
        if( dx == 0 && dy == 0 ) // TODO remove check when coding finished?
            System.err.println( "Cannot randomPath with no directions" );
        randomPath( rand, map, x, y, dx, dy );
    }
    private static void randomPathMid( Random rand, boolean[][] map, int x, int y )
    {
        int dx = rand.nextInt( 3 ) - 1;
        int dy = ( dx == 0 ) ? ( rand.nextBoolean() ? 1 : -1 ) : 0;
        randomPath( rand, map, x, y, dx, dy );
    }
    private static void randomPath( Random rand, boolean[][] map, int x, int y, int dx, int dy )
    {
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
    private static void generateRuinEdge( Random rand, boolean[][] map )
    {
        generateRuin( rand, map, 0, 0, map.length, map[0].length );
    }
    private static void generateRuin( Random rand, boolean[][] map )
    {
        int size = map.length - 2;
        int min = size / 4;
        int x1 = rand.nextInt( size - min ) + 1;
        int y1 = rand.nextInt( size - min ) + 1;
        int w1 = rand.nextInt( size - x1 ) + min;
        int h1 = rand.nextInt( size - y1 ) + min;
        generateRuin( rand, map, x1, y1, w1, h1 );
    }
    private static void generateRuin( Random rand, boolean[][] map, int x, int y, int w, int h )
    {
        int length = Math.max( w, h );
        for ( int i = 0; i < length; i++ )
        {
            if ( i < w )
            {
                if ( inBounds( map, x+i, y ) )
                    map[x+i][y] = rand.nextBoolean();
                if ( inBounds( map, x+i, y+h-1 ) )
                    map[x+i][y+h-1] = rand.nextBoolean();
            }
            if ( i < h && inBounds( map, x, y+i ) )
            {
                if ( inBounds( map, x, y+i ) )
                    map[x][y+i] = rand.nextBoolean();
                if ( inBounds( map, x+w-1, y+i ) )
                    map[x+w-1][y+i] = rand.nextBoolean();
            }
        }
    }
    
    // ---------------------------- Test methods --------------------------- //
    
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
            if ( s.equals( "fill" ) )
            {
                System.out.println( "Input Boolean: " );
                Generator.fill( map, scanUser.nextBoolean() );
                System.out.println( "Filled" );
            }
            else if ( s.equals( "def" ) )
            {
                Generator.generateDefault( rand, map );
                System.out.println( "Generated default" );
            }
            else if ( s.equals( "maze" ) )
            {
                Generator.generateMaze( rand, map );
                System.out.println( "Generated maze" );
            }
            else if ( s.equals( "ruin" ) )
            {
                Generator.generateRuin( rand, map );
                System.out.println( "Generated ruin" );
            }
            else if ( s.equals( "ruinInput" ) )
            {
                System.out.println( "Input X: " );
                int x = scanUser.nextInt();
                System.out.println( "Input Y: " );
                int y = scanUser.nextInt();
                System.out.println( "Input width: " );
                int w = scanUser.nextInt();
                System.out.println( "Input height: " );
                int h = scanUser.nextInt();
                Generator.generateRuin( rand, map, x, y, w, h );
                System.out.println( "Generated ruin" );
            }
            else if ( s.equals( "room" ) )
            {
                System.out.println( "Input X: " );
                int x = scanUser.nextInt();
                System.out.println( "Input Y: " );
                int y = scanUser.nextInt();
                System.out.println( "Input width: " );
                int w = scanUser.nextInt();
                System.out.println( "Input height: " );
                int h = scanUser.nextInt();
                System.out.println( "Input Boolean: " );
                Generator.generateRoom( map, x, y, w, h, scanUser.nextBoolean() );
                System.out.println( "Generated room" );
            }
            else if ( s.equals( "pathEdge" ) )
            {
                System.out.println( "Input X: " );
                int x = scanUser.nextInt();
                System.out.println( "Input Y: " );
                int y = scanUser.nextInt();
                Generator.randomPathEdge( rand, map, x, y );
            }
            else if ( s.equals( "pathMid" ) )
            {
                System.out.println( "Input X: " );
                int x = scanUser.nextInt();
                System.out.println( "Input Y: " );
                int y = scanUser.nextInt();
                Generator.randomPathMid( rand, map, x, y );
            }
            else if ( s.equals( "path" ) )
            {
                System.out.println( "Input X: " );
                int x = scanUser.nextInt();
                System.out.println( "Input Y: " );
                int y = scanUser.nextInt();
                System.out.println( "Input dX: " );
                int dx = scanUser.nextInt();
                System.out.println( "Input dY: " );
                int dy = scanUser.nextInt();
                Generator.randomPath( rand, map, x, y, dx, dy );
            }
            else if ( s.equals( "DEF" ) )
            {
                map = Generator.generate( rand, Generation.DEFAULT, size ).getMap();
                System.out.println( "Generated true default" );
            }
            else if ( s.equals( "MAZE" ) )
            {
                map = Generator.generate( rand, Generation.MAZE, size ).getMap();
                System.out.println( "Generated true Maze" );
            }
            else if ( s.equals( "RUIN" ) )
            {
                map = Generator.generate( rand, Generation.RUIN, size ).getMap();
                System.out.println( "Generated true Ruin" );
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
