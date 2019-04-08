package com.seesuint.forgis.datacenter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.store.ContentFeatureSource;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.opengis.feature.simple.SimpleFeatureType;

public class SpatialRDBManager {
	public ContentFeatureSource getdbSource(Map Orimap, String Lang, String typeNM) throws IOException {

		DataStore dataStore = DataStoreFinder.getDataStore(Orimap);
		SimpleFeatureType type1 = dataStore.getSchema(typeNM);
		ContentFeatureSource fc = null;
		try {
			// File f = new File(Path);

			fc = (ContentFeatureSource) dataStore.getFeatureSource(typeNM);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fc;
	}

	public SimpleFeatureType SetFeatureType(String TblName, SimpleFeatureType schema) {
		SimpleFeatureType Type;
		SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
		builder.setName(TblName);
		builder.setAttributes(schema.getAttributeDescriptors());
		builder.setCRS(schema.getCoordinateReferenceSystem());
		SimpleFeatureType newSchema = builder.buildFeatureType();

		return newSchema;
	}
}
