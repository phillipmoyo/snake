import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;


public class MyAgent extends za.ac.wits.snake.MyAgent {
	
	public static int[][] field;
    public static void main(String args[]) throws IOException {
        MyAgent agent = new MyAgent();
        MyAgent.start(agent, args);
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String initString = br.readLine();
            String[] temp = initString.split(" ");
            int nSnakes = Integer.parseInt(temp[0]);

            int m =0;
            while (true) {
            	field = new int[50][50];
            	
                String line = br.readLine();
                if (line.contains("Game Over")) {
                    break;
                }

                String apple1 = line;            
                Point Apple = new Point();
                Apple.SplitArrayInt(apple1, " ");
                
                int nObstacles = 3;
                for (int obstacle = 0; obstacle < nObstacles; obstacle++) {
                    String obs = br.readLine();
                    draw(obs, field,1);
                }
         
                int mySnakeNum = Integer.parseInt(br.readLine());          
                String[] mySnake = null;
                String[] otherSnakes = new String[4];

                for (int i = 0; i < nSnakes; i++) {
                    String snakeLine = br.readLine();
                    if (i == mySnakeNum) 
                    {
                    	
                    	mySnake = snakeLine.split(" ");
                    	otherSnakes[i] = snakeLine;
                    	draw(SnakeBody(snakeLine),field,1);
                    	
                    }

                    otherSnakes[i] = snakeLine;
                    draw(SnakeBody(snakeLine),field,1);
                }
                
                //finished reading, calculate move:
                Point point = new Point();
                point.SplitArrayInt(mySnake[3], ",");
                
            	AStar me;
            	List<Node> path;
                me = new AStar(point.X,point.Y,false,field);
                path = me.findPathTo(Apple.X, Apple.Y);
                if(path != null)
                {
                	
                	AstarMove(point.X,point.Y,path.get(1).x,path.get(1).y);
                	
                }
                else
                { 
                	//System.out.println(5);//need to do better
                	loop(SnakeBody(otherSnakes[0]));
                }
                if(m == 0)
                {
                	printBoard(field);
                	m++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void caps(String s)
    {
    	Point p = new Point();
    	
        p.SplitArrayInt(s, ",");
    	System.err.println(inBound(p.X-1,p.Y));
    	if(inBound(p.X-1,p.Y))
    	{
    		field[p.X-1][p.Y]=1;
    	}
    	System.err.println(inBound(p.X+1,p.Y));
    	if(inBound(p.X+1,p.Y))
    	{
    		field[p.X+1][p.Y]=1;
    	}
    	System.err.println(inBound(p.X,p.Y-1));
    	if(inBound(p.X,p.Y-1))
    	{
    		field[p.X][p.Y-1]=1;
    	}
    	System.err.println(inBound(p.X,p.Y+1));
    	if(inBound(p.X,p.Y+1))
    	{
    		field[p.X][p.Y+1]=1;
    	}
    }
    String Head(String s)
    {
    	String[] mySnake = s.split(" ");
        return mySnake[3];
    }
    void loop(String snake)
    {
    	
    	String[] arr = snake.split(" ");
    	String dir = CurrentMovementDirection(arr[0],arr[1]);
    	int num = generatorRandom(2,3);
    	int n = generatorRandom(0,1);
    	
    	Point point =new Point();
    	point.SplitArrayInt(arr[0], ",");
    	
    	
    	if(dir.equals("upwards"))
    	{
    		if(inBound(point.X,point.Y-1))
    		{
    			if(field[point.X][point.Y-1] == 0)
    				System.out.println(0);
			
    			if(field[point.X][point.Y-1] == 1)
					if(num == 2 && inBound(point.X-1,point.Y))
					{
						if(field[point.X-1][point.Y] == 0)
							System.out.println(num);
					}
					else if(num == 3 && inBound(point.X+1,point.Y))
					{
						if(field[point.X+1][point.Y] == 0)
							System.out.println(num);
					}
			}
    		else//work fine
    		{
				if(num == 2 && inBound(point.X-1,point.Y))
				{
					if(field[point.X-1][point.Y] == 0)
						System.out.println(num);
				}
				else if(num == 3 && inBound(point.X+1,point.Y))
				{
					if(field[point.X+1][point.Y] == 0)
						System.out.println(num);
				}
    		}
    	}
    	
    	
    	if(dir.equals("downwards"))
    	{
    		if(inBound(point.X,point.Y+1))
    		{
    			if(field[point.X][point.Y+1] == 0)
    				System.out.println(1);
    		
    			if(field[point.X][point.Y+1] == 1)
					if(num == 2 && inBound(point.X-1,point.Y) )
					{
						if(field[point.X-1][point.Y] == 0)
							System.out.println(num);
					}
					else if(num == 3 && inBound(point.X+1,point.Y) )
					{
						if(field[point.X+1][point.Y] == 0)
							System.out.println(num);
					}
    		}
    		else//work fine
    		{
				if(num == 2 && inBound(point.X-1,point.Y))
				{
					if(field[point.X-1][point.Y] == 0)
						System.out.println(num); 
				}
				else if(num == 3 && inBound(point.X+1,point.Y))
				{
					if(field[point.X+1][point.Y] == 0)
						System.out.println(num);
				}
    		}
    	}
    	
    	if(dir.equals("left"))
    	{
    		if(n == 0 && inBound(point.X,point.Y-1))
    		{
    			if(field[point.X][point.Y-1] == 0)
    				System.out.println(n);
    		}
    		else if(n == 1 && inBound(point.X,point.Y+1))
    		{
    			if(field[point.X][point.Y+1] == 0)
    				System.out.println(n);
    		}
    		else
    		{
    			System.out.println(5);
    		}
    	}
    	if(dir.equals("right"))
    	{
    		if(n == 0 && inBound(point.X,point.Y-1))
    		{
    			if(field[point.X][point.Y-1] == 0)
    				System.out.println(0);
    		}
    		else if(n == 1 && inBound(point.X,point.Y+1))
    		{
    			if(field[point.X][point.Y+1] == 0)
    				System.out.println(1);
    		}
    		else
    		{
    			System.out.println(5);
    		}
    	}
    }
    int generatorRandom(int min, int max)
    {
    	double num = Math.random() * (max - min + 1) + min;
        return (int)num;
    }
    boolean inBound(int Xindex,int Yindex)
    {
    	if(Xindex >= 0 && Yindex >= 0 && Yindex < 50 && Xindex < 50)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    boolean inRange(int myDistance, int opponentDistance)
    {
    	return opponentDistance > myDistance;
    }
    void AstarMove(int xHead, int yHead, int xNext, int yNext)
    {
    	
    	if(xHead == xNext)//we moving up or down
    	{
    		if(yHead > yNext)//move upwards
    		{	
    			System.out.println(0);//up
    		}
    		else//move downwards
    		{
    			System.out.println(1);//down
    		}
    	}
    	else if(yHead == yNext)//moving left or right
    	{
    		if(xHead > xNext)//move leftwards 
    		{
    			System.out.println(2);//left
    			
    		}
    		else//move rightwards
    		{
    			System.out.println(3); //right
    		}
    	}
    	else
    	{
    		System.out.println(5);
    	}
    	
    }
    void draw(String snakeLine, int[][] board,int type )
	{
		String[] arr = snakeLine.split(" ");
		for(int i = 0; i < arr.length-1; i++)
		{
			Block(board,arr[i], arr[i+1], type);
		}
	}
    String SnakeBody(String str)
    {
    	String[] arr = str.split(" ");
    	String newStr = "";
    	for(int i = 3; i < arr.length;i++)
    	{
    		newStr += arr[i]+ " ";
    	}
    	
    	return newStr.trim();
    }
    void Block(int[][] board, String str1, String str2, int num)
	{
		String[] x = str1.split(",");
		String[] y = str2.split(",");
		if(x[1].equals(y[1]))//upwards and downwards
		{
			if(Integer.parseInt(x[0]) < Integer.parseInt(y[0]))//downwards
			{
				for(int i = Integer.parseInt(x[0]); i <= Integer.parseInt(y[0]);i++)
				{
					board[Integer.parseInt(x[1])][i] = num;
				}
			}
			else//upwards
			{
				for(int i = Integer.parseInt(y[0]); i <= Integer.parseInt(x[0]); i++)
				{
					board[Integer.parseInt(x[1])][i] = num;
				}
			}
			
		}
		else if (x[0].equals(y[0]))//right and left
		{
			if(Integer.parseInt(x[1]) < Integer.parseInt(y[1]))//right
			{
				for(int i = Integer.parseInt(x[1]); i <= Integer.parseInt(y[1]);i++)
				{
					board[i][Integer.parseInt(x[0])] = num;
				}
			}
			else//left
			{
				for(int i = Integer.parseInt(y[1]); i <= Integer.parseInt(x[1]); i++)
				{
					board[i][Integer.parseInt(x[0])] = num;
				}
			}
			
		}
		
	}
	void printBoard(int[][] Board)
	{
		for(int j = 0; j < Board.length; j++) 
		{
			for(int i = 0; i< Board.length; i++)
			{
				if(i != Board.length - 1)
				{
					System.err.print(Board[j][i] + " ");
				}
				else
				{
					System.err.println(Board[j][i]);
				}
			}
		}
	}   
    int move(Point head, Point apple, String direction)
    {
    	if(head.X == apple.X)
    	{
    		if(direction.equals("upwards"))
    		{
    			if(apple.Y > head.Y)//note we swaped(1) with (2)
    			{
    				return 3;//decisions need to be made/wall
    			}
    			else
    			{
    				return 5;
    			}
    		}
    		else if(direction.equals("downwards"))//(2)
    		{
    			if(head.Y > apple.Y)
    			{
    				return 3;//decisions need to be made/wall
    			}
    			else
    			{
    				return 5;
    			}
    		}
    		else if(direction.equals("left") || direction.equals("right"))
    		{
    			if(head.Y > apple.Y)
    			{
    				return 0;
    			}
    			else
    			{
    				return 1;
    			}
    		}
    	}
    	else if(head.Y == apple.Y )
    	{
    		if(direction.equals("upwards") || direction.equals("downwards"))
    		{
    			if(head.X > apple.X)
    			{
    				return 2;
    			}
    			else
    			{
    				return 3;
    			}
    		}
    		else if(direction.equals("left"))
    		{
    			if(head.X > apple.X)
    			{
    				return 5;
    			}
    			else
    			{
    				return 0;//need working/wall
    			}
    		}
    		else if(direction.equals("right"))
    		{
    			if(apple.X > head.X)
    			{
    				return 5;
    			}
    			else
    			{
    				return 0;//need working/wall
    			}
    		}
    		else
    		{
    			return 5;//not that important
    		}
    	}
    	else if(head.X != apple.X && head.Y != apple.Y)
    	{
    		if(head.X > apple.X && head.Y > apple.Y)
    		{
    			if(direction.equals("right"))
    			{
    				return 0;
    			}
    			else if(direction.equals("downwards"))
    			{
    				return 2;
    			}
    			else
    			{
    				return 5;
    			}
    		}
    		else if(apple.X > head.X && apple.Y > head.Y)
    		{
    			if(direction.equals("left"))
    			{
    				return 1;
    			}
    			else if(direction.equals("upwards"))
    			{
    				return 3;
    			}
    			else
    			{
    				return 5;
    			}
    		}
    		else if(head.X > apple.X && apple.Y > head.Y)
    		{
    			if(direction.equals("right"))
    			{
    				return 1;
    			}
    			else if(direction.equals("upwards"))
    			{
    				return 2;
    			}
    			else
    			{
    				return 5;
    			}
    		}
    		else if(apple.X > head.X && head.Y > apple.Y)
    		{
    			if(direction.equals("left"))
    			{
    				return 0;
    			}
    			else if(direction.equals("downwards"))
    			{
    				return 3;
    			}
    			else
    			{
    				return 5;
    			}
    		}
    	}
    	return 5;
    } 
    String CurrentMovementDirection(String XYhead ,String XYnext)
    {
    	
    	Point head = new Point();
    	Point next = new Point();
    	head.SplitArrayInt(XYhead, ",");
    	next.SplitArrayInt(XYnext, ",");
    	
    	
    	if(head.X == next.X )
    	{
    		if(head.Y < next.Y)
    		{
    			return "upwards";
    			//we moving upwards
    		}
    		else if(head.Y > next.Y)
    		{
    			return "downwards";
    			//we moving downwards
    		}
    		
    	}
    	if(head.Y == next.Y)
    	{
    		if(head.X < next.X)
    		{
    			return "left";
    			//we moving left
    		}
    		else if(head.X > next.X)
    		{
    			return "right";
    			//we moving right
    		}
    	}
		return null;
    }

}