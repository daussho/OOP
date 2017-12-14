/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package takis.FilesIO;

import takis.YOGS.*;
import takis.etc.*;

public class LoadPlayer {
    public Player loadPlayer(){
        Player player;
        Card[] card;
        ReadFile reader = new ReadFile("files/player/player.txt");
        LoadCard loader = new LoadCard("files/player/monstercard.txt", "files/player/spellcard.txt", "files/player/trapcard.txt");
        card = loader.loadCard();
        String[] text = reader.readFile();
        player = new Player(text[0], Integer.parseInt(text[1]), card, new Point(Integer.parseInt(text[2]), Integer.parseInt(text[3])));
        return player;
    }
}
