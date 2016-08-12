/*
* Copyright (C) 2012 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.vfw5.base.model;

/**
*
* @author kdvt_binhnt22@viettel.com.vn
* @version 1.0
* @since May 2012
*/
public abstract class BaseFWModel implements BaseFwModelInterface {
    private transient String colId = "ID";
    private transient String colName = "NAME";
    private transient String[] uniqueColumn = new String[0];
    
    public String getModelName() {
       return this.getClass().getSimpleName();
    }

    public String getColId() {
        return colId;
    }

    public void setColId(String colId) {
        this.colId = colId;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String[] getUniqueColumn() {
        return uniqueColumn;
    }

    public void setUniqueColumn(String[] uniqueColumn) {
        this.uniqueColumn = uniqueColumn;
    }

}
