package util;

public class Simulator {

	public int totalPeople = 1000000;	//1 mil
	
	public double rBase = 2.5;
	
	public int infected;
	public int susceptible;
	public int immune;
	
	public int timeInfected = 14;
	public int timeImmune = 365;
	
	public Simulator() {
		
		infected = 20;
		susceptible = totalPeople - 20;
		immune = 0;
		
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
