import java.awt.Image;
import java.io.IOException;
import java.io.File;
import javax.imageio.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;


public class Person implements WorldObject {
	public static final double MAX_NEEDS = 100;

	private int level;
	private double hunger;
	private double fun;
	private double social;
	private double energy;
	private double bladder;
	private double hygiene;
	private double comfort;
	private double space;
	private int age;
	private boolean male;
	private String name;
	private int x;
	private int y;
	private int walkX = -1;
	private int walkY = -1;
	private int steps = 0;
	private LinkedList<String> actions = new LinkedList<String>();
	HashMap<String,Image> images = new HashMap<String,Image>();
	private Direction facing;
	
	private World world;

	public Person(int a, String nam, int ix, int iy, double b, double c,double e, double f, double h, double hy,  double s,  double sp, boolean m) {
		hunger = h;
		fun = f;
		social = s;
		energy = e;
		bladder = b;
		hygiene = hy;
		comfort = c;
		space = sp;
		age = a;
		male = m;
		name = nam;
		x = ix;
		y = iy;

	}
	public Person(World w) {
		world = w;
		level = 3;
		hunger = MAX_NEEDS;
		fun = MAX_NEEDS;
		social = MAX_NEEDS;
		energy = MAX_NEEDS;
		bladder = MAX_NEEDS;
		hygiene = MAX_NEEDS;
		comfort = MAX_NEEDS;
		space = MAX_NEEDS;
		age = 25;
		male = true;
		name = "Juanjo Lopez";
		facing = Direction.DOWN;
		try {
			loadImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
		x = 50;
		y = 50;
	}

	public void loadImages() throws IOException {
		images.put("UP",ImageIO.read(new File("PUp.png")));
		images.put("DOWN",ImageIO.read(new File("PDown.png")));
		images.put("RIGHT",ImageIO.read(new File("PRight.png")));
		images.put("LEFT",ImageIO.read(new File("PLeft.png")));
	}
	// Variable setters

	public void setHunger(double i) {
		hunger = i;
	}

	public void setFun(double i) {
		fun = i;
	}

	public void setSocial(double i) {
		social = i;
	}

	public void setEnergy(double i) {
		energy = i;
	}

	public void setBladder(double i) {
		bladder = i;
	}

	public void setHygiene(double i) {
		hygiene = i;
	}

	public void setComfort(double i) {
		comfort = i;
	}

	public void setSpace(double i) {
		space = i;
	}

	public void setAge(int i) {
		age = i;
	}

	public void setMale(boolean b) {
		male = b;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setName(String n) {
		name = n;
	}
	// Variable getters

	
	public Direction isFacing(){
		return facing;
	}
	public double getHunger() {
		return hunger;
	}

	public double getFun() {
		return fun;
	}

	public double getSocial() {
		return social;
	}

	public double getEnergy() {
		return energy;
	}

	public double getBladder() {
		return bladder;
	}

	public double getHygiene() {
		return hygiene;
	}

	public double getComfort() {
		return comfort;
	}

	public double getSpace() {
		return space;
	}

	public int getAge() {
		return age;
	}

	public boolean getMale() {
		return male;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getName() {
		return name;
	}

	// checks
	public boolean inPos(int ix, int iy) {
		if ((Math.abs(ix - x) <= 10) && (Math.abs(iy - y) <= 10)) {
			return true;
		} else return false;
	}

	public void move(Direction d) {
		int amt = 3;

		switch (d) {
		case UP:
			if((y - amt) >= 10){
				if (facing != d) { facing = d; } 
				if(!world.l2Collide(x, y-amt)) {
					world.remove(this);
					world.addPerson(this, x, y-amt);
				}
			}
			break;
		case DOWN:
			if ((y + amt) <= 490){
				if (facing != d) { facing = d; } 
				if(!world.l2Collide(x, y+amt)) {
					world.remove(this);
					world.addPerson(this, x, y+amt);
					}
			}
			break;
		case RIGHT:
			if ((x + amt) <= 490){
				if (facing != d) { facing = d; } 
				if(!world.l2Collide(x + amt, y)) {
					world.remove(this);
					world.addPerson(this, x + amt,y);
					}
			}
			break;
		case LEFT:
			if ((x - amt) >= 10) {
				if (facing != d) { facing = d; } 
				if(!world.l2Collide(x - amt, y)) {
					world.remove(this);
					world.addPerson(this, x - amt, y);
					}
			}
			break;
		}
	}
	public void depreciate(int speed) {

		hunger -= 0.3 * speed;
		energy -= 0.1* speed;
		bladder -= 0.2* speed;
		hygiene -= 0.14* speed;
		social -= 0.07* speed;
		fun -= 0.2* speed;
		comfort -= 0.08* speed;

	}

	public void eat() {
		if (hunger < 100){
			hunger += 0.1;
		}
	}

	public void sleep() {
		if (energy < 100) {
			energy += 0.05;
		}
	}

	public void stats() {
		System.out.println("Hunger: " + hunger + "   Energy:  " + energy + "   Bladder: " + bladder);
		System.out.println("Fun:    " + fun +    "   Social:  " + social + "   Hygiene: " + hygiene);
		System.out.println("Space:  " + space +  "   Comfort: " + comfort);
	}

	public int level() {
		return level;
	}
	//walk!! 
	/*public void actionWalk(int ix, int iy) {
		steps = (int) (Math.sqrt(Math.pow(ix, 2) + Math.pow(iy, 2)))/5;
		for (int i= 0; i < steps; i++){
			actions.push("walk");
		}
	}
	public void walk(int ix, int iy) {

	}

	public boolean actionPending(){
		if (actions.isEmpty()) {
			return false;
		}
		return true;
	}

	public void execute() {
			Class[] c = { Integer.class, Integer.class };
			try {
				String method = actions.pop();
				Method m = this.getClass().getMethod(method,c);
				if (method.equals("walk")){
					m.invoke(x+5, y+5);
				}
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}*/
}
