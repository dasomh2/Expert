import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {

	static int N, M, servedH;
	static int[] map[];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());

		for(int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());

			map = new int[N][N];
			servedH = 1;

			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					findArea(i, j);
				}
			}
			System.out.println("#"+t+" "+servedH);
		}
	}
	private static void findArea(int x, int y) {
		int[] dx = {-1,0,1,0}, dy = {0,1,0,-1};
		Queue<Point> q = new LinkedList<>();
		boolean[] visit[] = new boolean[N][N];

		int cnt = 1, home = 0;
		q.offer(new Point(x, y));
		visit[x][y] = true;
		if(map[x][y] == 1)
			home++;

		for(int k=2; k<=N+1; k++) {//K 범위 
			int qsize = q.size();

			for(int a=0; a<qsize; a++) {

				Point p = q.poll();

				for(int i=0; i<4; i++) {
					int nx = p.x + dx[i];
					int ny = p.y + dy[i];

					if(isRange(nx, ny) && !visit[nx][ny]) {
						q.offer(new Point(nx, ny));
						visit[nx][ny] = true;

						if(map[nx][ny] == 1) {
							home++;
						}
					}
				}
			}
			cnt++;

			if(cnt == k) {
				if(home > 0) {
					int res = (home * M) - (k*k + (k-1)*(k-1));
					if(res >= 0) {
						servedH = Math.max(servedH,home);
					}
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
		this.x = x;
		this.y = y;
	}
}
