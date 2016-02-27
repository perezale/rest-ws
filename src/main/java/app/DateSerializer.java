package app;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

//CustomDateSerializer class
public class DateSerializer extends JsonSerializer<Date> {    
  @Override
  public void serialize(Date value, JsonGenerator gen, SerializerProvider arg2) throws 
      IOException, JsonProcessingException {      

      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
      formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
      String formattedDate = formatter.format(value);

      gen.writeString(formattedDate);

  }
}

