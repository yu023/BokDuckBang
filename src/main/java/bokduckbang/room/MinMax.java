package bokduckbang.room;

public class MinMax {
	private Integer maxLease;
	private Integer maxDeposit;
	private Integer maxRent;
	private Integer minLease;
	private Integer minDeposit;
	private Integer minRent;
	private Integer multi;
	private Integer rentMulti;
	
	public Integer getMaxLease() {
		return maxLease;
	}
	public void setMaxLease(Integer maxLease) {
		this.maxLease = maxLease;
	}
	public Integer getMaxDeposit() {
		return maxDeposit;
	}
	public void setMaxDeposit(Integer maxDeposit) {
		this.maxDeposit = maxDeposit;
	}
	public Integer getMaxRent() {
		return maxRent;
	}
	public void setMaxRent(Integer maxRent) {
		this.maxRent = maxRent;
	}
	public Integer getMinLease() {
		return minLease;
	}
	public void setMinLease(Integer minLease) {
		this.minLease = minLease;
	}
	public Integer getMinDeposit() {
		return minDeposit;
	}
	public void setMinDeposit(Integer minDeposit) {
		this.minDeposit = minDeposit;
	}
	public Integer getMinRent() {
		return minRent;
	}
	public void setMinRent(Integer minRent) {
		this.minRent = minRent;
	}
	public void setMulti(Integer max,Integer min) {
		this.multi = (max+min)/2;
	}
	public Integer getMulti() {
		return multi;
	}
	public void setRentMulti(Integer max,Integer min) {
		this.rentMulti = (max+min)/2;
	}
	public Integer getRentMulti() {
		return rentMulti;
	}
}
