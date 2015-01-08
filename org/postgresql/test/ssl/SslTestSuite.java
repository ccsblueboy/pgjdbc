package org.postgresql.test.ssl;

import java.io.FileInputStream;
import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

import org.postgresql.test.TestUtil;

public class SslTestSuite  extends TestSuite {

  private static void add(TestSuite suite, String param)
  {
    if (prop.getProperty(param,"").equals(""))
    {
      System.out.println("Skipping "+param+".");
    } else {
      suite.addTest(SslTest.getSuite(prop,param));
    }              
  }
  
  static Properties prop;
  /*
   * The main entry point for JUnit
   */
  public static TestSuite suite() throws Exception
  {
      TestSuite suite = new TestSuite();
      prop = new Properties();
      prop.load(new FileInputStream(System.getProperty("ssltest.properties")));
      add(suite,"ssloff8");
      add(suite,"sslhostnossl8");
      add(suite,"ssloff9");
      add(suite,"sslhostnossl9");

      String[] hostmode = {"sslhost","sslhostssl","sslhostsslcert","sslcert"};
      String[] certmode = {"gh","bh"};

      for (String aHostmode : hostmode) {
          for (String aCertmode : certmode) {
              add(suite, aHostmode + aCertmode + "8");
              add(suite, aHostmode + aCertmode + "9");
          }
      }
      
      TestUtil.initDriver();
      
      return suite;
  }
}
