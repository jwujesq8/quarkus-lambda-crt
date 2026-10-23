package org.acme.lambda;

public class GreetingResponse {

    private String greeting;
    private String s3Key;
    private int totalGreetings;

    public GreetingResponse() {
    }

    public String getGreeting() {
        return greeting;
    }

    public GreetingResponse setGreeting(String greeting) {
        this.greeting = greeting;
        return this;
    }

    public String getS3Key() {
        return s3Key;
    }

    public GreetingResponse setS3Key(String s3Key) {
        this.s3Key = s3Key;
        return this;
    }

    public int getTotalGreetings() {
        return totalGreetings;
    }

    public GreetingResponse setTotalGreetings(int totalGreetings) {
        this.totalGreetings = totalGreetings;
        return this;
    }
}
