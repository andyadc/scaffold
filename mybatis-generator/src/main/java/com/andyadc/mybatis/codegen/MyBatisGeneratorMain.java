package com.andyadc.mybatis.codegen;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author andy.an
 * @since 2018/2/4
 */
public class MyBatisGeneratorMain {

    public static void main(String[] args) {

        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);

        ConfigurationParser parser = new ConfigurationParser(warnings);

        MyBatisGenerator generator;
        Configuration configuration;
        String genCfg = "/generatorConfig.xml";
        try {
            File configFile = new File(MyBatisGeneratorMain.class.getResource(genCfg).getFile());
            System.out.println(configFile.getAbsolutePath());
            configuration = parser.parseConfiguration(configFile);

            generator = new MyBatisGenerator(configuration, callback, warnings);
            generator.generate(null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("------warnings-------");
        for (String warning : warnings) {
            System.out.printf("%s %n", warning);
        }
    }
}
