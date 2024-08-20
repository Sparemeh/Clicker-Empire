package ui;

import model.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

// Represents the view finder panel
public class ViewFinder extends JPanel {
    private static final int WIDTH = 960;
    private static final int HEIGHT = 540;

    ImageIcon backgroundImage;
    Image house;
    Image manor;
    JLabel background;
    private ArrayList<ImageInfo> buildings;

    // Constructs a ViewFinder given player
    public ViewFinder(Player player) throws IOException {
        buildings = new ArrayList<>();
        setBackground(Color.green);
        backgroundImage = new ImageIcon("src/images/background.jpg");
        Image backgroundScaled = backgroundImage.getImage().getScaledInstance(WIDTH,HEIGHT, Image.SCALE_DEFAULT);
        backgroundImage.setImage(backgroundScaled);
        house = ImageIO.read(new File("src/images/house.png"));
        Image houseScaled = house.getScaledInstance(150,150,Image.SCALE_DEFAULT);
        house = houseScaled;
        manor = ImageIO.read(new File("src/images/EstateHouse.png"));
        background = new JLabel(backgroundImage);
        setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.gray));
    }

    // Add a new image to the list with random position
    public void addImage(String building) {
        Image image = null;

        if (building.equals("house")) {
            image = house;
        } else if (building.equals("manor")) {
            image = manor;
        }

        Random rand = new Random();
        int x = rand.nextInt(getWidth() - 75);
        int y = rand.nextInt(getHeight() - 75);

        buildings.add(new ImageInfo(image, x, y));
        repaint();
    }

    // removes the first index of buildings
    public void removeImage() {
        buildings.remove(0);
        repaint();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (ImageInfo b : buildings) {
            g.drawImage(b.getImage(), b.getXloc(), b.getYloc(), this);
        }
    }

    // Helper class to hold image and its position
    class ImageInfo {
        private Image image;
        private int xloc;
        private int yloc;

        public ImageInfo(Image image, int x, int y) {
            this.image = image;
            this.xloc = x;
            this.yloc = y;
        }

        public Image getImage() {
            return image;
        }

        public int getXloc() {
            return xloc;
        }

        public int getYloc() {
            return yloc;
        }
    }
}
