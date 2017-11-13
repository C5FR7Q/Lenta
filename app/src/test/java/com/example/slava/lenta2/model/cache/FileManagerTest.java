package com.example.slava.lenta2.model.cache;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by vva on 13/11/2017.
 */
public
class FileManagerTest
{
	@Test
	public
	void test() throws Exception {
		final FileManager manager = new FileManager();
		final File testFile = new File("test.txt");
		final String testContent = "test content";
		manager.writeToFile(testFile, testContent);
		final String fileContent = manager.readFileContent(testFile).trim();
		Assert.assertTrue(testContent.equals(fileContent));
		Assert.assertTrue(testFile.exists());
	}
}