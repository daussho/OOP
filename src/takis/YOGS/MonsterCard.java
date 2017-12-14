package takis.YOGS;

public class MonsterCard extends Card{
    private String attribute;
    private String monsType;
    private int effect;
    private int level;
    private int atk;
    private int def;
    private int oriAtk;
    private int oriDef;
    private boolean turn;

    public MonsterCard(int id, String name, float rarity, int state, String monsType, String desc, String attribute, int effect, int level, int atk, int def) {
        super(id,name,rarity,"Monster Card",state,desc);
        this.monsType = monsType;
        this.attribute = attribute;
        this.effect = effect;
        this.level = level;
        this.atk = atk;
        this.oriAtk = atk;
        this.def = def;
        this.oriDef = def;
        this.turn = false;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public String getMonsType(){
        return monsType;
    }

    public void setMonsType(String monsType) {
        this.monsType = monsType;
    }

    public String getAttribute() {
        return attribute;
    }

    public int isEffect() {
        return effect;
    }

    public int getLevel() {
        return level;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setDef(int def) {
        this.def = def;
    }
    
    public void reset(){
        this.atk = oriAtk;
        this.def = oriDef;
        this.state=0;
        this.turn=false;
    }
    
    public void print(){
        String sState="";
        if(state==3){
            sState="Defense Mode";
        }else if(state==4){
            sState="Attack Mode";
        }
        System.out.println(this.getName()+" | *"+this.getLevel()+" | ATK "+this.getAtk()+"/Def "+this.getDef()+" | "+sState);
    }
    
}