package com.batch_processing.config;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {FileDeletingTasklet.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
@SpringBatchTest
class FileDeletingTaskletDiffblueTest {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @MockBean
    private Resource directory = new FileSystemResource("src/main/resources/templates");

    @Test
    public void testDeleteFiles() throws Exception {
        // Mock the Resource to return a valid directory
        when(directory.getFile()).thenReturn(new File("src/main/resources/templates"));

        // Create some test files in the directory
        File dir = new File("src/main/resources/templates");
        dir.mkdirs();
        File file1 = new File(dir, "file1.txt");
        File file2 = new File(dir, "file2.txt");
        file1.createNewFile();
        file2.createNewFile();

        // Set the directory on the tasklet
        FileDeletingTasklet tasklet = new FileDeletingTasklet();
        tasklet.setDirectoryResource(directory);

        // Execute the tasklet
        Job job =  new JobBuilder("taskletJob", jobRepository)
                .start(new StepBuilder("deleteFilesInDir", jobRepository)
                        .tasklet(tasklet, transactionManager)
                        .build())
                .build();

        JobExecution jobExecution = jobLauncher.run(job, new JobParameters());

        // Assert that the files are deleted
        assertThat(file1.exists()).isFalse();
        assertThat(file2.exists()).isFalse();

        // Assert the job execution status
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}
