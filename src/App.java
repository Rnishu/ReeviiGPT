//Class "Node" has value, operation, children(operands), and gradient
import JTorch.*;
public class App{
    public static void main(String[] args){
        Node unitNode=new Node(1);
        Node loss=new Node(0);
        Node[][] orQuestions={{new Node(1), new Node(0)},{new Node(0),new Node(1)},{new Node(0),new Node(0)}};
        Layer n1=new Layer(2,2);
        for(int i=0;i<100 ;i++){
        Node[] out1=n1.forward(orQuestions[i%3]);
        loss=unitNode.sub(out1[(~((int)orQuestions[i%3][0].value&(int)orQuestions[i%3][1].value))&((int)orQuestions[i%3][0].value|(int)orQuestions[i%3][1].value)]);
        loss.backward();
        Node[] parameters=n1.parameters();
        for(int j=0;j<parameters.length;j++){
            parameters[j]=new Node(parameters[j].value-(0.1*parameters[j].grad), parameters[j].op, parameters[j].childrNodes);
            parameters[j].grad=0;
        }
    }
        Node[] in={new Node( 1),new Node(01)};
        Node[] out2=n1.forward(in);
        System.out.println("loss is:"+loss.value+"\nProbability of a zero:"+out2[0].value+"\nProbability of a one:"+out2[1].value);
    }
}
 

