package com.example.foodclub.batch;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DownloadingStepExecutionListener extends StepExecutionListenerSupport {

	@Autowired
	private ResourceLoader resourceLoader;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		String fileName = (String) stepExecution.getExecutionContext().get("fileName");

		Resource resource = this.resourceLoader.getResource(fileName);

		try {
			File file = File.createTempFile("input", ".csv");

			StreamUtils.copy(resource.getInputStream(), new FileOutputStream(file));

			stepExecution.getExecutionContext().put("localFile", file.getAbsolutePath());
			System.out.println(">> downloaded file : " + file.getAbsolutePath());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}