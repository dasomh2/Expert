import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {

	static int M, A;//M 이동시간		A BC의 개수 
	static int[] dist[], dx = {0,-1,0,1,0}, dy = {0,0,1,0,-1};
	static String[] map[] ;
	static int[] res[];
	static List<Integer> list ;
	static Queue<Point> q = new LinkedList<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st, st2;

		int T = Integer.parseInt(br.readLine());

		for(int a=1; a<=T; a++) {
			st = new StringTokenizer(br.readLine());

			M = Integer.parseInt(st.nextToken());
			A = Integer.parseInt(st.nextToken());

			dist = new int[2][M];
			map = new String[10][10];
			res = new int[2][M+1];
			list = new LinkedList<>();

			st = new StringTokenizer(br.readLine());
			st2 = new StringTokenizer(br.readLine());

			for(int i=0; i<M; i++) {
				dist[0][i] = Integer.parseInt(st.nextToken());
				dist[1][i] = Integer.parseInt(st2.nextToken());
			}

			for(int i=0; i<A; i++) {
				st = new StringTokenizer(br.readLine());
				int y = Integer.parseInt(st.nextToken())-1;
				int x = Integer.parseInt(st.nextToken())-1;
				int C = Integer.parseInt(st.nextToken());
				int P = Integer.parseInt(st.nextToken());

				list.add(P);

				q.add(new Point(x, y));
				if(map[x][y] == null)
					map[x][y] = (i)+"";
				else
					map[x][y] += i+"";
				setArea(C, 0, q, i);
				q.clear();
			}
/*			for(int i=0; i<10; i++) {
				for(int j=0; j<10; j++) {
					System.out.print(map[i][j]+"\t");
				}
				System.out.println();
			}*/
			//이동궤적 조사 
			BFS();
			System.out.println("#"+a+" "+(res[0][M]+res[1][M]));
		}
	}
	private static void BFS() {
		int ax = 0, ay = 0, bx = 9, by = 9;
		String tmpA = map[ax][ay];
		String tmpB = map[bx][by];

		int tmpIdx = 0, tmpV=0;
		
		if(tmpA != null) {
			for(int i=0; i<tmpA.length(); i++) {
				tmpIdx = tmpA.charAt(i) - 48;
				tmpV = list.get(tmpIdx);
				
				res[0][0] = Math.max(res[0][0], tmpV);
			}
		}

		if(tmpB != null) {
			tmpIdx=0; tmpV=0;
			for(int i=0; i<tmpB.length(); i++) {
				tmpIdx = tmpB.charAt(i) - 48;
				tmpV = list.get(tmpIdx);
				
				res[1][0] = Math.max(res[1][0], tmpV);
			}
		}


		for(int i=1; i<=M; i++) {
			int Anx = ax + dx[dist[0][i-1]];
			int Any = ay + dy[dist[0][i-1]];
			int Bnx = bx + dx[dist[1][i-1]];
			int Bny = by + dy[dist[1][i-1]];

			String cur_A = map[Anx][Any];
			String cur_B = map[Bnx][Bny];

			if(cur_A == null && cur_B == null) {
				res[0][i] = res[0][i-1];
				res[1][i] = res[1][i-1];
			}
			else if(cur_A == null && cur_B != null) {
				res[0][i] = res[0][i-1];

				int vb = 0;
				for(int k=0; k<cur_B.length(); k++) {
					int idx = cur_B.charAt(k) - 48;
					vb = Math.max(vb, list.get(idx));
				}

				res[1][i] = res[1][i-1] + vb;
			}
			else if(cur_A != null && cur_B == null) {
				res[1][i] = res[1][i-1];

				int va = 0;
				for(int k=0; k<cur_A.length(); k++) {
					int idx = cur_A.charAt(k) - 48;
					va = Math.max(va, list.get(idx));
				}

				res[0][i] = res[0][i-1] + va;
			}
			else if(cur_A != null && cur_B != null) {
				comb(i, cur_A, cur_B);
			}
			ax = Anx;
			ay = Any;
			bx = Bnx;
			by = Bny;
		}

	}
	private static void comb(int num, String A, String B) {
		ArrayList<Integer> a = new ArrayList<>();
		ArrayList<Integer> b = new ArrayList<>();

		for(int i=0; i<A.length(); i++) {
			a.add(A.charAt(i)-48);
		}
		for(int i=0; i<B.length(); i++) {
			b.add(B.charAt(i)-48);
		}

		int MAX = Integer.MIN_VALUE;
		int va=0, vb=0;

		for(int i=0; i<A.length(); i++) {
			int idx_a = A.charAt(i)-48;
			for(int j=0; j<B.length(); j++) {
				int idx_b = B.charAt(j)-48;

				if(idx_a == idx_b) {
					va = vb = list.get(idx_a)/2;
				}
				else {
					va = list.get(idx_a);
					vb = list.get(idx_b);
				}
				if(MAX < va+vb) {
					MAX = va+vb;
					res[0][num] = res[0][num-1] + va;
					res[1][num] = res[1][num-1] + vb;
				}
			}
		}
	}
	private static void setArea(int C, int cnt, Queue<Point> q, int BC) {
		boolean[] visit[] = new boolean[10][10];

		while(!q.isEmpty()) {
			
			if(cnt == C)
				return;
			
			int qsize = q.size();

			for(int a=0; a<qsize; a++) {
				Point p = q.poll();
				visit[p.x][p.y] = true;

				for(int i=1; i<=4; i++) {
					int nx = p.x + dx[i];
					int ny = p.y + dy[i];

					if(isRange(nx, ny) && !visit[nx][ny]) {
						visit[nx][ny] = true;
						if(map[nx][ny] == null)
							map[nx][ny] = "";
						map[nx][ny] += BC+"";
						q.add(new Point(nx, ny));
					}
				}
			}
			cnt++;
		}
	}
	private static boolean isRange(int x, int y) {
		if(x<0 || y<0 || x>10-1 || y>10-1)
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
