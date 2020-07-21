package com.su.ehobesmallsamkoon.util;

public class ChangeUtils {
    //-------------------------------------------------------
    // 判断奇数或偶数，位运算，最后一位是1则为奇数，为0是偶数
    public static int isOdd(int num) {
        return num & 1;
    }

    //-------------------------------------------------------
    //Hex字符串转int
    public static int HexToInt(String inHex) {
        return Integer.parseInt(inHex, 16);
    }

    //-------------------------------------------------------
    //Hex字符串转byte
    public static byte HexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    //-------------------------------------------------------
    //1字节转2个Hex字符
    public static String Byte2Hex(Byte inByte) {
        return String.format("%02x", new Object[]{inByte}).toUpperCase();
    }

    //-------------------------------------------------------
    //字节数组转转hex字符串
    public static String ByteArrToHex(byte[] inBytArr) {
        StringBuilder strBuilder = new StringBuilder();
        for (byte valueOf : inBytArr) {
            strBuilder.append(Byte2Hex(Byte.valueOf(valueOf)));
        }
        return strBuilder.toString();
    }

    //-------------------------------------------------------
    //字节数组转转hex字符串，可选长度
    public static String ByteArrToHex(byte[] inBytArr, int offset, int byteCount) {
        StringBuilder strBuilder = new StringBuilder();
        int j = byteCount;
        for (int i = offset; i < j; i++) {
            strBuilder.append(Byte2Hex(Byte.valueOf(inBytArr[i])));
        }
        return strBuilder.toString();
    }

    //-------------------------------------------------------
    //把hex字符串转字节数组
    public static byte[] HexToByteArr(String inHex) {
        byte[] result;
        int hexlen = inHex.length();
        if (isOdd(hexlen) == 1) {
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = HexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }
    public static String convertCharToString(byte[] buf, int len) {
        String barcodeMsg = "";
        for (int i = 0; i < len; i++) {
            if (buf[i] != 0) {
                if (buf[i] == '\n') {
                    barcodeMsg += '\n';
                } else if (buf[i] == '\r') {
                    barcodeMsg += '\r';
                } else if (buf[i] == '\t') {
                    barcodeMsg += '\t';
                } else if (buf[i] == 29) {

                } else {
                    barcodeMsg += (char) (buf[i]);
                } // Asc码转换底层返回的字节数组数据
            }
        }
        return barcodeMsg;
    }
    public static long SumCheck(byte[] msg) {
        byte [] check = new byte[21];
        System.arraycopy(msg,1,check,0,21);
        long mSum = 0;

        /** 逐Byte添加位数和 */
        for (byte byteMsg : check) {
            long mNum = ((long) byteMsg >= 0) ? (long) byteMsg : ((long) byteMsg + 256);
            mSum += mNum;
        } /** end of for (byte byteMsg : msg) */


        return mSum;
    }

    public static String integerToHex(int id, int length) {
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toHexString(id));
        int length1 = sb.length();
        if (length - length1 > 0) {
            for (int i = 0; i < length - length1; i++) {
                sb.insert(0, "0");
            }
        }
        return sb.toString().toUpperCase();
    }
}
