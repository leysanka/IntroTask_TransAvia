public class WrongPageException extends Throwable {
   private String pageMessage;


    WrongPageException(String pageMessage) {
        this.pageMessage = pageMessage;
    }

    public WrongPageException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return this.pageMessage + "Exception cause: " + super.getCause();
    }
}