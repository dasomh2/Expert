import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

	static int N, res;
	static int[] map[], D = new int[2];
	static Queue<Point> q;
	static ArrayList<Point> wave;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());

		for(int a=1; a<=T; a++) {
			N = Integer.parseInt(br.readLine());
			q = new LinkedList<>();
			map = new int[N][N];
			wave = new ArrayList<>();

			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					if(map[i][j] == 2) {
						wave.add(new Point(i,j));
					}
					else if(map[i][j] == 1) {
						map[i][j] = -1;
					}
				}
			}
			st = new StringTokenizer(br.readLine());
			int sx = Integer.parseInt(st.nextToken());
			int sy = Integer.parseInt(st.nextToken());
			q.add(new Point(sx, sy));

			st = new StringTokenizer(br.readLine());
			D[0] = Integer.parseInt(st.nextToken());
			D[1] = Integer.parseInt(st.nextToken());

			BFS();
			System.out.println("#"+a+" "+res);
			res=0;
		}
	}
	private static void BFS() {
		int[] dx = {-1,0,1,0}, dy = {0,1,0,-1};
		boolean[] visit[] = new boolean[N][N];
		visit[q.peek().x][q.peek().y] = true;
		int cnt=0;
		//먼저 움직이고 파도 숫자 깎아
		while(!q.isEmpty()) {
			int size = q.size();

			for(int a=0; a<size; a++) {

				Point p = q.poll();
				if(p.x == D[0] && p.y == D[1]) {
					res = cnt;
					return;
				}

				for(int i=0; i<4; i++) {
					int nx = p.x + dx[i];
					int ny = p.y + dy[i];

					if(isRange(nx, ny)) {
						if(map[nx][ny] > 0) {//소용돌이 있는데?
							q.add(new Point(p.x, p.y));
						}
						if(!visit[nx][ny] && map[nx][ny] == 0) {//그냥 가기 
							visit[nx][ny] = true;
							q.add(new Point(nx, ny));
						}
					}
				}	
			}
			for(int i=0; i<wave.size(); i++) {
				Point w = wave.get(i);

				if(map[w.x][w.y] == 0) {
					map[w.x][w.y] = 3;
				}
				map[w.x][w.y]--;
			}
			cnt++;
		}
		res = -1;
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
		this.y = y;
	}
}
