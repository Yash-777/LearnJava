package com.github.coreconcepts;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
 
public class ImgDiffPercent
{
  public static void main(String args[])
  {
   /* BufferedImage img1 = null;
    BufferedImage img2 = null;*/
	String file1 = "D:\\img1.png";
  	String file2 = "D:\\img2.png";
  	 
  	Image img1 = Toolkit.getDefaultToolkit().getImage(file1);
  	Image img2 = Toolkit.getDefaultToolkit().getImage(file2); 
    /*try {
      URL url1 = new URL("http://rosettacode.org/mw/images/3/3c/Lenna50.jpg");
      URL url2 = new URL("http://rosettacode.org/mw/images/b/b6/Lenna100.jpg");
      img1 = ImageIO.read(url1);
      img2 = ImageIO.read(url2);
    	
    } catch (IOException e) {
      e.printStackTrace();
    }*/
    int width1 = img1.getWidth(null);
    int width2 = img2.getWidth(null);
    int height1 = img1.getHeight(null);
    int height2 = img2.getHeight(null);
    if ((width1 != width2) || (height1 != height2)) {
      System.err.println("Error: Images dimensions mismatch");
      System.exit(1);
    }
    long diff = 0;
    for (int y = 0; y < height1; y++) {
      for (int x = 0; x < width1; x++) {
        int rgb1 = ((BufferedImage) img1).getRGB(x, y);
        int rgb2 = ((BufferedImage) img2).getRGB(x, y);
        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >>  8) & 0xff;
        int b1 = (rgb1      ) & 0xff;
        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >>  8) & 0xff;
        int b2 = (rgb2      ) & 0xff;
        diff += Math.abs(r1 - r2);
        diff += Math.abs(g1 - g2);
        diff += Math.abs(b1 - b2);
      }
    }
    double n = width1 * height1 * 3;
    double p = diff / n / 255.0;
    System.out.println("diff percent: " + (p * 100.0));
  }
}