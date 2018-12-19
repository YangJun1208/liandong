package com.bwei.erjiliandong.bean;

import java.util.List;

public class ShopTypeProductBean {
    private String msg;
    private String code;
    private List<Data> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data{
        private String cid;
        private String name;
        private String pcid;

        private List<ProductData> list;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPcid() {
            return pcid;
        }

        public void setPcid(String pcid) {
            this.pcid = pcid;
        }

        public List<ProductData> getList() {
            return list;
        }

        public void setList(List<ProductData> list) {
            this.list = list;
        }

        public class ProductData{
            private String icon;
            private String name;
            private String pcid;
            private String pscid;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPcid() {
                return pcid;
            }

            public void setPcid(String pcid) {
                this.pcid = pcid;
            }

            public String getPscid() {
                return pscid;
            }

            public void setPscid(String pscid) {
                this.pscid = pscid;
            }
        }
    }
}
