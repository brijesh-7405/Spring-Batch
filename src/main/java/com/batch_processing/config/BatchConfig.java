package com.batch_processing.config;

import com.batch_processing.model.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {



//    @Bean
//    public Job jobBean(JobRepository jobRepository,CompletionNotificationImpl listener,
//                       Step steps){
//        return new JobBuilder("job",jobRepository)
//                .listener(listener)
//                .start(steps)
//                .build();
//    }

    @Bean
    public Job taskletJob(JobRepository jobRepository, Step deleteFilesInDir) {
        return new JobBuilder("taskletJob", jobRepository)
                .start(deleteFilesInDir)
                .build();
    }
    @Bean
    public CompletionNotificationImpl listener() {
        return new CompletionNotificationImpl();
    }
//    @Bean
//    public Step deleteFilesInDir(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("deleteFilesInDir", jobRepository)
//                .tasklet(fileDeletingTasklet(), transactionManager)
//                .build();
//    }
//    @Bean
//    public FileDeletingTasklet fileDeletingTasklet() {
//        FileDeletingTasklet tasklet = new FileDeletingTasklet();
//
//        tasklet.setDirectoryResource(new FileSystemResource("src/main/resources/templates"));
//
//        return tasklet;
//    }

    @Bean
    public Step steps( JobRepository jobRepository,
                       DataSourceTransactionManager transactionManager,
                       ItemReader<Product> reader,
                       CustomItemProcessor processor,
                       ItemWriter<Product> writer){
        return new StepBuilder("step1",jobRepository)
                .<Product,Product>chunk(5,transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public FlatFileItemReader<Product> reader(){
        return new FlatFileItemReaderBuilder<Product>()
                .name("reader")
                .resource(new ClassPathResource("data.csv"))
                .delimited()
                .names("productId","title","description","price","discount")
                .targetType(Product.class)
                .linesToSkip(1)
                .build();
    }

    @Bean
    public ItemProcessor<Product,Product> processor(){
        return new CustomItemProcessor();
    }

    @Bean
    public ItemWriter<Product> writer(DataSource dataSource){
        return new JdbcBatchItemWriterBuilder<Product>()
                .sql("insert into " +
                        "products(productId,title,description,price,discount,discounted_price) " +
                        "values(:productId,:title,:description,:price,:discount,:discountedPrice)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }
}
