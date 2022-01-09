/**
 * 
 */
package com.ap.iamstu;

import com.ap.iamstu.infrastructure.support.util.GetterUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * @author Trim tất cả tham số String đầu vào
 */
@JsonComponent
public class TrimmingJsonDeserializer extends JsonDeserializer<String> {

	@Override
	public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		return p.hasToken(JsonToken.VALUE_STRING) ? GetterUtil.getString(p.getText(), null) : p.getValueAsString();
	}
}
