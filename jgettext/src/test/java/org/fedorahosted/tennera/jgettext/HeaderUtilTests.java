package org.fedorahosted.tennera.jgettext;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;



public class HeaderUtilTests {

	PoParser parser;
	
	public HeaderUtilTests() {
		parser = new PoParser();
	}
	
	@Test
	public void testSamplePo() throws Throwable{
		File sample = getResource("/valid/sample.po");
		Catalog catalog = parser.parseCatalog(sample);
		Message headerMsg = catalog.locateHeader();
		
		String headerStr = headerMsg.getMsgstr();
		
		HeaderUtil header = HeaderUtil.wrap(headerMsg);
		assertEquals(header.getKeys().size(), 9);
		assertEquals(header.getValue(HeaderUtil.KEY_MimeVersion), "1.0");
		
		header.setValue("MyKey", "abcd");
		assertEquals(header.getKeys().size(), 10);
		header.setValue("MyKey", "xxx");
		assertEquals(header.getKeys().size(), 10);
		
		
		Message result = header.unwrap();
		assertEquals(headerStr + "MyKey: xxx\n", result.getMsgstr());
		
	}
	
	
	private File getResource(String file){
		return new File( getClass().getResource(file).getFile() );
	}
}
