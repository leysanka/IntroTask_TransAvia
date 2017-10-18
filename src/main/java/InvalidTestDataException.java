public class InvalidTestDataException extends Throwable {
    private String testDataError;

    InvalidTestDataException(String s) {
        this.testDataError = s;
    }

    @Override
    public String getMessage() {
        return this.testDataError + super.getMessage();
    }
}
