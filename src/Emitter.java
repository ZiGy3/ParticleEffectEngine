import java.util.Random;

public class Emitter {
	private static int pNum = 10;
	private static Particle[] particles = new Particle[pNum];

	public static void main(String[] args) {
		Random rnd = new Random();
		for (int i = 0; i < pNum; i++) {
			particles[i] = new Particle(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat());
			System.out.println(particles[i]);
		}
	}
}
