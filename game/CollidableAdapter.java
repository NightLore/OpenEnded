package game;

public class CollidableAdapter implements Collidable
{
    private boolean canCollide;

    @Override
    public boolean canCollide()
    {
        return canCollide;
    }

    @Override
    public void setCollidable( boolean canCollide )
    {
        this.canCollide = canCollide;
    }

}
