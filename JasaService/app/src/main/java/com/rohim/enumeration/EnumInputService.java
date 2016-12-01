package com.rohim.enumeration;

/**
 * Created by Nurochim on 19/10/2016.
 */

public enum EnumInputService {
    SpinnerInput("SI") {

        @Override
        public String toString() {
            return ("Spinner");
        }
    },
    TextShort("TS") {

        @Override
        public String toString() {
            return ("Text Short");
        }
    },
    TextLong("TL") {

        @Override
        public String toString() {
            return ("TextLong");
        }
    },
    Map("MP") {

        @Override
        public String toString() {
            return ("Map");
        }
    },
    TextAutomatic("TA") {

        @Override
        public String toString() {
            return ("TextAutomatic");
        }
    };
    private String val;
    EnumInputService(String val) {
        this.val = val;
    }

    public static EnumInputService valOf(String val) {

        if (val.equals(EnumInputService.SpinnerInput)) {
            return EnumInputService.SpinnerInput;
        }else if (val.equals(EnumInputService.TextShort)) {
            return EnumInputService.TextShort;
        }else if (val.equals(EnumInputService.TextLong)) {
            return EnumInputService.TextLong;
        }else if (val.equals(EnumInputService.Map)) {
            return EnumInputService.TextAutomatic;
        }else if (val.equals(EnumInputService.TextAutomatic)) {
            return EnumInputService.TextAutomatic;
        }else {
            return null;
        }

    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
