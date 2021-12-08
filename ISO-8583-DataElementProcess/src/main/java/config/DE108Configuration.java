package config;

import lombok.extern.slf4j.Slf4j;
import tlv.DE108;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class DE108Configuration {

  private static final String DE108_PROPERTIES = "/de108.properties";

  public DE108 loadDE108Properties() throws IOException {
    try {
      InputStream inputStream = getClass().getResourceAsStream(DE108_PROPERTIES);

      final Properties de108Props = new Properties();
      de108Props.load(inputStream);

      final DE108 de108 = new DE108();

      de108.setTagSize(Integer.parseInt(de108Props.getProperty("size.tag")));
      de108.setLengthSize(Integer.parseInt(de108Props.getProperty("size.length")));
      de108.setDataLength(Integer.parseInt(de108Props.getProperty("size.data.length")));

      de108.setSubModuleOne(de108Props.getProperty("submodule.one"));
      de108.setSubModuleTwo(de108Props.getProperty("submodule.two"));
      de108.setSubModuleThree(de108Props.getProperty("submodule.three"));

      de108.setAccountFirst(de108Props.getProperty("account.first"));
      de108.setAccountMiddle(de108Props.getProperty("account.middle"));
      de108.setAccountLast(de108Props.getProperty("account.last"));
      de108.setAccountCity(de108Props.getProperty("account.city"));
      de108.setAccountState(de108Props.getProperty("account.state"));
      de108.setAccountNumber(de108Props.getProperty("account.number"));

      de108.setFundingResource(de108Props.getProperty("funding.resource"));
      de108.setTransactionPurpose(de108Props.getProperty("transaction.purpose"));

      de108.setCryptoCurrencyPurpose(de108Props.getProperty("purpose.crypto"));

      return de108;
    } catch (Exception e) {
      log.error("Error handling the file: " + e.getMessage());
      throw e;
    }
  }
}
