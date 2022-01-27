package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Board extends Canvas
{
    protected boolean[][] grid;
    protected final double randomProbability = 0.3;
    protected final int cellRounding = 10;
    protected final int shiftRate = 50;
    protected int cellSize = 10;
    protected Color cellColour = Color.WHITE;
    protected Color background = Color.BLACK;
    protected int xOffset = 0;
    protected int yOffset = 0;
    protected Timeline timer;
    protected boolean timerPlaying;

    protected Template template = null;
    protected Color templateColour = Color.RED;
    protected int templateX;
    protected int templateY;
    protected int templateXMin = 10;
    protected int templateYMin = 27;

    public Board(double width, double height, int boardSize, boolean darkMode)
    {
        super(width, height);
        grid = new boolean[boardSize][boardSize];
        grid = blankOut(grid);
        if(!darkMode)
            setLightMode();
        draw();
        double frameRate = 200;
        setupTimer(frameRate);
    }

    public void step()
    {
        grid = updateGrid(grid);
        draw();
    }

    public void draw()
    {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setFill(background);
        gc.fillRect(0,0,this.getWidth(), this.getHeight());

        gc.setFill(cellColour);
        for(int x = 0; x < grid.length; x++)
        {
           for(int y = 0; y < grid.length; y++)
            {
                if(grid[x][y])
                {
                    int xCoord = (x * cellSize) + xOffset;
                    int yCoord = (y * cellSize) + yOffset;
                    gc.fillRoundRect(xCoord, yCoord, cellSize, cellSize, cellRounding, cellRounding);
                }
            }
        }

        if(template != null)
        {
            gc.setFill(templateColour);
            for(int x = 0; x < template.getSize(); x++)
            {
                for(int y = 0; y < template.getSize(); y++)
                {
                    int xCoord = ((templateX + x) * cellSize) + xOffset;
                    int yCoord = ((templateY + y) * cellSize) + yOffset;
                    if(template.getGrid(x,y))
                        gc.fillRoundRect(xCoord, yCoord, cellSize, cellSize, cellRounding, cellRounding);
                }
            }
        }
    }

    protected boolean[][] updateGrid(boolean[][] grid)
    {
        boolean[][] gridCopy = new boolean[grid.length][grid[0].length];

        for(int x = 0; x < grid.length; x++)
        {
            for(int y = 0; y < grid[0].length; y++)
            {
                int n = 0;
                for(int i = x-1; i <= x+1; i++)
                {
                    for(int j = y-1; j <= y+1; j++)
                    {
                        if(i >= 0 && i < grid.length && j >= 0 && j < grid[0].length)
                        {
                            if((i != x || j != y) && grid[i][j])
                                n++;
                        }
                    }
                }

                if(grid[x][y])
                {
                    gridCopy[x][y] = n == 3 || n == 2;
                }

                if(!grid[x][y])
                {
                    if(n == 3)
                        gridCopy[x][y] = true;
                }

            }
        }
        return gridCopy;
    }

    public void setTemplatePosition(MouseEvent e)
    {
        double rawX = e.getSceneX() - (e.getSceneX() % cellSize);  // Remove the remainder so that x snaps to grid
        templateX = (int) rawX/cellSize;

        double rawY = e.getSceneY() - (e.getSceneY() % cellSize);  // Remove the remainder so that Y snaps to grid
        templateY = (int) rawY/cellSize;
        draw();
    }

    public void placeTemplate()
    {
        if (template != null)
        {
            System.out.println("PLACE AT - X:" + templateX + "Y:" + templateY);
            boolean[][] copy = copy(grid);
            for (int x = 0; x < template.getSize(); x++)
            {
                for (int y = 0; y < template.getSize(); y++)
                {
                    copy[templateX+x][templateY+y] = template.getGrid(x, y);
                }
            }
            template = null;
            grid = copy;
            draw();
        }
    }

    public void rotateClockwise()
    {
        if(template != null)
        {
            template = template.flipVertical();
            template = template.flipHorizontal();
        }
        draw();
    }

    public void flipVertical()
    {
        if(template != null) {
            template = template.flipVertical();
            draw();
        }

    }

    public void flipHorizontal()
    {
        if(template != null) {
            template = template.flipHorizontal();
            draw();
        }
    }

    public void removeTemplate()
    {
        template = null;
    }

    public void setTemplate(Template template)
    {
        this.template = template;
    }

    protected boolean[][] copy(boolean[][] grid)
    {
        boolean[][] copy = new boolean[grid.length][grid[0].length];
        for(int i = 0; i < copy.length; i++)
        {
            System.arraycopy(grid[i], 0, copy[i], 0, copy.length);
        }
        return copy;
    }

    protected void randomise()
    {
        for(int i = 0; i < grid.length; i++)
        {
            for(int j = 0; j < grid.length; j++)
            {
                grid[i][j] = Math.random() < randomProbability;
            }
        }
    }

    protected boolean[][] blankOut(boolean[][] grid)
    {
        for(int i = 0; i < grid.length; i++)
        {
            for(int j = 0; j < grid.length; j++)
            {
                grid[i][j] = false;
            }
        }
        return grid;
    }

    protected void setupTimer(double frameRate)
    {
        timer = new Timeline(new KeyFrame(Duration.millis(frameRate), e -> step()));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
        timerPlaying = true;
    }

    public void decreaseCellSize()
    {
        if(cellSize >= 3)
            cellSize--;
        draw();
    }

    public void increaseCellSize()
    {
        if(cellSize < 20)
            cellSize++;
        draw();
    }

    public void positiveShiftX()
    {
        xOffset += shiftRate;
        draw();
    }

    public void negativeShiftX()
    {
        xOffset -= shiftRate;
        draw();
    }

    public void positiveShiftY()
    {
        yOffset += shiftRate;
        draw();
    }

    public void negativeShiftY()
    {
        yOffset -= shiftRate;
        draw();
    }

    public void toggleTimer()
    {
        if(timerPlaying)
            pause();
        else
            play();
    }

    public void play()
    {
        timer.play();
        timerPlaying = true;
    }

    public void pause()
    {
        timer.pause();
        timerPlaying = false;
    }

    public void setDarkMode()
    {
        cellColour = Color.WHITE;
        background = Color.BLACK;
    }

    public void setLightMode()
    {
        cellColour = Color.BLACK;
        background = Color.WHITE;
    }
}
