package com.coderscampus.assignment6;

import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import com.coderscampus.assignment6.domain.SalesData;
import com.coderscampus.assignment6.fileservice.FileService;

public class TeslaSalesReportApplication {
	/* class variables and constants */
	public static final String REPORT_HEADER = " Yearly Sales Report";
	public static final String REPORT_LINE_DIVIDER = "---------------------------";
	
	public static void main(String[] args) throws IOException {
		/* data structure to store the sales data by car model */
		List<SalesData> TeslaModel3 = new ArrayList<>(50);
		List<SalesData> TeslaModelS = new ArrayList<>(50);
		List<SalesData> TeslaModelX = new ArrayList<>(50);
		
		/* call file service to load the sales data into the List data structure */
		FileService teslaFS = new FileService();
		TeslaModel3 = (teslaFS.loadSalesDataList("./model3.csv"));
		TeslaModelS = (teslaFS.loadSalesDataList("./modelS.csv"));
		TeslaModelX = (teslaFS.loadSalesDataList("./modelX.csv"));
	
		/* create sales data report by model ***STREAM SOLUTION*** */
		CreateSalesDataReport(TeslaModel3, "Model 3");
		CreateSalesDataReport(TeslaModelS, "Model S");
		CreateSalesDataReport(TeslaModelX, "Model X");

	} //end of main
	
	/* Sales Data Report Output */
	private static void CreateSalesDataReport(List<SalesData> teslaSalesData, String modelName) {
		System.out.println(modelName + REPORT_HEADER);
		System.out.println(REPORT_LINE_DIVIDER);

		/* use a hash map data structure to group the sales data by year (key) and the sales data object (value) */
		Map<Integer, List<SalesData>> groupedByYearlySalesData = 
				teslaSalesData.stream()
				     		  .collect(Collectors.groupingBy(x -> x.getDate().getYear()));
		
		/* This block of code was done with Trevor's help. */
		groupedByYearlySalesData.entrySet()
								.stream()
								.forEach(entry -> {
									System.out.println(entry.getKey() + " -> " + 
														entry.getValue()
														.stream()
														.mapToInt(salesData -> salesData.getSales())
														.sum());
														});
		System.out.println();	
		
		/* use a sorted List of sales data to place the minimum sales total   */
		/* at the front of the List and the maximum sales totals at the end   */
		/* of the List. This block of code was done with Trevor's help!       */
		List<SalesData> sortedTeslaSalesData = teslaSalesData.stream()
									   .sorted((o1, o2) -> o1.getSales().compareTo(o2.getSales()))
									   .collect(Collectors.toList());
		
		YearMonth minSalesYearMonth = sortedTeslaSalesData.get(0).getDate();
		YearMonth maxSalesYearMonth = sortedTeslaSalesData.get(sortedTeslaSalesData.size()-1).getDate();
				
		System.out.println("The best month for " + modelName + " was: " + maxSalesYearMonth);
		System.out.println("The worst month for " + modelName + " was: " + minSalesYearMonth);
		System.out.println();
		
	} //end of CreateSalesDataReport

} //end of TeslaSalesReportApplication class
