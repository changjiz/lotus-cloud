package com.lotus.framework.base;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lotus.web.base.IdDecryptDeserializer;
import com.lotus.web.base.IdEncryptionSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = IdEncryptionSerializer.class)
@JsonDeserialize(using = IdDecryptDeserializer.class)
public @interface BaseIdJson {
}
