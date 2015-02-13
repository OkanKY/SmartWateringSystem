package com.ok.attribute;

public class UserField {
	private String FieldName =null;
	private Integer FieldID =null;
	public String getFieldName() {
		return FieldName;
	}
	public void setFieldName(String fieldName) {
		FieldName = fieldName;
	}
	public Integer getFieldID() {
		return FieldID;
	}
	public void setFieldID(Integer fieldID) {
		FieldID = fieldID;
	}
	@Override
	public String toString() {
		        return this.FieldName + "";
	 }
}
