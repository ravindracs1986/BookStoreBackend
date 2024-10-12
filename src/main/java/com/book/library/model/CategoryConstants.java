package com.book.library.model;

public enum CategoryConstants {
	
	Fantasy("Fantasy"), Mystery("Mystery") ,Classics("Classics"),Adventure("Adventure"),Poetry("Poetry");
    private String value;

    public String getResponse() {
        return value;
    }

    CategoryConstants(String value){
        this.value = value;
    }

}
