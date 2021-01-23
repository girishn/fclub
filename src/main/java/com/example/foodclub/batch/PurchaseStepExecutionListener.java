package com.example.foodclub.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class PurchaseStepExecutionListener extends StepExecutionListenerSupport {

	@Autowired
	private ResourceLoader resourceLoader;

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("After stepExecution: {}", stepExecution.getExecutionContext());
		return super.afterStep(stepExecution);
	}
}