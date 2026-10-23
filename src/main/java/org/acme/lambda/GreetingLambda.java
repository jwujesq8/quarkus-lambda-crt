package org.acme.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class GreetingLambda implements RequestHandler<Person, GreetingResponse> {

    @Inject
    S3Client s3;

    @ConfigProperty(name = "greeting.bucket")
    String bucketName;

    @Override
    public GreetingResponse handleRequest(Person input, Context context) {
        String name = input.getName();
        String greeting = "Hello " + name;
        String key = "greetings/" + name + ".txt";

        // Store the greeting in S3
        s3.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType("text/plain")
                        .build(),
                RequestBody.fromString(greeting, StandardCharsets.UTF_8));

        // Read it back to confirm the round-trip
        String stored = s3.getObjectAsBytes(
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build())
                .asUtf8String();

        // Count total greetings stored
        List<?> objects = s3.listObjects(
                ListObjectsRequest.builder()
                        .bucket(bucketName)
                        .prefix("greetings/")
                        .build())
                .contents();

        return new GreetingResponse()
                .setGreeting(stored)
                .setS3Key(key)
                .setTotalGreetings(objects.size());
    }
}
