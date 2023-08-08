package JTorch;

public class MLP {
    Layer[] layers;
    public MLP(int[] len){
        layers=new Layer[len.length-1];
        for(int i=1;i<len.length;i++){
            layers[i-1]=new Layer(len[i-1],len[i]);
        }
    }
    public Node[] forward(Node[] in){
        Node[] out=in;
        for(int i=0;i<this.layers.length;i++){
            out=this.layers[i].forward(out);
        }
        return out;
    }
    public Node[][] forward(Node[][] in){
        Node[][] out=in;
        for(int i=0;i<this.layers.length;i++){
            out=this.layers[i].forward(out);
        }
        return out;
    }
    public Node[] forward(Node[] in,char a){
        Node[] out=in;
        for(int i=0;i<this.layers.length;i++){
            out=this.layers[i].forward(out,a);
        }
        return out;
    }
    public Node[][] forward(Node[][] in, char a){
        Node[][] out=in;
        for(int i=0;i<this.layers.length;i++){
            out=this.layers[i].forward(out,a);
        }
        return out;
    }
    public Node[] parameters(){
        Node[] para=new Node[0];
        for(Layer l:layers){
            para=Node.concat(para,l.parameters());
        }
        return para;
    }
}
