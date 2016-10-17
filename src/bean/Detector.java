package bean;

public class Detector {

	private String id;
	private double speed;
	private int volume;

	public Detector(String id, double speed, int volume) {
		this.id = id;
		this.speed = speed;
		this.volume = volume;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public String toString() {
		return id + "," + speed + "," + volume;
	}

}
