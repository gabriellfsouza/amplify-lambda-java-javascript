
package example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;



public class LambdaRequestHandler implements RequestHandler<RequestClass, ResponseClass>{  
    private static final Logger logger = LoggerFactory.getLogger(LambdaRequestHandler.class); 

    public ResponseClass handleRequest(RequestClass request, Context context){
        String bucketName = System.getenv("STORAGE_SAMPLESTORAGE_BUCKETNAME");
        String key = request.key;
        logger.info("Key: #s",key);

        Boolean processed = false;
    
        S3Object fullObject = null;

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
            String contentType = fullObject.getObjectMetadata().getContentType();
            fullObject.getObjectContent();
            InputStream input = fullObject.getObjectContent();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);

            ByteArrayOutputStream dest = new ByteArrayOutputStream();
            
            PdfReader reader = new PdfReader(input);
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdfDocument = new PdfDocument(reader,writer);
            Document doc = new Document(pdfDocument);      

            request.metadatas.keySet().forEach((meta)->{
                logger.info("Metadata: %s, Value: %s",meta, request.metadatas.get(meta));
                pdfDocument.getDocumentInfo().setMoreInfo(meta, request.metadatas.get(meta));
            });

            // pdfDocument.getDocumentInfo().setMoreInfo("2_16_76_1_12_1_1", "");
            // pdfDocument.getDocumentInfo().setMoreInfo("2_16_76_1_4_2_2_1", "20325");
            // pdfDocument.getDocumentInfo().setMoreInfo("2_16_76_1_4_2_2_2", "SP");

            doc.close();
            pdfDocument.close();
            writer.close();
            reader.close();
            

            InputStream destInput = new ByteArrayInputStream(dest.toByteArray());
            
            s3Client.putObject(bucketName,"atestado-destino.pdf",destInput,metadata);
            
            processed = true;
        } catch (AmazonServiceException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return new ResponseClass(processed);
    }
}


// package example;


// // import com.amazonaws.AmazonServiceException;
// // import com.amazonaws.SdkClientException;
// // import com.amazonaws.regions.Regions;
// import com.amazonaws.services.lambda.runtime.Context;
// import com.amazonaws.services.lambda.runtime.RequestHandler;
// import com.amazonaws.services.s3.AmazonS3;
// import com.amazonaws.services.s3.AmazonS3ClientBuilder;
// import com.amazonaws.services.s3.model.GetObjectRequest;
// import com.amazonaws.services.s3.model.S3Object;

// public class LambdaRequestHandler implements RequestHandler<RequestClass, ResponseClass>{   

//     public ResponseClass handleRequest(RequestClass request, Context context){
//         String greetingString = String.format("Hello %s %s!", request.firstName, request.lastName);
//         System.out.println(System.getenv("STORAGE_SAMPLESTORAGE_BUCKETNAME"));

//         // Regions clientRegion = Regions.US_EAST_1;
//         String bucketName = "samplestorage180812-spykes";
//         String key = "atestado.pdf";
        
        
//         try {
//             S3Object fullObject = null;
//             AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
//                 // .withRegion(clientRegion)
//                 // .withCredentials(new ProfileCredentialsProvider())
//                 // .build();
            
//             fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
//             System.out.println(fullObject.getObjectMetadata().getContentType());
//             return new ResponseClass(greetingString);
//         } catch (Exception e) {
//             throw new RuntimeException(e);
            
//         }


        
//     }
// }