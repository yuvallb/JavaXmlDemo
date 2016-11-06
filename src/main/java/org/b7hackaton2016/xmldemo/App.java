package org.b7hackaton2016.xmldemo;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

/**
 * this demo app will read xml data of population per city in Israel, and
 * xml data of cellular antennas in Israel. It will them calculate antennas per
 * capita, and show top 5 cities
 */
public class App {

	// https://data.gov.il/dataset/995eb826-c471-4572-8fd3-39d92a3a9603/resource/7bfe75ce-3042-4571-98cc-ea3e5d44df86/download/antennaactive.xml
	final private static String atennasFile = "antennaactive.xml";

	// https://data.gov.il/dataset/00172ee8-4433-4d5d-8fd4-11f0f08ce5c4/resource/9ba87444-aec5-4f46-89a5-a690a32668e2/download/mnaniuliisrishuvgilteur01112016.xml";
	// the file was converted from windows-1255 to utf-8
	final private static String citiesFile = "citiesUtf8.xml";

	public static void main(String[] args) throws IOException {

		// read antennas from resource xml file
		StringWriter writer = new StringWriter();
		InputStream antennasStream = App.class.getClassLoader().getResourceAsStream(atennasFile);
		IOUtils.copy(antennasStream, writer, "UTF-8");

		String atennasAllXml = writer.toString();

		// store in hashmap - city is our primary key
		HashMap<String, Integer> antennasPerCity = new HashMap<String, Integer>();

		// regex match XML to find city name (6th Value tag in each Record tag)
		Matcher m = Pattern.compile(
				"<Record\\sxsi:type=.*?<Value\\s.*?</Value><Value\\s.*?</Value><Value\\s.*?</Value><Value\\s.*?</Value><Value\\s.*?</Value><Value\\s.*?>(.*?)</Value>",
				Pattern.DOTALL).matcher(atennasAllXml);
		while (m.find()) {
			String city = m.group(1);
			Integer currentValue = antennasPerCity.getOrDefault(city, 0) + 1;
			antennasPerCity.put(city, currentValue);
		}

		// read population per city
		writer = new StringWriter();
		InputStream citiesStream = App.class.getClassLoader().getResourceAsStream(citiesFile);

		IOUtils.copy(citiesStream, writer, "UTF-8");
		String citiesAllXml = writer.toString();

		// the key is antennas divided to population, value is city name
		TreeMap<Float, String> antennasPerCapita = new TreeMap<Float, String>();

		// regex match XML to find city name and population (6th Value tag in
		// each Record tag)
		final String CityName = "שםישוב";
		final String Total = "סהכ";
		m = Pattern
				.compile("<" + CityName + ">\\s*(.*?)\\s*</" + CityName + ">.*?<" + Total + ">\\s*(.*?)\\s*</" + Total,
						Pattern.DOTALL)
				.matcher(citiesAllXml);
		while (m.find()) {
			String city = m.group(1);
			if (antennasPerCity.containsKey(city)) {
				String populationStr = m.group(2);
				Float population = Float.parseFloat(populationStr) / 1000;

				antennasPerCapita.put(antennasPerCity.get(city) / population, city);
			}
		}

		// read top 5 cities
		System.out.println("Top 5 cities: number of cellular antennas per 1000 persons: ");
		int count = 0;
		for (Map.Entry<Float, String> entry : antennasPerCapita.descendingMap().entrySet()) {
			if (count >= 5)
				break;
			System.out.println(entry.getValue() + " has " + entry.getKey() + " antennas per 1000 persons");
			count++;
		}
		System.out.println("");

		// read last 5 cities
		System.out.println("Last 5 cities: number of cellular antennas per 1000 persons: ");
		count = 0;
		for (Map.Entry<Float, String> entry : antennasPerCapita.entrySet()) {
			if (count >= 5)
				break;
			System.out.println(entry.getValue() + " has " + entry.getKey() + " antennas per 1000 persons");
			count++;
		}

	}
}
