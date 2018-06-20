package com.tianque.pojo;

import java.sql.Timestamp;

public class Sale extends BasePojo {

    private static final long serialVersionUID = -920084535686806674L;

    public static final String CUSTOMER_TYPE_PERSON = "person";
    public static final String CUSTOMER_TYPE_COMPANY = "company";

    private String name;
    private Float price;
	private Customer customer;
    private String custname;
    private String progress;
    private Timestamp createtime;
    private String lasttime;
    private String successtime;
    private String type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }



    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }



	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getSuccesstime() {
        return successtime;
    }

    public void setSuccesstime(String successtime) {
        this.successtime = successtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
