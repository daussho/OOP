//Takis Game Corp//fikr 
package takis.YOGS;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;
import takis.FilesIO.LoadCard;
import takis.etc.Point;

public class Shop {
	//attribute
            private Card[] gachaCard; //array of card yg mungkin didapat
            private static Point p; //posisi pada map(pulau duelist)
	
	//konstruktor
	public Shop() {
		this.gachaCard = new Card[100]; 
		this.p = new Point(8,4);
	}
	
	//method
        
        public void fillGacha() {
                int j; //untuk looping sejumlah kartu
                int k = 0; //indeks array Gacha
                LoadCard cardLoader = new LoadCard("files/card/monstercard.txt","files/card/spellcard.txt","files/card/trapcard.txt");
                Card[] availableCard = cardLoader.loadCard();
                for(int i = 0; i < cardLoader.getCardCount(); i++) {
                    int jmlKartu = Math.round(availableCard[i].getRarity()*100);
                    j = 0;
                    do{
                        gachaCard[k] = availableCard[i];
                        j++;
                        k++;
                    }while(j<jmlKartu);
                    //k = k + jmlKartu;
                }                 
        }
        
	public Card[] buyCard(int playerMoney){
                fillGacha();
		Card[] packBought = new Card[3];
                for (int i = 0; i < 3; i++){
                    //mengeluarkan angka index random 1-100
                    int randomIdx = ThreadLocalRandom.current().nextInt(0, 99 + 1);
                    packBought[i] = gachaCard[randomIdx]; //dapat kartu random
                }
                return packBought;
	}
	
	
	public Card[] welcome(int playerMoney){
		System.out.println("Selamat datang di Toko Kartu!");
                Scanner in = new Scanner(System.in);
		String beli;
                Card[] temp = null;
                boolean exit = false;
		do{
                    System.out.println("Anda bisa membeli satu pack kartu berisi 3 kartu acak seharga $800"); //harga ngasal
                    System.out.println("Apa anda mau beli?");
                    System.out.println("> Yes");
                    System.out.println("> No");
                    System.out.print("> ");
			beli = in.nextLine();
			if("yes".equalsIgnoreCase(beli)){
                            int hargaPack = 800; //misalnya
                            if(playerMoney > hargaPack){
                                temp = buyCard(playerMoney);
                                //bisa juga di main, menampilkan kartu apa saja yg didapatkan
                                for (int i = 0; i < 3; i++) {
                                System.out.println("Anda mendapatkan kartu "+temp[i].getName()+"!");
                                System.out.println("Rarity: "+temp[i].getRarity());
                                }
                            }else  {
                                System.out.println("Maaf, uang anda tidak cukup.");
                            }
			}else if("no".equalsIgnoreCase(beli)){
                            exitShop();
                            exit = true;
			}else { 
                            System.out.println("Maaf, bisa diulang. Beli atau tidak?");
                        }
		}while(!exit);
                return temp;
	}
	
	public void exitShop(){
		//kembali ke Island. Posisi terakhir player berada.
		System.out.println("Terima kasih sudah datang!");
	}

}
