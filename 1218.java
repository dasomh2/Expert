import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {

	static Stack<Character> stack;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		for(int t=1; t<=10; t++) {

			boolean isOver = false;
			stack = new Stack<>();
			int N = Integer.parseInt(br.readLine());
			String s = br.readLine();

			for(int i=0; i<s.length(); i++) {
				char ch = s.charAt(i);

				if(ch == '(' || ch == '[' || ch == '{' || ch == '<') {
					stack.push(ch);
				}
				else {
					boolean isClosed = false;

					char door = stack.pop();
					switch(ch) {
					case ')':
						if(door == '(')
							isClosed = true;
						break;
					case '}':
						if(door == '{')
							isClosed = true;
						break;
					case '>':
						if(door == '<')
							isClosed = true;
						break;
					case ']':
						if(door == '[')
							isClosed = true;
						break;
					}
					if(!isClosed) {
						System.out.println("#"+t+" "+0);
						isOver = true;
						break;
					}
				}
			}
			if(!isOver) {
				if(stack.size() == 0)
					System.out.println("#"+t+" "+1);
				else
					System.out.println("#"+t+" "+0);
			}
		}
	}
}
