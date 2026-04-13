package com.exemplo.quectel.ueCommands;

/*This command disables or enables the use of final result code +CME ERROR: <err> as the indication
for errors. When enabled, errors will trigger +CME ERROR: <err> final result code instead of ERROR.
*/

public class ErrorVerboseOnCommand extends ExtendedCommand {
    public ErrorVerboseOnCommand() {
        super("+CMEE=2");
        this.typeOfAnswer = "OK";
    }

}

/*
 * Integer type. Whether to enable result code.
 * 0
 * Disable result code and use ERROR instead.
 * 1
 * Enable result code and use numeric values.
 * 2
 * Enable result code and use verbose values.
 */