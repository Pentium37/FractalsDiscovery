import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ComplexFractal extends JComponent {
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 780;
	public static final int ITERATIONS = 100;
	public double SCALE_FACTOR = 300;

	public JFrame frame;

	/*
	 COLOR SCHEMES:
	 Sky_Blue: 0x00000000 : Color.HSBtoRGB((float) i / ITERATIONS + 0.62f, 0.5f, 1)
	 Red: 0x00000000 : Color.HSBtoRGB((float) i / ITERATIONS , 0.5f, 1)
	 BW:  Color.WHITE.getRGB() : Color.HSBtoRGB(0 , 0.5f, (float) i / ITERATIONS)
	 */

	public BufferedImage bufferedImage;

	public void startFractal(String setName) {
		bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		renderSet();
		createFrame(setName);
	}

	public abstract void renderSet();

	private void createFrame(String setName) {
		frame = new JFrame(setName);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.getContentPane()
				.add(this);
		frame.pack();
		frame.setVisible(true);
	}
	public int calculateColor(double x, double y, double cx, double cy) {
		int i;
		for (i = 0; i < ITERATIONS; i++) {
			double nx = x * x - y * y + cx;
			double ny = 2 * x * y + cy;
			x = nx;
			y = ny;

			if (x * x + y * y > 16) {
				break;
			}
		}

		return (i == ITERATIONS) ?   0x00000000 : Color.HSBtoRGB((float) i / ITERATIONS , 0.5f, 1);
	}

	@Override
	public void addNotify() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(bufferedImage, 0, 0, null);
	}
}
