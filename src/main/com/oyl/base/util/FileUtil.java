package com.oyl.base.util;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil
{
    public static void writeByteToDisk(byte[] data, String targetFile)
            throws IOException

    {
        FileOutputStream out = null;

        try
        {
            out = new FileOutputStream(targetFile);
            out.write(data);
            out.flush();
        }
        finally
        {
            if (out != null)
            {
                out.close();
                out = null;
            }
        }
    }
}
