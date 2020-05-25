package com.seesuint.forgis.orgr;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.ogr.OGRDataStoreFactory;
import org.geotools.data.ogr.jni.JniOGRDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.opengis.feature.simple.SimpleFeature;

import com.vividsolutions.jts.geom.Geometry;

public class OgrTest02 {

	public static void main(String[] args) throws Exception {
		OGRDataStoreFactory factory = new JniOGRDataStoreFactory();
		Map<String, String> connectionParams = new HashMap<String, String>();
		connectionParams.put("DriverName", "OpenFileGDB");
		connectionParams.put("DatasourceName", new File("D:\\test_fgdb.gdb.zip").getAbsolutePath());
		System.out.println("========================");
		DataStore store = factory.createDataStore(connectionParams);
		System.out.println(store.getNames().get(0));
		System.out.println(store.getInfo());
		System.out.println(store.getTypeNames());
		System.out.println("========================");
        /**
		SimpleFeatureSource source = store.getFeatureSource("entities");
		System.out.println(source);

		SimpleFeatureIterator it = source.getFeatures().features();
		try {
			while (it.hasNext()) {
				SimpleFeature feature = it.next();
				System.out.println(((Geometry) feature.getDefaultGeometry()).getCentroid());
			}
		} finally {
			it.close();
		}
		**/
	}
}
