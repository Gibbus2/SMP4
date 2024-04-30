package ui;

import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

public class ImageLoader {

    private Texture[] textures;
    public ImageLoader() {}

    /*
    public void loadTextures() {
            URL url = ImageLoader.class.getResource("/assets");
            File folder = new File(url.getPath());
            File[] listOfFiles = folder.listFiles();

            if (listOfFiles != null) {
                int pngCount = 0;
                for (File file : listOfFiles) {
                    if (file.isFile() && file.getName().endsWith(".png")) {
                        pngCount++;
                    }
                }

                int index = 0;
                for (File file : listOfFiles) {
                    if (file.isFile() && file.getName().endsWith(".png")) {
                        InputStream is = ImageLoader.class.getResourceAsStream("/assets/" + file.getName());
                        Image img = new Image(is);
                        System.out.println("Processing PNG file: " + file.getName());
                        FXGL.getChildren().add(texture);
                        index++;
                    }
                }
            } else {
                System.out.println("No files found in /assets directory.");
            }




    }*/


    public Texture[] getTextures() {
        URL url = ImageLoader.class.getResource("/assets");
        File folder = new File(url.getPath());
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            int pngCount = 0;
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".png")) {
                    pngCount++;
                }
            }

            // Initialize the textures array with the PNG count
            textures = new Texture[pngCount];

            int index = 0;
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".png")) {
                    InputStream is = ImageLoader.class.getResourceAsStream("/assets/" + file.getName());
                    Image img = new Image(is);
                    textures[index] = new Texture(img);
                    /*System.out.println("Processing PNG file: " + file.getName());*/
                    index++;
                }
            }
        } else {
            System.out.println("No files found in /assets directory.");
        }


        return textures;
    }

/*    public static void main(String[] args) {
        ImageLoader imageLoader = new ImageLoader();
        System.out.println(Arrays.toString(imageLoader.getTextures()));
    }*/
}