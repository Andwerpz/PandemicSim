package util;

public class Simulator {

	public int totalPeople = 10000000;	//10 mil
	
	public double rBase = 2.5;
	public double rReal;
	public double herdImmunityPercentage = 1 - (1 / rBase);
	
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
	
	private boolean pause;
	private boolean immuneDecay = true;
	
	private double handWashingMultiplier = 0;
	private double socialDistancingMultiplier = 0;
	private double summerMultiplier = 0;
	private double maxSummerMultiplier = 0;
	
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
		
		rReal = rBase;
		
	}
	
	public void setrBase(double n) {
		rBase = n;
	}
	
	public void setMortalityRate(double n) {
		mortalityRate = n;
	}
	
	public void setHandWashingMultiplier(double n) {
		handWashingMultiplier = n;
	}
	
	public void setSocialDistancingMultiplier(double n) {
		socialDistancingMultiplier = n;
	}
	
	public void setTimeInfected(int n) {
		timeInfected = n;
	}
	
	public void setTimeImmune(int n) {
		timeImmune = n;
	}
	
	public void toggleNoImmuneDecay() {
		immuneDecay = !immuneDecay;
	}
	
	public void setIncubationTime(int n) {
		incubationTime = n;
	}
	
	public void setMaxSummerMultiplier(double n) {
		maxSummerMultiplier = n;
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

	public void togglePause(){
		pause = !pause;
	}
	
	public String getTimeElapsed() {
		
		
		String output = years + " Years, " + months + " Months, " + days + " Days";
		return output;
		
	}
	
	public void isNewMonth() {
		
	}
	
	public void vaccinate() {
		
		int numNotSusceptible = immune + infected + exposed;
		//double percentageNotSusceptible = (double) numNotSusceptible / (totalPeople - dead);
		
		int numNeededForHerdImmunity = (int) ((totalPeople - dead) * herdImmunityPercentage);
		
		int numVaccinated = numNeededForHerdImmunity - numNotSusceptible;
		
		immune += numVaccinated;
		susceptible -= numVaccinated;
		
	}
	
	public void reset() {
		susceptible = totalPeople - 25;
		exposed = 0;
		infected = 25;
		immune = 0;
		dead = 0;
		
		days = 0;
		months = 1;
		years = 0;
	}
	
	public void pause() {
		
	}
	
	public double getSummerMultiplier() {
		return summerMultiplier;
	}
	
	public double getMaxSummerMultiplier() {
		return maxSummerMultiplier;
	}
	
	public double getHandWashingMultiplier() {
		return handWashingMultiplier;
	}
	
	public double getSocialDistancingMultiplier() {
		return socialDistancingMultiplier;
	}
	
	public void tick() {
		
		days++;
		
		if(days == 31) {
			months++;
			days = 1;
		}
		
		if(months == 13) {
			years++;
			months = 1;
		}
		
		int dayInYear = days + months * 30;
		
		summerMultiplier = (Math.sin(((2 * Math.PI) * ((double) dayInYear + 250)) / 365) * maxSummerMultiplier + maxSummerMultiplier) / 2 / 100;	//go onto desmos to test this function
		
		//+ 250 is offset to set the peak of summer in may or july
		//31% is peak summer reduction in r0 in New York
		
		//System.out.println(summerMultiplier);
		
		herdImmunityPercentage = 1 - (1 / rBase);
		
		this.rReal = rBase * ((double) susceptible / (totalPeople - dead)) * (1 - handWashingMultiplier) * (1 - socialDistancingMultiplier) * (1 - summerMultiplier);
		
		double exposeChance = rReal / (double) timeInfected;
		//int numExposed = (int) ((infected * exposeChance) * ((double) susceptible / (totalPeople - dead)));
		int numExposed = (int) ((infected * exposeChance));
		
		double infectChance = (double) 1 / incubationTime;
		int numInfected = (int) (exposed * infectChance);
		
		double recoverChance = (double) 1 / timeInfected;
		int numRecovered = (int) ((infected * recoverChance));
		
		double mortalityChance = (1 / (double)timeInfected) * mortalityRate;
		int numDead = (int) (infected * mortalityChance);
		
		double susceptibleChance = 0;
		int numSusceptible = 0;
		
		if(immuneDecay) {
			susceptibleChance = (double) 1 / timeImmune;
			numSusceptible = (int) ((immune * susceptibleChance));
		}
		
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
