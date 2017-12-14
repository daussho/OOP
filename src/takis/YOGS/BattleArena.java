/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package takis.YOGS;
import java.util.Random;
import takis.etc.Stack;
import java.util.Scanner;
/*
 *
 *
 * @author Teo Wijayarto
 * 
 */
public class BattleArena {
    private Card[] playerHand;
    private Card[] enemyHand;
    private Card[] playerSTCard;
    private Card[] enemySTCard;
    private Card[] playerMonsterCard;
    private Card[] enemyMonsterCard;
    private Card[] playerGraveyard;
    private Card[] enemyGraveyard;
    private Stack playerDeck;
    private Stack enemyDeck;
    private int phase;
    private int playerLP;
    private int enemyLP;
    private int turn;
    private int battlePhase;
    
    BattleArena(Stack playerDeck , Stack enemyDeck ){
        this.playerDeck=playerDeck;
        this.enemyDeck=enemyDeck;
        BattleArena.shuffleCard((Card[])(this.enemyDeck.getArray()));
        BattleArena.shuffleCard((Card[])(this.playerDeck.getArray()));
        phase=1;
        battlePhase=0;
        // Phase 1 = Standbyphase;
        // Phase 2 = Mainphase;
        // Phase 3 : Battlephase ;
        // Phase 4 : EndPhase;
        playerLP=2000;
        enemyLP=2000;
        turn=1;
        playerHand= new Card[5];
        enemyHand= new Card[5];
        playerSTCard=new Card[3];
        enemySTCard= new Card[3];
        playerMonsterCard= new Card[3];
        enemyMonsterCard= new Card[3];
        playerGraveyard= new Card[20];
        enemyGraveyard= new Card[20];
        for(int i=0;i<3;i++){
            playerHand[i]= (Card) playerDeck.pop();
            enemyHand[i]= (Card) enemyDeck.pop();
        }        
    }

    public Card[] getPlayerHand() {
        return playerHand;
    }

    public Card[] getEnemyHand() {
        return enemyHand;
    }

    public Card[] getPlayerSTCard() {
        return playerSTCard;
    }

    public Card[] getEnemySTCard() {
        return enemySTCard;
    }

    public Card[] getPlayerMonsterCard() {
        return playerMonsterCard;
    }

    public Card[] getEnemyMonsterCard() {
        return enemyMonsterCard;
    }

    public Card[] getPlayerGraveyard() {
        return playerGraveyard;
    }

    public Card[] getEnemyGraveyard() {
        return enemyGraveyard;
    }

    public Stack getPlayerDeck() {
        return playerDeck;
    }

    public Stack getEnemyDeck() {
        return enemyDeck;
    }

    public int getPhase() {
        return phase;
    }

    public int getPlayerLP() {
        return playerLP;
    }

    public int getEnemyLP() {
        return enemyLP;
    }

    public int getTurn() {
        return turn;
    }

    public int searchSpace(Card[] kartu,int id){
        int i=0;
        if(id==1){
            while(i<kartu.length && kartu[i]==null){
                i++;
            }
        }else if(id==2){
            while(i<kartu.length && kartu[i]!=null){
                i++;
            }
        }
        return i;
    }
    
    private static void shuffleCard(Card[] array){
        int index;
        Card temp;
        Random random = new Random();
        for (int i = size(array) - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
    
    public void nextPhase(){
        if(phase==2){
            phase++;
            battlePhase=1;
        }else if(phase==3){
            phase++;
            battlePhase=0;
        }else if(phase==4){
            phase=1;
            turn++;
        }else{
            phase++;
        }
    }
    
    public Card removeCardFromHand(Card[] kartu,int idx){
        if(idx>4){//lebih dari jumlah maksimum kartu ditangan
            return null;
        }else{
            Card temp= kartu[idx];
            kartu[idx]=null;
            for(int i=idx; i<4;i++){
                kartu[i]=kartu[i+1];
            }
            return temp;
        }
    }

    public Card removeCardFromField(Card[] kartu, int idx){
        if(idx>2){
            return null;
        }else{
            Card temp=kartu[idx];                
            kartu[idx]=null;
            if(temp.getType().equals("Monster Card")){

                ((MonsterCard)temp).reset(); 
            }
            return temp; 
        }
    }
    
    public void playerDraw(){
        Scanner sc = new Scanner(System.in);
        int idx;
        // Mekanisme apabila kartu ditangan lebih dari 5
        if(5==size(playerHand)){
            System.out.println("Kartu ditangan sudah mencapai jumlah maksimal!\n Buang satu kartu ke Graveyard!");
            do{            
                printCard(playerHand);
                System.out.println("Masukkan nomor kartu yang akan dibuang ke Graveyard: ");
                idx= Integer.parseInt(sc.nextLine())-1;
                //ada kemungkinan out of bound dan null pointer
            }while(removeCardFromHand(playerHand, idx)==null);
        }
        playerHand[size(playerHand)]=(Card) playerDeck.pop();
        System.out.println("Kartu berhasil ditarik dari deck!");
    }
    
    public void enemyDraw(){
        if(5==size(enemyHand)){
            System.out.println("Kartu ditangan sudah mencapai jumlah maksimal!\nBuang satu kartu ke Graveyard!");
            removeCardFromHand(enemyHand,4);
        }
        enemyHand[size(enemyHand)]=(Card) enemyDeck.pop();
        System.out.println("Kartu berhasil ditarik dari deck!");
    }

    public boolean playerSpell(SpellCard ins){
        int idx;
        Scanner sc= new Scanner(System.in);
        //Pengecekan kondisi
        if(size(playerSTCard)!=3){
            //STCard Tidak penuh
            String temp;
            do{
                //Memilih pilihan menu
                System.out.println("Pilih Menu yang akan digunakan\n1. Gunakan kartu\n2. Set kartu");
                temp=sc.nextLine();
            }while(!(temp.equals("1")||temp.equals("2")));
            //Masuikin dulu aja
            if(temp.equals("1")){
                //Use card;
                //Mengganti antarmuka mengisyratkan seakan bisa digunakan
                if(useSpellCard(ins.getId(),1)==true){
                    int id=searchSpace(playerSTCard,2);
                    playerSTCard[id]=ins;
                    ins.setState(4);
                    playerGraveyard[size(playerGraveyard)]=removeCardFromField(playerSTCard,id);
                    return true;
                }else{
                    System.out.println("Kartu tidak bisa digunakan!");
                }
            }else if(temp.equals("2")){
                ins.setState(2);
                int id=searchSpace(playerSTCard,2);
                playerSTCard[id]=ins;
                return true;
            }                
        }else{
            System.out.println("FieldPenuh!");
        }
        return false;
    }

    public void enemySpell(SpellCard ins){
        int idx;
        Scanner sc= new Scanner(System.in);
        //Pengecekan kondisi
        if(size(playerSTCard)!=3){
            if(useSpellCard(ins.getId(),2)==true){
                int id=searchSpace(enemySTCard,1);
                enemySTCard[id]=ins;
                ins.setState(4);
                enemyGraveyard[size(enemyGraveyard)]=removeCardFromField(enemySTCard,id);
            }else{
                System.out.println("Kartu tidak bisa digunakan!");
                ins.setState(2);
                int id=searchSpace(playerHand,1);
                playerSTCard[id]=ins;
            }
        }
    }
    
    public boolean playerSummon(MonsterCard ins){
        int idx;
        //Pengecekan kondisi
        if(size(playerMonsterCard)!=3){
            Scanner sc= new Scanner(System.in);
            //Percabangan berdasarkan jumlah bintang
            if(ins.getLevel()>4 && ins.getLevel()<7){
                //Memerlukan tribute
                //Mengecek Field
                if(size(playerMonsterCard)>0){
                    System.out.println("Masukkan index monster yang akan dikorbankan!");
                    idx= Integer.parseInt(sc.nextLine())-1;
                    printCard(playerMonsterCard);
                    while(removeCardFromField(playerMonsterCard,idx)==null){
                        System.out.println("Masukkan index kembali! Dari monster yang akan dikorbankan!");
                        idx= Integer.parseInt(sc.nextLine())-1;
                    }
                //Berhasil disummon
                String temp;
                do{
                    System.out.println("Pilih Menu yang akan digunakan\n1.Summon Attack\n2.Summon Def");
                    temp=sc.nextLine();
                }while(!(temp.equals("1")||temp.equals("2")));
                if(temp.equals("1")){
                    //Summon Attack
                    ins.setState(4);
                }else if(temp.equals("2")){
                    //Summon Def Facedown
                    ins.setState(1);
                }
                idx=searchSpace(playerMonsterCard,2);
                playerMonsterCard[idx]=ins;
                System.out.println("Anda memanggil "+ ins.getName()+" ke dalam field");
                return true;
                }
            }else if(ins.getLevel()>6){
                if(size(playerMonsterCard)>1){
                    for(int i=0; i<2;i++){
                        System.out.println("Masukkan index kembali! Dari monster yang akan dikorbankan!");
                        idx= Integer.parseInt(sc.nextLine())-1;
                        while(removeCardFromField(playerMonsterCard,idx)==null){
                            System.out.println("Masukkan index kembali!");
                            idx= Integer.parseInt(sc.nextLine())-1;
                        }
                    }
                String temp ;
                do{
                    System.out.println("Pilih Menu yang akan digunakan\n1.Summon Attack\n2.Summon Def");
                    temp=sc.nextLine();
                }while(!(temp.equals("1")||temp.equals("2")));
                if(temp.equals("1")){
                    //Summon Attack
                    ins.setState(4);
                }else{
                    //Summon Def Facedown
                    ins.setState(1);
                }
                idx=searchSpace(playerMonsterCard,2);
                playerMonsterCard[idx]=ins;
                System.out.println("Anda memanggil "+ ins.getName()+" ke dalam field");
                return true;
                }
            }else{
                idx=searchSpace(playerMonsterCard,2);
                String temp;
                do{
                    System.out.println("Pilih Menu yang akan digunakan\n1.Summon Attack\n2.Summon Def");
                    temp=sc.nextLine();
                }while(!(temp.equals("1")||temp.equals("2")));
                playerMonsterCard[idx]=ins;
                if(temp.equals("1")){
                    //Summon Attack
                    ins.setState(4);
                }else{
                    //Summon Def Facedown
                    ins.setState(1);
                }
                System.out.println("Anda memanggil "+ ins.getName()+" ke dalam field");
                return true;
            }
        }
        return false;
        }
  
    public boolean enemySummon(MonsterCard ins){
        //Pengecekan kondisi
        //Percabangan berdasarkan jumlah bintang
        if(ins.getLevel()>4 && ins.getLevel()<7){
            //Memerlukan tribute
            //Mengecek Field
            if(size(enemyMonsterCard)>0){
                removeCardFromField(enemyMonsterCard,searchSpace(enemyMonsterCard,1));
                enemyMonsterCard[searchSpace(enemyMonsterCard,2)]=ins;
                System.out.println("Lawan memanggil "+ ins.getName()+" ke dalam field");
                ins.setState(4);
                return true;
            }
        }else if(ins.getLevel()>6){
            //mentribute 2 monster
            if(size(enemyMonsterCard)>1){
                for(int i=0; i<2; i++){
                removeCardFromField(enemyMonsterCard,searchSpace(enemyMonsterCard,1));
                }
            enemyMonsterCard[searchSpace(enemyMonsterCard,2)]=ins;
            ins.setState(4);
            System.out.println("Lawan memanggil "+ ins.getName()+" ke dalam field");
            return true;
            }
        }else{
            enemyMonsterCard[searchSpace(enemyMonsterCard,2)]=ins;
            System.out.println("Lawan memanggil "+ ins.getName()+" ke dalam field");
            ins.setState(4);
            return true;
        }    
        return false;
    }
    
    public void playerAtk(int x, int y){
        MonsterCard player=((MonsterCard)(playerMonsterCard[x]));
        MonsterCard enemy=((MonsterCard)(enemyMonsterCard[y]));
        int enemyatkdef=enemy.getAtk();
        //mengecek apakah menggunakan attack atau defense
        if(enemy.getState()==1){
            //FLIP SUMMON
            playerFlip(x);
            //
            enemyatkdef=enemy.getDef();
        }
        if(player.getAtk()>enemyatkdef){
            //Menghapus monster card lawan
            if(enemy.getState()!=3){
                //bila kondisinya attack
                System.out.println("Damage yang ditimbulkan!"+ (player.getAtk()+enemyatkdef));
                enemyLP=enemyLP-player.getAtk()+enemy.getAtk();
            }
            enemyGraveyard[size(enemyGraveyard)]=removeCardFromField(enemyMonsterCard,y);
        }
        if(player.getAtk()==enemyatkdef){
            //kondisi keduanya 
            if(enemy.getState()!=3){
                enemyGraveyard[size(enemyGraveyard)]=removeCardFromField(enemyMonsterCard,y);
                playerGraveyard[size(playerGraveyard)]=removeCardFromField(playerMonsterCard,x);   
            }
        }
        else{
            //enemy atk def lawan lebih besar
            playerLP=playerLP+player.getAtk()-enemyatkdef;
            System.out.println("Damage yang ditimbulkan!"+ (player.getAtk()+enemyatkdef));
            if(enemy.getState()!=3){
                playerGraveyard[size(playerGraveyard)]=removeCardFromField(playerMonsterCard,x);
            }
        }
    }
    
    void enemyAtk(int x, int y){
        MonsterCard enemy=((MonsterCard)(playerMonsterCard[x]));
        MonsterCard player=((MonsterCard)(enemyMonsterCard[y]));
        System.out.println(player.getName()+" menyerang "+ enemy.getName());
        int enemyatkdef=enemy.getAtk();
        //mengecek apakah menggunakan attack atau defense
        if(enemy.getState()==1){
            //FLIP SUMMON
            playerFlip(x);
        }
        if(enemy.getState()==3){
            enemyatkdef=enemy.getDef();
        }
        if(player.getAtk()>enemyatkdef){
            //Menghapus monster card lawan
            if(enemy.getState()==4){
                //bila kondisinya attack
                System.out.println("Damage yang ditimbulkan!"+ (player.getAtk()-enemyatkdef));
                playerLP=playerLP-player.getAtk()+enemyatkdef;
            }
            playerGraveyard[size(playerGraveyard)]=removeCardFromField(playerMonsterCard,x);
        }
        else if(player.getAtk()==enemyatkdef){
            //kondisi keduanya 
            if(enemy.getState()==4){
                enemyGraveyard[size(enemyGraveyard)]=removeCardFromField(enemyMonsterCard,y);
                playerGraveyard[size(playerGraveyard)]=removeCardFromField(playerMonsterCard,x);   
            }
        }
        else{
            //enemy atk def lawan lebih besar
            enemyLP=enemyLP+player.getAtk()-enemyatkdef;
            if(enemy.getState()==4){
                enemyGraveyard[size(enemyGraveyard)]=removeCardFromField(enemyMonsterCard,x);
            }
        }
    }
    
    void playerFlip(int idx){
        //Asumsi idx adalah posisi monster yang akan di flip
        if (this.playerMonsterCard[idx].getState()==1)
        {
            if(((MonsterCard)(playerMonsterCard[idx])).isEffect()==1){
                useFlipEffect(playerMonsterCard[idx].getId(),1);
            }
            this.playerMonsterCard[idx].setState(3);
        }

    }
    
    void enemyFlip(int idx){
        //Asumsi idx adalah posisi monster yang akan di flip
        if (this.enemyMonsterCard[idx].getState()==1)
        {
            this.enemyMonsterCard[idx].setState(3);
            if(((MonsterCard)(enemyMonsterCard[idx])).isEffect()==1){
                useFlipEffect(enemyMonsterCard[idx].getId(),2);
            }    
        }
    }
    
    int isFinish(){
        if(playerLP<=0){
            return 1;
        } else if (enemyLP<=0){
            return 2;
        }
        else{
            return 0;
        }
    }
        
    public static int size(Card[] list){
        int count=0;
        for (Card list1 : list) {
            if (list1 != null) {
                count++;
            }
        }
        return count;
    }

    public void useFlipEffect(int id, int type){
    //Type satu yang menggunakan adalah player type dua yang menggunakan adalah enemy
    //P1 merupakan pihak pengguna sedangkan P2 merupakan pihak lawan
    //Attribut
    if(id==4){
        // Monster Hane-hane Effect 
        printArena();
        int i;
        //Mengecek apakah lawan ada monster
        if (type==1){
            i=searchSpace(enemyMonsterCard,1);
        }else{
            i=searchSpace(playerMonsterCard,1);
        }
        
        if (i==3){
            //tidak ada monster card
            System.out.println("Tidak ada monster di field lawan.");
        }else{//effect dijalankan
            //akan mengecek sampai berhasl mengeluarkan suatu kartu
            int idx;
            Card temp=null;
            if (type==1){
                Scanner sc= new Scanner(System.in);
                do{
                    System.out.println("Masukkan index yang ingin dikeluarkan");
                    idx=Integer.parseInt(sc.nextLine())-1;
                    temp=removeCardFromField(enemyMonsterCard,idx);
                }while(temp==null);              
            }else{//NPC yang menjalankan
                temp=removeCardFromField(playerMonsterCard,searchSpace(playerMonsterCard,2));
            }
            //mekanisme menambahkan kembali ke tangan
            if(playerHand.length<5){
                playerHand[size(playerHand)]=temp;
            }else{
                Scanner sc = new Scanner(System.in);
                do{            
                printCard(playerHand);
                System.out.println("Masukkan nomor kartu yang akan dibuang ke Graveyard: ");
                idx= Integer.parseInt(sc.nextLine());
                }while(removeCardFromHand(playerHand, (idx-1))==null);
            }
        }
    }else if(id==5){
        //Man-Eater Effect 
        printArena();
        //Mengecek apakah lawan ada monster
        printArena();
        int i;
        //Mengecek apakah lawan ada monster
        if (type==1){
            i=searchSpace(enemyMonsterCard,1);
        }else{
            i=searchSpace(playerMonsterCard,1);
        }
        if (i==3){
            //tidak ada monster card
            System.out.println("Tidak ada monster di field lawan.");
        }else{//effect dijalankan
            Scanner sc= new Scanner(System.in);
            //akan mengecek sampai berhasl mengeluarkan suatu kartu
            int idx=0;
            Card temp=null;
            if (type==1){
                do{
                    System.out.println("Masukkan index yang ingin dikeluarkan");
                    idx=Integer.parseInt(sc.nextLine())-1;
                    temp=removeCardFromField(enemyMonsterCard,idx);
                }while(temp==null);              
                enemyGraveyard[size(enemyGraveyard)]=temp;
            }else{//NPC yang menjalankan
                temp=removeCardFromField(enemyMonsterCard,searchSpace(enemyMonsterCard,2));
                playerGraveyard[size(playerGraveyard)]=temp;
             }
           }
       }
    }
    
    public  boolean  useSpellCard(int id,int type){
        //inisialisasi

        if(id==9){
        // CARD ID  : 009
        // CARD NAME: FISSURE

            //Efeknya menghancurkan kartu lawan dengan attack terendah
            //Mengecek apakah lawan memiliki monster atau tidak
            int i;
            if (type==1){
                i=searchSpace(enemyMonsterCard,1);
            }else{
                i=searchSpace(playerMonsterCard,1);
            }
            if (i!=3){
                i=0;
                //Monster card ada difield lawan 
                int temp=0;
                if(type==1){
                    while(i<3 && enemyMonsterCard[i]==null){
                        if(((MonsterCard)enemyMonsterCard[i]).getAtk()>((MonsterCard)enemyMonsterCard[temp]).getAtk()){
                            temp=i;
                        }   
                        i++;
                    }
                    enemyGraveyard[size(enemyGraveyard)]=removeCardFromField(enemyMonsterCard,i);     
                    return true;
                }else{
                    while(i<3 && playerMonsterCard[i]==null){
                        if(((MonsterCard)enemyMonsterCard[i]).getAtk()>((MonsterCard)enemyMonsterCard[temp]).getAtk()){
                            temp=i;
                        }   
                        i++;
                    }
                   playerGraveyard[size(playerGraveyard)]=removeCardFromField(playerMonsterCard,i);     
                   return true;
                }
            }
            return false;
        }
        else if(id==10){
        // KARTU ID     : 010
        // NAMA KARTU   : CHANGE OF HEART
            int i;
            if (type==1){
                i=searchSpace(enemyMonsterCard,1);
            }else{
                i=searchSpace(playerMonsterCard,1);
            }
            //Menggunakan kartu lawan sampai selesai Change of Heart yaitu end Phase
            if(phase==2){// Effect kartunya berfungsi
                while(i<3 && enemyMonsterCard[i]==null){
                    i++;
                }
                if (i==3){
                //tidak ada monster card
                    System.out.println("Tidak ada monster di field lawan.");
                    return false;
                }else{//effect dijalankan
                    Scanner sc= new Scanner(System.in);
                    //akan mengecek sampai berhasl mengeluarkan suatu kartu
                    int idx=0;
                    Card temp=null;
                    if (type==1){
                        do{
                            System.out.println("Masukkan index kartu yang ingin diambil");
                            idx=Integer.parseInt(sc.nextLine());
                            temp=removeCardFromField(enemyMonsterCard,idx);
                        }while(temp==null);              
                    }else{//NPC yang menjalankan
                        temp=removeCardFromField(enemyMonsterCard,idx);
                    }
                //memasukan ke field kita sendiri;
                playerMonsterCard[size(playerMonsterCard)]=temp;
                return true;
               }
            }
            return false;
        }
        else if(id==11){
        // CARD ID      : 011
        // CARD NAME    : STOP DEFENSE
        // EFFECT DETAIL: Select 1 monster on opponent side of field
        // MENGHITUNG JUMLAH KOSNG DITEMPAT LAWAN
            int i;
            if (type==1){
                i=searchSpace(enemyMonsterCard,1);
            }else{
                i=searchSpace(playerMonsterCard,1);
            }            
        //JIKA TIDAK KOSONG LANJUT KE TAHAP SELANJUTNYA YAITU PENGECEKAN STATE
            if(i!=3){
                int idx;
                int count=0;
                //dipisahakan berdasarkan tipe
                if(type==1){
                    //Player use
                    i=0;
                    //Kondisi apakah bisa dilakukan
                    while(i>=0 && i<3){
                        if(enemyMonsterCard[i]!=null){
                            if(enemyMonsterCard[i].getState()==1 || enemyMonsterCard[i].getState()==3){
                                count++;    
                            }
                        }
                        i++;
                    }
                    //ada kartu yang dalam kondisi defense
                    if(count>0){
                        //Mekanisme pemilihan kartu yang akan diganti statenya;
                        int found=0;
                        printCard(enemyMonsterCard);
                        Scanner sc= new Scanner(System.in);
                        do{
                            //memasukan nilai Index
                            try{
                                System.out.println("Masukkan index dari kartu yang ingin diganti statenya!");
                                idx=Integer.parseInt(sc.nextLine())-1;
                                if(enemyMonsterCard[idx].getState()==1 || enemyMonsterCard[idx].getState()==3){
                                    found=1;
                                    enemyMonsterCard[idx].setState(4);
                                    return true;
                                }
                            }
                            catch(NullPointerException ooa){
                                System.out.println("Masukkan merupakan null Pointer! Ulangi!");
                            }
                        }while(found==0);
                    }
                }else{//NPC yang menggunakan
                    while(i>=0 && i<3){
                        if(playerMonsterCard[i]!=null){
                            if(playerMonsterCard[i].getState()==1 || playerMonsterCard[i].getState()==3 ){
                                count++;    
                            }
                        }
                        i++;
                    }
                    //ada kartu yang dalam kondisi defense
                    if(count>0){
                        //Mekanisme pemilihan kartu yang akan diganti statenya;
                        int found=0;
                        idx=0;
                        printCard(playerMonsterCard);
                        do{
                            //memasukan nilai Index
                            try{
                                System.out.println("Masukkan index dari kartu yang ingin diganti statenya!");
                                idx=0;
                                if(enemyMonsterCard[idx].getState()!=1 || enemyMonsterCard[idx].getState()!=3){
                                    found=1;
                                    enemyMonsterCard[idx].setState(4);
                                    return true;
                                }
                            }
                            catch(NullPointerException e){
                                System.out.println("Masukkan merupakan null pointer! Ulangi!");
                                idx++;
                            }
                        }while(found==0);
                    }
                }
            }
            return false;
        }
        else if (id==12)
        {
            //CARD ID       : 012
            //CARD NAME     : FOLLOW WIND
            //Checker
            if(type==1){
                //Player turn
                Card temp=null;
                int i=0; 
                int found=0;
                while(i<3 && found==0){
                    if(playerMonsterCard[i]!=null){
                        if(((MonsterCard)(playerMonsterCard[i])).getMonsType().equals("Wind")){
                            System.out.print("Apakah anda ingin menggunakann efek Follow Wind pada "+playerMonsterCard[i].getName()+" Ketik 1 untuk menggunakan");
                            Scanner sc= new Scanner(System.in);
                            if(sc.nextLine().equals("1")){
                                ((MonsterCard)(playerMonsterCard[i])).setAtk(((MonsterCard)(playerMonsterCard[i])).getAtk()+300); 
                                ((MonsterCard)(playerMonsterCard[i])).setDef(((MonsterCard)(playerMonsterCard[i])).getDef()+300); 
                                found=1;
                                return true;
                            }
                        }
                    }
                    i++;
                }  
            }else if (type==2){
                int i=0; 
                int found=0;
                while(i<3 && found==0){
                    if(enemyMonsterCard[i]!=null){
                            if(((MonsterCard)(enemyMonsterCard[i])).getMonsType().equals("Wind")){
                                ((MonsterCard)(enemyMonsterCard[i])).setAtk(((MonsterCard)(enemyMonsterCard[i])).getAtk()+300); 
                                ((MonsterCard)(enemyMonsterCard[i])).setDef(((MonsterCard)(enemyMonsterCard[i])).getDef()+300); 
                                found=1;
                                return true;
                                }
                            }
                        }
                }
            }
        return false;
        }
    
    public boolean checkerTrapCard(int id,int type,int idx1,int idx2){
        Scanner sc= new Scanner(System.in);
        try{//Throw Exception condition
            if(id==13){
                //Negate attack
                if(battlePhase==2){
                    throw new TrapTriggeredException();
                }
            }else if(id==14){
                if(phase==2){
                    //mengirimkan triggered exception
                    throw new TrapTriggeredException();
                }
            }else{
                return false;
            }
        }
        catch(TrapTriggeredException e){
            //list effect yang akan digunakan
            e.print();
            if(id==13){
                //Skip damage step
                battlePhase=1;
            }else if(id==14){
                if(type==1){// player choose
                    System.out.println("Apakah anda ingin menggunakan effect dari Trap Hole?\nPilih angka 1 untuk menggunakan.");
                    if(Integer.parseInt(sc.nextLine())==1){
                        //Remove Trap Card
                        removeCardFromField(playerMonsterCard,idx1);
                    }
                }else if(type==2){
                    //remove TRap Card
                    removeCardFromField(enemyMonsterCard,idx1); 
                }
            }else if(id==15){
                for(int i=0; i<3;i++){
                    if(enemyMonsterCard[i]!=null){
                        if(enemyMonsterCard[i].getState()==3){
                            enemyGraveyard[size(enemyGraveyard)]= removeCardFromField(enemyMonsterCard, i);
                        }
                    }
                } 
                for(int i=0; i<3;i++){
                    if(playerMonsterCard[i]!=null){
                        if(playerMonsterCard[i].getState()==3){
                            playerGraveyard[size(playerGraveyard)]= removeCardFromField(playerMonsterCard, i);
                        }
                    }
                }
            }
        return true;
        }
    return false;
    }

    public void printCard(Card[] kartu){
        for(int i=0; i<kartu.length;i++){
            if(kartu[i]!=null){
              System.out.print((i+1)+". ");
              kartu[i].print();
            }
        }
    }
    
    public void printArena(){
        //Print punya player
        System.out.println("Player LP: "+playerLP);
        System.out.println("Monster Field:");
        printFieldCard(playerMonsterCard);
        System.out.println("Spell/Trap Field:");
        printFieldCard(playerSTCard);
        //print punya enemy
        System.out.println("NPC LP: "+enemyLP);
        System.out.println("Monster Field:");
        printFieldCard(enemyMonsterCard);
        System.out.println("Spell/Trap Field:");
        printFieldCard(enemySTCard);
    }
    

    public void printFieldCard(Card[] arr){
        for(int i=0; i<3;i++){
            if(arr[i]==null){
                System.out.println((i+1)+". Kosong");
            }else{
                if(arr[i].getState()==3 || arr[i].getState()==4){
                    System.out.print((i+1)+". ");
                    arr[i].print();
                }else{
                    System.out.println((i+1)+". Kartu tertutup");
                }
            }
        }
    }    
   
    public int start(){
        String pil;
        int idx;
        Card tempCard;
        Scanner sc= new Scanner(System.in);
        boolean summonPermission=true;
        //Battle dimulai
        //Draw Phase Player;
        do{
            System.out.println("============Draw Phase==========");
            playerDraw();
            nextPhase();
            
            System.out.println("============Main Phase==========");//Main Phase
            do{
                //Print dulu di Kartu ditangan
                printArena();
                System.out.println("Kartu ditangan");
                printCard(playerHand);
                //Pilihan yang ada
                System.out.println("Pilihan menu yang ada\n1. Print Arena\n2. Summon monster\n3. Spell/Trap Card\n4. Change Position\n5. Next Phase");
                pil=sc.nextLine();
                switch(pil){
                    case "1":{
                        printArena();
                    }break;
                    case "2":{
                        if(summonPermission){
                            System.out.println("Kartu ditangan");
                            printCard(playerHand);
                            System.out.print("Pilih index: ");
                            //Cek apakah dia monster
                            idx=Integer.parseInt(sc.nextLine())-1;
                            if(playerHand[idx].getType().equals("Monster Card")){
                                tempCard=removeCardFromHand(playerHand,idx);
                                if(!(playerSummon((MonsterCard) tempCard))){
                                    System.out.println("Summon gagal!");
                                    playerHand[size(playerHand)]=tempCard;
                                }else{
                                    summonPermission=false;
                                }
                            }
                        }else{
                            System.out.println("Anda tidak bisa melakukan summon lagi pada giliran ini!");
                        }
                    }break;
                    case"3":{
                        System.out.println("Kartu ditangan");
                        printCard(playerHand);
                        System.out.print("Pilih index: ");
                        idx=Integer.parseInt(sc.nextLine())-1;
                        if(playerHand[idx]!=null){
                            if(playerHand[idx].getType().equals("Spell Card")||playerHand[idx].getType().equals("Trap Card")){
                                boolean success=playerSpell((SpellCard)playerHand[idx]);
                                if(success){
                                    removeCardFromHand(playerHand,idx);
                                    System.out.println("Spell card berhasil digunakan");
                                }
                            }
                        }
                        
                    } break;
                    case "4":{
                        //Change Position
                        //cek Field apakah ada yang bisa di flip atau diganti posisinya
                        System.out.println("Kartu yang bisa diganti statenya");
                        for(int i=0;i<3;i++){
                            if(playerMonsterCard[i]!=null){
                                System.out.print(i+1+". ");
                                playerMonsterCard[i].print();
                            }
                        }
                        System.out.print("Pilih index: ");
                        //Cek apakah dia bisa diganti posisinya
                        idx=Integer.parseInt(sc.nextLine())-1;
                        if(playerMonsterCard[idx]!=null){
                            if(((MonsterCard)playerMonsterCard[idx]).isTurn()){
                                if(playerMonsterCard[idx].getState()==1){
                                    playerFlip(idx);
                                }                            
                            }
                        }else{
                            System.out.println("Kosong!");
                        }
                    }break;
                    case "5":{
                        nextPhase();
                    }
                }
            }while(!(pil.equals("5")));
            //Battle Phase
            //lakukan inisialisasi attack turn kepada setiap monster;
            if(turn!=1){
                for(int i=0;i<3;i++){
                    if(playerMonsterCard[i]!=null){
                       ((MonsterCard)playerMonsterCard[i]).setTurn(true); 
                    }
                }
            }
            //masuk ke inner Phase dari battle
            System.out.println("============Battle Phase==========");
            do{
                System.out.println("Pilihan yang tersedia:\n1. Pilih monster untuk attack\n2. Next Phase ");
                pil=sc.nextLine();
                if (pil.equals("1")){//pilih attack
                    printFieldCard(playerMonsterCard);
                    System.out.println("Pilih index monster yang akan menyerang!");
                    idx=Integer.parseInt(sc.nextLine())-1;
                    if(playerMonsterCard[idx]==null){
                        System.out.println("Index tersebut tidak terisi!");
                    }else{
			MonsterCard monster= (MonsterCard)playerMonsterCard[idx];
                        if ( monster.isTurn() && monster.getState()==4){//cek apakah player monster belum atk this turn dan sedang posisi atk
				if(size(enemyMonsterCard)!=0){
                                    printFieldCard(enemyMonsterCard);
                                    System.out.println("Pilih index monster yang akan diserang!");
                                    int idx2=Integer.parseInt(sc.nextLine())-1;
                                    if (enemyMonsterCard[idx2]!= null){
                                        battlePhase++;
                                        monster.setTurn(false);
                                        playerAtk(idx,idx2);
                                    } else {
                                        battlePhase++;
                                        System.out.println("Tidak ada monster card di field!");
                                    }                                    
                                }else{
                                    monster.setTurn(false);
                                    enemyLP=enemyLP-monster.getAtk();
                                }
                        }else{
                            System.out.println("Tidak bisa menyerang!");
                        }
                    }
                }else if(pil.equals("2")){
                    nextPhase();
                }
                if(isFinish()==1) break;
            }while(phase==3);
            //EndPhase
            if(isFinish()==1) break;
            nextPhase();
            System.out.println("============End Phase==========");
            //Inisialisasi boleh change Position
            for(int i=0;i<3;i++){
                if(playerMonsterCard[i]!=null){
                    ((MonsterCard)playerMonsterCard[i]).setTurn(true); 
                }
            }
            //Bagian Enemy
            System.out.println("============Draw Phase==========");
            //Draw Phase
            System.out.println("Giliran lawan!");
            printArena();
            enemyDraw();
            nextPhase();
            //MainPhase
            System.out.println("============Main Phase==========");
            summonPermission=true;
            int i=0;
            while(summonPermission==true && i<size(enemyHand)){
                if(enemyHand[i]!=null){
                    if(enemyHand[i].getType().equals("Monster Card")){
                        if(enemySummon((MonsterCard)enemyHand[i])==true){
                            removeCardFromHand(enemyHand,i);
                            summonPermission=false;
                        }
                    }
                }
                i++;
            }
            nextPhase();
            System.out.println("============Battle Phase==========");
            //Battle Phase
            if(turn!=1){
                for(i=0;i<3;i++){
                    if(enemyMonsterCard[i]!=null){
                       ((MonsterCard)enemyMonsterCard[i]).setTurn(true); 
                    }
                }
            }
            for(i=0;i<3;i++){
                if(enemyMonsterCard[i]!=null){
                    if(size(playerMonsterCard)==0){
                        //ini kalau kosong
                        playerLP-=((MonsterCard)enemyMonsterCard[i]).getAtk();
                    }else{
                        //kalau ada orang
                        enemyAtk(i,searchSpace(playerMonsterCard,1));
                    }
                }
            }
            nextPhase();
            System.out.println("============End Phase==========");
            //endPhase
            nextPhase();
            summonPermission=true;
        }while(isFinish()==0);
        if(isFinish()==2){
            System.out.println("YOU WIN!!!! UWAW");
            return 1000;
        }else{
            System.out.println("YOU LOSE!!!! Hello");
            return 0;
        }
    }
}