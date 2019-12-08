package wargame;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Adaptater pour {@link MouseListener}, {@link MouseMotionListener},
 * {@link MouseWheelListener}, {@link ComponentListener}
 */
public interface ListenerAdapter extends MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener {
	@Override
	default void mouseClicked(MouseEvent e) {
	}

	@Override
	default void mouseDragged(MouseEvent e) {
	}

	@Override
	default void mouseEntered(MouseEvent e) {
	}

	@Override
	default void mouseExited(MouseEvent e) {
	}

	@Override
	default void mouseMoved(MouseEvent e) {
	}

	@Override
	default void mousePressed(MouseEvent e) {
	}

	@Override
	default void mouseReleased(MouseEvent e) {
	}

	@Override
	default void mouseWheelMoved(MouseWheelEvent e) {
	}

	@Override
	default void componentHidden(ComponentEvent e) {
	}

	@Override
	default void componentShown(ComponentEvent e) {
	}

	@Override
	default void componentMoved(ComponentEvent e) {
	}

	@Override
	default void componentResized(ComponentEvent e) {
	}

}
