package com.seesuint.forgis.datacenter;

import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.jdbc.JDBCDataStore;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.GeometryDescriptor;
import org.opengis.feature.type.GeometryType;
import org.opengis.geometry.Geometry;

public class DbManager {
	public Map getDbParam() {

		ConfigManager conf = new ConfigManager();
		// String Config =
		// String Path =conf.getPath();
		conf.conf(conf.getIndex(), conf.getLang());

		System.out.print(conf.getSchema());
		Map DBGISParams = new HashMap<String, Object>();

		DBGISParams.put("dbtype", conf.getDbType()); // must be postgis
		DBGISParams.put("host", conf.getHost()); // the name or ip address of the machine running PostGIS
		DBGISParams.put("port", conf.getPort()); // the port that PostGIS is running on (generally 5432)
		DBGISParams.put("database", conf.getDatabase()); // the name of the database to connect to.
		DBGISParams.put("user", conf.getId()); // the user to connect with
		DBGISParams.put("passwd", conf.getPassword()); // the password of the user.
		DBGISParams.put("schema", conf.getSchema()); // the schema of the database
		DBGISParams.put("create spatial index", conf.getIndex());
		DBGISParams.put("charset", conf.getLang());

		return DBGISParams;
	}

	public String[] LayerList() {

		String temp,tempnm,temptype = null;
		DataStore dbstore = null;
		FeatureCollection fea;
		String[] sty = null;
		String[] layername = null;
		String[] newArray =null;
		 Map map = getDbParam();
		 SimpleFeatureSource featureSource;
		 SimpleFeatureCollection fc;
		 GeometryType stype;
		// Vector<String> vector = null;
		try {
			dbstore = DataStoreFinder.getDataStore(map);

			sty = dbstore.getTypeNames();
			///temp = sty.toString();

			//layername = temp.split(",");
			int stnum =sty.length;
            String typenm = null;
            newArray = new String[stnum-1];
            String datatype =null;
            String geom =null;

			for (int i=0;i<stnum;i++) {

				 tempnm = sty[i];
		//		 System.out.println(tempnm);

               featureSource = dbstore.getFeatureSource(tempnm);

		        SimpleFeatureType schema = (SimpleFeatureType) featureSource.getSchema();
				try {


		        if (schema.getType(0) != null) {
		        	//System.out.println("GEOM");

		        	datatype= "GISData";
		        }

				} catch(Exception e) {

					datatype="no GISData";
				}

                System.out.println(datatype);

                if(datatype.equals("GISData")==true) {
                	  geom = schema.getGeometryDescriptor().getType().getBinding().getSimpleName();
                      datatype = ":" + datatype +":" +geom;

                } else {
                	   datatype=":no GISData";
                }
    	//	    String geom = schema.getGeometryDescriptor().getType().getBinding().getSimpleName();


			 // try {
			    //    geomType.getSimpleName().toString()
                 //GeometryType g = gDes.getType();
                	 newArray[i] = sty[i]+datatype;

			//  } catch(Exception e) {

				//     newArray[i] = sty[i]+" : ";

			  //}



			}

           // sty = newArray;


			//System.out.println(sty.toString());
		} catch (Exception e) {
			System.out.println("error2!!" + e.getMessage());
		}

		dbstore.dispose();
		return newArray;
	}

	public Boolean getConnect(Map map) throws Exception {

		boolean c_str = false;
		DataStore dbstore = null;
		Connection con = null;
		try {
			dbstore = DataStoreFinder.getDataStore(map);
			con = ((JDBCDataStore) dbstore).getDataSource().getConnection();
			c_str = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			c_str = false;
		} finally {
			dbstore.dispose();
			con.close();
		}
		return c_str;
	}

	public void SpatialToDB(Map map, SimpleFeatureType st, FeatureCollection fCollection, String TName) {

		DataStore dataStore1 = null;
		try {
			dataStore1 = DataStoreFinder.getDataStore(map);
			String[] sty = dataStore1.getTypeNames();

			boolean ret = useArraysBinarySearch(sty, TName);
			// System.out.println("**************:"+ret);
			if (ret == false) {
				dataStore1.createSchema(st);
				FeatureStore<SimpleFeatureType, SimpleFeature> featStore = (FeatureStore<SimpleFeatureType, SimpleFeature>) dataStore1
						.getFeatureSource(TName);
				featStore.addFeatures(fCollection);
			} else {
				dataStore1.removeSchema(TName);
				dataStore1.createSchema(st);
				FeatureStore<SimpleFeatureType, SimpleFeature> featStore = (FeatureStore<SimpleFeatureType, SimpleFeature>) dataStore1
						.getFeatureSource(TName);
				featStore.addFeatures(fCollection);
			}

		} catch (Exception e) {
			System.out.println("error2!!" + e.getMessage());
		}
		dataStore1.dispose();

	}

	public void DBToDB(Map Remotemap, SimpleFeatureType st, FeatureCollection fCollection, String TName) {

		DataStore Remotedb = null;
		Map map = getDbParam();
		try {
			Remotedb = DataStoreFinder.getDataStore(Remotemap);
			String[] sty = Remotedb.getTypeNames();

			boolean ret = useArraysBinarySearch(sty, TName);
			// System.out.println("**************:"+ret);
			if (ret == false) {
				Remotedb.createSchema(st);
				FeatureStore<SimpleFeatureType, SimpleFeature> featStore = (FeatureStore<SimpleFeatureType, SimpleFeature>) Remotedb
						.getFeatureSource(TName);
				featStore.addFeatures(fCollection);
			} else {
				Remotedb.removeSchema(TName);
				Remotedb.createSchema(st);
				FeatureStore<SimpleFeatureType, SimpleFeature> featStore = (FeatureStore<SimpleFeatureType, SimpleFeature>) Remotedb
						.getFeatureSource(TName);
				featStore.addFeatures(fCollection);
			}

		} catch (Exception e) {
			System.out.println("error2!!" + e.getMessage());
		}
		Remotedb.dispose();

	}

	public boolean useArraysBinarySearch(String[] arr, String targetValue) {

		int a = Arrays.binarySearch(arr, targetValue);

		if (a > 0)

			return true;

		else

			return false;

	}

}
