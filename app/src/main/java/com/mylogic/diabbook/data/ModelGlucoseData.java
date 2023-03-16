package com.mylogic.diabbook.data;

public class ModelGlucoseData {
    private String _ID;
    private String txtDateVal;
    private String txtTimeVal;
    private String txtMealStatusVal;

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    private String txtFoodStatusVal;
    private String txtGlucoseVal;

    public String getTxtMealStatusVal() {
        return txtMealStatusVal;
    }

    public void setTxtMealStatusVal(String txtMealStatusVal) {
        this.txtMealStatusVal = txtMealStatusVal;
    }

    public ModelGlucoseData(String _ID,String txtDateVal, String txtTimeVal, String txtMealStatusVal, String txtFoodStatusVal, String txtGlucoseVal) {
        this._ID = _ID;
        this.txtGlucoseVal = txtGlucoseVal;
        this.txtTimeVal = txtTimeVal;
        this.txtMealStatusVal = txtMealStatusVal;
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
