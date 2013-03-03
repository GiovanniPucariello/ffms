/**
 *
 *   File Name       :  EncodeUtil.java
 *   Date Created    :  2006-7-7 14:00:39
 *   Last Changed By :  $Author: HuangFei $
 *   Last Changed On :  $Date: 2006-7-7 14:00:39
 *   Revision        :  $Revision: 
 *   Release         :  $Name:  
 *   Description     :  *  
 *   PracBiz Pte Ltd.  Copyright (c) 2004-2005.  All Rights Reserved.
 *
 **/

package com.oyl.base.util;

import java.io.IOException;
import java.security.MessageDigest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class EncodeUtil
{
    private final static Log logger = LogFactory.getLog(EncodeUtil.class);
    
    private static byte[] xorKey = {123, 51, 17, 31, 63, 23, 117, 83};

    // ~ Methods
    // ================================================================

    /**
     * Encode a string using algorithm specified in web.xml and return the
     * resulting encrypted password. If exception, the plain credentials string
     * is returned
     * 
     * @param password Password or other credentials to use in authenticating
     *            this username
     * @param algorithm Algorithm used to do the digest
     * 
     * @return encypted password based on the algorithm.
     */
    public static String encodePassword(String password, String algorithm)
    {
        byte[] unencodedPassword = password.getBytes();

        MessageDigest md = null;

        try
        {
            // first create an instance, given the provider
            md = MessageDigest.getInstance(algorithm);
        }
        catch (Exception e)
        {
            logger.error("EncodeUtil ", e.getCause());

            return password;
        }

        md.reset();

        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update(unencodedPassword);

        // now calculate the hash
        byte[] encodedPassword = md.digest();

        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < encodedPassword.length; i++)
        {
            if ((encodedPassword[i] & 0xff) < 0x10)
            {
                buf.append("0");
            }

            buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
        }

        return buf.toString();
    }

    /**
     * Encode a string using Base64 encoding. Used when storing passwords as
     * cookies.
     * 
     * This is weak encoding in that anyone can use the decodeString routine to
     * reverse the encoding.
     * 
     * @param str
     * @return String
     */
    public static String encodeString(String str)
    {
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        return encoder.encodeBuffer(str.getBytes()).trim();
    }

    /**
     * Decode a string using Base64 encoding.
     * 
     * @param str
     * @return String
     */
    public static String decodeString(String str)
    {
        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
        try
        {
            return new String(dec.decodeBuffer(str));
        }
        catch (IOException io)
        {
            logger.error("EncodeUtil ", io.getCause());
            
            throw new RuntimeException(io.getMessage(), io.getCause());
        }
    }
    
    /**
     * Encode the provided string.
     * @author Chia Meng Choo
     *
     * @param inStr (IN) - String to be encoded.
     *
     * @return String - Encoded string.
     **/
     public static String encodeStr(String inStr)
     {
        byte[] inStrBytes = inStr.getBytes();
        
        int keyLength = xorKey.length;
        for (int i=0; i<inStrBytes.length; i++)
        {
            int j = i % keyLength;
            inStrBytes[i] = (byte)(inStrBytes[i] ^ xorKey[j]);
        }
        
        String result = "";
        Base64 base64 = new Base64();
        String str = base64.encode(inStrBytes);
        String magicStr = "/01234ZYXWVUTSRQPONMLKJIHGFEDCBA98765abcdefghijklmnopqrstuvwxyz";
        int magicLen = magicStr.length();
        int strLen = str.length();
        char[] charArr = str.toCharArray();
        int i;
        for (i=0; i<(strLen/4)-1; i++)
        {
            result = result + str.substring(i*4, i*4+4) +
                magicStr.charAt(((charArr[i*4]-'/')+31)%magicLen) +
                magicStr.charAt(((charArr[i*4+1]-'/')+7)%magicLen) +
                magicStr.charAt(((charArr[i*4+2]-'/')+23)%magicLen) +
                magicStr.charAt(((charArr[i*4+3]-'/')+11)%magicLen);
        }
        return (result + str.substring(i*4, i*4+4));
     }

    /**
     * Decode the provided string.
     * @author Chia Meng Choo
     *
     * @param inStr (IN) - String to be decoded.
     *
     * @return String - Decoded string.
     **/
     public static String decodeStr(String inStr)
     {
        StringBuffer result = new StringBuffer();
        int strLen = inStr.length();
        for (int i=0; i<(strLen/4); i=i+2)
        {
            result.append(inStr.substring(i*4, i*4+4));
        }
        
        byte[] decodeBytes = Base64.decode(result.toString());
        
        int keyLength = xorKey.length;
        for (int i=0; i<decodeBytes.length; i++)
        {
            int j = i % keyLength;
            decodeBytes[i] = (byte)(decodeBytes[i] ^ xorKey[j]);
        }
        
        return new String(decodeBytes);
     }
     
    public static void main(String[] args)
    {
        String source = "password";
        
        System.out.println(EncodeUtil.encodeString(source));
        System.out.println(EncodeUtil.encodePassword(source, "MD5"));
    }
}
