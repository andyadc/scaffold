package com.andyadc.scaffold.showcase.test;

import org.junit.Test;

import java.net.URL;

/**
 * @author andy.an
 * @since 2017/8/17
 */
public class ClassloaderTest {

    /**
     * BootstrapClassloader
     */
    @Test
    public void getBootstrapPaths() {
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urls) {
            System.out.println(url.toExternalForm());
        }
    }

    @Test
    public void getPaths() {
        // ExtClassloader
        System.out.println(System.getProperty("java.ext.dirs"));

        // AppClassloader
        System.out.println(System.getProperty("java.class.path"));
    }
}
