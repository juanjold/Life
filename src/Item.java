import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;


public class Item implements WorldItems {
	private int x;
	private int y;
	private double price;
	private Direction facing;
	private String name;
	private Person owner;
	private int level;
	HashMap<String, BufferedImage> images = new HashMap<String,BufferedImage>();
	
	public Item(){
		x = 0;
		y = 0;
		price = 100;
		name = "Plant";
		owner = null;
		level = 2;	
		facing = Direction.UP;
		loadImages();

	}
	
	public void loadImages() {
		try {
		images.put(Direction.UP.toString(), ImageIO.read(new File("img/items/" + name + "/UP.png")));
		images.put(Direction.DOWN.toString(), ImageIO.read(new File("img/items/" + name + "/DOWN.png")));
		images.put(Direction.RIGHT.toString(), ImageIO.read(new File("img/items/" + name + "/RIGHT.png")));
		images.put(Direction.LEFT.toString(), ImageIO.read(new File("img/items/" + name + "/LEFT.png")));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	@Override
	public void setX(int a) {
		x = a;
	}

	@Override
	public void setY(int a) {
y = a;		
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public boolean inPos(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int level() {
		return level;
	}

	@Override
	public Person getOwner() {
		return owner;
	}

	@Override
	public BufferedImage getImage(String str) {
		return images.get(str);
	}

	@Override
	public double getPrice() {
		return price;
	}

	@Override
	public void setPrice(double a) {
		price = a;
	}

	@Override
	public void setOwner(Person p ) {
		owner = p;
		
	}
	
	public Direction isFacing(){
		return facing;
	}

}
