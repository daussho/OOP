// Takis Group Inc.
// Deskripsi    : Kelas Card
package takis.YOGS;

public abstract class Card{
    //atribut
    protected int id;
    protected String name;
    protected float rarity;
    protected String type;
    protected int state;
    protected String desc;    

    public Card(int id, String name, float rarity, String type, int state, String desc) {
        this.id = id;
        this.name = name;
        this.rarity = rarity;
        this.type = type;
        this.state = state;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getRarity() {
        return rarity;
    }

    public String getType() {
        return type;
    }

    public int getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRarity(float rarity) {
        this.rarity = rarity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public abstract void print();
}
