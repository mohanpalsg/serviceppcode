package hibernateobjects;

public class Nsebase implements java.io.Serializable {
	
private String duration;
private String stocksymbol;
private double stochk;
private double stockd;
private double willr;
private double rsi;

private double sma50;
private double sma200;
public String getDuration() {
	return duration;
}
public void setDuration(String duration) {
	this.duration = duration;
}
public String getStocksymbol() {
	return stocksymbol;
}
public void setStocksymbol(String stocksymbol) {
	this.stocksymbol = stocksymbol;
}
public double getStochk() {
	return stochk;
}
public void setStochk(double stochk) {
	this.stochk = stochk;
}
public double getStockd() {
	return stockd;
}
public void setStockd(double stockd) {
	this.stockd = stockd;
}
public double getWillr() {
	return willr;
}
public void setWillr(double willr) {
	this.willr = willr;
}
public double getRsi() {
	return rsi;
}
public void setRsi(double rsi) {
	this.rsi = rsi;
}
public double getSma50() {
	return sma50;
}
public void setSma50(double sma50) {
	this.sma50 = sma50;
}
public double getSma200() {
	return sma200;
}
public void setSma200(double sma200) {
	this.sma200 = sma200;
}

}
