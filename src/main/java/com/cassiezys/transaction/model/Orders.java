package com.cassiezys.transaction.model;

public class Orders {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.id
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.creator
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    private Long creator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.outerid
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    private Long outerid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.outer_title
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    private String outerTitle;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.receiver
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    private Long receiver;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.gmt_create
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.gmt_modified
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    private Long gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.amount
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    private Integer amount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.status
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.address
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    private String address;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.tele
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    private Long tele;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.id
     *
     * @return the value of orders.id
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.id
     *
     * @param id the value for orders.id
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.creator
     *
     * @return the value of orders.creator
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public Long getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.creator
     *
     * @param creator the value for orders.creator
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public void setCreator(Long creator) {
        this.creator = creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.outerid
     *
     * @return the value of orders.outerid
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public Long getOuterid() {
        return outerid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.outerid
     *
     * @param outerid the value for orders.outerid
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public void setOuterid(Long outerid) {
        this.outerid = outerid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.outer_title
     *
     * @return the value of orders.outer_title
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public String getOuterTitle() {
        return outerTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.outer_title
     *
     * @param outerTitle the value for orders.outer_title
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public void setOuterTitle(String outerTitle) {
        this.outerTitle = outerTitle == null ? null : outerTitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.receiver
     *
     * @return the value of orders.receiver
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public Long getReceiver() {
        return receiver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.receiver
     *
     * @param receiver the value for orders.receiver
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.gmt_create
     *
     * @return the value of orders.gmt_create
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.gmt_create
     *
     * @param gmtCreate the value for orders.gmt_create
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.gmt_modified
     *
     * @return the value of orders.gmt_modified
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public Long getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.gmt_modified
     *
     * @param gmtModified the value for orders.gmt_modified
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.amount
     *
     * @return the value of orders.amount
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.amount
     *
     * @param amount the value for orders.amount
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.status
     *
     * @return the value of orders.status
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.status
     *
     * @param status the value for orders.status
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.address
     *
     * @return the value of orders.address
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.address
     *
     * @param address the value for orders.address
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.tele
     *
     * @return the value of orders.tele
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public Long getTele() {
        return tele;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.tele
     *
     * @param tele the value for orders.tele
     *
     * @mbg.generated Sun Mar 22 15:13:47 CST 2020
     */
    public void setTele(Long tele) {
        this.tele = tele;
    }
}