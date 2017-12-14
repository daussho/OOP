
package takis.YOGS;

import java.util.Random;
import takis.FilesIO.*;
import takis.etc.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //attribute
        Scanner in = new Scanner(System.in);
        String menu, pil, name;
        Player player;
        NPCDuelist[] npc = new NPCDuelist[3];
        Island island = new Island();
        Shop shop = new Shop();
        LoadCard loader;
        LoadPlayer playerLoader = new LoadPlayer();
        ReadFile reader = new ReadFile("files/welcome.txt");
        String[] welcome = reader.readFile();
        
        //program
        do{
            for (int i=0; i<welcome.length;i++){
                System.out.println(welcome[i]);
            }
            //Pilih menu
            do{
                System.out.println("> New Game");
                System.out.println("> Continue");
                System.out.println("> Exit");
                System.out.print("> "); menu = in.nextLine();
            }while(!"New Game".equalsIgnoreCase(menu) && !"Continue".equalsIgnoreCase(menu) && !"Exit".equalsIgnoreCase(menu));
            
            //playing game
            if ("New Game".equalsIgnoreCase(menu) || "Continue".equalsIgnoreCase(menu)){
                
                if("New Game".equalsIgnoreCase(menu)){
                    System.out.print("What is your name : "); name = in.nextLine();
                    loader = new LoadCard("files/card/monstercard.txt", "files/card/spellcard.txt", "files/card/trapcard.txt");
                    player = new Player(name, 5000, loader.loadCard(), new Point(7,9));
                } else{
                    player = playerLoader.loadPlayer();
                }
                
                //inisialisasi NPC
                loader = new LoadCard("files/card/monstercard.txt", "files/card/spellcard.txt", "files/card/trapcard.txt");
                npc[0] = new NPCDuelist(1, "Kaiba", new Stack(loader.loadCard()), new Point(0,0), "Bronze");
                loader = new LoadCard("files/card/monstercard.txt", "files/card/spellcard.txt", "files/card/trapcard.txt");
                npc[1] = new NPCDuelist(2, "Yugi", new Stack(loader.loadCard()), new Point(0,0), "Silver");
                loader = new LoadCard("files/card/monstercard.txt", "files/card/spellcard.txt", "files/card/trapcard.txt");
                npc[2] = new NPCDuelist(3, "Joe", new Stack(loader.loadCard()), new Point(0,0), "Gold");
                
                //gameplay
                do{
                    island.printMap(player.getPos());
                    System.out.println("> W - Atas");
                    System.out.println("> A - Kiri");
                    System.out.println("> S - Bawah");
                    System.out.println("> D - Kanan");
                    System.out.println("> C - Character");
                    System.out.println("> Save");
                    System.out.println("> Exit");
                    //baca pilihan player
                    System.out.print("> ");
                    pil = in.nextLine();

                    //cek pilihan player
                    if ("save".equalsIgnoreCase(pil)){
                        player.save();
                    }else if("c".equalsIgnoreCase(pil)){
                        System.out.println("Nama: "+player.getName());
                        System.out.println("Uang: "+player.getMoney());
                        player.editDeck();
                    }else if ("W".equalsIgnoreCase(pil) || "A".equalsIgnoreCase(pil) || "S".equalsIgnoreCase(pil) || "D".equalsIgnoreCase(pil)) {
                        player.move(pil.charAt(0));
                        //cek posisi player
                        if (island.getMap()[player.getPos().getY()][player.getPos().getX()] == Island.shop){
                            Card[] temp = island.Shopping(player.getMoney());
                            if (temp != null){
                                player.addToDeck(temp);
                                player.addMoney(-800);
                            }
                        } else if (island.getMap()[player.getPos().getY()][player.getPos().getX()] == Island.npc){
                            Random random = new Random();
                            int x=random.nextInt(2);
                            npc[x].TalkTo();
                            System.out.println("Do you want to duel? (Yes/No)");
                            System.out.print("> ");
                            String temp = in.nextLine();
                            if (temp.equalsIgnoreCase("yes")){
                                player.addMoney((new BattleArena(player.getDeck(), npc[x].getDeck())).start());
                            }
                        }
                    }
                }while(!"Exit".equalsIgnoreCase(pil));
            }
        }while(!"Exit".equalsIgnoreCase(menu));
    }
}
