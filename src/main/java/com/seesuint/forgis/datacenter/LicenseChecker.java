package com.seesuint.forgis.datacenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LicenseChecker {

	boolean ret=false;
	String yyyy = null;
	String mm = null;
	String dd = null;
	String keydate = null;
	int lt =0;
	int decs=0;

	public String getLicenseTitle(String[] arr) {

	    String Til=null;
		if(arr[1].equals("D")) {
			Til="Demo Version";
		} else {
			Til ="";
		}


		return Til;
	}

	public Boolean init(String[] arr) {


		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	        Calendar c1 = Calendar.getInstance();

		 String strToday = sdf.format(c1.getTime());
         int tonum = Integer.parseInt(strToday);
		if(arr[1].equals("D")) {

		 yyyy=arr[4].substring(0, 4);
		 mm = arr[4].substring(5, 7);
		 dd = arr[4].substring(8,10);


		 keydate = yyyy+mm+dd;

		 lt =Integer.parseInt(keydate);

		 decs = lt - tonum;

		  if(decs<0) {
	        	ret = true;
		  } else {
	           ret = false;
		  }


		} else {
			ret = false;
		}

  		return ret;
	}
}
