//Class "Node" has value, operation, children(operands), and gradient
import JTorch.*;
public class App{
    static int ctoi(char c,char[] arr){
        int out=-1;
        for(int i=0;i<58;i++){
            if(arr[i]==c){out=i; break;}
        }
        return out;
    }
    static int choose(Node[] probs){
        double x=Math.random();
        int out=-1;
        for(int i=0;i<probs.length;i++){
            if(x<probs[i].value){
                out=i;
                break;
            }
            else x-=probs[i].value;
        }
        return out;
    }
    public static void main(String[] args){
        String inp="hi guys";
        int[] len={8,94,94,58};
        MLP reevaa=new MLP(len);
        char[] lookup=new char[58];
        for(char i='A';i<='Z';i++){
            lookup[i-65]=i;
        }
        for(char i='a';i<='z';i++){
            lookup[i-97+26]=i;
        }
        lookup[52]=' ';
        lookup[53]='!';
        lookup[54]='(';
        lookup[55]=')';
        lookup[56]='.';
        lookup[57]=',';
        Node[] input=new Node[8];
        Node[] para=reevaa.parameters();
        int answer;
        for(int i=0;i<1000;i++){
           
            int ran=(int)Math.random()*(inp.length()-8);
            for(int s=ran;s<ran+8;s++){
                input[s-ran]=new Node(ctoi(inp.charAt(s),lookup));
            }
            answer=ctoi(inp.charAt(ran+8),lookup);
            Node[] o=reevaa.forward(input, 'a');
            Node loss=Node.nll(Node.softmax(o),answer);
            loss.backward();
            for(Node p:para){
                p.value-=(0.01*p.grad);
                p.grad=0;
            }
        }
        String query="are you?";
        Node[] question=new Node[8];
        for(int s=0;s<8;s++){
                question[s]=new Node(ctoi(query.charAt(s),lookup));
            }
        Node[] probs=new Node[58];
        for(int i=0;i<100;i++){
            probs=Node.softmax((reevaa.forward(question,'a')));
            char a=lookup[choose(probs)];
            System.out.print(a);
            query=query.substring(1).concat(Character.toString(a));
        }
    }
}
 

