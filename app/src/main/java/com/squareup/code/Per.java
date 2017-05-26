package com.squareup.code;

import java.util.List;

/**
 * Created by Administrator on 2017/05/25 0025.
 */

public class Per {


    /**
     * data : [{"content":"最新","count":110},{"content":"天然呆","count":109}]
     * result : 0
     */

    private int result;
    private List<DataBean> data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public  class DataBean {
        /**
         * content : 最新
         * count : 110
         */

        private String content;
        private int count;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
