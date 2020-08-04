package com.easyrules.demo.rules;

public class YesOrNo {

    public static YesOrNo.Yes yes = new Yes("yes");
    public static YesOrNo.No no = new No("no");

    private YesNo yesNo;

    public YesOrNo(YesNo yesNo) {
        this.yesNo = yesNo;
    }

    public void setYesNo(YesNo yesNo) {
        this.yesNo = yesNo;
    }

    public YesNo getYesNo() {
        return this.yesNo;
    }

    public static class YesNo {
        private String name;

        YesNo(String name) {
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
    }

    public static class Yes extends YesNo {
        Yes(String name) {
            super(name);
        }
    }

    public static class No extends YesNo {
        No(String name) {
            super(name);
        }
    }
}
