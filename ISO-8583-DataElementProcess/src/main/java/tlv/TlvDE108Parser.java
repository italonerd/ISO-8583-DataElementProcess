package tlv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Account;
import model.Transaction;
import model.TransactionReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TlvDE108Parser {
  public static final Logger log = LoggerFactory.getLogger(TlvDE108Parser.class);
  private TlvParser tlvParser;
  private DE108 de108;

  public TlvDE108Parser(final DE108 de108) {
    this.tlvParser = new TlvParser(de108.getTagSize(), de108.getLengthSize());
    this.de108 = de108;
  }

  public Transaction parse(final String tlvMessage) {
    final HashMap<String, String> subModules =
        tlvParser.parse(tlvMessage.substring(de108.getDataLength()));

    final Transaction transaction = new Transaction();
    transaction.setReceiver(parseAccount(subModules.get(de108.getSubModuleOne())));
    transaction.setSender(parseAccount(subModules.get(de108.getSubModuleTwo())));
    transaction.setReferenceData(parseReferenceData(subModules.get(de108.getSubModuleThree())));

    return transaction;
  }

  private Account parseAccount(final String tlvMessage) {
    final HashMap<String, String> accountMap = tlvParser.parse(tlvMessage);

    final Account receiver = new Account();
    receiver.setFirst(accountMap.get(de108.getAccountFirst()));
    receiver.setMiddle(accountMap.get(de108.getAccountMiddle()));
    receiver.setLast(accountMap.get(de108.getAccountLast()));
    receiver.setCity(accountMap.get(de108.getAccountCity()));
    receiver.setState(accountMap.get(de108.getAccountState()));
    receiver.setAccount(Long.valueOf(accountMap.get(de108.getAccountNumber())));

    return receiver;
  }

  private TransactionReference parseReferenceData(final String tlvMessage) {
    final HashMap<String, String> referenceTlvMap = tlvParser.parse(tlvMessage);

    final TransactionReference transactionReference = new TransactionReference();
    transactionReference.setFundingSource(referenceTlvMap.get(de108.getFundingResource()));
    transactionReference.setTransactionPurpose(referenceTlvMap.get(de108.getTransactionPurpose()));

    return transactionReference;
  }
}
