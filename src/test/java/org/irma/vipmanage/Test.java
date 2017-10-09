package org.irma.vipmanage;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class Test {

    public static void main(String[] args) {
        try {
            String conf = IOUtils.toString(Test.class.getResourceAsStream("httpd.conf"));
            StringTokenizer tokenizer = new StringTokenizer(conf);
            while (tokenizer.hasMoreTokens()) {
                System.out.println(tokenizer.nextToken());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
