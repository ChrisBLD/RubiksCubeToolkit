package tutorial;

import javafx.animation.Timeline;
import javafx.scene.PerspectiveCamera;

public class NotationTutorial {
	public PerspectiveCamera camera;
	public boolean startTimer;
	public Timeline timer;

	public NotationTutorial(boolean startTimer) {
		this.startTimer = startTimer;
	}
}