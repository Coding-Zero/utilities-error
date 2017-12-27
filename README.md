An unchecked exception which provides an simple way to build business related error messages.

### Raising an error

```
throw BusinessError.raise(BusinessError.DefaultErrors.NO_SUCH_ENTITY_FOUND)
        .message("No such student found for the given id.")       
        .details("schoolId", "1")
        .details("studentId", "123456")                    
        .build(); 
```
```
Exception in thread "main" com.codingzero.utilities.error.BusinessError: No such student found for the given id.
```

### Extending error types

```java
public enum Errors implements ErrorType {
    
    DUPLICATE_STUDENT_ID("DuplicateStudentId")
    ;

    private final String name;

    Errors(String name) {
        this.name = name;
    }

    public String toString() {
        return this.getName();
    }

    @Override
    public String getName() {
        return name;
    }

    public static Errors toErrorType(String name) {
        Errors[] errors = values();
        for (Errors error: errors) {
            if (name.equalsIgnoreCase(error.getName())) {
                return error;
            }
        }
        return null;
    }

}
```

```
throw BusinessError.raise(Errors.DUPLICATE_STUDENT_ID)
        .message("Student id has been assigned.")
        .details("schoolId", "1")
        .details("studentId", "666666")                    
        .build(); 
```