package com.seesuint.forgis.orgr;

import java.io.IOException;

import org.gdal.ogr.DataSource;
import org.gdal.ogr.Driver;
import org.gdal.ogr.Layer;
import org.gdal.ogr.ogr;

public class OgrTest03 {
	public static void main(String[] args) throws IOException {

		ogr.RegisterAll();

		System.out.println(ogr.Open("c:\\\\dxf\\\\RDL_TREE_PS.dxf"));

		DataSource ds = ogr.Open("c:\\dxf\\RDL_TREE_PS.dxf");
		Driver drv = ds.GetDriver();
		System.out.println(drv.getName());

		System.out.println("	layer 갯수:" + ds.GetLayerCount());

		Driver Targetdrv = ogr.GetDriverByName("GML");
		String trgPath = "c:\\dxf\\RDL_TREE_PS.gml"; // export geojson destination path
		DataSource trgDataset = Targetdrv.CreateDataSource(trgPath, null);

		Layer Olayer = ds.GetLayerByIndex(0);

		trgDataset.CopyLayer(Olayer, Olayer.GetName(), null);

		System.out.println("	layer :" + Olayer.GetName());

		// trgDataset.ReleaseResultSet(Olayer);
	}

}
