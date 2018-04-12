import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static int N, ret, K, highest, the[] = new int[2];
	static boolean flag;
	static Queue<Point> q = new LinkedList<>();
	static int[] map[], dx= {-1,0,1,0}, dy = {0,1,0,-1};
	static boolean[] visit[];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());

		for(int a=1; a<=T; a++) {
			ret = 0;
			highest = 0;
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			map = new int[N][N];

			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					highest = Math.max(highest, map[i][j]);
				}
			}
			pick_sp();
			int qsize = q.size();

			for(int i=0; i<qsize; i++) {
				flag = false;
				visit = new boolean[N][N];
				dfs(q.peek().x, q.poll().y, K, 1);
			}
			System.out.println("#"+a+" "+ret);
		}
	}
	private static void dfs(int x, int y, int high, int cnt) {

		visit[x][y] = true;
		ret = Math.max(ret, cnt);
		
		for(int i=0; i<4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];

			if(isRange(nx, ny) && !visit[nx][ny]) {

				if(map[x][y] > map[nx][ny]) {
					dfs(nx, ny, high, cnt+1);
				}
				else if(!flag && map[x][y] <= map[nx][ny]){
				
					if(map[nx][ny]-K <= map[x][y]-1) {
						flag = true;
						int origin = map[nx][ny];
						map[nx][ny] = map[x][y]-1;
						dfs(nx, ny, high, cnt+1);
						map[nx][ny] = origin;
						flag = false;
					}
				}
			}
		}
		visit[x][y] = false;
	}
	private static void pick_sp() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(map[i][j] == highest) {
					q.add(new Point(i,j));
				}
			}
		}
	}
	private static boolean isRange(int x, int y) {
		if(x<0 || y<0 || x>N-1 || y>N-1)
			return false;
		return true;
	}
}
class Point {
	int x; int y;
	public Point(int x, int y) {
		this.x =x ;
		this.y=y;
	}
}
