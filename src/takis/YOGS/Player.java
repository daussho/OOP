// Takis Group Inc.
// Deskripsi	: Kelas Player
// Author       : Sanwidi


package takis.YOGS;

import java.io.*;
import takis.etc.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Player
{
	private String name; 
	private int money;
	private Stack deck;
	private Card[] nonDeck;
	private Point pos;


	// Konstruktor
    // Konstruktor terdiri dari nama, jumlah uang, koleksi kartu player, dan posisi player
    // Konstruktor akan melakukan assign deck secara otomatis dari koleksi kartu player di indeks 1-20
    // Konstruktor akan melakukan assign kartu non deck secara otomatis dimulai dari indeks diatas 20

    /**
     *
     * @param name
     * @param money
     * @param koleksiKartuPlayer
     * @param pos
     */
	public Player(String name, int money, Card[] koleksiKartuPlayer, Point pos)
	{
            this.name=name;
            this.money=money;
            this.pos=pos;
            int i=0;
            int j=0;

            Card[] tempDeck = new Card[20];
            this.nonDeck = new Card[100];
            while((koleksiKartuPlayer[i]!=null)&&(i<20))
            {    
                tempDeck[i] = koleksiKartuPlayer[j];
                i++;
                j++;
            }

            i=0;
            while(koleksiKartuPlayer[j]!=null)
            {
                this.nonDeck[i]=koleksiKartuPlayer[j];
                i++;
                j++;
            }


            this.deck = new Stack(tempDeck);
            //Menambahkan ke non Deck
	}

	// GETTER & SETTER

	//Getter

    /**
     *
     * @return
     */
	public String getName()
	{
            return this.name;
	}

    /**
     *
     * @return
     */
    public int getMoney()
	{
            return this.money;
	}

    /**
     *
     * @return
     */
    public Stack getDeck()
	{
		return this.deck;
	}

    /**
     *
     * @return
     */
    public Point getPos()
	{
		return this.pos;
	}



    /**
     *
     */
    public Card[] getNonDeckCard[];

	//SETTER

    /**
     *
     * @param name
     */

	public void setName(String name)
	{
		this.name=name;
	}

    /**
     *
     * @param money
     */
    public void setMoney(int money)
	{
		this.money=money;
	}

    /**
     *
     * @param deck
     */
    public void setDeck(Stack deck)
	{
		this.deck=deck;
	}

    /**
     *
     * @param pos
     */
    public void setPos(Point pos)
	{
		this.pos=pos;
	}

	//MONEY ADDER & SUBSTRACT
        //iseng ehehe

    /**
     *
     * @param moneyAdded
     */

	public void addMoney(int moneyAdded)
	{
		this.money=this.money+moneyAdded;
	}

	// METHOD KELAS PLAYER
    
        //Menambahkan kartu ke nonDeck;

    /**
     *
     * @param Added
     */
        public void addToDeck(Card[] added)
        {
            int i = 0;
            while(this.nonDeck[i] != null)
            {
                i++;
            }
            for(int j=0;j<3;j++)
            {
                this.nonDeck[i]=added[j];
                i++;
            }
        }
        
        
    //Merubah posisi player berdasarkan input karakter
    // w = ke atas ; a = ke kiri ; s = ke bawah ; d = ke kanan

    /**
     *
     * @param c
     */
	public void move(char c)
	{
            switch (c) {
                case 'w':
                    if(this.pos.getY()==10)
                    {
                        System.out.println("Anda sudah berada di ujung");
                    }
                    else
                    {
                        this.pos.setY(this.pos.getY()+1);
                    }
                    break;
                case 's':
                    if(this.pos.getY()==1)
                    {
                        System.out.println("Anda sudah berada di ujung");
                    }
                    else
                    {
                        this.pos.setY(this.pos.getY()-1);
                    }
                    break;
                case 'a':
                    if(this.pos.getX()==1)
                    {
                        System.out.println("Anda sudah berada di ujung");
                    }
                    else
                    {
                        this.pos.setX(this.pos.getX()-1);
                    }
                    break;
                case 'd':
                    if(this.pos.getX()==10)
                    {
                        System.out.println("Anda sudah berada di ujung");
                    }
                    else
                    {
                        this.pos.setX(this.pos.getX()+1);
                    }
                    break;
                default:
                    break;
            }
	}
    
    // Menyimpan data player ke dalam file eksternal Player.txt & PlayerCards.txt
    // Baru bisa print player.txt

    /**
     *
     */
	public void save()
	{
            FileWriter fWriter1 = null;
            FileWriter fWriter2 = null;
            FileWriter fWriter3 = null;
            try {
                //print name, money, dan posistion ke file eksternal
                File outFile = new File ("files/player/player.txt");
                File outFile1 = new File ("files/player/monstercard.txt");
                File outFile2 = new File ("files/player/spellcard.txt");
                File outFile3 = new File ("files/player/trapcard.txt");
                fWriter1 = new FileWriter (outFile1);
                fWriter2 = new FileWriter (outFile2);
                fWriter3 = new FileWriter (outFile3);
                FileWriter fWriter = new FileWriter (outFile);
                PrintWriter pWriter = new PrintWriter (fWriter);
                
                pWriter.println (this.name);
                pWriter.println (this.money);
                pWriter.println(this.pos.getX());
                pWriter.println(this.pos.getY());
                pWriter.close();
                //Membuat Filewrite untuk monstrercard.txt, spellcard.txt & trapcard.txt
                
                
                //Menggunakan kelas printWriter agar dapat meenggunakan method println ke file txt
                PrintWriter pWriter1 = new PrintWriter (fWriter1);	//Monstercard
                PrintWriter pWriter2 = new PrintWriter (fWriter2);	//SpellCard
                PrintWriter pWriter3 = new PrintWriter (fWriter3);	//TrapCard
                //Menulis ke file monstercard, trapcard, & spell card dari deck
                Card[] deckTemp = (Card[]) this.deck.getArray();
                int i=0;
                while(deckTemp[i]!=null)
                {
                    if(deckTemp[i] instanceof MonsterCard)
                    {
                        pWriter1.println("====================================");
                        pWriter1.println(deckTemp[i].getId());
                        pWriter1.println(deckTemp[i].getName());
                        pWriter1.println(deckTemp[i].getRarity());
                        pWriter1.println(deckTemp[i].getState());
                        pWriter1.println(((MonsterCard)deckTemp[i]).getMonsType());
                        pWriter1.println(deckTemp[i].getDesc());
                        pWriter1.println(((MonsterCard)deckTemp[i]).getMonsType());
                        pWriter1.println(((MonsterCard)deckTemp[i]).isEffect());
                        pWriter1.println(((MonsterCard)deckTemp[i]).getLevel());
                        pWriter1.println(((MonsterCard)deckTemp[i]).getAtk());
                        pWriter1.println(((MonsterCard)deckTemp[i]).getDef());
                    }
                    else if(deckTemp[i] instanceof SpellCard)
                    {
                        pWriter2.println("====================================");
                        pWriter2.println(deckTemp[i].getId());
                        pWriter2.println(deckTemp[i].getName());
                        pWriter2.println(deckTemp[i].getRarity());
                        pWriter2.println(deckTemp[i].getState());
                        pWriter2.println(deckTemp[i].getDesc());
                    }
                    else if(deckTemp[i] instanceof TrapCard)
                    {
                        pWriter3.println("====================================");
                        pWriter3.println(deckTemp[i].getId());
                        pWriter3.println(deckTemp[i].getName());
                        pWriter3.println(deckTemp[i].getRarity());
                        pWriter3.println(deckTemp[i].getState());
                        pWriter3.println(deckTemp[i].getDesc());
                    }
                    i++;
                }
                // Menulis ke file monstercard.txt, spellcard.txt, dan trapcard.txt dari nondeck
                i=0;
                while(this.nonDeck[i]!=null)
                {
                    if(this.nonDeck[i] instanceof MonsterCard)
                    {
                        pWriter1.println("====================================");
                        pWriter1.println(this.nonDeck[i].getId());
                        pWriter1.println(this.nonDeck[i].getName());
                        pWriter1.println(this.nonDeck[i].getRarity());
                        pWriter1.println(this.nonDeck[i].getState());
                        pWriter1.println(((MonsterCard)this.nonDeck[i]).getMonsType());
                        pWriter1.println(this.nonDeck[i].getDesc());
                        pWriter1.println(((MonsterCard)this.nonDeck[i]).getMonsType());
                        pWriter1.println(((MonsterCard)this.nonDeck[i]).isEffect());
                        pWriter1.println(((MonsterCard)this.nonDeck[i]).getLevel());
                        pWriter1.println(((MonsterCard)this.nonDeck[i]).getAtk());
                        pWriter1.println(((MonsterCard)this.nonDeck[i]).getDef());
                    }
                    else if(this.nonDeck[i] instanceof SpellCard)
                    {
                        pWriter2.println("====================================");
                        pWriter2.println(this.nonDeck[i].getId());
                        pWriter2.println(this.nonDeck[i].getName());
                        pWriter2.println(this.nonDeck[i].getRarity());
                        pWriter2.println(this.nonDeck[i].getState());
                        pWriter2.println(this.nonDeck[i].getDesc());
                        
                    }
                    else if(this.nonDeck[i] instanceof TrapCard)
                    {
                        pWriter3.println("====================================");
                        pWriter3.println(this.nonDeck[i].getId());
                        pWriter3.println(this.nonDeck[i].getName());
                        pWriter3.println(this.nonDeck[i].getRarity());
                        pWriter3.println(this.nonDeck[i].getState());
                        pWriter3.println(this.nonDeck[i].getDesc());
                    }
                    i++;
                }
                pWriter1.println("====================================");
                pWriter2.println("====================================");
                pWriter3.println("====================================");
                System.out.println("Save berhasil!");
                //Menutup filewriter
            } catch (IOException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (fWriter1 != null){
                        fWriter1.close();
                    }
                    if (fWriter2 != null){
                        fWriter2.close();
                    }
                    if (fWriter3 != null){
                        fWriter3.close();
                    }
                    
                } catch (IOException ex) {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
	}
    
    
    //Menambahkan kartu ke deck

    /**
     *
     * @param kartu
     */
	public void addCard(Card kartu)
	{
        deck.push(kartu);
	}
	// Method print isi deck 

    /**
     *
     */
	public void printDeck()
	{
            Card[] copyDeck = (Card[])this.deck.getArray();
	    for (int i=0; i<=this.deck.getTop(); i++)
            {
                if (copyDeck[i] != null){
                    System.out.println((i+1)+". "+copyDeck[i].getName());	
                }
            }
	}

	// Method print kartu non Deck

    /**
     *
     */
	public void printNonDeck()
	{
		int i=0;
		while(this.nonDeck[i]!=null)
		{
			System.out.println((i+1)+". "+this.nonDeck[i].getName());
			i++;
		}
	}

    /**
     *
     */
    public void editDeck()
	{
		
		Scanner in = new Scanner(System.in);
		int a;
		do
                {
                System.out.println("====Welcome to Deck editor====");
		System.out.println("1. View Your Deck ");
		System.out.println("2. View Your nonDeck Card");
		System.out.println("3. Edit Your Deck");
		System.out.println("4. Exit");
                System.out.println("Masukkan angka: ");
                System.out.print("> ");
		a = in.nextInt();
		// Pilihan 1
		// Print isi deck
		if (a==1)
		{
			this.printDeck();
                        System.out.println("Press any key to get back.....");
                        String temp = in.nextLine();
		}
		// Pilihan 2
		// Print isi koleksi kartu
		if (a==2)
		{
			this.printNonDeck();
                        System.out.println("Press any key to get back.....");
                        String temp = in.nextLine();
		}
		// Pilihan 3
		// Masuk ke menu edit kartu
		if (a==3)
		{
			System.out.println("Masukkan Angka : ");
			System.out.println("1. Add Card to Deck");
			System.out.println("2. Remove card from Deck");
			System.out.println("3. Swap Card from Deck");
			System.out.println("pilihan anda : ");
                        System.out.print("> ");
			int editDeckLevel2Option = in.nextInt();
			if (editDeckLevel2Option==1)
			{
				printNonDeck();
				System.out.println("pilih indeks kartu yang akan dimasukkan ke dalam deck : ");
				int insertDeck = in.nextInt();
				Card[] temp = (Card[]) this.deck.getArray();
				//cek apakah dek sudah mencapai maksimum
                                if(this.deck.getTop()<19)
                                {
                                    if(this.nonDeck[insertDeck-1]!=null)
                                    {
                                        System.out.println(this.deck.getTop());
                                        this.deck.push(this.nonDeck[insertDeck-1]);
                                        System.out.println(this.deck.getTop());
                                        int i=insertDeck-1;
                                        while(this.nonDeck[i]!=null)
                                        {
                                            this.nonDeck[i]=this.nonDeck[i+1];
                                            i++;
                                        }  
                                    }
                                    else
                                    {
                                        System.out.println("Tidak ada kartu pada indeks"+(insertDeck-1));
                                    }
				}
				else 
				{
					System.out.println("Deck sudah mencapai kapasitas maksimum");
				}
			}
			else if (editDeckLevel2Option == 2)
			{
				printDeck();
				System.out.println("pilih indeks kartu yang akan dikeluarkan dari deck : ");
                                System.out.print("> ");
				int removeDeck = in.nextInt();
				int i = 0;
				while(this.nonDeck[i]!=null)
				{
                                    i++;
				}
				this.nonDeck[i] = (Card) this.deck.getArray()[removeDeck-1];
                                Card[] temp = (Card[]) this.deck.getArray();
                                for(int j=removeDeck-1;j<19;j++)
                                {
                                    temp[j]=temp[j+1];
                                }  
			}
			else if (editDeckLevel2Option == 3)
			{
				this.printDeck();
				System.out.println("Pilih kartu yang ingin ditukar dengan koleksi Kartu : ");
                                System.out.print("> ");
				int card1 = in.nextInt();
				this.printNonDeck();
				System.out.println("Pilih kartu yang ingin ditukar dengan deck : ");
                                System.out.print("> ");
				int card2 = in.nextInt();
                                Card[] tempdeck = (Card[]) this.deck.getArray();
                                Card temp = tempdeck[card1-1];
                                tempdeck[card1-1] = this.nonDeck[card2-1];
                                this.nonDeck[card2-1] = temp;
				this.deck = new Stack(tempdeck);
				System.out.println("Berhasil menukar kartu : ");
			}
		}
		if (a==4)
		{
			System.out.println("Keluar dari Deck editor... Press any key to continue");
			String temp = in.nextLine();			
		}
                }
                while(a!=4);
	}
}
