package com.coderscampus.assignment6;

import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.coderscampus.assignment6.domain.SalesData;
import com.coderscampus.assignment6.fileservice.FileService;

public class TeslaSalesReportApplication {

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
//		System.out.println("------");
		
		List<YearMonth> modelYYMM = TeslaModel3.stream()
											   .map(yrMon -> yrMon.getDate())
											   .collect(Collectors.toList());
		modelYYMM.stream().forEach(model -> System.out.println(model));
		
	
		List<Integer> modelYear = TeslaModel3.stream()
											   .map(year -> year.getDate().getYear())
											   .distinct()
//											   .map(sales -> sales.)
											   .collect(Collectors.toList());
		modelYear.stream().forEach(model -> System.out.println(model));
		System.out.println("------");
		
		ProcessSalesData(TeslaModel3, "Model 3");
		ProcessSalesData(TeslaModelS, "Model S");
		ProcessSalesData(TeslaModelX, "Model X");
//		System.out.println(TeslaModelS.size());
//		TeslaModelS.stream()
//		   .forEach(modelS -> System.out.println(modelS));
//		System.out.println("------");
//		
//		System.out.println(TeslaModelX.size());
//		TeslaModelX.stream()
//		   .forEach(modelX -> System.out.println(modelX));
//		System.out.println("------");


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
		for(SalesData model : TeslaSalesData) {
			Integer currentSalesAmount = model.getSales();
			if(currentSalesAmount < minSalesAmount) {
				minSalesAmount = currentSalesAmount;
				minSalesYearMonth = model.getDate();
			}
			if(currentSalesAmount > maxSalesAmount) {
				maxSalesAmount = currentSalesAmount;
				maxSalesYearMonth = model.getDate();
			}
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
				default:
					System.out.println("Year model out of bounds");
					System.out.println(model);
					System.exit(999);
			} //end switch block
		} //end for-each block
		System.out.println(modelName + " Yearly Sales Report");
		System.out.println("---------------------------");
		if (total2016Sales != 0)
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
