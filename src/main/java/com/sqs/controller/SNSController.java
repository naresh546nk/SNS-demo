package com.sqs.controller;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SNSController {

    @Autowired
    private AmazonSNSClient snsClient;

    @Value("${cloud.aws.topic-arn}")
    private String topicArn;


    @GetMapping("addSub/{email}")
    public  String addSubscription(@PathVariable String email){
        SubscribeRequest request=new SubscribeRequest(topicArn,"email",email);
        snsClient.subscribe(request);
        return  "Subscription Request is pending . Just check your email :"+email;
    }


    @GetMapping("sendNotification")
    public String publishMessageToTopic(){
        PublishRequest request=new PublishRequest(topicArn,"Hi Dear, This is testing of Amazon SNS","Amazon SNS");
        PublishResult publish = snsClient.publish(request);
        return publish.getMessageId();
    }
}
