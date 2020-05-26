package com.kvuljanko.simpleNotes.belot.DataClass;

import java.io.Serializable;

public class ConfigData implements Serializable {

    private String belotName1;
    private String belotName2;
    private String belotName3;
    private String belotName4;


    public ConfigData() {
    }

    public String getBelotName1() {
        return belotName1;
    }

    public void setBelotName1(String belotName1) {
        this.belotName1 = belotName1;
    }

    public String getBelotName2() {
        return belotName2;
    }

    public void setBelotName2(String belotName2) {
        this.belotName2 = belotName2;
    }

    public String getBelotName3() {
        return belotName3;
    }

    public void setBelotName3(String belotName3) {
        this.belotName3 = belotName3;
    }

    public String getBelotName4() {
        return belotName4;
    }

    public void setBelotName4(String belotName4) {
        this.belotName4 = belotName4;
    }
}
