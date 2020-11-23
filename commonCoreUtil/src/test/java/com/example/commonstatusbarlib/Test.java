package com.example.commonstatusbarlib;

import com.google.gson.Gson;

/**
 * @author 许阳
 * 2020/10/20 18:25
 **/
class Test {

    static String a="{\"name\":\"\",\"address\":null}";
    public static void main(String[] args) {
        Gson gson = new Gson();
        CCC ccc = gson.fromJson(a, CCC.class);
        System.out.println(ccc);
        System.out.println(ccc.name);
        System.out.println(ccc.address);
    }



    class CCC{
        private String name;
        private String address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
