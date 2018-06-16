package dataobjects;

public class InvestStratTwo {

	private String Stocksymbol;
	private double ltp;
	private double lowband;
	private double highband;
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
	public double getLowband() {
		return lowband;
	}
	public void setLowband(double lowband) {
		if(lowband < 0)
			this.lowband=0.01;
			else
		this.lowband = lowband;
	}
	public double getHighband() {
		return highband;
	}
	public void setHighband(double highband) {
		this.highband = highband;
	}
	public String getTrendstatus() {
		return Trendstatus;
	}
	public void setTrendstatus(String trendstatus) {
		Trendstatus = trendstatus;
	}
	private String Trendstatus;
}
