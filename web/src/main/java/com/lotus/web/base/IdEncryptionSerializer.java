package com.lotus.web.base;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.lotus.common.utils.LongUtils;
import com.lotus.web.utils.IdUtils;

import java.io.IOException;

/**
 * @program: framework
 * @description: id加密序列化
 * @author: changjiz
 * @create: 2019-12-08 17:14
 **/
public class IdEncryptionSerializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long aLong, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (LongUtils.isEmpty(aLong)) {
            jsonGenerator.writeString("");
        } else {
            jsonGenerator.writeString(IdUtils.encryptionStr(aLong));
        }
    }
}
