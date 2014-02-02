
public interface WorldObject {
	public void setX(int a);
	public void setY(int a);
	public int getX();
	public int getY();
	public boolean inPos(int x, int y);
	public int level();
	public Direction isFacing();
	
}
