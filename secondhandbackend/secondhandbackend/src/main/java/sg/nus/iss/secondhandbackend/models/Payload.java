package sg.nus.iss.secondhandbackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payload {

    String name;
    String email;
    String phone;
    String title;
    String description;
    String date;
    String id;

    
}
