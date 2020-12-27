package engine.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class QuestionSerializer extends StdSerializer<QuestionDB> {

    public QuestionSerializer() {
        this(null);
    }

    protected QuestionSerializer(Class<QuestionDB> t) {
        super(t);
    }

    @Override
    public void serialize(QuestionDB value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("title", value.getTitle());
        gen.writeStringField("text", value.getText());
        //gen.writeObjectField("options", value.getJsonOptions());
        //gen.writeStartArray(value.getJsonOptions());
        //gen.writeObjectFieldStart("options");
        gen.writeFieldName("options");
        gen.writeStartArray();
        for (String str : value.getJsonOptions()) {
            gen.writeString(str);
        }
        //gen.writeObjectField("options", value.getJsonOptions());
        gen.writeEndArray();
        //gen.writeEndObject();
        gen.writeEndObject();
        /*
        gen.writeNumberField("owner", value.owner.id);
        gen.writeEndObject();
        //gen.writeStartObject();
        gen.writeObject(value.toQuestionJSON());*/

        //gen.writeEmbeddedObject(value.toQuestionJSON());
    }
}
