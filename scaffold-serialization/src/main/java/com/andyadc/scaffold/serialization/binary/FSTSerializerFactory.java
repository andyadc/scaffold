package com.andyadc.scaffold.serialization.binary;

import org.apache.commons.lang3.SerializationException;
import org.apache.commons.pool2.ObjectPool;
import org.nustaq.serialization.FSTConfiguration;

/**
 * @author andaicheng
 * @version 2017/2/13
 */
public class FSTSerializerFactory {

    // 官方文档说，FSTConfiguration太重了，需要通过ThreadLocal或者静态对象去维护它
    private static FSTConfiguration fstConfiguration;
    private static ObjectPool<FSTConfiguration> fstConfigurationObjectPool;
    private static boolean usePool = false;

    public static void initialize() {
        if (usePool) {

        } else {
            fstConfiguration = createFstConfiguratio();
        }
    }

    public static FSTConfiguration createFstConfiguratio() {
        return FSTConfiguration.createDefaultConfiguration();
    }

    public static ObjectPool<FSTConfiguration> getDefaultPool() {
        return fstConfigurationObjectPool;
    }

    public static FSTConfiguration getDefaultFstConfiguration() {
        if (usePool) {
            return getFstConfiguration(fstConfigurationObjectPool);
        } else {
            return fstConfiguration;
        }
    }

    public static FSTConfiguration getFstConfiguration(ObjectPool<FSTConfiguration> pool) {
        FSTConfiguration fstConfiguration = null;
        try {
            fstConfiguration = pool.borrowObject();
        } catch (Exception e) {
            throw new SerializationException(e.getMessage(), e);
        } finally {
            if (fstConfiguration != null) {
                try {
                    pool.returnObject(fstConfiguration);
                } catch (Exception e) {
                    // ignore
                }
            }
        }

        return fstConfiguration;
    }
}
