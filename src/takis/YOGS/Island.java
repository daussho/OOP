//Takis Game Corp 
package takis.YOGS;
import takis.etc.Point;


public class Island {
	//attribute
        private int[][] map; 
        public final static int npc = 66;
        public final static int shop = 55;
        public final static int jalan = 22;
	//

	//konstruktor
	public Island() {
            this.map = new int[][]{
            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
            {1, 99, 99, 99, 99, 99, 99, jalan,99, 99, 99},
            {2, 99, 99, 99, 99, 99, 99, jalan, 99, 99, 99},
            {3, 99, 99, 99, jalan, jalan, jalan, jalan, 99, 99, 99 },
            {4, 99, 99, npc, jalan, 99, 99, 99, 99, 99, 99 },
            {5, 99, 99, 99, jalan, 99, 99, 99, 99, 99, 99 },
            {6, 99, 99, 99, jalan, 99, 99, npc, 99, 99, 99 },
            {7, jalan, jalan, jalan, jalan, jalan, jalan, jalan, jalan, jalan, jalan },
            {8, 99, 99, 99, shop, 99, jalan, 99, 99, 99, 99 },
            {9, 99, 99, 99, 99, 99, jalan, 99, 99, 99, 99 },
            {10, 99, 99, npc, 99, 99, jalan, 99, 99, 99, 99 }
            };
	}
	
	//method
	public void printMap(Point playerPos){
           //print matrix, ganti char dengan bg color
		for(int i = 11; i >= 0; i--) {
				for(int bar = 1; bar <=2; bar++){
					for(int j = 0; j <= 11; j++){
						if(j==0||j==11){
							if(i==0||i==11){
								System.out.print("  ");}
							else
							System.out.print(" |");
						}else
							for(int kol = 1; kol <=4; kol++){
								if(i==0){
									if(bar==1){
									System.out.print(" ~");}
                                                                }else if(i==11){
                                                                    if(bar==2){
                                                                    System.out.print(" ~");}
								}else 
								if(bar==1){
									if(j==playerPos.getX()&&i==playerPos.getY()){ //map[i][j]==jalan//
										System.out.print(" 0");
									}else if(map[i][j]==jalan){
										System.out.print("  ");
									}else if(map[i][j] == npc){
										System.out.print(" ?");
									}else if(map[i][j]==shop){
										if(kol==1){ 
											System.out.print(" [");}
										else if(kol==4) { System.out.print(" ]");}
										else System.out.print(" #");
									}else
										System.out.print(" `");
							}else{
									if(j==playerPos.getX()&&i==playerPos.getY()){
										if(kol==1){ 
											System.out.print(" (");}
										else if(kol==4) { System.out.print(" )");}
										else System.out.print(" ^");
									}else if(map[i][j]==jalan){
										System.out.print("  ");
									}else if(map[i][j] == npc){
										if(kol==1){ 
											System.out.print(" (");}
										else if(kol==4) { System.out.print(" )");}
										else System.out.print(" -");
									}else if(map[i][j]==shop){
										if(kol==1){ 
											System.out.print(" |");}
										else if(kol==4) { System.out.print(" |");}
										else System.out.print(" $");
									}else
										System.out.print(" `");
							}
						}
            }
            System.out.println();
		}
	}}
           
	public Card[] Shopping(int playerMoney) {
            //instansiasi objek Shop
            //masuk toko (sudah dicek posisi player = posisi shop)
            Shop toko = new Shop();
            return toko.welcome(playerMoney);
	}

        public int[][] getMap(){
            return this.map;
        }

}
