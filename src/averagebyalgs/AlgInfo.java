package averagebyalgs;

public class AlgInfo {
	
	private String numAlgs, occ, percent, average, best;
	
	public AlgInfo(String numAlgs, int occ, double percent) {
		this.numAlgs = numAlgs;
		this.occ = ""+occ;
		this.percent = ""+percent;
		this.average = "-";
		this.best = "-";
	}

	public AlgInfo(String numAlgs, int occ, double percent, Double average, Double best) {
		this.numAlgs = numAlgs;
		this.occ = ""+occ;
		if (occ == 0) {
			this.percent = "-";
			this.average = "-";
			this.best = "-";
		} else {
			this.percent = ""+percent;
			this.average = ""+average;
			this.best = ""+best;
		}
	}
	
	public String getNumAlgs() {
		return numAlgs;
	}
	
	public String getOcc() {
		return occ;
	}
	
	public String getPercent() {
		return percent;
	}
	
	public String getAverage() {
		return average;
	}
	
	public String getBest() {
		return best;
	}
}
