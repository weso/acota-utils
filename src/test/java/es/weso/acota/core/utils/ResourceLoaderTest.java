/**
 * 
 */
package es.weso.acota.core.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

/**
 * @author César Luis Alvargonzález
 *
 */
public class ResourceLoaderTest {

	private ResourceLoader resourceLoader;
	
	@Before
	public void startTest(){
		this.resourceLoader = new ResourceLoader();
	}
	
	@Test
	public void getConfigurationTest(){
		assertEquals(resourceLoader.configuration, resourceLoader.getConfiguration()); 
	}
	
	@Test
	public void getConfigurationNotNullTest(){
		assertNotEquals(null, resourceLoader.getConfiguration()); 
	}
	
	@Test
	public void validOpenNlpEsPosPath(){
		String path = resourceLoader.getConfiguration().getString("opennlp.es.pos");
		File file = new File(path);
		assertNotNull(file);
	}
	
	@Test
	public void validOpenNlpEsSentPath(){
		String path = resourceLoader.getConfiguration().getString("opennlp.es.sent");
		File file = new File(path);
		assertNotNull(file);
	}
	
	@Test
	public void validOpenNlpEsTokPath(){
		String path = resourceLoader.getConfiguration().getString("opennlp.es.tok");
		File file = new File(path);
		assertNotNull(file);
	}
	
	@Test
	public void validOpenNlpEnPosPath(){
		String path = resourceLoader.getConfiguration().getString("opennlp.en.pos");
		File file = new File(path);
		assertNotNull(file);
	}
	
	@Test
	public void validOpenNlpEnSentPath(){
		String path = resourceLoader.getConfiguration().getString("opennlp.en.sent");
		File file = new File(path);
		assertNotNull(file);
	}
	
	@Test
	public void validOpenNlpEnTokPath(){
		String path = resourceLoader.getConfiguration().getString("opennlp.en.tok");
		File file = new File(path);
		assertNotNull(file);
	}
	
	@Test
	public void validOpenNlpEnDictPath(){
		String path = resourceLoader.getConfiguration().getString("wordnet.en.dict");
		File file = new File(path);
		assertNotNull(file);
	}
	
	@Test
	public void validOpenNlpEnDictPathIsDirectory(){
		String path = resourceLoader.getConfiguration().getString("wordnet.en.dict");
		File file = new File(path);
		assertTrue(file.isDirectory());
	}
	
}
