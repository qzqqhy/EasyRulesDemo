package com.easyrules.demo.data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@PropertySource(value={"classpath:application-novellist.yml"})
@ConfigurationProperties(prefix = "novellist")
public class NovelList {

    private ArrayList<String> novelTypes = new ArrayList<>();

    private ArrayList<Novel> novels = new ArrayList<>();

    private List<Map<String, String>> list;

    public List<Map<String, String>> getList() {
        return list;
    }

    public void setList(List<Map<String, String>> list) {
        this.list = list;
    }

    public ArrayList<String> getNovelTypes() {
        return novelTypes;
    }

    public void setNovelTypes(ArrayList<String> novelTypes) {
        this.novelTypes = novelTypes;
    }

    public ArrayList<Novel> getNovels() {
        return novels;
    }

    public void setNovels(ArrayList<Novel> novels) {
        this.novels = novels;
    }

    @Override
    public String toString() {
        return "NovelList{" +
                "list=" + list +
                '}';
    }

    @PostConstruct
    public void novelArrayList() {
        List<Map<String, String>> mapList = this.getList();
        mapList.forEach((v) -> {
            this.novels.add(new Novel(v.get("name"), v.get("type"), v.get("master"), v.get("author"), v.get("description")));
            if (this.novelTypes.indexOf(v.get("type")) == -1) {
                this.novelTypes.add(v.get("type"));
            }
        });
    }

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer properties() {
//        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
//        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
//        yaml.setResources(new ClassPathResource("application-novellist.yml"));
//        configurer.setProperties(yaml.getObject());
//        return configurer;
//    }
}
