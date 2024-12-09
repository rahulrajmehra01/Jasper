package com.rclgroup.dolphin.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateHelper {
	
	public static String convertDateFormat(String inputDate) {

		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		try {
			LocalDate date = LocalDate.parse(inputDate, inputFormatter);
			System.out.println("----" + date.format(outputFormatter));
			return date.format(outputFormatter);
		} catch (DateTimeParseException e) {
			System.err.println("Invalid date format: " + e.getMessage());
			return null;
		}
	}

}
