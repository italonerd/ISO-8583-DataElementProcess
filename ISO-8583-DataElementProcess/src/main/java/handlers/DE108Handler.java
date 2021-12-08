package handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import model.Transaction;
import java.nio.file.Files;
import java.nio.file.Paths;
import tlv.DE108;
import tlv.TlvDE108Parser;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Slf4j
public class DE108Handler {
  private DE108 de108;

  public String handleFile(final String fileFullPath) throws IOException {
    final ArrayList<Transaction> approvedTransactions = new ArrayList<>();

    try {
      Files.lines(Paths.get(FilenameUtils.normalize(fileFullPath)))
          .forEach(
              line -> {
                final TlvDE108Parser tlvDE108Parser = new TlvDE108Parser(de108);
                final Transaction transaction = tlvDE108Parser.parse(line);
                validateTransaction(transaction, approvedTransactions);
              });
      return buildResponse(approvedTransactions);
    } catch (Exception e) {
      log.error("Error handling the DE108 file: " + e.getMessage());
      throw e;
    }
  }

  private String buildResponse(final ArrayList<Transaction> transactions)
      throws JsonProcessingException {
    try {
      return new ObjectMapper().writeValueAsString(transactions);
    } catch (JsonProcessingException e) {
      log.error("Error processing json response: " + e.getMessage());
      throw e;
    }
  }

  private void validateTransaction(
      final Transaction transaction, final ArrayList<Transaction> approvedTransactions) {
    if (!de108
        .getCryptoCurrencyPurpose()
        .equals(transaction.getReferenceData().getTransactionPurpose())) {
      approvedTransactions.add(transaction);
    }
  }
}
