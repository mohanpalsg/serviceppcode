package hibernateobjects;

public class Defaultcomplexsetting implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String settingtype ;
	private String duration;
	private String period;
	private String avg1;
	private String avg2;
	private String adjustment;
	private String factor;
	public String getSettingtype() {
		return settingtype;
	}
	public void setSettingtype(String settingtype) {
		this.settingtype = settingtype;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getAvg1() {
		return avg1;
	}
	public void setAvg1(String avg1) {
		this.avg1 = avg1;
	}
	public String getAvg2() {
		return avg2;
	}
	public void setAvg2(String avg2) {
		this.avg2 = avg2;
	}
	public String getAdjustment() {
		return adjustment;
	}
	public void setAdjustment(String adjustment) {
		this.adjustment = adjustment;
	}
	public String getFactor() {
		return factor;
	}
	public void setFactor(String factor) {
		this.factor = factor;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
