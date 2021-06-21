package com.coderscampus.assignment6;

import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.coderscampus.assignment6.domain.SalesData;
import com.coderscampus.assignment6.fileservice.FileService;

public class TeslaSalesReportApplication {
	/* class variables and constants */
	public static final String REPORT_HEADER = " Yearly Sales Report";
	public static final String REPORT_LINE_DIVIDER = "---------------------------";
	
	public static void main(String[] args) throws IOException {
		List<SalesData> TeslaModel3 = new ArrayList<>(50);
		List<SalesData> TeslaModelS = new ArrayList<>(50);
		List<SalesData> TeslaModelX = new ArrayList<>(50);
		
		FileService teslaFS = new FileService();
		TeslaModel3 = (teslaFS.loadSalesDataList("./model3.csv"));
		TeslaModelS = (teslaFS.loadSalesDataList("./modelS.csv"));
		TeslaModelX = (teslaFS.loadSalesDataList("./modelX.csv"));
		
//		System.out.println(TeslaModel3.size());
//		TeslaModel3.stream()
//				   .forEach(model3 -> System.out.println(model3));
//		System.out.println(REPORT_LINE_DIVIDER);
//		
//		System.out.println(TeslaModelS.size());
//		TeslaModelS.stream()
//		   .forEach(modelS -> System.out.println(modelS));
//		System.out.println(REPORT_LINE_DIVIDER);
//		
//		System.out.println(TeslaModelX.size());
//		TeslaModelX.stream()
//		   .forEach(modelX -> System.out.println(modelX));
//		System.out.println(REPORT_LINE_DIVIDER);
		
		List<YearMonth> modelYYMM = TeslaModel3.stream()
											   .map(yrMon -> yrMon.getDate())
											   .collect(Collectors.toList());
		modelYYMM.stream().forEach(model -> System.out.println(model));
		System.out.println(REPORT_LINE_DIVIDER);
		
	
		List<Integer> modelYear = TeslaModel3.stream()
											   .map(year -> year.getDate().getYear())
											   .distinct()
//											   .map(sales -> sales.)
											   .collect(Collectors.toList());
		modelYear.stream().forEach(model -> System.out.println(model));
		System.out.println(REPORT_LINE_DIVIDER);
		System.out.println();
	
		/* create sales data report by model */
		ProcessSalesData(TeslaModel3, "Model 3");
		ProcessSalesData(TeslaModelS, "Model S");
		ProcessSalesData(TeslaModelX, "Model X");

	} //end of main
	
	public static void ProcessSalesData(List<SalesData> TeslaSalesData, String modelName) {
		Integer minSalesAmount = Integer.MAX_VALUE;
		Integer maxSalesAmount = Integer.MIN_VALUE;
		YearMonth minSalesYearMonth = null;
		YearMonth maxSalesYearMonth = null;
		Integer total2016Sales = 0;
		Integer total2017Sales = 0;
		Integer total2018Sales = 0;
		Integer total2019Sales = 0;
		Integer currentSalesAmount = 0;
		
		for(SalesData model : TeslaSalesData) {
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
