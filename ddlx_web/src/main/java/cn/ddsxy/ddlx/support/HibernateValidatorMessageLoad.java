package cn.ddsxy.ddlx.support;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.*;

/**
 * 配置加载
 */
public class HibernateValidatorMessageLoad {

    public HibernateValidatorMessageLoad() {
        System.err.println("----------------------------hibernateValidtorMessage初始化--------------------------------");
        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File file;

    public void init() throws IOException {
        Resource resource =new ClassPathResource("message.properties");
        InputStream in = resource.getInputStream();
        file = resource.getFile();
        in.close();
    }

    public void load() throws IOException {
        init();
        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = patternResolver.getResources("classpath*:**/*validMessage.properties");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("");//清空
        for (Resource resource : resources) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String temp = null;
            while ((temp = reader.readLine()) != null){
                writer.append(temp);
                writer.newLine();
            }
            reader.close();
        }
        writer.flush();
        writer.close();
    }

}
