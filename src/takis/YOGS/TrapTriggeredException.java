/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package takis.YOGS;

/**
 *
 * @author Teo Wijayarto
 */
public class TrapTriggeredException extends Exception {
    //Berisi implementasi dari trap card yang ada
    

    public TrapTriggeredException(){
        super("Ada trap card yang bisa anda aktifkan");
    }
    
    void print(){
        System.out.println(getMessage());
    }
}
