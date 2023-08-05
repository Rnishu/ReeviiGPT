package JTorch;
//Class Neuron, each having weights and a bias, that input an array and output 1 number
public class Neuron{
    //each neuron will have a array of weights and some bias to be added
    public Node[] weights;
    public Node bias;
    //each neuron is initialized to have nin weights
    public Neuron(int nin){
        this.weights=new Node[nin];
        for(int i=0;i<nin;i++){
            this.weights[i]=new Node((Math.random()*2)-1);
        }
        this.bias=new Node(0);
    }
    //needed a way to create a node at every iteration so i created a array of nodes called sum which in its computation creates objects of the multiplication type anyway
    public Node forward(Node[] in){
        Node[] sum=new Node[in.length-1];
        sum[0]=in[0].mul(this.weights[0]);
        for(int i=1;i<in.length-1;i++)
            sum[i]=sum[i-1].add(in[i].mul(this.weights[i]));
        return ((sum[in.length-2].add(in[in.length-1].mul(this.weights[in.length-1]))).add(this.bias)) ;//sum[in.length-1];
        } 
    public Node forward(Node[] in, char a){
        Node[] sum=new Node[in.length-1];
        sum[0]=in[0].mul(this.weights[0]);
        for(int i=1;i<in.length-1;i++)
            sum[i]=sum[i-1].add(in[i].mul(this.weights[i]));
        return ((sum[in.length-2].add(in[in.length-1].mul(this.weights[in.length-1]))).add(this.bias)).relu() ;//sum[in.length-1];
        }     
    // Function to access all the parameters of a given layer
        Node[] parameters(){
        Node[] para=new Node[this.weights.length+1];
        para[this.weights.length]=this.bias;
        for(int i=0;i<this.weights.length;i++){
            para[i]=this.weights[i];
        }
        return para;
    }
}
