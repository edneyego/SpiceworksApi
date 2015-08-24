package org.pcarrasco.spiceworksapi.service;

/**
 * Response from the service methods which includes the affected entity and validation and error states
 * (which for now if true or false)
 */
public class ServiceResponse<T> {

    private T data;
    private boolean hasValidationError;
    private String validationMessage;
    private boolean hasException;
    private String exceptionMessage;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean getHasValidationError() {
        return hasValidationError;
    }

    public void setHasValidationError(boolean hasValidationError) {
        this.hasValidationError = hasValidationError;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public boolean getHasException() {
        return hasException;
    }

    public void setHasException(boolean hasException) {
        this.hasException = hasException;
    }

    public String getExceptionMessagexceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
