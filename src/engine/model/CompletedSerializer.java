package engine.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CompletedSerializer extends StdSerializer<Completed> {

    public CompletedSerializer() {
        this(null);
    }

    protected CompletedSerializer(Class<Completed> t) {
        super(t);
    }

    @Override
    public void serialize(Completed value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getQuestion().getId());
        gen.writeStringField("completedAt", value.getLocalDateTime().toString());
        gen.writeEndObject();
    }
}
