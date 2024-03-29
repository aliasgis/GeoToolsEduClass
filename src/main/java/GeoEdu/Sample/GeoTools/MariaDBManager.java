package GeoEdu.Sample.GeoTools;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureWriter;
import org.geotools.data.Transaction;
import org.geotools.data.mysql.MySQLDataStoreFactory;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.store.ContentEntry;
import org.geotools.feature.NameImpl;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.jdbc.JDBCDataStore;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;

public class MariaDBManager {

	public void ShpToMaria(String host,String dataBase,int port,String userName,String pwd,String filepath,String encoding) {
		   JDBCDataStore connnection2mysql = connnection2mysql(host, dataBase, port, userName, pwd);
	        SimpleFeatureSource featureSource = readSHP(filepath, encoding);
	        JDBCDataStore ds = createTable(connnection2mysql, featureSource);
	        writeShp2Mysql(ds, featureSource);
	}

	  public  SimpleFeatureSource readSHP( String shpfile,String encode){
          SimpleFeatureSource featureSource =null;
          try {
              File file = new File(shpfile);
              ShapefileDataStore shpDataStore = null;

              shpDataStore = new ShapefileDataStore(file.toURL());

           Charset charset = Charset.forName(encode);
           shpDataStore.setCharset(charset);

             String tableName = shpDataStore.getTypeNames()[0];
              featureSource =  shpDataStore.getFeatureSource (tableName);
        }catch (Exception e){
           e.printStackTrace();
         }
         return featureSource;
              }
 public  JDBCDataStore  connnection2mysql(String host,String dataBase,int port,String userName,String pwd ){
          JDBCDataStore ds=null;
         DataStore dataStore=null;

          java.util.Map params = new java.util.HashMap();
          params.put(MySQLDataStoreFactory.DBTYPE.key, "mysql");
          params.put(MySQLDataStoreFactory.HOST.key, host);
          params.put(MySQLDataStoreFactory.PORT.key, port);
          params.put(MySQLDataStoreFactory.DATABASE.key, dataBase);
          params.put(MySQLDataStoreFactory.USER.key, userName);
          params.put(MySQLDataStoreFactory.PASSWD.key, pwd);
        
          try {
             dataStore=DataStoreFinder.getDataStore(params);
             String[] sty = dataStore.getTypeNames();
             if (dataStore!=null) {
                 ds=(JDBCDataStore)dataStore;
                 System.out.println(dataBase+"완료");
                              }else{

                 System.out.println(dataBase+"없다.");
             }

         } catch (IOException e) {
             // TODO Auto-generated catch block

             e.printStackTrace();

         }

       return ds;
     }

public void writeShp2Mysql(JDBCDataStore ds, SimpleFeatureSource featureSource ){
          SimpleFeatureType schema = featureSource.getSchema();

          try {
             FeatureWriter<SimpleFeatureType, SimpleFeature> writer = ds .getFeatureWriter(schema.getTypeName().toLowerCase(), Transaction.AUTO_COMMIT);
              SimpleFeatureCollection featureCollection = featureSource.getFeatures();
              SimpleFeatureIterator features = featureCollection.features();
              while (features.hasNext()) {
                  writer.hasNext();
                 SimpleFeature next = writer.next();
                 SimpleFeature feature = features.next();
                 for (int i = 0; i < feature.getAttributeCount(); i++) {
                     next.setAttribute(i,feature.getAttribute(i) );

                 }
                 writer.write();
             }
             writer.close();
             ds.dispose();
          //   System.out.println("성공이다");
         } catch (IOException e) {
           // TODO Auto-generated catch block
             e.printStackTrace();
         }


     }
    public JDBCDataStore createTable(JDBCDataStore ds, SimpleFeatureSource featureSource){
         SimpleFeatureType schema = featureSource.getSchema();
         DbManager db = new DbManager();
         Boolean ret;
       //  String[] sty = ds.getTypeNames();
           try {
			String[] Names =  ds.getTypeNames();
			ret = db.useArraysBinarySearch(Names, featureSource.getName().toString());

			if (ret == false) {
				ds.createSchema(schema);
			} else {
				ds.removeSchema(featureSource.getName().toString());
				ds.createSchema(schema);
			//	ds.
			}


		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

         return ds;
              }
}
