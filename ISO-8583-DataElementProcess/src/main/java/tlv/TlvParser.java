package tlv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@AllArgsConstructor
@Getter
@Setter
public class TlvParser {

  public final int tagDigits;
  public final int lengthDigits;

  public final HashMap<String, String> parse(final String tlvMessage) {
    final HashMap<String, String> tlvMap = new HashMap<>();
    int cursor = 0;
    while (tlvMessage.length() > cursor) {
      final String tag = tlvMessage.substring(cursor, cursor + tagDigits);
      final String length =
          tlvMessage.substring(cursor + tagDigits, cursor + tagDigits + lengthDigits);
      final String value =
          tlvMessage.substring(
              cursor + tagDigits + lengthDigits,
              cursor + tagDigits + lengthDigits + Integer.parseInt(length));
      cursor += tagDigits + lengthDigits + Integer.parseInt(length);
      tlvMap.put(tag, value);
    }

    return tlvMap;
  }
}
