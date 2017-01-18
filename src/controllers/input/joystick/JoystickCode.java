package controllers.input.joystick;

public enum JoystickCode {
    UP(0x1, "Up"),
    DOWN(0x2, "Down"),
    RIGHT(0x3, "Right"),
    LEFT(0x4, "Left"),
    PRESS(0x5, "Press");

    private final String name;
    private final int code;

    private JoystickCode(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
