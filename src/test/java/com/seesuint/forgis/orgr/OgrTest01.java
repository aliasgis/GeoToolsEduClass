package com.seesuint.forgis.orgr;

import java.io.IOException;
import java.util.Set;

import org.geotools.data.ogr.OGRDataStoreFactory;
import org.geotools.data.ogr.jni.JniOGRDataStoreFactory;

public class OgrTest01 {
	public static void main(String[] args) throws IOException {
		OGRDataStoreFactory factory = new JniOGRDataStoreFactory();
		Set<String> drivers = factory.getAvailableDrivers();
		for (String driver : drivers) {
			System.out.println(driver);
		}
	}
}
