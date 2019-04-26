package base.appstore.exceptions;

public class AppNotFoundException extends RuntimeException {
  AppNotFoundException(Long id) {
    super("Could not find app " + id);
  }
}
