import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStar {
	public List<Node> openSet;
	public List<Node> closeSet;
	public List<Node> path;
	public int xStart, yStart, xEnd, yEnd;
	public boolean diag;
	public int[][] field;
	public Node now;
	
	public AStar(int xStart, int yStart, boolean diag, int[][] field) 
	{
		this.xStart = xStart;
		this.yStart = yStart;
		this.diag = diag;
		this.field = field;
		
		this.openSet = new ArrayList<>();
		this.closeSet = new ArrayList<>();
		this.path = new ArrayList<>();
		
		this.now = new Node(null, xStart, yStart, 0, 0);
	}
	
	 /*
	    ** Finds path to xend/yend or returns null
	    **
	    ** @param (int) xend coordinates of the target position
	    ** @param (int) yend
	    ** @return (List<Node> | null) the path
	    */
	
    public List<Node> findPathTo(int xend, int yend) {
        this.xEnd = xend;
        this.yEnd = yend;
        this.closeSet.add(this.now);
        addNeigborsToOpenList();
        while (this.now.x != this.xEnd || this.now.y != this.yEnd) {
            if (this.openSet.isEmpty()) { // Nothing to examine
                return null;
            }
            this.now = this.openSet.get(0); // get first node (lowest f score)
            this.openSet.remove(0); // remove it
            this.closeSet.add(this.now); // and add to the closed
            addNeigborsToOpenList();
        }
        this.path.add(0, this.now);
        while (this.now.x != this.xStart || this.now.y != this.yStart) {
            this.now = this.now.parent;
            this.path.add(0, this.now);
        }
        return this.path;
    }
    
    /*
     ** Looks in a given List<> for a node
     **
     ** @return (bool) NeightborInListFound
     */
    
    private static boolean findNeighborInList(List<Node> array, Node node) {
        return array.stream().anyMatch((n) -> (n.x == node.x && n.y == node.y));
    }
	
    /*
     ** Calulate distance between this.now and xend/yend
     **
     ** @return (int) distance
     */

    private double distance(int dx, int dy) {
        if (this.diag) { // if diagonal movement is alloweed
            return Math.hypot(this.now.x + dx - this.xEnd, this.now.y + dy - this.yEnd); // return hypothenuse
        } else {
            return Math.abs(this.now.x + dx - this.xEnd) + Math.abs(this.now.y + dy - this.yEnd); // else return "Manhattan distance"
        }
    }
    
    private void addNeigborsToOpenList() {
        Node node;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (!this.diag && x != 0 && y != 0) {
                    continue; // skip if diagonal movement is not allowed
                }
                node = new Node(this.now, this.now.x + x, this.now.y + y, this.now.g, this.distance(x, y));
                if ((x != 0 || y != 0) // not this.now
                    && this.now.x + x >= 0 && this.now.x + x < this.field[0].length // check maze boundaries
                    && this.now.y + y >= 0 && this.now.y + y < this.field.length
                    && this.field[this.now.y + y][this.now.x + x] != 1 // check if square is walkable
                    && !findNeighborInList(this.openSet, node) && !findNeighborInList(this.closeSet, node)) { // if not already done
                        node.g = node.parent.g + 1.; // Horizontal/vertical cost = 1.0
                        node.g += field[this.now.y + y][this.now.x + x]; // add movement cost for this square
 
                   
                        this.openSet.add(node);
                }
            }
        }
        Collections.sort(this.openSet);
    }
}
