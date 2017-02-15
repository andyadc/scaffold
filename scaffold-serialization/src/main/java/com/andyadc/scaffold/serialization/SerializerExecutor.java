package com.andyadc.scaffold.serialization;

import com.andyadc.scaffold.serialization.binary.JDKSerializer;
import com.andyadc.scaffold.serialization.compression.CompressorType;
import com.andyadc.scaffold.serialization.json.FastjsonSerializer;
import com.andyadc.scaffold.serialization.json.JacksonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author andaicheng
 * @version 2016/12/30
 */
public class SerializerExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(SerializerExecutor.class);

    public static <T> byte[] serialize(T object) {
        return null;
    }

    public static <T> T deserialize(byte[] bytes) {
        return null;

    }

    public static <T> byte[] serialize(T object, boolean compress) {
        return null;

    }

    public static <T> T deserialize(byte[] bytes, boolean compress) {
        return null;

    }

    public static <T> String toJson(T object) {
        return null;

    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return null;

    }

    public static <T> byte[] serialize(T object, SerializerType serializerType, CompressorType compressorType, boolean compress, boolean serializerLogPrint) {
        return null;

    }

    public static <T> T deserialize(byte[] bytes, SerializerType serializerType, CompressorType compressorType, boolean compress, boolean serializerLogPrint) {
        if (compress) {
            print(bytes, null, false, true, serializerLogPrint);

            bytes = decompress(bytes, compressorType);

            print(bytes, null, false, false, serializerLogPrint);
        }

        T object = null;
        if (serializerType == SerializerType.FST_BINARY) {

        } else if (serializerType == SerializerType.KRYO_BINARY) {

        } else if (serializerType == SerializerType.JDK_BINARY) {
            object = JDKSerializer.deserialize(bytes);
        } else {
            throw new SerializerException("Invalid serializer type of binary : " + serializerType);
        }

        if (!compress) {
            print(bytes, object.getClass(), false, false, serializerLogPrint);
        }

        return object;
    }

    public static <T> String toJson(T object, SerializerType serializerType) {
        if (serializerType == SerializerType.JACKSON_JSON) {
            return JacksonSerializer.toJson(object);
        } else if (serializerType == SerializerType.FAST_JSON) {
            return FastjsonSerializer.toJSON(object);
        } else if (serializerType == SerializerType.FST_JSON) {
            // TODO
            return null;
        } else {
            throw new SerializerException("Invalid serializer type of json : " + serializerType);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz, SerializerType serializerType) {
        if (serializerType == SerializerType.JACKSON_JSON) {
            return JacksonSerializer.fromJson(json, clazz);
        } else if (serializerType == SerializerType.FAST_JSON) {
            return FastjsonSerializer.fromJson(json, clazz);
        } else if (serializerType == SerializerType.FST_JSON) {
            // TODO
            return null;
        } else {
            throw new SerializerException("Invalid serializer type of json : " + serializerType);
        }
    }

    public static byte[] compress(byte[] bytes, CompressorType compressorType) {
        return null;
    }

    public static byte[] decompress(byte[] bytes, CompressorType compressorType) {
        return null;
    }

    public static void print(byte[] bytes, Class<?> clazz, boolean serialize, boolean compress, boolean serializerLogPrint) {
        if (serializerLogPrint) {
            if (clazz != null) {
                LOG.info("{}, size={} KB, {} Byte, compress={}, class={}", serialize ? "Serialize" : "Deserialize", (float) bytes.length / 1024, bytes.length, compress, clazz.getName());
            } else {
                LOG.info("{}, size={} KB, {} Byte, compress={}", serialize ? "Serialize" : "Deserialize", (float) bytes.length / 1024, bytes.length, compress);
            }
        }
    }
}
