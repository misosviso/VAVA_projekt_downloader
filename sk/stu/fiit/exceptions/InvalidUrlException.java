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
public class InvalidUrlException extends Exception {

    /**
     * Creates a new instance of <code>InvalidUrlException</code> without detail
     * message.
     */
    public InvalidUrlException() {
    }

    /**
     * Constructs an instance of <code>InvalidUrlException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidUrlException(String msg) {
        super(msg);
    }
}
