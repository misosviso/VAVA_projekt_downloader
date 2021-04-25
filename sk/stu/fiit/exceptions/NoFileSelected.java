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
public class NoFileSelected extends Exception {

    /**
     * Creates a new instance of <code>NoFileSelected</code> without detail
     * message.
     */
    public NoFileSelected() {
    }

    /**
     * Constructs an instance of <code>NoFileSelected</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public NoFileSelected(String msg) {
        super(msg);
    }
}
