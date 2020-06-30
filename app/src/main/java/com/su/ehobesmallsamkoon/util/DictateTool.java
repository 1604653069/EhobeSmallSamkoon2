package com.su.ehobesmallsamkoon.util;

public class DictateTool {

    /**
     * 功率值换算
     */
    public static String getSetPowerStr(String p) {
        // BB 00 B6 00 02 07 D0 8F 7E
        String ret = "BB 00 B6 00 02";
        int power = Integer.parseInt(p) * 100;
        power -= 5;
        int _lByte = power % 256;
        int _hByte = (power / 256);
        String _slByte = Integer.toHexString(_lByte).toUpperCase();
        if (_slByte.length() == 1)
            _slByte = "0" + _slByte;
        String _shByte = Integer.toHexString(_hByte).toUpperCase();
        if (_shByte.length() == 1)
            _shByte = "0" + _shByte;
        ret = ret + " " + _shByte + " " + _slByte;
        ret = ret + getChecksum(ret) + " 7E";
        return ret;
    }

    /**
     * 解调器参数换算
     */
    public static String getSetDetuner(String d) {
        String ret = "BB 00 F0 00 04 03 06";
        String _shByte = Integer.toHexString(Integer.parseInt(d)).toUpperCase();
        if (_shByte.length() == 1)
            _shByte = "0" + _shByte;
        ret = ret + " " + _shByte + " B0";
        ret = ret + getChecksum(ret) + " 7E";
        return ret;
    }

    /**
     * 校验位换算
     */
    public static String getChecksum(String strInfo) {
        int allsum = 0;
        String[] _str = strInfo.split(" ");
        for (int i = 1; i < _str.length; i++) {
            allsum = allsum + ChangeUtils.HexToInt(_str[i]);
        }
        String rec = Integer.toHexString(allsum % 256).toUpperCase();
        if (rec.length() == 1)
            rec = "0" + rec;
        return " " + rec;
    }
    
}
