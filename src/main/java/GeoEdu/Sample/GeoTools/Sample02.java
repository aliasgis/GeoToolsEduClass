package GeoEdu.Sample.GeoTools;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.geotools.data.store.ContentFeatureCollection;
import org.geotools.data.store.ContentFeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.CRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.ReferenceIdentifier;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

public class Sample02 {

	public static void main(String[] args) throws FactoryException {
		String shapeFileLoc = "E:\\07.강서구\\DB\\welfare\\어린이집.shp"; // shapefile 위치
		String postGISTblName = "Data_Sample101";// 생성테이블명
		String shapeEPSG = "EPSG:4326";// epsg
		String init = null;

	//	Set<String> epsgCRSSet = CRS.getSupportedCodes("EPSG");
	//	Iterator<String> epsgIterator = epsgCRSSet.iterator();
/**
		while (epsgIterator.hasNext()) {
			String code = epsgIterator.next();
			System.out.println(code);
		}
		**/

		try {
			File shpFile = new File(shapeFileLoc);
			ConfigManager conf = new ConfigManager();

			conf.conf(true, "UTF-8");

			DbManager db = new DbManager();
			ShpManager shp = new ShpManager();

			ContentFeatureSource featureSource = shp.getShpSource(shapeFileLoc, conf.getLang());
			ContentFeatureCollection featureCollection = featureSource.getFeatures();

			SimpleFeatureType schema = featureSource.getSchema();
			CoordinateReferenceSystem sourceCRS = schema.getCoordinateReferenceSystem();
			CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:4326");
			MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS);
			FeatureCollection<SimpleFeatureType, SimpleFeature> featSrcCollection = featureSource.getFeatures();
			SimpleFeatureType newSchema = shp.SetFeatureType(postGISTblName, schema);

			CoordinateReferenceSystem crs = schema.getCoordinateReferenceSystem();

			// test of the CRS based on the .prj file
			Integer crsCode = CRS.lookupEpsgCode(crs, true);

			Set<ReferenceIdentifier> refIds = schema.getCoordinateReferenceSystem().getIdentifiers();
			if (((refIds == null) || (refIds.isEmpty())) && (crsCode == null)) {
				CoordinateReferenceSystem crsEpsg = CRS.decode(shapeEPSG);
				newSchema = SimpleFeatureTypeBuilder.retype(newSchema, crsEpsg);
			}

			Map GISParam = db.getDbParam();
			db.SpatialToDB(GISParam, newSchema, featSrcCollection, postGISTblName);

			System.out.println("Perfect !!!!!");
		} catch (IOException e) {
			System.out.println("ERROR:" + e);
		}
	}



}
