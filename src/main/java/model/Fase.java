package model;

public class Fase {
	String zone;
	int day;

	public Fase(String zone, int day) {
		this.zone = zone;
		this.day = day;
	}

	@Override
	public String toString() {
		return zone + day;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

}
