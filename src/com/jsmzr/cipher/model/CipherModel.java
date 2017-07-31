package com.jsmzr.cipher.model;

import org.jetbrains.annotations.NotNull;

public class CipherModel extends CipherBaseModel{
    private String alg;
    private String work;
    private String pend;
    private String detail;
    private int keySize;
    private int ivSize;



    public CipherModel(@NotNull String detail) {
        super(1);
        this.detail = detail.substring(0, detail.indexOf("("));
        String[] tmp = detail.split("/");
        switch (tmp.length) {
            case 3:
                this.pend = tmp[2].substring(0, tmp[2].indexOf("("));
                int tmpLen = Integer.parseInt(tmp[2].substring(tmp[2].indexOf("(")+1, tmp[2].length()-1)) / 8;
                this.keySize = tmpLen + (tmpLen % 8 != 0 ? 8-tmpLen%8 : 0);
            case 2:
                this.work = tmp[1];
            case 1:
                this.alg = tmp[0];
                break;
            default:
                break;
        }
        this.ivSize = "DESede".equals(this.alg) ? 8 : this.keySize;
    }

    private CipherModel() {
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getPend() {
        return pend;
    }

    public void setPend(String pend) {
        this.pend = pend;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getKeySize() {
        return keySize;
    }

    public int getIvSize() {
        return ivSize;
    }

    public void setIvSize(int ivSize) {
        this.ivSize = ivSize;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }
}
