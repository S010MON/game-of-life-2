package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class TemplateLoader
{
    public static ArrayList<Template> loadTemplates()
    {
        File file = new File(getFilePath("templates"));
        if (!file.exists()) {
            System.out.println("Files not read, default loaded");
            return generateDefault();
        }
        try {
            return readTemplatesFromFile(file);
        } catch (IOException e) {
            System.out.println("Files not read, default loaded");
            return generateDefault();
        }
    }

    public static ArrayList<Template> loadTitleFile()
    {
        File file = new File(getFilePath("title"));
        if (!file.exists()) {
            System.out.println("Files not read, default loaded");
            return generateDefault();
        }
        try {
            return readTemplatesFromFile(file);
        } catch (IOException e) {
            System.out.println("Files not read, default loaded");
            return generateDefault();
        }
    }

    private static ArrayList<Template> readTemplatesFromFile(File file) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        ArrayList<Template> templates = new ArrayList<>();

        while (!line.equals("EOF"))
        {
            if(!line.startsWith("//"))
            {
                String[] subLines = line.split(",");
                String name = subLines[1];
                int size = Integer.parseInt(subLines[3]);
                boolean[][] grid = new boolean[size][size];
                for(int i = 0; i < size; i++)
                {
                    line = reader.readLine();
                    subLines = line.split(",");
                    for(int j = 0; j < size; j++)
                    {
                        grid[i][j] = subLines[j].equals("1");
                    }
                }
                templates.add(new Template(name, grid));
            }
            line = reader.readLine();
        }
        return templates;
    }

    private static String getFilePath(String fileName)
    {
        FileSystem fileSystem = FileSystems.getDefault();
        String path = fileSystem.getPath("").toAbsolutePath().toString();
        return path.concat("/src/main/resources/" + fileName);
    }

    private static ArrayList<Template> generateDefault()
    {
        Template square = new Template("Square");
        ArrayList<Template> templates = new ArrayList<>();
        boolean[][] squareGrid = new boolean[2][2];
        for (boolean[] booleans : squareGrid)
        {
            Arrays.fill(booleans, true);
        }
        square.setGrid(squareGrid);
        templates.add(square);
        return templates;
    }
}
