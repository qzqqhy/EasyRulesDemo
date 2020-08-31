package com.easyrules.demo.data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@PropertySource(value={"classpath:application-api.yml"})
@ConfigurationProperties(prefix = "apilist")
public class APIList {

    private ArrayList<ApiItem> itemlist = new ArrayList<>();

    private List<Map<String, String>> apiitems;

    public ArrayList<ApiItem> getItemlist() {
        return itemlist;
    }

    public void setItemlist(ArrayList<ApiItem> itemlist) {
        this.itemlist = itemlist;
    }

    public List<Map<String, String>> getApiitems() {
        return apiitems;
    }

    public void setApiitems(List<Map<String, String>> apiitems) {
        this.apiitems = apiitems;
    }

    @Override
    public String toString() {
        return "APIList{" +
                "itemlist=" + itemlist +
                ", apiitems=" + apiitems +
                '}';
    }

    @PostConstruct
    public void init() {
        List<Map<String, String>> mapList = this.getApiitems();
        mapList.forEach((v) -> {
            this.itemlist.add(new ApiItem(v.get("code"), v.get("url"), v.get("appid"), v.get("appSecret")));
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
