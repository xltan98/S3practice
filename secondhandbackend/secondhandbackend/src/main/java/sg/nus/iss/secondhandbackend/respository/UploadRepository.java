package sg.nus.iss.secondhandbackend.respository;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.nus.iss.secondhandbackend.models.Payload;

@Repository
public class UploadRepository {
    @Autowired
    @Qualifier("post")
    private RedisTemplate<String,String> template;

    @Autowired
    private JdbcTemplate sqlTemplate;

    public Payload savePayload(String payload,String id){
        JsonObject o=Json.createReader(new StringReader(payload)).readObject();

        Payload p= new Payload();
        p.setName(o.getString("name"));
        p.setEmail(o.getString("email"));
        p.setPhone(o.getString("phone"));
        p.setDescription(o.getString("description"));
        p.setTitle(o.getString("title"));
        p.setDate(LocalDate.now().toString());
        p.setId(id);

        return p;

    }

    public void saveToRedis(Payload payload){
        String toJson = Json.createObjectBuilder()
        .add("id",payload.getId())
        .add("name",payload.getName())
        .add("email",payload.getEmail())
        .add("phone",payload.getPhone())
        .add("description",payload.getDescription())
        .add("title",payload.getTitle())
        .add("date",payload.getDate())
        .build().toString();

        template.opsForValue().set(payload.getId(),toJson,15, TimeUnit.MINUTES);
        //append
    }

    public String getFromRedis(String id){

        String jpayload=template.opsForValue().get(id);

        // JsonObject jsonObject = Json.createReader(new StringReader(jpayload)).readObject();

        // Payload payload= new Payload();
        // payload.setId(jsonObject.getString("id"));
        // payload.setName(jsonObject.getString("name"));
        // payload.setEmail(jsonObject.getString("email"));
        // payload.setPhone(jsonObject.getString("phone"));
        // payload.setDescription(jsonObject.getString("description"));
        // payload.setTitle(jsonObject.getString("title"));
        // payload.setDate(jsonObject.getString("date"));
        
        return jpayload;
    }

    public boolean delete(String id){
        return template.delete(id);
    }

    private final String insertDetails="insert into uploads(id,name,email,phone,description,title,date) value(?,?,?,?,?,?,?)";

    public Boolean insertDetails(String jsonp){
        boolean saved=false;
        JsonObject o= Json.createReader(new StringReader(jsonp)).readObject();
        saved=sqlTemplate.execute(insertDetails,new PreparedStatementCallback<Boolean>() {
//id,name,email,phone,description,title,date) value(?,?,?,?,?,?,?)";
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
              ps.setString(1,o.getString("id"));
              ps.setString(2,o.getString("name"));
              ps.setString(3, o.getString("email"));
              ps.setString(4,o.getString("phone"));
              ps.setString(5, o.getString("description"));
             ps.setString(6, o.getString("title"));
             ps.setString(7, o.getString("date"));

             return ps.execute();

            }
            
        });
        
        return saved;


    }
}
