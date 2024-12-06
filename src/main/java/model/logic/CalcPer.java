package model.logic;

import java.io.Serializable;

import model.FaseBoard;
import model.SessionRegulation;

public class CalcPer implements Serializable{
	private static SessionRegulation sr;
	private FaseBoard fb;
	
	public CalcPer() {
		
	}

	public CalcPer(FaseBoard fb) {
		this.fb = fb;
	}

	
	
	
	public String perToJsp(float value) {
        return String.format("%.1f", value * 100);
    }
	
	public static void setSr(SessionRegulation SR) {
		sr=SR;
	}
}
