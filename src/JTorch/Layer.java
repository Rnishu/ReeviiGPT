package JTorch;
//Class Layer is a Group of neurons, taking some number of inputs and doing dot products with the vector assigned to each neuron, and giving an array of length of number of neurons in each layer
public class Layer{
    //the group of neurons that each layer consists
    public Neuron[] neurons;
    //Constructor creates nout neurons each having nin weights
    public Layer(int nin, int nout){
        this.neurons=new Neuron[nout];
        for(int i=0;i<nout;i++){
            this.neurons[i]=new Neuron(nin);
        }
    }


    //needed a way to create a node at every iteration so i created a array of nodes called sum which in its computation creates objects of the multiplication type anyway
    public Node[] forward(Node[] in){
        Node[] out=new Node[this.neurons.length];
            for(int i=0;i<out.length;i++){
                out[i]=this.neurons[i].forward(in);
            }
            return out;
    }
    public Node[][] forward(Node[][] in){
        Node[][] out=new Node[in.length][this.neurons.length];
            for(int i=0;i<out.length;i++)
                out[i]=this.forward(in[i]);
            return out;
        }
    //return array of parameters
    public Node[] parameters(){
        Node[] para=new Node[this.neurons.length*(this.neurons[0].weights.length+1)];
        Node[] temp=new Node[this.neurons[0].weights.length+1];
        for(int i=0;i<this.neurons.length;i++){
            temp=this.neurons[i].parameters();
            for(int j=i*(this.neurons[0].weights.length+1);j<(i+1)*(this.neurons[0].weights.length+1);j++){
                para[j]=temp[j%(this.neurons[0].weights.length+1)];
            }
        }
        return para;
    }
    public Node[] forward(Node[] in, char a){
        Node[] out=new Node[this.neurons.length];
            for(int i=0;i<out.length;i++){
                out[i]=this.neurons[i].forward(in,a);
            }
            return out;
    }
    public Node[][] forward(Node[][] in,char a){
        Node[][] out=new Node[in.length][this.neurons.length];
            for(int i=0;i<out.length;i++)
                out[i]=this.forward(in[i],a);
            return out;
        }
}

