package takis.YOGS;

public class SpellCard extends Card{
    int linked;
    public SpellCard(int id, String name, float rarity, int state, String desc){
        super(id,  name, rarity, "Spell Card", state, desc);
        int linked=(-1);
    }
    @Override
    public void print(){
        System.out.println(this.getName()+" | "+this.getType());
    }
}
