package com.seesuint.forgis.datacenter;

import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import org.geotools.referencing.CRS;

public class CoordinateManager {

	Vector<String> CoordinateList;

	public Vector<String> getCoordinateList() {
		ConfigManager conf = new ConfigManager();
		conf.conf(true, "UTF-8");

		String clist = conf.getCoordsys();

		String[] coordsys = clist.split(",");

		CoordinateList = new Vector<String>();
		boolean ret;
		Set<String> epsgCRSSet = CRS.getSupportedCodes("EPSG");
		Iterator<String> epsgIterator = epsgCRSSet.iterator();

		while (epsgIterator.hasNext()) {
			String code = epsgIterator.next();
			if (!"WGS84(DD)".equals(code) && !"EPSG:TEST1".equals(code) && !"EPSG:TEST2".equals(code)) {
				code = qualifyCRS("EPSG:", code);
			}
			ret = clist.contains(code);
			// System.out.println("code:"+ret);
			if (ret == true) {
				CoordinateList.add(code);
			}
			// CoordinateList.add(code);
		}

		return CoordinateList;
	}

	private boolean getUserCoordsys(String code) {
		boolean ret = false;
		ConfigManager conf = new ConfigManager();
		conf.conf(true, "UTF-8");

		String clist = conf.getCoordsys();
		String[] coordsys = clist.split(",");
		ret = clist.contains(code);
		System.out.println("code:" + ret);
		return ret;
	}

	public void setCoordinateList(Vector<String> coordinateList) {
		CoordinateList = coordinateList;
	}

	private String qualifyCRS(String prefix, String srs) {
		if (srs.indexOf(':') == -1) {

			srs = prefix + srs;

		}
		return srs;
	}

}
