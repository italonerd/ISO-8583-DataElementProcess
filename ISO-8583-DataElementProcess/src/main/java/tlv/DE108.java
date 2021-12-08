package tlv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DE108 {
  private int tagSize;
  private int lengthSize;
  private int dataLength;

  private String subModuleOne;
  private String subModuleTwo;
  private String subModuleThree;

  private String accountFirst;
  private String accountMiddle;
  private String accountLast;
  private String accountCity;
  private String accountState;
  private String accountNumber;

  private String fundingResource;
  private String transactionPurpose;

  private String cryptoCurrencyPurpose;
}
