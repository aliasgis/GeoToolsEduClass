package com.seesuint.forgis.datacenter;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.FeatureWriter;
import org.geotools.data.FileDataStoreFactorySpi;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.Transaction;
import org.geotools.data.mysql.MySQLDataStoreFactory;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.data.store.ContentFeatureSource;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.util.URLs;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

public class ShpManager {

	public ContentFeatureSource getShpSource(String Path, String Lang) throws IOException {
		ShapefileDataStore dataStore;
		ContentFeatureSource fc = null;
		try {
			File f = new File(Path);
			dataStore = new ShapefileDataStore(f.toURL());
			dataStore.setCharset(Charset.forName(Lang));
			fc = dataStore.getFeatureSource();

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

	public void DbToShp(String tblname,File directory) throws IOException {
		DbManager db=new DbManager();
		 Map map = db.getDbParam();
		// SimpleFeatureSource featureSource;
		 SimpleFeatureType stype;
		// Vector<String> vector = null;
		 DataStore dbstore;
         SimpleFeature feature;
		dbstore = DataStoreFinder.getDataStore(map);

		//SimpleFeatureSource featureSource = dbstore.getFeatureSource(tblname);
		//MySQLDataStoreFactory.DATABASE.key.
		SimpleFeatureSource featureSource = dbstore.getFeatureSource("cities");

        SimpleFeatureType ft = featureSource.getSchema();

        String fileName = ft.getTypeName();
        File file = new File(directory,tblname+".shp");

        Map<String, java.io.Serializable> creationParams = new HashMap<String, Serializable>();
        creationParams.put("url", URLs.fileToUrl(file));

        FileDataStoreFactorySpi factory = FileDataStoreFinder.getDataStoreFactory("shp");
        DataStore dataStore = factory.createNewDataStore(creationParams);

        dataStore.createSchema(ft);

        // The following workaround to write out the prj is no longer needed
        // ((ShapefileDataStore)dataStore).forceSchemaCRS(ft.getCoordinateReferenceSystem());

        SimpleFeatureStore featureStore = (SimpleFeatureStore) dataStore.getFeatureSource(tblname);

        Transaction t = new DefaultTransaction();
        try {
            SimpleFeatureCollection collection = featureSource.getFeatures(); // grab all features
            //collection.getSchema().getGeometryD
            featureStore.addFeatures(collection);
            t.commit(); // write it out
        } catch (IOException eek) {
            eek.printStackTrace();
            try {
                t.rollback();
            } catch (IOException doubleEeek) {
                // rollback failed?
            }
        } finally {
            t.close();
            dbstore.dispose();
            dataStore =null;
        }

       // return dataStore;
    //}
	}
}
