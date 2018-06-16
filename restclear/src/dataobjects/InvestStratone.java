package dataobjects;

public class InvestStratone {

	private String Stocksymbol;
	private double ltp;
	private double trendup;
	private double trenddown;
	private String Trendstatus;
	
	public String getStocksymbol() {
		return Stocksymbol;
	}
	public void setStocksymbol(String stocksymbol) {
		Stocksymbol = stocksymbol;
	}
	public double getLtp() {
		return ltp;
	}
	public void setLtp(double ltp) {
		this.ltp = ltp;
	}
	public double getTrendup() {
		return trendup;
	}
	public void setTrendup(double trendup) {
		if(trendup > 0)
		this.trendup = trendup;
		else
		this.trendup=0.01;
	}
	public double getTrenddown() {
		return trenddown;
	}
	public void setTrenddown(double trenddown) {
		if(trenddown > 0)
			this.trenddown = trenddown;
			else
			this.trenddown=0.01;
		
		
	}
	public String getTrendstatus() {
		return Trendstatus;
	}
	public void setTrendstatus(String trendstatus) {
		Trendstatus = trendstatus;
	}
	
}
