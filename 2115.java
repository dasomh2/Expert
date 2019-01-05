import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {

	static int[] map[], dx = {1,1,-1,-1}, dy = {1,-1,-1,1};
	static int N, inX, inY, ans;
	static boolean[] dessert, visit[];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());

		for(int t=1; t<=T; t++) {
			N = Integer.parseInt(br.readLine());
			map = new int[N][N];
			visit = new boolean[N][N];
			dessert = new boolean[101];
			ans = -1;

			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			for(int i=0; i<N; i++) {
				for(int j=1; j<N; j++) {
					inX = i; inY = j;
					
					visit[i][j] = true;
					dessert[map[i][j]] = true;
					
					goCafe(i,j,0,1);
					
					visit[i][j] = false;
					dessert[map[i][j]] = false;
				}
			}
			if(ans < 4)
				ans = -1;
			System.out.println("#"+t+" "+ans);
		}
	}
	private static void goCafe(int x, int y, int d, int cnt) {//d는 방향 
		if(d == 4)
			return;

		int nx = x + dx[d];
		int ny = y + dy[d];

		if(isRange(nx, ny)) {
			int des = map[nx][ny];

			if(!visit[nx][ny] && !dessert[des]) {

				dessert[des] = true;
				visit[nx][ny] = true;

				goCafe(nx, ny, d, cnt+1);
				goCafe(nx, ny, d+1, cnt+1);

				dessert[des] = false;
				visit[nx][ny] = false;
			}
			else {
				if(nx == inX && ny == inY) {
					ans = Math.max(ans, cnt);
					return;
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
