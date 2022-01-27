package game;

public abstract class Screen
{
    public static double getWidth()
    {
        return javafx.stage.Screen.getPrimary().getBounds().getWidth();
    }
    public static double getHeight()
    {
        return javafx.stage.Screen.getPrimary().getBounds().getHeight();
    }
}
