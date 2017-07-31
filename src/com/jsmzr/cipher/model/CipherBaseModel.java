package com.jsmzr.cipher.model;

public class CipherBaseModel {
    private int type;

    public CipherBaseModel(int type) {
        this.type = type;
    }

    public CipherBaseModel() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
