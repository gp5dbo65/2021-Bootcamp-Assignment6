package com.coderscampus.assignment6.fileservice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.coderscampus.assignment6.domain.SalesData;

public class FileService {
	
	public List<SalesData> loadSalesDataList(String fileName) throws IOException {
		List<SalesData> tempSalesData = new ArrayList<>(50);
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(fileName));
			// Read and ignore the first line of the file "Date,Sales"
			String line = reader.readLine();
			// Read and separate data until end of file
			while ((line = reader.readLine()) != null) {
				String[] inputString = line.split(",");
				tempSalesData.add(new SalesData(inputString[0], inputString[1]));
				
			} //end of while loop
			return tempSalesData;
			
		} finally {
			if (reader != null) {
				reader.close();
			}
		} //end finally block
	} //end of loadSalesDataList
} //end FileService class
