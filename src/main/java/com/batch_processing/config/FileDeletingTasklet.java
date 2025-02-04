//package com.batch_processing.config;
//
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.UnexpectedJobExecutionException;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.core.io.Resource;
//import org.springframework.util.Assert;
//
//import java.io.File;
//
//public class FileDeletingTasklet implements Tasklet, InitializingBean {
//
//    private Resource directory;
//
//    public RepeatStatus execute(StepContribution contribution,
//                                ChunkContext chunkContext) throws Exception {
//        File dir = directory.getFile();
////        Assert.state(dir.isDirectory());
//
//        File[] files = dir.listFiles();
//        for (int i = 0; i < files.length; i++) {
//            boolean deleted = files[i].delete();
//            if (!deleted) {
//                throw new UnexpectedJobExecutionException("Could not delete file " +
//                        files[i].getPath());
//            }
//        }
//        return RepeatStatus.FINISHED;
//    }
//
//    public void setDirectoryResource(Resource directory) {
//        this.directory = directory;
//    }
//
//    public void afterPropertiesSet() throws Exception {
//        Assert.state(directory != null, "directory must be set");
//    }
//}
//
