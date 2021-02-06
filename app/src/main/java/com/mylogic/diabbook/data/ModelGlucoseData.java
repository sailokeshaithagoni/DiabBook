package com.mylogic.diabbook.data;

public class ModelGlucoseData {
    private String txtDateVal;
    private String txtTimeVal;
    private String txtFoodStatusVal;
    private String txtGlucoseVal;

    public ModelGlucoseData(String txtDateVal, String txtTimeVal, String txtFoodStatusVal, String txtGlucoseVal) {
        this.txtGlucoseVal = txtGlucoseVal;
        this.txtTimeVal = txtTimeVal;
        this.txtFoodStatusVal = txtFoodStatusVal;
        this.txtDateVal = txtDateVal;
    }

    public String getTxtDateVal() {
        return txtDateVal;
    }

    public void setTxtDateVal(String txtDateVal) {
        this.txtDateVal = txtDateVal;
    }

    public String getTxtTimeVal() {
        return txtTimeVal;
    }

    public void setTxtTimeVal(String txtTimeVal) {
        this.txtTimeVal = txtTimeVal;
    }

    public String getTxtFoodStatusVal() {
        return txtFoodStatusVal;
    }

    public void setTxtFoodStatusVal(String txtFoodStatusVal) {
        this.txtFoodStatusVal = txtFoodStatusVal;
    }

    public String getTxtGlucoseVal() {
        return txtGlucoseVal;
    }

    public void setTxtGlucoseVal(String txtGlucoseVal) {
        this.txtGlucoseVal = txtGlucoseVal;
    }
}
