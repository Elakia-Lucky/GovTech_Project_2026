package com.govtech;

import com.govtech.entity.User;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.LineMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@EnableBatchProcessing
public class UserBatchConfig {

    @Bean
    public FlatFileItemReader<User> userReader() {
        FlatFileItemReader<User> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("users.csv"));
        reader.setLinesToSkip(0);

        reader.setLineMapper((line, lineNumber) -> {
            User user = new User();
            user.setUsername(line.trim());
            return user;
        });

        return reader;
    }


    @Bean
    public JpaItemWriter<User> userWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<User> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }


    @Bean
    public Step loadUsersStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<User> userReader,
            JpaItemWriter<User> userWriter) {

        return new StepBuilder("loadUsersStep", jobRepository)
                .<User, User>chunk(5, transactionManager)
                .reader(userReader)
                .writer(userWriter)
                .build();
    }


    @Bean
    public Job userImportJob(
            JobRepository jobRepository,
            Step loadUsersStep) {

        return new JobBuilder("userImportJob", jobRepository)
                .start(loadUsersStep)
                .build();
    }
}

