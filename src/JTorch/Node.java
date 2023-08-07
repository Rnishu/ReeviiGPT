package JTorch;
public class Node {
    //attributes
    public double value;
    public char op;
    public Node[] childrNodes;
    public double grad;
    //two constructors, one is the simple one that just defines value, the second is used for all different functions
    public Node(double value){
        this.value=value;
    }
    public Node(double value, char op, Node[] childrNodes){
        this.value=value;
        this.op=op;
        this.childrNodes=childrNodes;
    }
    //Defines different functions, the most basic ones being add,mul,pow. Everything else is derived
    public Node add(Node n2){
        Node[] children={this,n2};
        return (new Node(this.value+n2.value , '+',children ));
    }
    public Node mul(Node n2){
        Node[] children={this,n2};
        return (new Node(this.value*n2.value , '*', children));
    }
    public Node mul(double n){
        Node[] children={this,new Node(n)};
        return (new Node(this.value*n , '*', children));
    }
    public Node neg(){
        return this.mul(-1);
    }
    public Node pow(double n){
        Node []children={this,new Node(n)};
        return (new Node(Math.pow(this.value,n) , '^',children ));
    }
    public Node sub(Node n2){
        return this.add(n2.neg());
    }
    public Node div(Node n2){
        return this.mul(n2.pow(-1));
    }
    public Node relu(){
        Node[] children={this};
        if(this.value>0){
            return new Node(this.value, 'r',children);
        }
        else{
            return new Node(0,'r',children);
        }
    } 
    public static Node[] softmax(Node[] in){
        double sum=0;
        for(Node node:in)
        sum+=Math.exp(node.value);
        for(Node node:in){
            node=new Node(Math.exp(node.value)/sum,'s',new Node[] {node});
        }
        return in;
    }
    public static Node nll(Node[] prob, int answer){
        Node[] childrNodes={prob[answer]};
        return new Node(-Math.log(prob[answer].value),'l',childrNodes);
    }
    //only called on output, not recursive, sets gradient of output 1
    public void backward(){
        this.grad=1;
        this.backpass();
    }
    //Recursive function, decides gradient of children that is derived from gradient of parent and the operation done, and then calls the function on the children
    public void backpass(){
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
            case 'r':this.childrNodes[0].grad+=this.value==0?0:this.grad;
            this.childrNodes[0].backpass();
            break;
            case '@':for(int i=0;i<this.childrNodes.length/2;i++){
                this.childrNodes[i].grad+=(this.childrNodes[i+this.childrNodes.length/2].value*this.grad);
                this.childrNodes[i+this.childrNodes.length/2].grad+=(this.childrNodes[i].value*this.grad);
            }
            break;
            case 's':this.childrNodes[0].grad=this.value*(1-this.value);
            this.childrNodes[0].backpass();
            break;
            case 'l':this.childrNodes[0].grad=-1/this.childrNodes[0].value;
            this.childrNodes[0].backpass();
            break;
        }
    }
    public static Node[][] transpose(Node[][] arr){
        Node[][] temp=new Node[arr[0].length][arr.length];
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
            temp[j][i]=arr[i][j];
             }
        }
        return temp;
    }
    public static Node[][] matMul(Node[][] arr1,Node[][] arr2){
        Node[][] temp=Node.transpose(arr2);
        Node[][] out=new Node[arr1.length][arr2[0].length];
        for(int i=0;i<arr1.length;i++){
            for(int j=0;j<arr2[0].length;j++){
                out[i][j]=Node.dot(arr1[i],temp[j]);
            }
        }
        return out;
    }
    public static Node dot(Node[] arr1, Node[] arr2){
        Node[] temp1={},temp2={};
        double dot=0;
        for(int i=0;i<Math.min(arr1.length,arr2.length);i++){
            dot+=arr1[i].value*arr2[i].value;
            temp1=Node.concat(temp1,new Node[] {arr1[i]});
            temp2=Node.concat(temp2,new Node[] {arr2[i]});
        }
        Node[] childrNodes=Node.concat(temp1,temp2);
        
        return new Node(dot, '@',childrNodes);
    }
    //creating static method for concatenation as it is used a lot
    public static Node[] concat(Node[] arr1, Node[] arr2){
    Node[] sum=new Node[arr1.length+arr2.length];
    for(int i=0;i<arr1.length;i++)
        sum[i]=arr1[i];
    for(int i=0;i<arr2.length;i++)
        sum[i+arr1.length]=arr2[i];
    return sum;
    }
}

