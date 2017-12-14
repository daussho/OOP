// Takis Group Inc

package takis.etc;

public class Stack {
    private Object[] stack;
    private int top;
    private int oriTop;
    
    //konstruktor
    public Stack(Object[] stack){
        int i = 0;
        this.stack = stack;
        while(stack[i] != null){
            i++;
        }
        this.top = i-1;
        this.oriTop = this.top;
    }
    
    //method
    public Object pop(){        
        if (0==top){
            return null;
        }else{
            this.top--;
            return this.stack[this.top+1];
        }
        
    }
    
    public void push(Object stack){
        this.top++;
        this.stack[top] = stack;        
    }
    public Object[] stackToArray(){
        return stack;
    }

    public Object[] getArray()
    {
        return this.stack;
    }
    public int getTop()
    {
        return this.top;
    }
    
    public void reset(){
        this.top = this.oriTop;
    }
}
