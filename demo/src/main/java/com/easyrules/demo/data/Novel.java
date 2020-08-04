package com.easyrules.demo.data;

public class Novel {
//    - name: 笑傲江湖
//    type: 武侠
//    master: 令狐冲
//    author: 金庸
//    description: 小说以通过叙述华山派大弟子令狐冲的经历，反映了武林各派争霸夺权的历程。
    private String name;
    private String type;
    private String master;
    private String author;
    private String description;

    public Novel(String name, String type, String master, String author, String description) {
        this.name = name;
        this.type = type;
        this.master = master;
        this.author = author;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
