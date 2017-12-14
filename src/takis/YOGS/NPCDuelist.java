// Takis Game Corp 
// Author       : Sanwid
package takis.YOGS;
import takis.etc.*;
import java.util.Scanner;

// bikin 3 npc
public class NPCDuelist {
    //attribute
    int id; 
    String name;
    Stack deck;
    Point pos;
    String rank;
    
	
    //konstruktor
    public NPCDuelist(int id, String name, Stack deck, Point pos, String rank)
    {
        this.id = id;
        this.name = name;
        this.deck = deck;
        this.pos = pos;
        this.rank =  rank;
    }
    public Stack getDeck()
    {
        return this.deck;
    }
	
    //method
    public void TalkTo(){
        //ngobrol sama NPC Duelist
        //kalo yes, panggil method battle
        System.out.println("Hello bro I'm "+this.name);
        System.out.println("I'm little bored here");
    }
}
