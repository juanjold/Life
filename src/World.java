import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashSet;

import javax.swing.JPanel;


public class World {
	private WorldObject[][] level1;
	private WorldObject[][] level2;
	private WorldObject[][] level3;
	HashSet<Person> people = new HashSet<Person>();
	private int width;
	private int height;

	public World(int w, int h) {
		width = w;
		height = h;
		level1 = new WorldObject[w][h];
		level2 = new WorldObject[w][h];
		level3 = new WorldObject[w][h];
	}

	public WorldObject[][] getLevel(int i) {
		switch(i) {
		case 1:
			return level1;

		case 2:
			return level2;

		case 3:
			return level3;

		default:	
			return null;	
		}

	}

	public WorldObject getAround(int i, int x, int y, JPanel f){
		WorldObject[][] level = getLevel(i);
		Graphics2D g = (Graphics2D) f.getGraphics();
		for (int ix = x - 5; ix <= x + 5 && ix < width ; ix++){
			if (ix < 0) { ix = 0; }
			for (int iy = y - 5; iy <= y + 5 && iy < height; iy++) {
				if (iy < 0) { iy = 0; }
				if (level[ix][iy] != null) {
					return level[ix][iy];
				}
			}
		}
		return null;		
	}
	public boolean collide(int i, int x, int y) {
		WorldObject[][] level = getLevel(i);

		if (level[x][y] != null) {
			return true;
		} else return false;

	}

	public boolean l1Collide(int x, int y) {
		return collide(1, x, y);
	}

	public boolean l2Collide(int x, int y) {
		return collide(2, x, y);
	}

	public boolean l3Collide(int x, int y) {
		return collide(3, x, y);
	}

	public void put (int i, WorldObject o, int x, int y) {
		WorldObject[][] level = getLevel(i);
		level[x][y] = o;
	}

	public WorldObject get (int i, int x, int y) {
		WorldObject[][] level = getLevel(i);
		return level[x][y];
	}
	public boolean addPerson(Person p, int x, int y) {
		if (!collide(3,x,y)) {
			if(!people.contains(p)) { people.add(p); }
			level3[x][y] = p;
			p.setX(x);
			p.setY(y);
			return true;
		} else return false;
	}

	public void remove(WorldObject w) {
		WorldObject[][] level = getLevel(w.level());
		level[w.getX()][w.getY()] = null;
	}
}
