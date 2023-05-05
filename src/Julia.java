import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author Yalesan Thayalan {@literal <yalesan2006@outlook.com>}
 */
public class Julia extends ComplexFractal implements Runnable {
	public double re;
	public double im;
	public double theta;
	public double R;
	public boolean x;
	public static final DecimalFormat myFormat = new DecimalFormat("0.000");

	public static final double FPS = 60;
	public static final double TPS = 200;

	boolean keyPressed = false;

	KeyHandler keyHandler = new KeyHandler();
	Thread gameThread;

	public Julia(final double x, final double y) {
		re = x;
		im = y;
		theta = 0;
		R = 0.4;
		startFractal("Julia set for " + re + " + " + im + "i");
		this.frame.addKeyListener(keyHandler);
		this.setFocusable(true);
		startGameThread();
	}

	@Override
	public void renderSet() {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				double i = (x - WIDTH / 2d) / SCALE_FACTOR;
				double j = (y - HEIGHT / 2d) / SCALE_FACTOR;

				int color = calculateColor(i, j, re, im);
				bufferedImage.setRGB(x, y, color);
			}
		}
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double deltaTimeSeconds = 1.0 / TPS;
		double lastFrameTime = nanosToSeconds(System.nanoTime());
		double secondsToConsume = 0.0;

		while (gameThread != null) {
			double currentFrameTime = nanosToSeconds(System.nanoTime());
			double lastFrameNeeded = currentFrameTime - lastFrameTime;
			lastFrameTime = currentFrameTime;

			secondsToConsume += lastFrameNeeded;
			while (secondsToConsume >= deltaTimeSeconds) {
				update(deltaTimeSeconds);
				secondsToConsume -= deltaTimeSeconds;
			}

			if (keyPressed || keyHandler.playPressed) {
				renderSet();
				frame.setTitle("Julia set for " + myFormat.format(re) + " + " + myFormat.format(im) + "i");
				frame.validate();
				frame.invalidate();
				frame.repaint();
			}

			double currentFPS = 1.0 / lastFrameNeeded;
			if (currentFPS > FPS) {
				double targetSecondsPerFrame = 1.0 / FPS;
				double secondsToWaste = Math.abs(targetSecondsPerFrame - lastFrameNeeded) / 1000000;
				try {
					TimeUnit.SECONDS.sleep(secondsToMillis(secondsToWaste));
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}

		}
	}

	private static double nanosToSeconds(long nanos) {
		return nanos / 1E9;
	}

	private static long secondsToMillis(double seconds) {
		return (long) (seconds * 1E3);
	}

	public void update(double deltaTime) {
		keyPressed = false;
		if (keyHandler.upPressed) {
			im += 0.2 * deltaTime;
			keyPressed = true;
		}

		if (keyHandler.downPressed) {
			im -= 0.2 * deltaTime;
			keyPressed = true;
		}

		if (keyHandler.leftPressed) {
			re -= 0.2 * deltaTime;
			keyPressed = true;
		}

		if (keyHandler.rightPressed) {
			re += 0.2 * deltaTime;
			keyPressed = true;
		}

		if (keyHandler.playPressed) {
			theta += 0.1 * deltaTime;

			if (R > 1.5) {
				if (!x) {
					x = true;
				}
			} else if (R < 0.4) {
				x = false;
			}

			if (x) {
				R -= 0.1 * deltaTime;
			} else {
				R += 0.1 * deltaTime;
			}
			im = R * Math.pow(Math.sin(theta), 2);
			re = R * Math.pow(Math.cos(theta), 2);
		}

	}
}
