package app.exceptions;

import app.dto.TargetDTO;

public class TargetException extends Exception {

    private TargetDTO targetDTO;
    private int status;

    public TargetException(String message) {
        super(message);
    }

    public TargetException(String message, TargetDTO targetDTO, int status) {
        super(message);
        this.targetDTO = targetDTO;
        this.status = status;
    }

    public TargetDTO getTargetDTO() {
        return targetDTO;
    }

    public int getStatus() {
        return status;
    }
}
