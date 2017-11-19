package com.andyadc.scaffold.rabbitconsumer;

import java.io.Serializable;
import java.util.Date;

/**
 * @author andaicheng
 * @since 2017/11/17
 */
public class Order implements Serializable {

    private static final long serialVersionUID = -5747626377899952961L;

    private String ordId;
    private String ordName;
    private Integer num;
    private Date time;

    public Order() {
    }

    public Order(String ordId, String ordName, Integer num, Date time) {
        this.ordId = ordId;
        this.ordName = ordName;
        this.num = num;
        this.time = time;
    }

    public String getOrdId() {
        return ordId;
    }

    public void setOrdId(String ordId) {
        this.ordId = ordId;
    }

    public String getOrdName() {
        return ordName;
    }

    public void setOrdName(String ordName) {
        this.ordName = ordName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Order{" +
                "ordId='" + ordId + '\'' +
                ", ordName='" + ordName + '\'' +
                ", num=" + num +
                ", time=" + time +
                '}';
    }
}
