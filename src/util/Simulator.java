package util;

public class Simulator {

	public int totalPeople = 1000000;	//1 mil
	
	public double rBase = 2.5;
	
	public int infected;
	public int susceptible;
	public int immune;
	public int dead;
	
	public int timeInfected = 14;
	public int timeImmune = 365;
	public double mortalityRate = 0.12;
	
	private boolean vaccinate;
	
	public Simulator() {
		
		infected = 20;
		susceptible = totalPeople - 20;
		immune = 0;
		dead = 0;
		
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
	
	public void setInfected(int n) {
		infected = n;
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
	
	public void vaccinate() {
		
	}
	
	public void tick() {
		
		double infectChance = rBase / (double) timeInfected;
		int numInfected = (int) ((infected * infectChance) * ((double)susceptible / totalPeople));
		//System.out.println(infectChance);
		
		infected += numInfected;
		susceptible -= numInfected;
		
		if(susceptible < 0) {
			infected += susceptible;
			susceptible = 0;
		}
		
		double recoverChance = (double)1 / timeInfected;
		int numRecovered = (int) ((infected * recoverChance));
		
		immune += numRecovered;
		infected -= numRecovered;
		
		double mortalityChance = (1 / (double)timeInfected) * mortalityRate;
		int numDead = (int) (infected * mortalityChance);
		
		dead += numDead;
		infected -= numDead;
		
		//System.out.println(infected + " " + susceptible);
		
		
		double susceptibleChance = (double)1 / timeImmune;
		int numSusceptible = (int) ((immune * susceptibleChance));
		
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
