package dataobjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StrategyinvestResult {

	private String Stocksymbol;
	private double ltp;
	private double support;
	private double Resistance;
	private String Trendstatus;
	private double diffpercent;
	private double potential;
	public double getPotential() {
		return potential;
	}
	public void setPotential(double potential) {
		this.potential = potential;
	}
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
	public double getSupport() {
		return support;
	}
	public void setSupport(double support) {
		this.support = support;
	}
	public double getResistance() {
		return Resistance;
	}
	public void setResistance(double resistance) {
		Resistance = resistance;
	}
	public String getTrendstatus() {
		return Trendstatus;
	}
	public void setTrendstatus(String trendstatus) {
		Trendstatus = trendstatus;
	}
	public double getDiffpercent() {
		return diffpercent;
	}
	public void setDiffpercent(double diffpercent) {
		this.diffpercent = diffpercent;
	}
	
}
