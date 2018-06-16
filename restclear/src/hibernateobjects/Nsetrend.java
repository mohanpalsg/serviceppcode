package hibernateobjects;

public class Nsetrend implements java.io.Serializable{

	private String duration;
	private String Stocksymbol;
	private float Trendup;
	private float Trenddown;
	private String Trendstatus;
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getStocksymbol() {
		return Stocksymbol;
	}
	public void setStocksymbol(String stocksymbol) {
		Stocksymbol = stocksymbol;
	}
	public float getTrendup() {
		return Trendup;
	}
	public void setTrendup(float trendup) {
		Trendup = trendup;
	}
	public float getTrenddown() {
		return Trenddown;
	}
	public void setTrenddown(float trenddown) {
		Trenddown = trenddown;
	}
	public String getTrendstatus() {
		return Trendstatus;
	}
	public void setTrendstatus(String sptnd) {
		Trendstatus = sptnd;
	}
	
}
