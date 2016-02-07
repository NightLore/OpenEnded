package gui;

public interface Navigator
{
    public static final String UP = "UP";
    public static final String LEFT = "LEFT";
    public static final String DOWN = "DOWN";
    public static final String RIGHT = "RIGHT";
    
    public void up();
    public void left();
    public void down();
    public void right();
}
