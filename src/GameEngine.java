
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GameEngine extends JPanel implements ActionListener, MouseListener, KeyListener{
	Time clock;

	boolean isRunning = true; 
	private static final int windowHeight = 500;
	private static final int windowWidth = 500;
	JFrame parent;

	
	private World world = new World(windowHeight, windowWidth); 
	Person selectedPerson;
	Object selectedObject;
	 
	int counter = 0;

	int clickX = -1;
	int clickY = -1;

	KeyEvent key;

	private JLabel timeLabel;

	public GameEngine(JFrame sup) {
		parent = sup;
		initialize();


	}

	/** 
	 * This method will set up everything need for the game to run 
	 */ 
	void initialize() 
	{ 
		parent.setSize(windowWidth, windowHeight);
		parent.setResizable(false);
		Person p = new Person(world);
		world.addPerson(p,50,50);
		addMouseListener(this);
		addKeyListener(this);
		setFocusable(true);
		clock = new Time();
		timeLabel = new JLabel ( clock.time());
		add(timeLabel);
		setVisible(true);

	} 

	/** 
	 * This method will check for input, move things 
	 * around and check for win conditions, etc 
	 */ 
	void update() 
	{ 
		int speed = 1;
		if (counter % 100 == 0) {
			counter = 0;
			clock.pass();
			timeLabel.setText(clock.time());
			for (Person p : world.people) {
				p.depreciate(speed);
			}
		}
		counter ++;	
		if (clickX != -1 && clickY != -1) {
			if (selectedPerson == null) {
				for (int i = 3; i >= 1; i--) {
					WorldObject w = world.getAround(i,clickX,clickY,this);
					if (w != null) {
						if (w instanceof Person) {
							selectedPerson = (Person)w;
						} else selectedObject = w;
					}
				}
				clickX = -1;
				clickY = -1;
			} else if (selectedPerson != null) {
				if(!world.collide(3,clickX,clickY)) {
					world.addPerson(selectedPerson, clickX, clickY);
				clickX = -1;
				clickY = -1;
				selectedPerson = null;
				}
			}
		}
		if (selectedPerson != null) {
			if (key!= null) {
				switch (key.getKeyCode()) {
				case KeyEvent.VK_UP:
					selectedPerson.move(Direction.UP);
					break;
				case KeyEvent.VK_DOWN:
					selectedPerson.move(Direction.DOWN);
					break;
				case KeyEvent.VK_RIGHT:
					selectedPerson.move(Direction.RIGHT);
					break;
				case KeyEvent.VK_LEFT:
					selectedPerson.move(Direction.LEFT);
					break;
				case KeyEvent.VK_D:
					select(null);
					break;
				case KeyEvent.VK_E:
					selectedPerson.eat();
					break;
				case KeyEvent.VK_S:
					selectedPerson.sleep();
					break;
				case KeyEvent.VK_N:
					selectedPerson.stats();
					break;
				}
				key = null;
			}
		}
		if (key!= null) {
			switch (key.getKeyCode()) {
			case KeyEvent.VK_S:	
				//save();
				break;

			case KeyEvent.VK_L:
				//load();
				break;	
			case KeyEvent.VK_T:
				clock.time();
				break;
			}
			key = null;
		}

		repaint();
	} 

	/** 
	 * This method will draw everything 
	 */ 
	public void paintComponent(Graphics g1) 
	{ 
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D) g1;

		g.setColor(Color.WHITE); 
		g.fillRect(0, 0, windowWidth, windowHeight); 

		for (Person p : world.people) {
			g.drawImage(p.images.get(p.isFacing().toString()),p.getX(),p.getY(), parent);
		}

		if (selectedPerson != null) {
			paintNeeds(g);
		}

	}

	public void paintNeeds(Graphics2D g) {
		int i = 0; String str = "" ;
		double n = 0;

		while (i < 9) {
			int x = 100; int y = 400; int z = 20;
			switch(i) {
			case 1: 
				n = selectedPerson.getHunger();
				if(n < 0) { n = 0; }
				str = "Hunger: " + (int)n;
				break;
			case 2:
				n = selectedPerson.getEnergy();
				if(n < 0) { n = 0; }
				str = "Energy: " + (int)n;
				y += 20; 
				break;
			case 3:
				n = selectedPerson.getSpace();
				if(n < 0) { n = 0; }
				str = "Space: " + (int)n;
				y += 40; 
				break;
			case 4:
				n = selectedPerson.getHygiene();
				if(n < 0) { n = 0; }
				str = "Hygiene: " + (int)n;
				x += 150; z += 150;
				break;
			case 5:
				n = selectedPerson.getBladder();
				if(n < 0) { n = 0; }
				str = "Bladder: " + (int)n;
				x += 150; z += 150; y += 20;
				break;
				
			case 6:
				n = selectedPerson.getComfort();
				if(n < 0) { n = 0; }
				str = "Comfort: " + (int)n;
				x += 150; z += 150; y += 40;
				break;
			case 7:
				n = selectedPerson.getSocial();
				if(n < 0) { n = 0; }
				str = "Social: " + (int)n;
				x += 300; z += 300;
				break;
			case 8:
				n = selectedPerson.getFun();
				if(n < 0) { n = 0; }
				str = "Fun: " + (int)n;
				x += 300; z += 300; y += 20;
				break;
			}

			g.setColor(Color.BLACK);
			g.drawString(str, z,y+5);
			g.setStroke(new BasicStroke(5));
			g.setColor(Color.GRAY);
			g.draw(new Line2D.Double(x,y,x+50,y));
			g.setColor(new Color((float) (1-(n/100.0)),(float) (n/100.0F),0.1F));
			g.draw(new Line2D.Double(x,y,x+n*0.5,y));
			i++;
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		clickX = e.getX();
		clickY = e.getY();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	} 

	public void select(Person p) {
		String n ="";
		if (selectedPerson != null) {n = selectedPerson.getName();}
		selectedPerson = p;
		if (p != null) {
			System.out.println("Selected " + p.getName());
			p.stats();
		} else System.out.println(n + " deselected");
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		key = arg0;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		key = null;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
/*
	public void save() {
		File file = new File("save.txt");
		if (file.exists()) { file.delete(); }
		if(!file.exists()) {
			try {
				file.createNewFile();
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				//save time
				bw.write(clock.hour());bw.newLine();
				bw.write(clock.min());bw.newLine();
				bw.write(clock.day());bw.newLine();

				//save population size
				bw.write(new Integer(world.size()).toString());
				bw.newLine();
				//save people stats
				for (Person p : world){
					bw.write(new Integer(p.getAge()).toString());bw.newLine();
					bw.write(p.getName());bw.newLine();
					bw.write(new Integer(p.getX()).toString());bw.newLine();
					bw.write(new Integer(p.getY()).toString());bw.newLine();
					bw.write(new Double(p.getBladder()).toString());bw.newLine();
					bw.write(new Double(p.getComfort()).toString());bw.newLine();
					bw.write(new Double(p.getEnergy()).toString());bw.newLine();
					bw.write(new Double(p.getFun()).toString());bw.newLine();
					bw.write(new Double(p.getHunger()).toString());bw.newLine();
					bw.write(new Double(p.getHygiene()).toString());bw.newLine();
					bw.write(new Double(p.getSocial()).toString());bw.newLine();
					bw.write(new Double(p.getSpace()).toString());bw.newLine();
					bw.write(new Boolean(p.getMale()).toString());bw.newLine();
				}
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public void load() {
		File file = new File("save.txt");
		if (file.exists()){
			reset();
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String hour = br.readLine();
				String min = br.readLine();
				String day = br.readLine();
				String line = br.readLine();
				int people = Integer.parseInt(line);
				for (int i = 0 ; i < people ; i++) {
					Person p = new Person(
							Integer.parseInt(br.readLine()),
							br.readLine(),
							Integer.parseInt(br.readLine()),
							Integer.parseInt(br.readLine()),
							Double.parseDouble(br.readLine()),
							Double.parseDouble(br.readLine()),
							Double.parseDouble(br.readLine()),
							Double.parseDouble(br.readLine()),
							Double.parseDouble(br.readLine()),
							Double.parseDouble(br.readLine()),
							Double.parseDouble(br.readLine()),
							Double.parseDouble(br.readLine()),
							Boolean.parseBoolean(br.readLine()));
					world.add(p);
				}
				clock = new Time(Integer.parseInt(hour),Integer.parseInt(min),Integer.parseInt(day));

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	*/
	
	
	public void reset(){
		selectedPerson = null;
		//world.clear();
		clock = new Time();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		update();

	}

}

class Time {
	int hour;
	int minutes;
	int day;

	Time(int i, int j, int k) {
		hour = i;
		minutes = j;
		day = k;
	}

	Time() {
		hour = 0;
		minutes = 0;
		day = 0;
	}

	public void pass() {
		int v = 1;
		if (minutes < 60) {
			minutes++;
		} else {
			if (hour < 24) {
				hour++;
				minutes = 0;
			} else { 
				day++;
				hour = 0;
				minutes = 0;
			}
		}
	}

	public String time() {
		System.out.println(hour + ":" + minutes + " of day " + day);
		String s = Integer.toString(hour);
		if(minutes < 10) {
			s += ":0" + minutes;
		} else { s += ":" + minutes; }
		return s;
	}

	public String hour(){
		return "" + hour;
	}
	public String min() {
		return "" + minutes;
	}

	public String day() {
		return "" + day;
	}
}
