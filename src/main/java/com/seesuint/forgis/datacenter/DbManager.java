package com.seesuint.forgis.datacenter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureStore;
import org.geotools.data.mysql.MySQLDataStoreFactory;
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

		if(conf.getDbType().contentEquals("postgis")) {
		DBGISParams.put("dbtype", conf.getDbType()); // must be postgis
		DBGISParams.put("host", conf.getHost()); // the name or ip address of the machine running PostGIS
		DBGISParams.put("port", conf.getPort()); // the port that PostGIS is running on (generally 5432)
		DBGISParams.put("database", conf.getDatabase()); // the name of the database to connect to.
		DBGISParams.put("user", conf.getId()); // the user to connect with
		DBGISParams.put("passwd", conf.getPassword()); // the password of the user.
		DBGISParams.put("schema", conf.getSchema()); // the schema of the database
		DBGISParams.put("create spatial index", conf.getIndex());
		DBGISParams.put("charset", conf.getLang());
		} else {

		DBGISParams.put(MySQLDataStoreFactory.DBTYPE.key, conf.getDbType());
		DBGISParams.put(MySQLDataStoreFactory.HOST.key, conf.getHost());
		DBGISParams.put(MySQLDataStoreFactory.PORT.key, conf.getPort());
		//params.put("schema", Schema.trim());
		DBGISParams.put(MySQLDataStoreFactory.DATABASE.key, conf.getDatabase());
		DBGISParams.put(MySQLDataStoreFactory.USER.key, conf.getId());
		DBGISParams.put(MySQLDataStoreFactory.PASSWD.key, conf.getPassword());
		}
		return DBGISParams;
	}

	public String[] LayerList(Map Param) {

		String temp,geomnm,temptype = null;
		DataStore dbstore = null;
		FeatureCollection fea;
		String[] sty = null;
		String[] layername = null;
		String[] newArray =null;
		// Map map = getDbParam();
		 SimpleFeatureSource featureSource;
		 SimpleFeatureCollection fc;

		// Vector<String> vector = null;
		try {
			dbstore = DataStoreFinder.getDataStore(Param);

			sty = dbstore.getTypeNames();


			for (int i=0;i<sty.length;i++) {

	               featureSource = dbstore.getFeatureSource(sty[i]);
	              // GeometryDescriptor g = featureSource.getSchema().getGeometryDescriptor()
	                temptype= featureSource.getSchema().getGeometryDescriptor().getType().toString();
	                geomnm= temptype.replaceAll("GeometryTypeImpl ", "");
	                System.out.println(geomnm);
	                sty[i] = sty[i]+":"+geomnm;
			}


			///temp = sty.toString();
/**
			//layername = temp.split(",");
			int stnum =sty.length;
            String typenm = null;
            newArray = new String[stnum-1];
            String datatype =null;
            String geom =null;

			for (int i=0;i<stnum;i++) {

				 tempnm = sty[i];
				 System.out.println(tempnm);

               featureSource = dbstore.getFeatureSource(tempnm);

		        SimpleFeatureType schema = (SimpleFeatureType) featureSource.getSchema();

		      System.out.println("size:"+schema.getDescriptors().size());
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

			}
              **/
		} catch (Exception e) {
			System.out.println("error2!!" + e.getMessage());
		}

		dbstore.dispose();
		return sty;
	}

	public Boolean getConnect(Map map) throws IOException, SQLException  {

		boolean c_str = false;
		DataStore dbstore = null;
		Connection con = null;

		dbstore = DataStoreFinder.getDataStore(map);
		con = ((JDBCDataStore) dbstore).getDataSource().getConnection();


		try {
			dbstore = DataStoreFinder.getDataStore(map);
			con = ((JDBCDataStore) dbstore).getDataSource().getConnection();
			 System.out.println("데이터 베이스에 정상적으로 접속했습니다");
			 c_str = true;
			   con.close();
			  } catch (SQLException e) {
			   System.out.println(e.getMessage());
			   e.printStackTrace();
			  } finally {
				  dbstore.dispose();
				 con.close();

			  }

		System.out.println("get"+c_str);
		return c_str;
	}

	public void SpatialToDB(Map map, SimpleFeatureType st, FeatureCollection fCollection, String TName) {

		DataStore dataStore = null;
		try {
			dataStore = DataStoreFinder.getDataStore(map);
			String[] sty = dataStore.getTypeNames();

			boolean ret = useArraysBinarySearch(sty, TName);
			// System.out.println("**************:"+ret);
			if (ret == false) {
				dataStore.createSchema(st);
				FeatureStore<SimpleFeatureType, SimpleFeature> featStore = (FeatureStore<SimpleFeatureType, SimpleFeature>) dataStore
						.getFeatureSource(TName);
				System.out.println(fCollection.getSchema().getGeometryDescriptor().getType().toString());
				featStore.addFeatures(fCollection);
			} else {
				dataStore.removeSchema(TName);
				dataStore.createSchema(st);
				FeatureStore<SimpleFeatureType, SimpleFeature> featStore = (FeatureStore<SimpleFeatureType, SimpleFeature>) dataStore
						.getFeatureSource(TName);
				featStore.addFeatures(fCollection);
			}

		} catch (Exception e) {
			System.out.println("error2!!" + e.getMessage());
		}
		dataStore.dispose();

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
