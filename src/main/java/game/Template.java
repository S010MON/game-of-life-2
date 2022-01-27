package game;

public class Template
{
    private boolean[][] grid;
    private String name;

    public Template(String name, boolean[][] grid)
    {
        this.name = name;
        this.grid = grid;
    }

    public Template(String name)
    {
        this.name = name;
    }

    public Template flipVertical()
    {
        boolean[][] rotated = new boolean[grid.length][grid.length];
        for(int i = 0; i < rotated.length; i++)
        {
            for(int j = 0; j < rotated.length; j++)
            {
                rotated[i][j] = grid[j][i];
            }
        }
        return new Template(name, rotated);
    }

    public Template flipHorizontal()
    {
        boolean[][] rotated = new boolean[grid.length][grid.length];
        for(int i = 0; i < rotated.length; i++)
        {
            for(int j = 0; j < rotated.length; j++)
            {
                rotated[i][j] = grid[i][rotated.length -1 -j];
            }
        }
        return new Template(name, rotated);
    }

    public String getName()
    {
        return name;
    }

    public int getSize()
    {
        return grid.length;
    }

    public void setGrid(boolean[][] grid)
    {
        this.grid = grid;
    }

    public boolean getGrid(int x, int y)
    {
        return grid[y][x];
    }
}

