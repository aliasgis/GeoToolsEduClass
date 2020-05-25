package com.seesuint.forgis.orgr;

import java.io.IOException;

import org.gdal.ogr.DataSource;
import org.gdal.ogr.Driver;
import org.gdal.ogr.Layer;
import org.gdal.ogr.ogr;

public class OgrTest03 {
	public static void main(String[] args) throws IOException {

		ogr.RegisterAll();
		DataSource ds = ogr.Open("c:\\dxf\\export.dxf");
		Driver drv = ds.GetDriver();
		System.out.println(drv.getName());

		System.out.println("	layer 갯수:" + ds.GetLayerCount());

		Driver Targetdrv = ogr.GetDriverByName("ESRI shapefile");
		String trgPath = "c:\\dxf\\export.shp"; // export geojson destination path
		DataSource trgDataset = Targetdrv.CreateDataSource(trgPath, null);

		Layer Olayer = ds.GetLayerByIndex(0);
		trgDataset.CopyLayer(Olayer, Olayer.GetName(), null);

		System.out.println("	layer :" + Olayer.GetName());

		// trgDataset.ReleaseResultSet(Olayer);
	}

}
