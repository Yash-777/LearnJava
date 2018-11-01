package com.github.jars;

/**
 * <UL> JNLP (Java Network Launch Protocol)
 * <LI> <B>https://github.com/Yash-777/SeleniumDriverAutomation/blob/master/DynamicJNLP_ThroughJSP.jsp</B> </LI>
 * <LI> http://docs.oracle.com/javase/9/migrate/toc.htm#JSMIG-GUID-030E50AF-5C80-4A3A-8985-211DCA4409AB </LI>
 * <LI> http://www.ngs.ac.uk/ukca/certificates/certwizard/clearwebstartcache </LI>
 * </UL>
 * 
 * http://www.oracle.com/technetwork/java/javase/9-relnotes-3622618.html
 * 
 * @author yashwanth.m
 *
 */
public class JNLP_Specification {
	public static void main(String[] args) {
		String fileName = "chromedriver.exe";
		removeCachedFile(fileName, "");
	}

	static void removeCachedFile(String fileName, String version) {
		/*try { 
			javax.jnlp.DownloadService ds = (javax.jnlp.DownloadService)javax.jnlp.ServiceManager.lookup("javax.jnlp.DownloadService");
		
			// determine if a particular resource is cached
			java.net.URL fileURL = new java.net.URI("http://java.sun.com/products/javawebstart/lib/draw.jar").toURL();
			boolean cached = ds.isResourceCached(fileURL, version); // com.sun.javaws.ui.CacheObject
			// remove the resource from the cache
			if (cached) {
				ds.removeResource(fileURL, version);
			}
			// reload the resource into the cache
			javax.jnlp.DownloadServiceListener dsl = ds.getDefaultProgressWindow();
			ds.loadResource(fileURL, version, dsl);
		} catch (javax.jnlp.UnavailableServiceException e) {
			System.err.println( e.getMessage() ); // uninitialized
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
