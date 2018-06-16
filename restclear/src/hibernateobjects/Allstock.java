package hibernateobjects;

import java.util.Date;

public class Allstock implements java.io.Serializable{
private String stocksymbol;
private Date lastupdate;
private float lastprice;
private Date lasttradingday;
float highprice;
float openprice;
float lowprice;
float prev_closeprice;
float prev_highprice;
float prev_openprice;
float prev_lowprice;
String duration;
public float getPrev_closeprice() {
	return prev_closeprice;
}
public void setPrev_closeprice(float prev_closeprice) {
	this.prev_closeprice = prev_closeprice;
}
public float getPrev_highprice() {
	return prev_highprice;
}
public void setPrev_highprice(float prev_highprice) {
	this.prev_highprice = prev_highprice;
}
public float getPrev_openprice() {
	return prev_openprice;
}
public void setPrev_openprice(float prev_openprice) {
	this.prev_openprice = prev_openprice;
}
public float getPrev_lowprice() {
	return prev_lowprice;
}
public void setPrev_lowprice(float prev_lowprice) {
	this.prev_lowprice = prev_lowprice;
}
public String getDuration() {
	return duration;
}
public void setDuration(String duration) {
	this.duration = duration;
}
public float getHighprice() {
	return highprice;
}
public void setHighprice(float highprice) {
	this.highprice = highprice;
}
public float getOpenprice() {
	return openprice;
}
public void setOpenprice(float openprice) {
	this.openprice = openprice;
}
public float getLowprice() {
	return lowprice;
}
public void setLowprice(float lowprice) {
	this.lowprice = lowprice;
}
public Date getLasttradingday() {
	return lasttradingday;
}
public void setLasttradingday(Date lasttradingday) {
	this.lasttradingday = lasttradingday;
}
public float getLastprice() {
	return lastprice;
}
public void setLastprice(float lastprice) {
	this.lastprice = lastprice;
}
public String getStocksymbol() {
	return stocksymbol;
}
public void setStocksymbol(String stocksymbol) {
	this.stocksymbol = stocksymbol;
}
public Date getLastupdate() {
	return lastupdate;
}
public void setLastupdate(Date lastupdate) {
	this.lastupdate = lastupdate;
}

}
