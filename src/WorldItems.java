import java.awt.image.BufferedImage;
import java.util.HashMap;


public interface WorldItems extends WorldObject {
	public Person getOwner();
	public BufferedImage getImage(String str);
	public double getPrice();
	public void setPrice(double a);
	public void setOwner(Person p);
	
}
