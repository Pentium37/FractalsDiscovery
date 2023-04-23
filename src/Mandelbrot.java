public class Mandelbrot extends ComplexFractal {
	public Mandelbrot() {
		startFractal("MandelBrot Set");
	}

	@Override
	public void renderSet() {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				double i = (x - WIDTH / 2d) / SCALE_FACTOR;
				double j = (y - HEIGHT / 2d) / SCALE_FACTOR;

				int color = calculateColor(i, j, i, j);
				bufferedImage.setRGB(x, y, color);
			}
		}
	}
}