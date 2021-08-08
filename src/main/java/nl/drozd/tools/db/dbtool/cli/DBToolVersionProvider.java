package nl.drozd.tools.db.dbtool.cli;

import java.net.URL;
import java.util.Properties;

import picocli.CommandLine.IVersionProvider;

public class DBToolVersionProvider implements IVersionProvider {
	private final static String APP_VERSION_FILENAME = "app_version.properties";

	public String[] getVersion() throws Exception {
		URL url = getClass().getResource("/" + APP_VERSION_FILENAME);
		if (url == null) {
			return new String[] { String.format("No %s file found in the classpath.", APP_VERSION_FILENAME) };
		}
		Properties properties = new Properties();
		properties.load(url.openStream());
		
		String appName = properties.getProperty("appname","dbtool");
		String appVersion = properties.getProperty("version","0.0.1");
		String appBuildTimeStamp = properties.getProperty("buildtime","some time ago");
		
		return new String[] {
				String.format("%s version: %s \nBuild on: %s", appName, appVersion,appBuildTimeStamp)
		};
	}

}
