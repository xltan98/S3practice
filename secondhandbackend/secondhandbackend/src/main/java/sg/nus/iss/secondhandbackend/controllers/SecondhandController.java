package sg.nus.iss.secondhandbackend.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.nus.iss.secondhandbackend.models.Payload;
import sg.nus.iss.secondhandbackend.respository.UploadRepository;
import sg.nus.iss.secondhandbackend.services.SecondhandService;

@RestController
@CrossOrigin
public class SecondhandController {

    @Autowired
    UploadRepository uRepo;

    @Autowired
    SecondhandService sSvc;

    @PostMapping(path="/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> upload(@RequestPart String payload,@RequestPart MultipartFile imgFile){
         try{
            String mediaType=imgFile.getContentType();
            InputStream is=imgFile.getInputStream();

            Payload response=sSvc.upload(payload,imgFile);
            
            JsonObject resp=Json.createObjectBuilder().add("id",response.getId()).build();
            return ResponseEntity.ok(resp.toString());



        }catch(IOException ex){
            JsonObject resp=Json.createObjectBuilder()
            .add("error",ex.getMessage()).build();

            return ResponseEntity.status(500).body(resp.toString());
         
        }
    }
    @GetMapping(path="/posts/{id}")
    public ResponseEntity<String>post(@PathVariable String id){
        String payload=uRepo.getFromRedis(id);

        return ResponseEntity.ok(payload);
    }
    @PutMapping(path="/posting/{id}")
     public ResponseEntity<String>posting(@PathVariable String id){

         String payload=uRepo.getFromRedis(id);
         if (payload == null) {
            String errorMessage = String.format("id:%s,NotFound", id);
            return ResponseEntity.ok(Json.createObjectBuilder().add("message", errorMessage).build().toString());
         }
         String successMessage = String.format("id:%s,Accepted", id);
         sSvc.transferToSql(id);
        return ResponseEntity.ok(Json.createObjectBuilder().add("message", successMessage).build().toString());


    }

    // String toJson = Json.createObjectBuilder()
    //     .add("id",response.getId())
    //     .add("name",response.getName())
    //     .add("email",response.getEmail())
    //     .add("phone",response.getPhone())
    //     .add("description",response.getDescription())
    //     .add("title",response.getTitle())
    //     .add("date",response.getDate())
    //     .build().toString();

    //     return ResponseEntity.ok(toJson);
    
}
