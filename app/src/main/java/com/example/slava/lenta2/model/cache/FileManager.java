/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.slava.lenta2.model.cache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public
class FileManager
{
	public
	void writeToFile(final File file, final String fileContent) {
		if (file.exists()) {
			//noinspection ResultOfMethodCallIgnored
			file.delete();
		}
		try {
			final FileWriter writer = new FileWriter(file);
			writer.write(fileContent);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public
	String readFileContent(final File file) {
		final StringBuilder stringBuilder = new StringBuilder();
		if (file.exists()) {
			String stringLine;
			try (
					FileReader fileReader = new FileReader(file);
					BufferedReader bufferedReader = new BufferedReader(fileReader)
			) {
				while ((stringLine = bufferedReader.readLine()) != null) {
					stringBuilder.append(stringLine).append("\n");
				}
				bufferedReader.close();
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stringBuilder.toString();
	}

}
