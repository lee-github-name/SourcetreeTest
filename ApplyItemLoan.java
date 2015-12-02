package com.house365.newhouse.model;

import java.util.List;

import com.house365.core.bean.BaseBean;

public class ApplyItemLoan  extends BaseBean {
	private String a_id;
	private String a_amount;
	
	private String a_loain_id;
	private String a_loan_name;
	private String a_status;
	private String clientname;
	private String clientphone;
	private String banklogo;
	private String bankname;
	private String l_moneypay;
	private String a_deadline;
	private String bankusername;
	private String bankuserphone;
	private String creditcard_num;
	private List<ApplyProgress> applyprogress;
	public String getA_id() {
		return a_id;
	}
	public void setA_id(String a_id) {
		this.a_id = a_id;
	}
	public String getA_amount() {
		return a_amount;
	}
	public void setA_amount(String a_amount) {
		this.a_amount = a_amount;
	}
	public String getA_loain_id() {
		return a_loain_id;
	}
	public void setA_loain_id(String a_loain_id) {
		this.a_loain_id = a_loain_id;
	}
	public String getA_loan_name() {
		return a_loan_name;
	}
	public void setA_loan_name(String a_loan_name) {
		this.a_loan_name = a_loan_name;
	}
	public String getClientname() {
		return clientname;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	public String getBanklogo() {
		return banklogo;
	}
	public void setBanklogo(String banklogo) {
		this.banklogo = banklogo;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getL_moneypay() {
		return l_moneypay;
	}
	public void setL_moneypay(String l_moneypay) {
		this.l_moneypay = l_moneypay;
	}
	public String getA_deadline() {
		return a_deadline;
	}
	public void setA_deadline(String a_deadline) {
		this.a_deadline = a_deadline;
	}
	public String getBankusername() {
		return bankusername;
	}
	public void setBankusername(String bankusername) {
		this.bankusername = bankusername;
	}
	public String getA_status() {
		return a_status;
	}
	public void setA_status(String a_status) {
		this.a_status = a_status;
	}
	public String getBankuserphone() {
		return bankuserphone;
	}
	public void setBankuserphone(String bankuserphone) {
		this.bankuserphone = bankuserphone;
	}
	public String getClientphone() {
		return clientphone;
	}
	public void setClientphone(String clientphone) {
		this.clientphone = clientphone;
	}
	public String getCreditcard_num() {
		return creditcard_num;
	}
	public void setCreditcard_num(String creditcard_num) {
		this.creditcard_num = creditcard_num;
	}
	public List<ApplyProgress> getApplyprogress() {
		return applyprogress;
	}
	public void setApplyprogress(List<ApplyProgress> applyprogress) {
		this.applyprogress = applyprogress;
	}
	
	
}
