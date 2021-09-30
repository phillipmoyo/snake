
public class Node implements Comparable<Object> {
	public Node parent;
	public int x, y;
	public double g, h;
	
	public Node(Node parent, int x, int y, double g, double h) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.g = g;
		this.h = h;
	}
	//now we compare by f value

	@Override
	public int compareTo(Object o) {
		Node node = (Node) o;
		return (int)((this.g + this.h) - (node.g + node.h));
	}
	
}
