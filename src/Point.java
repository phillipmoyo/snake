

public class Point {
	public int X;
	public int Y;
	
	Point(){}
	
    public void SplitArrayInt(String coord, String Redix)
    {
    	String[] arr = coord.split(Redix);
		this.X = Integer.parseInt(arr[0]);
		this.Y = Integer.parseInt(arr[1]);
    	
    }
}
