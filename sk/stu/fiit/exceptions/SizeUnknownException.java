/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.exceptions;

/**
 *
 * @author Admin
 */
public class SizeUnknownException extends Exception {

    /**
     * Creates a new instance of <code>SizeUnknown</code> without detail
     * message.
     */
    public SizeUnknownException() {
    }

    /**
     * Constructs an instance of <code>SizeUnknown</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public SizeUnknownException(String msg) {
        super(msg);
    }
}
