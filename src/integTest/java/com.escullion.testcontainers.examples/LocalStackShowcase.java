package com.escullion.testcontainers.examples;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.localstack.LocalStackContainer;

public class LocalStackShowcase {

  @Rule
  public LocalStackContainer localstack = new LocalStackContainer()
      .withServices(S3);

  @Test
  public void testingS3Setup() {
    AmazonS3 s3 = AmazonS3ClientBuilder
        .standard()
        .withEndpointConfiguration(localstack.getEndpointConfiguration(S3))
        .withCredentials(localstack.getDefaultCredentialsProvider())
        .build();
    s3.createBucket("bucket-name");

    List<Bucket> buckets = s3.listBuckets();
    assertThat(buckets).isNotEmpty();
  }
}
