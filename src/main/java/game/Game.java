package game;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class Game extends BorderPane
{
    private boolean darkMode = false;
    private Board activeGame;
    private int defaultBoardSize = 1000;

    public Game()
    {
        this.setTop(generateMenuBar());
        activeGame = new TitleGame(Screen.getWidth(), Screen.getHeight(),defaultBoardSize, darkMode);
        this.setCenter(activeGame);
    }

    private MenuBar generateMenuBar()
    {
        MenuBar menuBar = new MenuBar();
        menuBar.setBackground(getThemedBackground());

        // File Menu
        Menu file = new Menu("File");
        menuBar.getMenus().add(file);

        MenuItem blankGame = new MenuItem("Blank Game");
        blankGame.setOnAction(e -> blankGame());

        MenuItem randomGame = new MenuItem("Random Game");
        randomGame.setOnAction(e -> randomGame());

        MenuItem loadGame = new MenuItem("Load");

        MenuItem saveGame = new MenuItem("Save");

        MenuItem endGame = new MenuItem("End");
        endGame.setOnAction(e -> endGame());

        MenuItem exitGame = new MenuItem("Exit");
        exitGame.setOnAction(e -> System.exit(0));

        file.getItems().addAll(blankGame, randomGame, loadGame, saveGame, endGame, exitGame);

        // Controls menu
        Menu control = new Menu("Controls");
        menuBar.getMenus().add(control);

        MenuItem play = new MenuItem("Play (Space Bar)");
        play.setOnAction(e -> play());

        MenuItem pause = new MenuItem("Pause (Space Bar)");
        pause.setOnAction(e -> pause());

        MenuItem rotate = new MenuItem("Rotate (r)");
        rotate.setOnAction(e -> {if(activeGame != null) activeGame.rotateClockwise();});

        MenuItem flipH = new MenuItem("Flip Horizontal (h)");
        flipH.setOnAction(e -> {if(activeGame != null) activeGame.flipHorizontal();});

        MenuItem flipV = new MenuItem("Flip Vertical (v)");
        flipV.setOnAction(e -> {if(activeGame != null) activeGame.flipVertical();});

        control.getItems().addAll(play, pause, rotate, flipH, flipV);

        // View menu
        Menu view = new Menu("View");
        menuBar.getMenus().add(view);

        MenuItem zoomIn = new MenuItem("Zoom In (+)");
        zoomIn.setOnAction(e -> {if(activeGame != null) activeGame.increaseCellSize();});

        MenuItem zoomOut = new MenuItem("Zoom Out (-)");
        zoomOut.setOnAction(e -> {if(activeGame != null) activeGame.decreaseCellSize();});

        MenuItem shiftUp = new MenuItem("Shift Up (↑)");
        shiftUp.setOnAction(e -> {if(activeGame != null) activeGame.positiveShiftY();});

        MenuItem shiftDown = new MenuItem("Shift Down (↓)");
        shiftDown.setOnAction(e -> {if(activeGame != null) activeGame.negativeShiftY();});

        MenuItem shiftLeft = new MenuItem("Shift Left (←)");
        shiftLeft.setOnAction(e -> {if(activeGame != null) activeGame.negativeShiftX();});

        MenuItem shiftRight = new MenuItem("Shift Right (→)");
        shiftRight.setOnAction(e -> {if(activeGame != null) activeGame.positiveShiftX();});

        MenuItem darkMode = new MenuItem("Dark Mode");
        darkMode.setOnAction(e -> darkMode());

        MenuItem lightMode = new MenuItem("Light Mode");
        lightMode.setOnAction(e -> lightMode());

        MenuItem settings = new MenuItem("Settings");
        view.getItems().addAll(zoomIn, zoomOut, shiftUp, shiftDown, shiftLeft, shiftRight, darkMode, lightMode, settings);

        // Add menu
        Menu addMenu = new Menu("Add");
        menuBar.getMenus().add(addMenu);
        ArrayList<Template> templates = TemplateLoader.loadTemplates();
        for(Template each: templates)
        {
            MenuItem menuItem = new MenuItem(each.getName());
            menuItem.setOnAction(e -> {
                if(activeGame != null)
                    activeGame.setTemplate(each);
            });
            addMenu.getItems().add(menuItem);

        }
        return menuBar;
    }

    public void handleKey(KeyEvent e)
    {
        if(activeGame != null)
        {
            switch (e.getCode())
            {
                case EQUALS ->  activeGame.increaseCellSize();
                case MINUS ->   activeGame.decreaseCellSize();
                case UP ->      activeGame.positiveShiftY();
                case DOWN ->    activeGame.negativeShiftY();
                case LEFT ->    activeGame.positiveShiftX();
                case RIGHT ->   activeGame.negativeShiftX();
                case ESCAPE ->  activeGame.removeTemplate();
                case SPACE ->   activeGame.toggleTimer();
                case R ->       activeGame.rotateClockwise();
                case H ->       activeGame.flipHorizontal();
                case V ->       activeGame.flipVertical();
            }
        }
    }

    public void handleMouseMoved(MouseEvent e)
    {
        if(activeGame != null)
            activeGame.setTemplatePosition(e);
    }

    public void handleMousePressed(MouseEvent e)
    {
        if(activeGame != null)
            activeGame.placeTemplate();
    }

    private  void generateMainMenu()
    {
        Label mainTitle = new Label("GAME OF LIFE 2");
        mainTitle.setFont(new Font("Arial", 50));
        this.setCenter(mainTitle);

        Label commands = new Label("""
                Commands
                Zoom in:         plus key
                Zoom out:       minus key
                Move:              arrow keys
                Pause:             space
                """);
        commands.setFont(new Font("Arial", 20));
        this.setBottom(commands);

        this.setVisible(false);
        this.setTop(generateMenuBar());
        this.setBackground(getThemedBackground());
        this.setVisible(true);
    }

    private void randomGame()
    {
        activeGame = new Board(Screen.getWidth(), Screen.getHeight(),defaultBoardSize, darkMode);
        activeGame.randomise();
        this.setCenter(activeGame);
    }

    private void blankGame()
    {
        activeGame = new Board(Screen.getWidth(), Screen.getHeight(),defaultBoardSize, darkMode);
        this.setCenter(activeGame);
    }

    private void endGame()
    {
        generateMainMenu();
        activeGame = null;
    }

    private void play()
    {
        if(activeGame != null)
            activeGame.play();
    }

    private void pause()
    {
        if(activeGame != null)
            activeGame.pause();
    }

    private void lightMode()
    {
        darkMode = false;
        this.setTop(generateMenuBar());
        if(activeGame != null)
            activeGame.setLightMode();
        else
            generateMainMenu();
    }

    private  void darkMode()
    {
        darkMode = true;
        this.setTop(generateMenuBar());
        if(activeGame != null)
            activeGame.setDarkMode();
        else
            generateMainMenu();
    }

    private Background getThemedBackground()
    {
        if(darkMode)
            return new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), Insets.EMPTY));
        else
            return new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY));
    }
}
