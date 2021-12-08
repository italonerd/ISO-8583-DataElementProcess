import config.DE108Configuration;
import handlers.DE108Handler;
import lombok.extern.slf4j.Slf4j;
import tlv.DE108;

@Slf4j
public class MainApplication {

  public static void main(String[] args) {
    if (args.length != 1) {
      log.error("Invalid command line, please inform a file to process.");
      System.exit(1);
    }
    try {
      final DE108Configuration de108Configuration = new DE108Configuration();
      final DE108 de108 = de108Configuration.loadDE108Properties();
      final DE108Handler handler = new DE108Handler(de108);
      final String response = handler.handleFile(args[0]);
      System.out.println(response);
    } catch (Exception e) {
      log.error("Error handling the file: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
