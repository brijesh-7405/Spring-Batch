package com.batch_processing.config;


import com.batch_processing.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.batch.core.*;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBatchTest
@SpringBootTest
public class BatchConfigTest {

    @Autowired
    private Job job;

    @Test
    public void testJobBeanCreation() {
        assertNotNull(job);
        assertEquals("job", job.getName());
    }
    @Autowired
    private CompletionNotificationImpl listener;

    @Test
    public void testListenerBeanCreation() {
        assertNotNull(listener);
    }

    @Autowired
    private Step step;

    @Test
    public void testStepBeanCreation() {
        assertNotNull(step);
        assertEquals("step1", step.getName());
//        assertEquals(5, step.getChunkSize());
    }

    @Autowired
    private FlatFileItemReader<Product> reader;

    @MockBean
    private ClassPathResource resource;

    @Test
    public void testReaderBeanCreation() {
        assertNotNull(reader);
        assertEquals("reader", reader.getName());
//        assertEquals("data.csv", reader.getResource().getDescription());
//        assertEquals(Product.class, reader.getTargetType());
    }

    @Autowired
    private CustomItemProcessor processor;

    @Test
    public void testProcessorBeanCreation() {
        assertNotNull(processor);
    }
//
//    private JobLauncherTestUtils jobLauncherTestUtils;
//
//
//    private JobRepositoryTestUtils jobRepositoryTestUtils;
//
//    @BeforeEach
//    public void before(){
//        jobLauncherTestUtils = new JobLauncherTestUtils();
//        jobRepositoryTestUtils = new JobRepositoryTestUtils();
//    }
//    @AfterEach
//    public void cleanUp() {
//        jobRepositoryTestUtils.removeJobExecutions();
//    }
//
//    @Test
//    public void testJobExecution() throws Exception {
//        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
//        assertEquals("COMPLETED", jobExecution.getExitStatus());
//    }

}
