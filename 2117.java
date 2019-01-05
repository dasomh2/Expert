import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {

	static int N, M, MAX;
	static int[] map[];
	static int[] dx = {-1,0,1,0}, dy = {0,1,0,-1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());

		for(int a=1; a<=T; a++) {
			st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			map = new int[N][N];
			MAX = 1;

			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			Queue<Point> q = new LinkedList<>();
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {

					q.add(new Point(i,j));

					if(map[i][j] == 1) {//현재 점이 집이면
						BFS(2, 1, q);
					}
					else {//현재 점이 집이 아니면
						BFS(2, 0, q);
					}
					q.clear();//큐 초기화 
				}
			}
			System.out.println("#"+a+" "+MAX);
		}
	}
	private static void BFS(int K, int cnt, Queue<Point> q) {
		boolean[][] visit = new boolean[N][N];

		while(!q.isEmpty()) {

			if(K > N+1) {
				return;
			}

			int qsize = q.size();
			for(int a=0; a<qsize; a++) {
				Point p = q.poll();
				visit[p.x][p.y] = true;

				for(int i=0; i<4; i++) {
					int nx = p.x + dx[i];
					int ny = p.y + dy[i];

					if(isRange(nx, ny) && !visit[nx][ny]) {
						visit[nx][ny] = true;
						if(map[nx][ny] == 1) {
							cnt++;//집이면 카운팅 
						}
						q.add(new Point(nx, ny));
					}
				}
			}
			int value = (K*K + (K-1)*(K-1));//이익 
			if((cnt*M) - value >= 0) {
				MAX = Math.max(MAX, cnt);//서비스가능한 최대 집의 수 
			}
			K++;
		}
		

		//DFS(K+1, cnt, q);
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
