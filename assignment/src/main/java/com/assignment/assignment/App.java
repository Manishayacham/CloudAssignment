package com.assignment.assignment;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String clientRegion = "us-east-1";
        String bucketName = "mssecarrental";
        String stringObjKeyName = "File";
        String fileObjKeyName = "File";
        String fileName ="/Users/manishayacham/Desktop/Screen Shot 2020-05-08 at 4.58.42 PM.png" ;
        String accessKey = "AKIATXI2EZCM7CQO6ZPR";
        String secretKey = "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";
        try {
            
        	 AWSCredentials awsCredss = new BasicAWSCredentials(accessKey, secretKey);
        	 AmazonS3 s3client =  AmazonS3ClientBuilder
             		  .standard()
             		  .withCredentials(new AWSStaticCredentialsProvider(awsCredss))
             		  .withRegion(clientRegion)
             		  .build();

          
            Image image = null;
            try {
                URL url = new URL(
                    "https://mssecarrental.s3.amazonaws.com/Screen+Shot+2020-04-19+at+3.00.40+PM.png");
                image = ImageIO.read(url);
            } catch (IOException e) {
            }
            
            System.out.println("Reading Image from S3");

            // Use a label to display the image
            JFrame frame = new JFrame();

            JLabel lblimage = new JLabel(new ImageIcon(image));
            frame.getContentPane().add(lblimage, BorderLayout.CENTER);
            frame.setSize(300, 400);
            frame.setVisible(true);
            
            System.out.println("Writing Image to S3");
            
            s3client.putObject(bucketName, stringObjKeyName, "Uploaded String Object");
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("title", "someTitle");
            request.setMetadata(metadata);
            s3client.putObject(request);
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            
            e.printStackTrace();
        }
    }
}
