package com.finalproject.bttd.Utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.finalproject.bttd.entity.Board;
import com.finalproject.bttd.entity.User;
import com.finalproject.bttd.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
public class BoardDeserializer extends StdDeserializer<Board> {
    @Autowired
    private BoardRepository boardRepository;

    public BoardDeserializer() {
        this(Board.class);
    }
    protected BoardDeserializer(Class<?> vc) {
        super(vc);
    }

    protected BoardDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected BoardDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public Board deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);
        log.info("json node: " + node);
       int post_id = node.asInt();
        log.info("postidNod : " + post_id);

        // 데이터베이스에서 Board 객체를 조회
        return boardRepository.findById(post_id).orElse(null);

    }
}
