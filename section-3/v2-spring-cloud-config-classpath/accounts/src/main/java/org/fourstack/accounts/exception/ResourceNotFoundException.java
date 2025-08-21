package org.fourstack.accounts.exception;

public class ResourceNotFoundException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param resource   Name of the resource
     * @param fieldName  Name of the field
     * @param fieldValue Value of the field
     */
    public ResourceNotFoundException(String resource, String fieldName, String fieldValue) {
        super(String.format("%s not found with the given input data %s : %s", resource, fieldName, fieldValue));
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param resource   Name of the resource
     * @param fieldName  Name of the field
     * @param fieldValue Value of the field
     * @param cause      the cause (which is saved for later retrieval by the
     *                   {@link #getCause()} method).  (A {@code null} value is
     *                   permitted, and indicates that the cause is nonexistent or
     *                   unknown.)
     */
    public ResourceNotFoundException(String resource, String fieldName, String fieldValue, Throwable cause) {
        this(String.format("%s not found with the given input data %s : %s", resource, fieldName, fieldValue), cause);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A {@code null} value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
