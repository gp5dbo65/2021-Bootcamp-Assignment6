package com.coderscampus.assignment6;

import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.coderscampus.assignment6.domain.SalesData;
import com.coderscampus.assignment6.fileservice.FileService;

public class TeslaSalesReportApplication {
	/* class variables and constants */
	public static final String REPORT_HEADER = " Yearly Sales Report";
	public static final String REPORT_LINE_DIVIDER = "---------------------------";
	private static ToIntFunction SalesData;
	
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
	
		/* create sales data report by model */
//		ProcessSalesData(TeslaModel3, "Model 3");
		CreateSalesDataReport(TeslaModel3, "Model 3");
		System.out.println(REPORT_LINE_DIVIDER);

//		ProcessSalesData(TeslaModelS, "Model S");
//		CreateSalesDataReport(TeslaModel3, "Model 3");
//		System.out.println(REPORT_LINE_DIVIDER);
//
//		ProcessSalesData(TeslaModelX, "Model X");
//		CreateSalesDataReport(TeslaModel3, "Model 3");
//		System.out.println(REPORT_LINE_DIVIDER);

	} //end of main
	
	/* Sales Data Report Output */
	private static void CreateSalesDataReport(List<SalesData> teslaSalesData, String modelName) {
		System.out.println(modelName + REPORT_HEADER);
		System.out.println(REPORT_LINE_DIVIDER);

		/* use a hash map data structure to group the sales data by year (key) and the sales data object (value) */
		Map<Integer, List<SalesData>> groupedByYearlySalesData = 
				teslaSalesData.stream()
				     		  .collect(Collectors.groupingBy(x -> x.getDate().getYear()));

		System.out.println(groupedByYearlySalesData);
		
		List<String> map = groupedByYearlySalesData.entrySet()
								.stream()
								.map(entry -> entry.getKey() + " -> " + entry.getValue().stream())
								.collect(Collectors.toList());
								
		System.out.println(map);
		
		
		
		
		System.out.println(REPORT_LINE_DIVIDER);
		
		OptionalInt maxSalesAmount = teslaSalesData.stream()
												   .mapToInt(x -> x.getSales())
												   .max();
		
		OptionalInt minSalesAmount = teslaSalesData.stream()
//												   .mapToInt(x -> x.getDate())
												   .mapToInt(x -> x.getSales())
												   .min();
		
		System.out.println("Tesla Model 3 max sales = " + maxSalesAmount);
		System.out.println("Tesla Model 3 min sales = " + minSalesAmount);

		System.out.println();
		
	} //end of CreateSalesDataReport

	private static void ProcessSalesData(List<SalesData> teslaSalesData, String modelName) {
		Integer minSalesAmount = Integer.MAX_VALUE;
		Integer maxSalesAmount = Integer.MIN_VALUE;
		YearMonth minSalesYearMonth = null;
		YearMonth maxSalesYearMonth = null;
		Integer total2016Sales = 0;
		Integer total2017Sales = 0;
		Integer total2018Sales = 0;
		Integer total2019Sales = 0;
		Integer currentSalesAmount = 0;
		
		for(SalesData model : teslaSalesData) {
			currentSalesAmount = model.getSales();
			//minimum sales amount logic
			if(currentSalesAmount < minSalesAmount) {
				minSalesAmount = currentSalesAmount;
				minSalesYearMonth = model.getDate();
			} //end of minimum sales amount logic
			//maximum sales amount logic
			
			if(currentSalesAmount > maxSalesAmount) {
				maxSalesAmount = currentSalesAmount;
				maxSalesYearMonth = model.getDate();
			} //end of maximum sales amount logic
			
			//accumulate sales totals by year 2016 - 2019
			switch (model.getDate().getYear()) {
				case 2016:
					total2016Sales += currentSalesAmount;
					break;
				case 2017:
					total2017Sales += currentSalesAmount;
					break;
				case 2018:
					total2018Sales += currentSalesAmount;
					break;
				case 2019:
					total2019Sales += currentSalesAmount;
					break;
				default: //encountered YearMonth data that is out of bounds - abend program
					System.out.println("Year model out of bounds");
					System.out.println(model);
					System.exit(999);
			} //end switch block
		} //end for-each block
		
		/* Sales Data Report Output */
		System.out.println(modelName + REPORT_HEADER);
		System.out.println(REPORT_LINE_DIVIDER);
		//check to see if there are any sales data for 2016
		if (total2016Sales > 0)
			System.out.println("2016 -> " + total2016Sales);
		System.out.println("2017 -> " + total2017Sales);
		System.out.println("2018 -> " + total2018Sales);
		System.out.println("2019 -> " + total2019Sales);
		System.out.println();
		System.out.println("The best month for " + modelName + " was: " + maxSalesYearMonth);
		System.out.println("The worst month for " + modelName + " was: " + minSalesYearMonth);
		System.out.println();
	} //end of ProcessSalesData method

} //end of TeslaSalesReportApplication class
