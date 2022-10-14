package com.lotus.web.base;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.lotus.web.utils.IdUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @program: framework
 * @description: ID反序列化解密
 * @author: changjiz
 * @create: 2019-12-08 18:12
 **/
public class IdDecryptDeserializer extends JsonDeserializer<Long> {

    @Override
    public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String value = jsonParser.getValueAsString();
        if (StringUtils.isNotEmpty(value)) {
            return IdUtils.decryptLong(value);
        }
        return null;
    }

}
