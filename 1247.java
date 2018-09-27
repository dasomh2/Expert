import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {

	static int[] S, D;
	static boolean[] visit;
	static int N, MIN;
	static ArrayList<Point> customer;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for(int a=1; a<=T; a++) {
			N = Integer.parseInt(br.readLine());
			visit = new boolean[N];
			MIN = Integer.MAX_VALUE;
			S = new int[2];
			D = new int[2];
			customer = new ArrayList<>();
			
			st = new StringTokenizer(br.readLine());
			
			S[0] = Integer.parseInt(st.nextToken());
			S[1] = Integer.parseInt(st.nextToken());
			
			D[0] = Integer.parseInt(st.nextToken());
			D[1] = Integer.parseInt(st.nextToken());
			
			for(int i=0; i<N; i++) {
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				
				customer.add(new Point(x, y));
			}
			dfs(0,S[0],S[1],0);
			System.out.println("#"+a+" "+MIN);
		}
		
	}
	private static void dfs(int depth, int x, int y, int d) {
		if(depth == N) {
			int res = d + dist(x,y,D[0],D[1]);
			MIN = Math.min(MIN, res);
			return;
		}
		
        if(d >= MIN)
            return;
        
		for(int i=0; i<N; i++) {
			if(!visit[i]) {
				visit[i] = true;
				Point p = customer.get(i);
				dfs(depth+1, p.x, p.y, d+dist(x,y,p.x,p.y));
				visit[i] = false;
			}
		}
	}
	private static int dist(int x1, int y1, int x2, int y2) {
		return (Math.abs(x1-x2) + Math.abs(y1-y2));
	}
}
class Point {
	int x; int y;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
