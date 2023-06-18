package pl.lodz.p.it.zzpj.hotelscollector.utils.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonParserException extends JsonProcessingException {
    public JsonParserException(String msg, Throwable rootCause) {
        super(msg, rootCause);
    }
}
