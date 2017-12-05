package controller;

import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class MediaRunnable implements Runnable {

	private String pauseResume = "Pause", resumePause = "Resume";
	private MediaPlayer video;
	private Label time;
	private boolean paused;
	private Button pauseButton;
	
	public MediaRunnable(MediaPlayer v, Button p, Label t) {
		video = v;
		setPauseButton(p);
		setTime(t);
	}

	public void run() {
		video.setAutoPlay(true);
		paused = false;
		video.currentTimeProperty().addListener((ov) -> {
			if(video!=null)
				updateTimer();
		});
	}
	
	public void stop() {
		video.stop();
		pauseButton.setText(pauseResume);
		pauseButton.setDisable(true);
	}
	
	public void setTime(Label t) {
		time = t;
	}
	
	public void setPauseButton(Button p) {
		pauseButton = p;
		pauseButton.setOnAction(e-> pauseEvent());
	}
	
	public void pauseEvent() {
		if(paused) {
			video.play();
			paused = false;
			pauseButton.setText(pauseResume);
		}
		else {
			video.pause();
			paused = true;
			pauseButton.setText(resumePause);
		}
	}

	//Next 2 Methods were sourced from https://docs.oracle.com/javase/8/javafx/media-tutorial/playercontrol.htm
	public void updateTimer() {
		Duration currentTime = video.getCurrentTime();
		time.setText(formatTime(currentTime, video.getMedia().getDuration()));
	}

	private static String formatTime(Duration elapsed, Duration duration) {
		int intElapsed = (int)Math.floor(elapsed.toSeconds());
		int elapsedHours = intElapsed / (60 * 60);
		if (elapsedHours > 0) {
			intElapsed -= elapsedHours * 60 * 60;
		}
		int elapsedMinutes = intElapsed / 60;
		int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 
				- elapsedMinutes * 60;

		if (duration.greaterThan(Duration.ZERO)) {
			int intDuration = (int)Math.floor(duration.toSeconds());
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
				intDuration -= durationHours * 60 * 60;
			}
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60 - 
					durationMinutes * 60;
			if (durationHours > 0) {
				return String.format("%d:%02d:%02d/%d:%02d:%02d", 
						elapsedHours, elapsedMinutes, elapsedSeconds,
						durationHours, durationMinutes, durationSeconds);
			} else {
				return String.format("%02d:%02d/%02d:%02d",
						elapsedMinutes, elapsedSeconds,durationMinutes, 
						durationSeconds);
			}
		} else {
			if (elapsedHours > 0) {
				return String.format("%d:%02d:%02d", elapsedHours, 
						elapsedMinutes, elapsedSeconds);
			} else {
				return String.format("%02d:%02d",elapsedMinutes, 
						elapsedSeconds);
			}
		}
	}
}
