package com.devaemlak.advertisement_service.config;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.locationtech.jts.geom.Point;

import java.io.IOException;

public class PointSerializer extends JsonSerializer<Point>{
    @Override
    public void serialize(Point point, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("lat");
        gen.writeNumber(point.getY());
        gen.writeFieldName("lng");
        gen.writeNumber(point.getX());
        gen.writeEndObject();
    }
}
