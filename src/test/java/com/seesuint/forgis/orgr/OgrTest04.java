package com.seesuint.forgis.orgr;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

import org.gdal.gdal.Dataset;
import org.gdal.gdal.Driver;
import org.gdal.gdal.gdal;
import org.gdal.osr.SpatialReference;
import org.geotools.data.ogr.OGRDataStoreFactory;
import org.geotools.data.ogr.jni.JniOGRDataStoreFactory;

public class OgrTest04 {
	public static void main(String[] args) throws IOException {
		Vector papszFileList;
		double[] adfGeoTransform = new double[6];
		Driver drv;
		gdal.AllRegister();
		
	
		Dataset ds = gdal.Open("e:\\data\\dem.tif");
		System.out.println(ds.GetDriver());
		System.out.println(ds.GetDescription());
		System.out.println("Count:" + ds.GetRasterCount());
		System.out.println("Projection:" + ds.GetProjection());
		System.out.println("Gcp:" + ds.GetGCPCount());
		
		drv = ds.GetDriver();
		System.out.println("Driver: " + drv.getShortName() + "/" + drv.getLongName());
		System.out.println("Size is " + ds.getRasterXSize() + ", " + ds.getRasterYSize());
		papszFileList = ds.GetFileList();
			SpatialReference ref;
		String pszProjection;
		ds.GetGeoTransform(adfGeoTransform);
		pszProjection = ds.GetProjectionRef();
		ref = new SpatialReference(pszProjection);
		String[] pszPrettyWkt = new String[1];
		ref.ExportToPrettyWkt(pszPrettyWkt, 0);
		System.out.println(pszPrettyWkt[0]);
		System.out.println("Origin = (" + adfGeoTransform[0] + "," + adfGeoTransform[3] + ")");
		System.out.println("Origin2 = (" + adfGeoTransform[1] + "," + adfGeoTransform[2] + ")");
		System.out.println("Origin3 = (" + adfGeoTransform[4] + "," + adfGeoTransform[5] + ")");
	}

}
