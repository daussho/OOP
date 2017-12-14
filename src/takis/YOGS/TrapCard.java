package takis.YOGS;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Teo Wijayarto
 */
public class TrapCard extends Card {
    public TrapCard(int id, String name, float rarity,  int state, String desc){
        super(id,  name, rarity, "Trap Card", state, desc);
    }
    
    @Override
    public void print(){
        System.out.println(this.getName()+" | "+this.getType());
    }
}
