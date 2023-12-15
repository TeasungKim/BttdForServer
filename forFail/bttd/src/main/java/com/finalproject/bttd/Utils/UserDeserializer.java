package com.finalproject.bttd.Utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.finalproject.bttd.entity.Board;
import com.finalproject.bttd.entity.User;

import java.io.IOException;

public class UserDeserializer extends StdDeserializer<User> {

    public UserDeserializer() {
        this(User.class);
    }
    protected UserDeserializer(Class<?> vc) {
        super(vc);
    }

    protected UserDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected UserDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public User deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);

        JsonNode userIdNode = node.get("user_id");
        if (userIdNode == null || userIdNode.isNull()) {
            // user_id가 없거나 null인 경우 처리
            return null; // 또는 적절한 기본값 반환
        }

        String user_id = userIdNode.asText();
        // 다른 필드도 유사하게 가져올 수 있습니다.
        return new User(user_id);




    }

}
