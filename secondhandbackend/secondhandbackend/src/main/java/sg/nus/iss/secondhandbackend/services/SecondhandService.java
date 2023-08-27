package sg.nus.iss.secondhandbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sg.nus.iss.secondhandbackend.models.Payload;
import sg.nus.iss.secondhandbackend.respository.S3Repository;
import sg.nus.iss.secondhandbackend.respository.UploadRepository;

@Service
public class SecondhandService {
    @Autowired
    S3Repository sRepo;

    @Autowired
    UploadRepository uRepo;
    public Payload upload(String payload,MultipartFile img){
        
        String id=sRepo.saveImage(img);
        Payload p=uRepo.savePayload(payload,id);
        uRepo.saveToRedis(p);

        return p;
    }

    public boolean transferToSql(String id){

        String payload=uRepo.getFromRedis(id);
        System.out.println("payload result"+payload);
        boolean b=uRepo.insertDetails(payload);
        System.out.println("isInsert"+b);

        uRepo.delete(id);

        return b;
        
    }

    
}
