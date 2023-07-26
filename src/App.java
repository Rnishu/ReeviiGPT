//Class "Node" has value, operation, children(operands), and gradient
class Node {
    //attributes
    double value;
    char op;
    Node[] childrNodes=new Node[2];
    double grad;
    //two constructors, one is the simple one that just defines value, the second is used for all different functions
    Node(double value){
        this.value=value;
    }
    Node(double value, char op, Node[] childrNodes){
        this.value=value;
        this.op=op;
        this.childrNodes=childrNodes;
    }
    //Defines different functions, the most basic ones being add,mul,pow. Everything else is derived
    Node add(Node n2){
        Node[] children={this,n2};
        return (new Node(this.value+n2.value , '+',children ));
    }
    Node mul(Node n2){
        Node[] children={this,n2};
        return (new Node(this.value*n2.value , '*', children));
    }
    Node mul(double n){
        Node[] children={this,new Node(n)};
        return (new Node(this.value*n , '*', children));
    }
    Node neg(){
        return this.mul(-1);
    }
    Node pow(double n){
        Node []children={this, new Node(n)};
        return (new Node(Math.pow(this.value,n) , '^',children ));
    }
    Node sub(Node n2){
        return this.add(n2.neg());
    }
    Node div(Node n2){
        return this.mul(n2.pow(-1));
    }
    //only called on output, not recursive, sets gradient of output 1
    void backward(){
        this.grad=1;
        this.backpass();
    }
    //Recursive function, decides gradient of children that is derived from gradient of parent and the operation done, and then calls the function on the children
    void backpass(){
        switch(this.op){
            case '+': this.childrNodes[0].grad+=this.grad;
            this.childrNodes[1].grad+=this.grad;
            this.childrNodes[0].backpass();
            this.childrNodes[1].backpass();
            break;
            case '*':this.childrNodes[0].grad+=this.childrNodes[1].value*this.grad;
            this.childrNodes[1].grad+=this.childrNodes[0].value*this.grad;
            this.childrNodes[0].backpass();
            this.childrNodes[1].backpass();
            break;
            case '^':this.childrNodes[0].grad+=this.childrNodes[1].value*Math.pow(this.childrNodes[0].value,this.childrNodes[1].value-1)*this.grad;
            this.childrNodes[0].backpass();
            break;
        }
    }
    //creating static method for concatenation as it is used a lot
    static Node[] concat(Node[] arr1, Node[] arr2){
    Node[] sum=new Node[arr1.length+arr2.length];
    for(int i=0;i<arr1.length;i++)
        sum[i]=arr1[i];
    for(int i=0;i<arr2.length;i++)
        sum[i+arr1.length]=arr2[i];
    return sum;
    }
}
//Class Neuron, each having weights and a bias, that input an array and output 1 number
class Neuron{
    //each neuron will have a array of weights and some bias to be added
    Node[] weights;
    Node bias;
    //each neuron is initialized to have nin weights
    Neuron(int nin){
        this.weights=new Node[nin];
        for(int i=0;i<nin;i++){
            this.weights[i]=new Node((Math.random()*2)-1);
        }
        this.bias=new Node(0);
    }
    //needed a way to create a node at every iteration so i created a array of nodes called sum which in its computation creates objects of the multiplication type anyway
    Node forward(Node[] in){
        Node[] sum=new Node[in.length-1];
        sum[0]=in[0].mul(this.weights[0]);
        for(int i=1;i<in.length-1;i++)
            sum[i]=sum[i-1].add(in[i].mul(this.weights[i]));
        return sum[in.length-2].add(in[in.length-1].mul(this.weights[in.length-1]));//sum[in.length-1];
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
//Class Layer is a Group of neurons, taking some number of inputs and doing dot products with the vector assigned to each neuron, and giving an array of length of number of neurons in each layer
class Layer{
    //the group of neurons that each layer consists
    Neuron[] neurons;
    //Constructor creates nout neurons each having nin weights
    Layer(int nin, int nout){
        this.neurons=new Neuron[nout];
        for(int i=0;i<nout;i++){
            this.neurons[i]=new Neuron(nin);
        }
    }
    //needed a way to create a node at every iteration so i created a array of nodes called sum which in its computation creates objects of the multiplication type anyway
    Node[] forward(Node[] in){
        Node[] output=new Node[this.neurons.length];
        for(int i=0;i<output.length;i++)
            output[i]=this.neurons[i].forward(in);
        return output;
    }
    //return array of parameters
    Node[] parameters(){
        /*Node[] para=new Node[this.neurons.length*(this.neurons[0].weights.length+1)];
        Node[] temp=new Node[this.neurons[0].weights.length+1];
        for(int i=0;i<this.neurons.length;i++){
            temp=this.neurons[i].parameters();
            for(int j=i*(this.neurons[0].weights.length+1);j<(i+1)*(this.neurons[0].weights.length+1);j++){
                para[j]=temp[j%(this.neurons[0].weights.length+1)];
            }
        }
        return para;*/   // This was the older code i used to generate parameters, before making the concat function which will be essential
        Node[] para=new Node[0];
        for(int i=0;i<this.neurons.length;i++)
            para=Node.concat(para,this.neurons[i].parameters());
        return para;
    }
}
public class App{
    public static void main(String[] args){
        Node[] arr={new Node(4), new Node(2),new Node(17)};
        Layer n1=new Layer(3,4);
        Node[] out1=n1.forward(arr);
        System.out.println("output="+out1[0].value);
        out1[0].backward();
        Node[] parameters=n1.parameters();
        for(int i=0;i<parameters.length;i++){
            Node w=parameters[i];
            System.out.println("value:"+w.value+" grad:"+w.grad);
        }
    }
}  

