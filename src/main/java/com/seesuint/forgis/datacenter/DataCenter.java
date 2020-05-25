package com.seesuint.forgis.datacenter;

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

public class DataCenter {

	public static void main(String[] args) throws FactoryException {
		String shapeFileLoc = "E:\\07.강서구\\DB\\welfare\\어린이집.shp"; // shapefile 위치
		String postGISTblName = "DataCenter_Sample803";// 생성테이블명
		String shapeEPSG = "EPSG:4326";// epsg
		String init = null;

		Set<String> epsgCRSSet = CRS.getSupportedCodes("EPSG");
		Iterator<String> epsgIterator = epsgCRSSet.iterator();

		while (epsgIterator.hasNext()) {
			String code = epsgIterator.next();
			System.out.println(code);
		}

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

	public void DbProc(Map map, String Path, String TblName, Boolean ret, String Lang, String Coord) {
		// String shapeFileLoc = "D:\\01.시선\\forgis데이터\\eastseabig.shp"; // shapefile 위치

		String init = null;
		String shapeEPSG = Coord;// epsg

		try {
			ConfigManager conf = new ConfigManager();

			conf.conf(ret, Lang);

			DbManager db = new DbManager();
			ShpManager shp = new ShpManager();

			ContentFeatureSource featureSource = shp.getShpSource(Path, Lang);
			ContentFeatureCollection featureCollection = featureSource.getFeatures();

			// System.out.println(featureSource.);

			SimpleFeatureType schema = featureSource.getSchema();

			CoordinateReferenceSystem sourceCRS = schema.getCoordinateReferenceSystem();
			// System.out.println(sourceCRS);
			// CoordinateReferenceSystem sourceCRS = CRS.decode(Coord);

			// CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:4326");
			// MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS);
			FeatureCollection<SimpleFeatureType, SimpleFeature> featSrcCollection = featureSource.getFeatures();
			SimpleFeatureType newSchema = shp.SetFeatureType(TblName, schema);

			CoordinateReferenceSystem crs = schema.getCoordinateReferenceSystem();
			System.out.println(crs);
			// test of the CRS based on the .prj file
			// Integer crsCode = CRS.lookupEpsgCode(crs, true);
			Integer crsCode = 900913;
			// Set<ReferenceIdentifier> refIds =
			// schema.getCoordinateReferenceSystem().getIdentifiers();
			// if (((refIds == null) || (refIds.isEmpty())) && (crsCode == null)) {
			System.out.println("qqqq");

			CoordinateReferenceSystem crsEpsg = CRS.decode(shapeEPSG);
			// newSchema = SimpleFeatureTypeBuilder.retype(newSchema, crsEpsg);
			// System.out.println(crsEpsg);
			newSchema = SimpleFeatureTypeBuilder.retype(newSchema, crsEpsg);
			// }

			Map GISParam = map;
			db.SpatialToDB(GISParam, newSchema, featSrcCollection, TblName);

			// System.out.println("Perfect !!!!!"+TblName);
		} catch (IOException e) {
			System.out.println("ERROR:" + e);
		} catch (NoSuchAuthorityCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * public void RemoteDbProc (Map OriginDB,Map RemoteDB,String TblName, Boolean
	 * ret, String Lang,String Coord) { //String shapeFileLoc =
	 * "D:\\01.시선\\forgis데이터\\eastseabig.shp"; // shapefile 위치
	 *
	 * String init = null; String shapeEPSG = Coord;// epsg
	 *
	 * try { // File shpFile = new File(shppath); ConfigManager conf = new
	 * ConfigManager();
	 *
	 * conf.conf(ret,Lang);
	 *
	 * DbManager db = new DbManager(); ShpManager shp = new ShpManager();
	 *
	 * ContentFeatureSource featureSource =
	 * shp.getShpSource(shppath,conf.getLang()); ContentFeatureCollection
	 * featureCollection = featureSource.getFeatures();
	 *
	 * //System.out.println(featureSource.);
	 *
	 *
	 *
	 * SimpleFeatureType schema = featureSource.getSchema();
	 *
	 *
	 *
	 * // CoordinateReferenceSystem sourceCRS =
	 * schema.getCoordinateReferenceSystem(); //System.out.println(sourceCRS);
	 * CoordinateReferenceSystem sourceCRS = CRS.decode(Coord);
	 *
	 * //CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:4326");
	 * //MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS);
	 * FeatureCollection<SimpleFeatureType, SimpleFeature> featSrcCollection =
	 * featureSource.getFeatures(); SimpleFeatureType newSchema =
	 * shp.SetFeatureType(TblName, schema);
	 *
	 * CoordinateReferenceSystem crs = schema.getCoordinateReferenceSystem();
	 * System.out.println(crs); // test of the CRS based on the .prj file // Integer
	 * crsCode = CRS.lookupEpsgCode(crs, true); Integer crsCode= 900913; //
	 * Set<ReferenceIdentifier> refIds =
	 * schema.getCoordinateReferenceSystem().getIdentifiers(); // if (((refIds ==
	 * null) || (refIds.isEmpty())) && (crsCode == null)) {
	 * System.out.println("qqqq");
	 *
	 * CoordinateReferenceSystem crsEpsg = CRS.decode(shapeEPSG); //newSchema =
	 * SimpleFeatureTypeBuilder.retype(newSchema, crsEpsg); //
	 * System.out.println(crsEpsg); newSchema =
	 * SimpleFeatureTypeBuilder.retype(newSchema, crsEpsg); //}
	 *
	 * Map GISParam = db.getDbParam(); db.SpatialToDB(GISParam, newSchema,
	 * featSrcCollection, TblName);
	 *
	 * System.out.println("Perfect !!!!!"+TblName); } catch (IOException e) {
	 * System.out.println("ERROR:" + e); } catch (NoSuchAuthorityCodeException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); } catch
	 * (FactoryException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 **/
}
