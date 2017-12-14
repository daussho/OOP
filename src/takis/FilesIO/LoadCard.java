package takis.FilesIO;

import takis.YOGS.*;

public class LoadCard {
    //atribut
    private final Card[] kartu = new Card[1000];
    private int j = 0;
    private Card[] card;
    
    public LoadCard(String monster, String spell, String trap){
        loadMonsterCard(monster);
        loadSpellCard(spell);
        loadTrapCard(trap);
        card = new Card[j+1];
        for (int i=0;i<=j;i++){
            card[i] = kartu[i];
        }
    }
    //method
    private void loadMonsterCard(String path){
        //Card[] kartu = new Card[15];
        int k = 1;
        ReadFile reader = new ReadFile(path);
        String[] text = reader.readFile();
        int count  = text.length/12;
        for (int i=0;i<count;i++){
            kartu[j] = new MonsterCard(Integer.parseInt(text[k]), text[++k], Float.parseFloat(text[++k]), Integer.parseInt(text[++k]), text[++k], text[++k], text[++k], Integer.parseInt(text[++k]),Integer.parseInt(text[++k]),Integer.parseInt(text[++k]),Integer.parseInt(text[++k]));
            k+=2;
            j++;
        }
    }
    
    private void loadSpellCard(String path){
        //Card[] kartu = new Card[4];
        int k = 1;
        ReadFile reader = new ReadFile(path);
        String[] text = reader.readFile();
        int count  = text.length/6;
        for (int i=0;i<count;i++){
            kartu[j] = new SpellCard(Integer.parseInt(text[k]), text[++k], Float.parseFloat(text[++k]), Integer.parseInt(text[++k]), text[++k]);
            k+=2;
            j++;
        }
    }
    
    private void loadTrapCard(String path){
        //Card[] kartu = new Card[3];
        int k = 1;
        ReadFile reader = new ReadFile(path);
        String[] text = reader.readFile();
        int count  = text.length/6;
        for (int i=0;i<count;i++){
            kartu[j] = new TrapCard(Integer.parseInt(text[k]), text[++k], Float.parseFloat(text[++k]), Integer.parseInt(text[++k]), text[++k]);
            k+=2;
            j++;
        }
    }
    
    public Card[] loadCard(){
        return card;
    }
    
    public int getCardCount(){
        return j;
    }
}