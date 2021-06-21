package com.coderscampus.assignment6.domain;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class SalesData {
	private YearMonth date;
	private Integer sales;
	
	/* Constructors */
	public SalesData (String date, String sales) {
		this.date = YearMonth.parse(date, DateTimeFormatter.ofPattern("MMM-yy"));
		this.sales = Integer.parseInt(sales);
	}
	
	/* Getters and Setters */
	public YearMonth getDate() {
		return date;
	}
	public void setDate(YearMonth date) {
		this.date = date;
	}
	public Integer getSales() {
		return sales;
	}
	public void setSales(Integer sales) {
		this.sales = sales;
	}
	
	/* Methods */
	@Override
	public String toString() {
		return "SalesData [date=" + date + ", sales=" + sales + "]";
	}
	
} //end of SalesData class
