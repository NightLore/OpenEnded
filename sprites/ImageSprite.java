package sprites;


import java.awt.*;
import java.awt.image.*;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Captain Awesome (http://www.javagaming.org/index.php?action=profile;u=28320)
 * You may use this sprite-class (or parts of it) in any way you want, as long
 * as you don't remove this notice and give me credit for my work.
 * 
 * The only thing I didn't make myself is the splitImage(); method,
 * which I found (and copied) from: http://www.javalobby.org/articles/ultimate-image/#13
 *
 * The reference to the BufferedImage you use in the constructor is not kept
 * since the Sprite creates it's own optimized BufferedImage.
 *
 */
public class ImageSprite implements Cloneable, Serializable {
    public ImageSprite(BufferedImage img) {
        spriteImg = toCompatibleImage(img);
    }

    @Override
    public ImageSprite clone() {
        try {
            return (ImageSprite) super.clone();
        }catch (CloneNotSupportedException e) {
            System.out.println("Clone failed.");
            return null;
        }
    }
    
    /**
     * Starts the animation of the sprite.
     * The user must then call continueAnimation() in order to animate the sprite.
     */
    public void setAnimation(int sleep) {
        sleepTime = sleep;
        currentSleepFrame = 0;
        runAnim = true;
    }

    public boolean isAnimating() {
        return runAnim;
    }

    /**
     * Stops the animation
     */
    public void stopAnimation() {
        runAnim=false;
    }


    /**
     * Paints the sprite. If splitSprite has been used,
     * it will paint the current frame.
     */
    public void paint(Graphics g) {
        g.drawImage(this.getImage(), this.getRealX(), this.getRealY(), null);
    }

    //Continues an animation
    public void continueAnimation() {
        if(isAnimating() && currentSleepFrame >= sleepTime) {
            currentSleepFrame = 0;
            this.nextFrame();
        }else {
            currentSleepFrame ++;
        }
    }


    /**
     *  Paints the original Sprite if setAnimation has been used
     */
    public void paintOrig(Graphics g) {
        g.drawImage(spriteImg, x, y, null);
    }

    /**
     * Sets the position based on the parameters
     */
    public void setPosition(int x, int y) {
        this.x=x;
        this.y=y;
    }

    
    /**
     * Defines which reference pixel (i.e where the image will be placed on the x/y coordinates)
     * @param x
     * @param y
     */
    public void setRefPixel(int x, int y) {
        refX = x;
        refY = y;
    }

    /**
     * Creates an animation of the current sprite, @param cols & rows decides
     * how many columnss and rows the sprite should be split into
     * It then modifies the @var frameSequence to hold every spritenumber in animImg
     */
    public void splitSprite(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;

        animImg = splitImage(spriteImg, cols, rows);
        frameSequence = new int[animImg.length];
        for(int i=0;i<animImg.length;i++)frameSequence[i]=i;
    }

    public void setFrame(int frame) {
        currentFrame = frame;
    }

    /**
     * Edits the framesequence (alters the animation), 2 versions
     * The first sets a name to use with getFrameSequence();
     * The other one just sets the framesequence
     */
    public void setFrameSequence(int[] sequence, String name) {
        frameSequence = sequence;
        currentFrame = 0;

        frameSequenceName = name;
    }

    public void setFrameSequence(int[] sequence) {
        frameSequence = sequence;
        currentFrame = 0;

        frameSequenceName = "UNDEFINED";
    }

    public String getFrameSequence() {
        return frameSequenceName;
    }

    public int[] getFrames() {
        return this.frameSequence;
    }

    /**
     * Goes to the next frame in the animation
     */
    public void nextFrame() {
        currentFrame++;
        if(currentFrame>=frameSequence.length)currentFrame=0;
    }

    public int getFrame() {
        return currentFrame;
    }

    public int getSize() {
        if(animImg!=null)return animImg.length;
        else return 1;
    }

    /**
     * Splits the image to create an animation
     */
    private static BufferedImage[] splitImage(BufferedImage img, int cols, int rows) {
        int w = img.getWidth()/cols;
        int h = img.getHeight()/rows;
        int num = 0;
        BufferedImage imgs[] = new BufferedImage[cols*rows];
        
        for(int y = 0; y < rows; y++) {
            for(int x = 0; x < cols; x++) {
                if(num==imgs.length)break;
                imgs[num] = createCompatibleImage(w, h);
                // Tell the graphics to draw only one block of the image
                Graphics2D g = imgs[num].createGraphics();
                g.drawImage(img, 0, 0, w, h, w*x, h*y, w*x+w, h*y+h, null);
                g.dispose();
                num++;
            }
        }

        return imgs;
    }


    /**
     * Creates a BufferedImage that is optimized for this system.
     * @param width
     * @param height
     * @return
     */
    private static BufferedImage createCompatibleImage(int width, int height) {
        GraphicsConfiguration gfx = GraphicsEnvironment.
                    getLocalGraphicsEnvironment().getDefaultScreenDevice().
                    getDefaultConfiguration();

        return gfx.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
    }

    private static BufferedImage toCompatibleImage(BufferedImage image)  {
            //Create a new compatible image
            BufferedImage bimg = createCompatibleImage(image.getWidth(), image.getHeight());

            //Get the graphics of the image and paint the original image onto it.
            Graphics2D g = (Graphics2D) bimg.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();

            //Return the new, compatible image.
            return bimg;
    }


    /**
     * Collision detection between the current sprite and another sprite
     */
    public boolean collidesWith(ImageSprite otherSprite, boolean pixelPerfect) {
        boolean isColliding=false;

        Rectangle r1 = getBounds(this);
        Rectangle r2 = getBounds(otherSprite);
        
        if(r1.intersects(r2)) {
            if(pixelPerfect) {
                isColliding = pixelPerfectCollision(otherSprite, r1, r2);
            }else {
                isColliding = true;
            }
        }

        return isColliding;
    }


    /**
     *  pixelPerfectCollision(); first determines the area where the sprites collides
     *  AKA the collision-rectangle. It then grabs the pixels from both sprites
     *  which are inside the rectangle. It then checks every pixel from the arrays
     *  given by grabPixels();, and if 2 pixels at the same position are opaque,
     *  (alpha value over 0) it will return true. Otherwise it will return false.
     */
    private boolean pixelPerfectCollision(ImageSprite sprite, Rectangle r1, Rectangle r2) {
        int cornerTopX=-1;
        int cornerTopY=-1;

        int cornerBottomX = 1;
        int cornerBottomY = 1;
        

        /*
         * Get the X-values for the two coordinates where the sprites collide
         * Seriously, don't use the for loop, I don't know what I was thinking.
         * Solution found below.
         */
//        for(int i=0;i<r1.getWidth();i++) {
//            if(r1.getX()+i >= r2.getX() & r1.getX()+i < r2.getX()+r2.getWidth()) {
//                if(cornerTopX==-1)cornerTopX = (int) (r1.getX() + i);
//                cornerBottomX = (int) (r1.getX() + i);
//            }
//        }

        cornerTopX = (r1.x>r2.x)?r1.x:r2.x;
        cornerBottomX = ((r1.x+r1.width) < (r2.x+r2.width))?(r1.x+r1.width):(r2.x+r2.width);

        /*
         * Get the Y-values for the two coordinates where the sprites collide
         * Solution found below.
         */
//        for(int i=0;i<r1.getHeight();i++) {
//            if(r1.getY()+i >= r2.getY() & r1.getY()+i < r2.getY()+r2.getHeight()) {
//                if(cornerTopY==-1)cornerTopY = (int) (r1.getY() + i);
//                cornerBottomY = (int) (r1.getY() + i);
//            }
//        }

        cornerTopY = (r1.y>r2.y)?r1.y:r2.y;
        cornerBottomY = ((r1.y+r1.height) < (r2.y+r2.height))?(r1.y+r1.height):(r2.y+r2.height);

        //Determine the width and height of the collision rectangle
        int width=cornerBottomX-cornerTopX;
        int height=cornerBottomY-cornerTopY;

        //Create arrays to hold the pixels
        int[] pixels1 = new int[width*height];
        int[] pixels2 = new int[width*height];

        //Create the pixelgrabber and fill the arrays
        PixelGrabber pg1 = new PixelGrabber(getImage(), cornerTopX-getRealX(), cornerTopY-getRealY(), width, height, pixels1, 0, width);
        PixelGrabber pg2 = new PixelGrabber(sprite.getImage(), cornerTopX-sprite.getRealX(), cornerTopY-sprite.getRealY(), width, height, pixels2, 0, width);

        //Grab the pixels
        try {
            pg1.grabPixels();
            pg2.grabPixels();
        } catch (InterruptedException ex) {
            Logger.getLogger(ImageSprite.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Check if pixels at the same spot from both arrays are not transparent.
        for(int i=0;i<pixels1.length;i++) {
            int a = (pixels1[i] >>> 24) & 0xff;
            int a2 = (pixels2[i] >>> 24) & 0xff;

            /* Awesome, we found two pixels in the same spot that aren't
             * completely transparent! Thus the sprites are colliding!
             */
            if(a > 0 && a2 > 0) return true;
            
        }
        
        return false;
    }


    /**
     * Invokes transparency on the selected color
     * @param color
     */
    public void invokeTransparency(Color color) {
        spriteImg = makeTransparent(spriteImg, color);

        if(this.cols > 0 & this.rows > 0)this.splitSprite(this.cols, this.rows);

    }


    public void invokeTransparency(Color color, int newAlphaValue) {
        spriteImg = makeTransparent(spriteImg, color, newAlphaValue);
        if(this.cols > 0 & this.rows > 0)this.splitSprite(this.cols, this.rows);
    }

    public static BufferedImage makeTransparent(BufferedImage img, final Color color) {
        ImageFilter filter = new RGBImageFilter() {

            public int markerRGB = color.getRGB() | 0xFF000000;
            
            @Override
            public final int filterRGB(int x, int y, int rgb) {
                if((rgb | 0xFF000000)==markerRGB)return 0x00FFFFFF & rgb;
                else return rgb;
            }
        };

        ImageProducer ip = new FilteredImageSource(img.getSource(), filter);

        Image temp = Toolkit.getDefaultToolkit().createImage(ip);


        BufferedImage bufImg = createCompatibleImage(img.getWidth(), img.getHeight());
        Graphics2D g = bufImg.createGraphics();
        g.drawImage(temp, 0, 0, null);
        g.dispose();

        return bufImg;
    }

    public static BufferedImage makeTransparent(BufferedImage img, final Color color, final int newColor) {
        ImageFilter filter = new RGBImageFilter() {

            public int markerRGB = color.getRGB() | 0xFF000000;

            @Override
            public final int filterRGB(int x, int y, int rgb) {
                if((rgb | 0xFF000000)==markerRGB) {
                    return newColor & rgb;
                }else {
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(img.getSource(), filter);

        Image temp = Toolkit.getDefaultToolkit().createImage(ip);


        BufferedImage bufImg = createCompatibleImage(img.getWidth(), img.getHeight());
        Graphics2D g = bufImg.createGraphics();
        g.drawImage(temp, 0, 0, null);
        g.dispose();

        return bufImg;
    }

    /**
     * Returns the width of the current sprite
     */
    public int getWidth() {
        return this.getImage().getWidth();
    }

    /**
     * Returns the height of the sprite
     */
    public int getHeight() {
        return this.getImage().getHeight();
    }

    /**
     * Returns the X-position of the sprite
     * getRealX() returns the X-Position of the Sprite's upper-left corner
     */
    public int getX() {
        return x;
    }

    public int getRefX() {
        return refX;
    }

    public int getRealX() {
        return x-refX;
    }

    /**
     * Returns the Y-position of the sprite
     * getRealY() returns the Y-position of the Sprite's upper-left corner
     */
    public int getY() {
        return y;
    }

    public int getRefY() {
        return refY;
    }

     public int getRealY() {
         return y-refY;
     }

    /**
     * Returns the boundaries for the sprite, used for collision detection
     */
    public static Rectangle getBounds(ImageSprite sprite) {
        return new Rectangle(sprite.getRealX(), sprite.getRealY(), sprite.getWidth(), sprite.getHeight());
    }


    /** 
     * Returns the image this sprite is using (if it was split, it will return
     * the current frame. Else it will return the whole image.)
     */
     public BufferedImage getImage() {
        if(animImg!=null && currentFrame<frameSequence.length)return animImg[frameSequence[currentFrame]];
        else return spriteImg;
    }


    /**
     * Returns the whole image, no matter if it has been split or not.
     * @return
     */
    public BufferedImage getOrigImage() {
        return spriteImg;
    }


    /**
     * Flips the sprite (horizontal/vertical)
     */
    public void flipHorizontal() {
        int w = this.getOrigImage().getWidth();
        int h = this.getOrigImage().getHeight();

        BufferedImage bimg = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = bimg.createGraphics();

        g.drawImage(this.getOrigImage(), 0, 0, w, h, w, 0, 0, h, null);
        g.dispose();
        
        this.spriteImg = toCompatibleImage(bimg);

        if(this.rows > 0 & this.cols > 0)animImg = splitImage(spriteImg, cols, rows);
    }

    public void flipVertical() {
        int w = this.getOrigImage().getWidth();
        int h = this.getOrigImage().getHeight();

        BufferedImage bimg = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = bimg.createGraphics();

        g.drawImage(this.getOrigImage(), 0, 0, w, h, 0, h, w, 0, null);
        g.dispose();

        this.spriteImg = toCompatibleImage(bimg);

        if(this.rows > 0 & this.cols > 0)animImg = splitImage(spriteImg, cols, rows);
    }


    /**
     * Call one of these methods after the sprite has been de-serialized.
     * @param img
     */
    public void reloadSprite(BufferedImage img) {
        this.spriteImg = img;
        if(this.rows > 0 & this.cols > 0)animImg = splitImage(spriteImg, cols, rows);
    }

    /**
     * Getting the cols
     * @return
     */
    public int getCols() {
        return this.cols;
    }

    public int getRows() {
        return this.rows;
    }

    public static BufferedImage duplicateAndReverse(BufferedImage bimg) {
        BufferedImage temp = createCompatibleImage(bimg.getWidth()*2, bimg.getHeight());

        int w = bimg.getWidth();
        int h = bimg.getHeight();

        Graphics2D g = temp.createGraphics();

        g.drawImage(bimg, 0, 0, null);

        g.drawImage(bimg, w, 0, w*2, h, w, 0, 0, h, null);
        g.dispose();

        return temp;
    }
    
    private transient BufferedImage spriteImg;
    private transient BufferedImage[] animImg;

    private int x;
    private int y;

    private int refX;
    private int refY;

    private int frameSequence[] = {0};
    private int currentFrame = 0;

    private int sleepTime;
    private int currentSleepFrame;
    private boolean runAnim = false;

    private int cols = 0;
    private int rows = 0;

    private String frameSequenceName = "ORIG";

    //For serialization
    private static final long serialVersionUID = 1L;

}
