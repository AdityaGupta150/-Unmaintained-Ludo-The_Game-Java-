package in.ag15;

import java.io.IOException;

public class experimental {
    public static void main(final String[] args) throws IOException, InterruptedException {
		System.out.println("Entry anything");
		int a = System.in.read();
		System.out.println("Passed and got - " + a );
		System.in.wait();
		System.out.println("Wait ended");
	}
}