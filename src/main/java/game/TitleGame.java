package game;

public class TitleGame extends Board
{
    public TitleGame(double width, double height, int boardSize, boolean darkMode)
    {
        super(width, height, boardSize, darkMode);

        Template title = TemplateLoader.loadTitleFile().get(0);
        grid = blankOut(new boolean[boardSize][boardSize]);

        double centreX = (Screen.getWidth()/2) / cellSize;
        double centreY = (Screen.getHeight()/2) / cellSize;
        int offsetX = (int) centreX - (title.getSize()/2);
        int offsetY = (int) centreY - (title.getSize()/2);

        for(int x = 0; x < title.getSize(); x++)
        {
            for(int y = 0; y < title.getSize(); y++)
            {
                grid[offsetX+x][offsetY+y] = title.getGrid(x,y);
            }
        }
        pause();
        draw();
    }
}
