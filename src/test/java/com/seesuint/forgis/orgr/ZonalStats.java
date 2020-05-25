package com.seesuint.forgis.orgr;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.media.jai.PlanarImage;
import javax.media.jai.iterator.RectIter;
import javax.media.jai.iterator.RectIterFactory;
import org.geotools.coverage.processing.Operations;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridEnvelope2D;
import org.geotools.coverage.grid.io.AbstractGridCoverage2DReader;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridFormatFinder;

public class ZonalStats {
	public static void main(String[] args) {
	   // String gridFile = "D:/park/bysan/quality_c.tif";
		String gridFile = "D:/seoul_raster/landsat.tif";
	    try {
	        AbstractGridFormat format = GridFormatFinder.findFormat(gridFile);
	        AbstractGridCoverage2DReader reader = format.getReader(gridFile);

	        GridCoverage2D inputGc = reader.read(null);
	        int bands = reader.getGridCoverageCount();
	        System.out.println("bands count:"+bands);
	        if(bands==1) {
	        calculateStatistics1(inputGc);
	        calculateStatistics2(inputGc);
	        } else {
	        calculateMulti( inputGc) ;
	        }
	    } catch (IllegalArgumentException e) {
	       // LOGGER.log(Level.FINER, e.getMessage(), e);
	    } catch (IOException e) {
	     //   LOGGER.log(Level.FINER, e.getMessage(), e);
	    }
	}
	static void calculateMulti(GridCoverage2D inputGc) {
	    int    count = 0;
	    double minVal = Double.MAX_VALUE;
	    double maxVal = Double.MIN_VALUE;
	    double sumOfVals = 0.0;
	    double sumOfSqrs = 0.0;

	    double noDataValue = 0.0;

	    PlanarImage renderedImage = (PlanarImage) inputGc.getRenderedImage();
	    java.awt.Rectangle bounds = renderedImage.getBounds();


	}
	static void calculateStatistics1(GridCoverage2D inputGc) {
	    int    count = 0;
	    double minVal = Double.MAX_VALUE;
	    double maxVal = Double.MIN_VALUE;
	    double sumOfVals = 0.0;
	    double sumOfSqrs = 0.0;

	    double noDataValue = 0.0;

	    PlanarImage renderedImage = (PlanarImage) inputGc.getRenderedImage();
	    java.awt.Rectangle bounds = renderedImage.getBounds();

	    RectIter rectIter = RectIterFactory.create(renderedImage, bounds);
	    rectIter.startLines();
	    while (!rectIter.finishedLines()) {
	        rectIter.startPixels();
	        while (!rectIter.finishedPixels()) {
	            double sampleVal = rectIter.getSampleDouble(0);

	            if (Double.compare(sampleVal, noDataValue) != 0) {
	                count++;
	                sumOfVals += sampleVal;
	                minVal = Math.min(minVal, sampleVal);
	                maxVal = Math.max(maxVal, sampleVal);
	                sumOfSqrs += Math.pow(sampleVal, 2);
	            }

	            rectIter.nextPixel();
	        }
	        rectIter.nextLine();
	    }

	    // Print output
	    final DecimalFormat DF = new DecimalFormat("#.###############");
	    System.out.println("Count: " + count);
	    System.out.println("NoData: " + DF.format(noDataValue));
	    System.out.println("Minimum: " + DF.format(minVal));
	    System.out.println("Maximum: " + DF.format(maxVal));
	    System.out.println("Sum: " + DF.format(sumOfVals));
	    System.out.println("Mean: " + DF.format(sumOfVals / count));

	    // Population Standard Deviation
	    double variance = (sumOfSqrs - Math.pow(sumOfVals, 2) / count) / count;
	    System.out.println("Variance: " + DF.format(variance));
	    System.out.println("Standard Deviation: " + DF.format(Math.sqrt(variance)));
	}
	static void calculateStatistics2(GridCoverage2D inputGc) {
	    int    count = 0;
	    double minVal = Double.MAX_VALUE;
	    double maxVal = Double.MIN_VALUE;
	    double sumOfVals = 0.0;
	    double sumOfSqrs = 0.0;

	  //  double noDataValue = (Double) inputGc.getProperty("GC_NODATA");
	    double noDataValue =0.0;
	    GridEnvelope2D ge2D = inputGc.getGridGeometry().getGridRange2D();
	    RenderedImage renderedImage = inputGc.getRenderedImage();
//          renderedImage.
	    for (int tileX = 0; tileX < renderedImage.getNumXTiles(); tileX++) {
	        for (int tileY = 0; tileY < renderedImage.getNumYTiles(); tileY++) {
	            Raster tileRs = renderedImage.getTile(tileX, tileY);

	            java.awt.Rectangle bounds = tileRs.getBounds();
	            for (int dy = bounds.y, drow = 0; drow < bounds.height; dy++, drow++) {
	                for (int dx = bounds.x, dcol = 0; dcol < bounds.width; dx++, dcol++) {
	                    if (ge2D.contains(dx, dy)) {
	                        double sampleVal = tileRs.getSampleDouble(dx, dy, 0);

	                        if (Double.compare(sampleVal, noDataValue) != 0) {
	                            count++;
	                            sumOfVals += sampleVal;
	                            minVal = Math.min(minVal, sampleVal);
	                            maxVal = Math.max(maxVal, sampleVal);
	                            sumOfSqrs += Math.pow(sampleVal, 2);
	                        }
	                    }
	                }
	            }
	        }
	    }

	    // Print output
	    final DecimalFormat DF = new DecimalFormat("#.###############");
	    System.out.println("Count: " + count);
	    System.out.println("NoData: " + DF.format(noDataValue));
	    System.out.println("Minimum: " + DF.format(minVal));
	    System.out.println("Maximum: " + DF.format(maxVal));
	    System.out.println("Sum: " + DF.format(sumOfVals));
	    System.out.println("Mean: " + DF.format(sumOfVals / count));

	    // Population Standard Deviation
	    double variance = (sumOfSqrs - Math.pow(sumOfVals, 2) / count) / count;
	    System.out.println("Variance: " + DF.format(variance));
	    System.out.println("Standard Deviation: " + DF.format(Math.sqrt(variance)));
	}
}
