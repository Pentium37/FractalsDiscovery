import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Yalesan Thayalan {@literal <yalesan2006@outlook.com>}
 */
public class KeyHandler implements KeyListener {
	public boolean upPressed, downPressed, leftPressed, rightPressed;

	@Override
	public void keyTyped(final KeyEvent e) {

	}

	@Override
	public void keyPressed(final KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_W -> upPressed = true;
			case KeyEvent.VK_S -> downPressed = true;
			case KeyEvent.VK_D -> rightPressed = true;
			case KeyEvent.VK_A -> leftPressed = true;
		}
	}

	@Override
	public void keyReleased(final KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_W -> upPressed = false;
			case KeyEvent.VK_S -> downPressed = false;
			case KeyEvent.VK_D -> rightPressed = false;
			case KeyEvent.VK_A -> leftPressed = false;
		}
	}
}
