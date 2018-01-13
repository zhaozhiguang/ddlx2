package cn.ddsxy.ddlx.support;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 自定义xml返回数据
 */
public class CustomXmlHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    public final static Charset UTF8 = Charset.forName("UTF-8");

    private static XStream xstream = new XStream(new XppDriver());

    public CustomXmlHttpMessageConverter() {
        super(new MediaType("application", "xml"),
                new MediaType("text", "xml"),
                new MediaType("application", "*+xml"));
    }

    protected boolean supports(Class<?> clazz) {
        return true;
    }

    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        xstream.alias(clazz.getSimpleName(), clazz);
        return xstream.fromXML(inputMessage.getBody());
    }

    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        xstream.alias(o.getClass().getSimpleName(), o.getClass());
        outputMessage.getBody().write(xstream.toXML(o).getBytes("utf-8"));
    }
}
