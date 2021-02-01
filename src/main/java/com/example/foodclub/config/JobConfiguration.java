package com.example.foodclub.config;

import com.example.foodclub.batch.*;
import com.example.foodclub.model.FoodClubCounter;
import com.example.foodclub.model.Purchase;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Configuration
public class JobConfiguration {

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private CustomPurchaseItemWriter customPurchaseItemWriter;

    @Bean
    public PurchaseStepExecutionListener purchaseStepExecutionListener() {
        return new PurchaseStepExecutionListener();
    }

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        Step step = load(stepBuilderFactory);
        return jobBuilderFactory.get("loadPurchases")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<PurchaseRecord> reader() {
        return new FlatFileItemReaderBuilder<PurchaseRecord>()
                .name("purchaseReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names("transactionId", "palsId", "purchaseDate", "skuId", "skuName", "skuPrice")
                .lineMapper(lineMapper())
                .targetType(PurchaseRecord.class)
                .build();
    }

    private LineMapper<PurchaseRecord> lineMapper() {

        DefaultLineMapper<PurchaseRecord> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
//        lineTokenizer.setNames("transactionId", "purchaseDate", "skuId", "skuName", "skuPrice");

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(new RecordFieldSetMapper());

        return defaultLineMapper;
    }

    @Bean
    @StepScope
    public EnrichmentProcessor processor() {
        return new EnrichmentProcessor();
    }

    @Bean
    public JpaItemWriter<Purchase> purchaseWriter() {
        return new JpaItemWriterBuilder<Purchase>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true)
                .build();
    }

    @Bean
    public JpaItemWriter<FoodClubCounter> counterWriter() {
        return new JpaItemWriterBuilder<FoodClubCounter>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true)
                .build();
    }

    @Bean
    public Step load(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("load")
                .<PurchaseRecord, List<Purchase>>chunk(2)
                .reader(reader())
                .processor(processor())
                .writer(customPurchaseItemWriter)
                .listener(purchaseStepExecutionListener())
                .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}