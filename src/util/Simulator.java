package util;

public class Simulator {

	public int totalPeople = 1000000;	//1 mil
	
	public double rBase = 2.5;
	
	public int infected;
	public int susceptible;
	public int immune;
	public int dead;
	public int day = 0;

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
		day++;

		double infectChance = rBase / (double) timeInfected;
		int numInfected = (int) ((infected * infectChance) * ((double)susceptible / (totalPeople - dead)));
		
		double recoverChance = (double)1 / timeInfected;
		int numRecovered = (int) ((infected * recoverChance));
		
		double mortalityChance = (1 / (double)timeInfected) * mortalityRate;
		int numDead = (int) (infected * mortalityChance);
		
		double susceptibleChance = (double)1 / timeImmune;
		int numSusceptible = (int) ((immune * susceptibleChance));
		
		infected += numInfected;
		susceptible -= numInfected;
		
		if(susceptible < 0) {
			infected += susceptible;
			susceptible = 0;
		}
		
		immune += numRecovered;
		infected -= numRecovered;
		
		
		
		dead += numDead;
		infected -= numDead;
		
		//System.out.println(infected + " " + susceptible);
		
		
		
		
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
