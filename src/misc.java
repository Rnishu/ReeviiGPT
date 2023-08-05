public class misc {
    public int factorial(int n){
        if(n==0) return 1;
        else return n*factorial(n-1);
    }
    public double cdf_n(double x,int n){
        if(n==0){
            return 0.5;
        }
        else if(n==1){
            return 1/Math.sqrt(2*Math.PI);
        }
        else{
            return (2-n)*cdf_n(x, n-2);
        }
    }
    public double cdf(double x){
        /*int n=0;
        double out;
        double cdf=0;
        do{
            out=cdf_n(x,n)*Math.pow(x,n)/factorial(n);
            cdf+=out;
            n++;
        } while(Math.abs(out)>Math.pow(10.0,-15.0));
        return cdf;*/
        double out=0,temp=0;
        for(int k=0;k<30;k++){
            temp=Math.pow(-1,k)*Math.pow(x,(2*k)+1)/(Math.pow(2,k)*factorial(k)*((2*k)+1));
            out+=temp;
        }
        return 0.5+(out/Math.sqrt(2*Math.PI));
    }
    public double invNorm(double desired){
        double x=0;
        double dx;
        for(int i=0;i<100;i++){
            dx=(cdf(x)-desired)*Math.exp(x*x*0.5)*Math.sqrt(2*Math.PI);
            x-=dx;
        } 
        return x;
    }
    public static void main(String[] args){
        double[] arr=new double[10000];
        int singleDeviation=0;
        int doubleDeviation=0;
        int tripleDeviation=0;
        misc Misc=new misc();
        int nan=0;
        //System.out.println(Misc.cdf(-2));
        //long start=System.currentTimeMillis();
        for(int i=0;i<100;i++){
            arr[i]=Misc.invNorm(Math.random());
            while(Double.isNaN(arr[i])) arr[i]=Misc.invNorm(Math.random()); //in order to ensure nothing outputs nan
        //for(int i=0;i<100;i++){
            if(arr[i]<1&&arr[i]>-1){
                singleDeviation++;
            }
            else if(arr[i]<2&&arr[i]>-2){
                doubleDeviation++;
            }
            else if(arr[i]<3&&arr[i]>-3){
                tripleDeviation++;
            } 
            if(Double.isNaN(arr[i])) nan++;
        }
        System.out.println("Probability of being in between the first deviations "+singleDeviation+"%"+"\nProbability of being in between the second deviations "+doubleDeviation+"%"+"\nProbability of being in between the third deviations "+tripleDeviation+"%");
        System.out.println("Number of NaNs: "+nan);
        //System.out.println(Double.isInfinite(Math.pow(2.718,450)));
        //System.out.println(4.00/0.00);
    }
}
