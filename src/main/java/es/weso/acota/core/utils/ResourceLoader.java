package es.weso.acota.core.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.configuration.PropertiesConfiguration;

import es.weso.acota.core.utils.ExternalizableConfiguration;

/**
 * @author César Luis Alvargonzález
 * 
 */
public class ResourceLoader implements ExternalizableConfiguration {

	protected static final int TEMP_DIR_ATTEMPTS = 10000;

	protected PropertiesConfiguration configuration;

	/**
	 * Zero-Argument Default Constructor
	 */
	public ResourceLoader() {
		this.configuration = new PropertiesConfiguration();
		try {
			configuration.addProperty("opennlp.es.pos",
					loadTmpFile("open_nlp/es/", "SpanishPOS.bin"));
			configuration.addProperty("opennlp.es.sent",
					loadTmpFile("open_nlp/es/", "SpanishSent.bin"));
			configuration.addProperty("opennlp.es.tok",
					loadTmpFile("open_nlp/es/", "SpanishTok.bin"));
			configuration.addProperty("opennlp.en.pos",
					loadTmpFile("open_nlp/en/", "EnglishPOS.bin"));
			configuration.addProperty("opennlp.en.sent",
					loadTmpFile("open_nlp/en/", "EnglishSent.bin"));
			configuration.addProperty("opennlp.en.tok",
					loadTmpFile("open_nlp/en/", "EnglishTok.bin"));
			configuration.addProperty("wordnet.en.dict",
					loadTmpDirectory("wordnet_30/en/", "dict/"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see ExternalizableConfiguration#getConfiguration()
	 */
	@Override
	public PropertiesConfiguration getConfiguration() {
		return configuration;
	}

	private String loadTmpFile(String path, String name) throws IOException {
		File tmpDir = createTempDir();
		tmpDir.deleteOnExit();
		
		String tmpPath = tmpDir.getPath();
		return createFile(path, tmpPath, name);
		
	}

	private String loadTmpDirectory(String path, String name)
			throws IOException {
		File tmpDir = createTempDir();
		tmpDir.deleteOnExit();
		
		String tmpPath = tmpDir.getPath();

		Properties prop = new Properties();
		prop.load(this.getClass().getClassLoader()
				.getResourceAsStream("inner.acota.utils.properties"));

		String[] files = ((String) prop.get("wordnet_files")).split(",");
		for (String file : files) {
			createFile(path + name, tmpPath + "/", file.trim());
		}
		return tmpPath;
	}

	private String createFile(String innerPath, String externPath, String name)
			throws IOException {
		innerPath = innerPath.endsWith(File.separator) ? innerPath : innerPath + File.separator;
		externPath = externPath.endsWith(File.separator) ? externPath : externPath + File.separator;
		
		File file = new File(externPath + name);
		file.deleteOnExit();
		OutputStream out = new FileOutputStream(file);

		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream(innerPath + name);

		int read = 0;
		byte[] bytes = new byte[1024];
		try {
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			inputStream.close();
		} finally {
			out.flush();
			out.close();
		}
		return file.getAbsolutePath().toString();
	}

	private File createTempDir() {
		File baseDir = new File(System.getProperty("java.io.tmpdir"));
		String baseName = System.currentTimeMillis() + "-";

		for (int counter = 0; counter < TEMP_DIR_ATTEMPTS; counter++) {
			File tempDir = new File(baseDir, baseName + counter);
			if (tempDir.mkdir()) {
				return tempDir;
			}
		}
		throw new IllegalStateException("Failed to create directory within "
				+ TEMP_DIR_ATTEMPTS + " attempts (tried " + baseName + "0 to "
				+ baseName + (TEMP_DIR_ATTEMPTS - 1) + ')');
	}
}
