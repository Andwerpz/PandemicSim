package util;

public class Simulator {

	public int totalPeople = 10000000;	//10 mil
	
	public double rBase = 2.5;
	
	public int exposed;
	public int infected;
	public int susceptible;
	public int immune;
	public int dead;
	
	public int incubationTime = 5;
	public int timeInfected = 14;
	public int timeImmune = 365;
	public double mortalityRate = 0.12;
	
	public int days;
	public int months;
	public int years;
	
	private boolean vaccinate;
	private boolean pause;
	
	public Simulator() {
		
		infected = 25;
		exposed = 0;
		susceptible = totalPeople - 25;
		immune = 0;
		dead = 0;
		
		days = 0;
		months = 0;
		years = 0;
		
		pause = false;
		
	}
	
	public void setrBase(double n) {
		rBase = n;
	}
	
	public void setMortalityRate(double n) {
		mortalityRate = n;
	}
	
	public void setTimeInfected(int n) {
		timeInfected = n;
	}
	
	public void setTimeImmune(int n) {
		timeImmune = n;
	}
	
	public void setIncubationTime(int n) {
		incubationTime = n;
	}
	
	public void setInfected(int n) {
		infected = n;
	}
	
	public void setExposed(int n) {
		exposed = n;
	}
	
	public void setSusceptible(int n) {
		susceptible = n;
	}
	
	public void setImmune(int n) {
		immune = n;
	}
	
	public void setDead(int n) {
		dead = n;
	}
	
	public String getTimeElapsed() {
		
		
		String output = years + " Years, " + months + " Months, " + days + " Days";
		return output;
		
	}
	
	public void isNewMonth() {
		
	}
	
	public void vaccinate() {
		
	}
	
	public void reset() {
		susceptible = totalPeople - 25;
		exposed = 0;
		infected = 25;
		immune = 0;
		dead = 0;
		
		days = 0;
		months = 0;
		years = 0;
	}
	
	public void pause() {
		
	}
	
	public void tick() {
		
		days++;
		
		if(days == 31) {
			months++;
			days = 0;
		}
		
		if(months == 13) {
			years++;
			months = 0;
		}
		
		double exposeChance = rBase / (double) timeInfected;
		int numExposed = (int) ((infected * exposeChance) * ((double) susceptible / (totalPeople - dead)));
		
		double infectChance = (double) 1 / incubationTime;
		int numInfected = (int) (exposed * infectChance);
		
		double recoverChance = (double) 1 / timeInfected;
		int numRecovered = (int) ((infected * recoverChance));
		
		double mortalityChance = (1 / (double)timeInfected) * mortalityRate;
		int numDead = (int) (infected * mortalityChance);
		
		double susceptibleChance = (double) 1 / timeImmune;
		int numSusceptible = (int) ((immune * susceptibleChance));
		
		exposed += numExposed;
		susceptible -= numExposed;
		
		if(susceptible < 0) {
			exposed += susceptible;
			susceptible = 0;
		}
		
		infected += numInfected;
		exposed -= numInfected;
		
		immune += numRecovered;
		infected -= numRecovered;
		
		dead += numDead;
		infected -= numDead;		
		
		susceptible += numSusceptible;
		immune -= numSusceptible;
		
	}
	
	public void infect(int num) {
		
		infected += num;
		susceptible -= num;
		
		if(susceptible < 0) {
			infected += susceptible;
			susceptible = 0;
		}
		
	}
	
}
