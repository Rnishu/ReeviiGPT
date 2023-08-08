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
        for(int i=0;i<58;i++){
            if(x<probs[i].value){
                out=i;
                break;
            }
            x-=probs[i].value;
        }
        return out;
    }
    public static void main(String[] args){
        String inp="HAPPY FRIENDSHIP DAY NISHANTH ! You are the best person I met in college undoubtedly. I am so glad that you shifted to  Goregaon for 4 months and we started travelling together . I know at the start I used to make fun and say that you dont live there but now how I wish you still did .  All my life , Ive felt very unlucky in friendships but after meeting you and becoming best friends , I genuinely feel that for once in my life there is someone who cares about me and takes care of me just as much . Thank you so much for helping me with studies, and not making me feel dumb( most of the time ). I love spending time with you and I feel like Ive literally found my friend soulmate in you . I hope we always stay this close for life . I hope you know that I am always gonna be here for you and try to support you to the best of my capabilities.Theres so much more I wish I could say but Ill try to keep it short . Thank  you for being a friend . I love you";
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
        for(int i=0;i<10000;i++){
           
            int ran=(int)Math.random()*(inp.length()-8);
            for(int s=ran;s<ran+8;s++){
                input[s-ran]=new Node(ctoi(inp.charAt(s),lookup));
            }
            answer=ctoi(inp.charAt(ran+8),lookup);
            Node[] o=reevaa.forward(input, 'a');
            Node[] probs=Node.softmax(o);
            Node loss=Node.nll(probs,answer);
            loss.backward();
            for(Node p:para){
                p.value-=(0.01*p.grad);
                p.grad=0;
            }
            if(i==999){ 
                for(int j=0;j<58;j++) System.out.println(lookup[j]+":"+probs[j].value);
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
 

