package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Lifegame {
    private int[][] square =new int[10005][10005];
    private int[][] square1 =new int[10005][10005];
    int n;
    int num;
    String readfilename;
    String writefilename;
    public Lifegame(String readfilename,String writefilename)  {
        this.readfilename = readfilename;
        this.writefilename = writefilename;
        readfile();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
        for(int i = 0; i < num ; i++){
            fixedThreadPool.execute(new MyThread(0,0,n/2,n/2));
            fixedThreadPool.execute(new MyThread(n/2,0,n,n/2));
            fixedThreadPool.execute(new MyThread(0,n/2,n/2,n));
            fixedThreadPool.execute(new MyThread(n/2,n/2,n,n));
            while(((ThreadPoolExecutor)fixedThreadPool).getQueue().size() > 0);
            square = square1.clone();
            System.out.println(i);
        }
        fixedThreadPool.shutdown();


         writeFile();
    }
    class MyThread implements Runnable{
        private int x,y,x1,y1;
        private MyThread(int x,int y,int x1,int y1){
            this.x = x;
            this.y = y;
            this.x1 = x1;
            this.y1 = y1;
        }
        @Override
        public void run(){
            gamestart(x,y,x1,y1);
        }
    };
    public void readfile() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(readfilename));
            String str;
            str=in.readLine();
            String[] f = str.split(",");
            n = Integer.parseInt(f[0]);
            str=in.readLine();
            f = str.split(",");
            num = Integer.parseInt(f[0]);
            while((str=in.readLine())!=null) {
                //System.out.println(str);
                f = str.split(",");
                square[Integer.parseInt(f[0])][Integer.parseInt(f[1])]=1;

            }
            in.close();
        }
        catch (Exception e) {

        }
    }
    public void writeFile(){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(writefilename));

            out.write(String.format(n+"\n"));
            System.out.println(n);
            out.write(num+"\n");
            System.out.println(num);
            for(int i=0;i<n;i++){
                for(int j = 0;j < n;j++){
                    if(square1[i][j]==1)
                    {
                        out.write(i+","+j+"\n");
                       
                    }

                }
            }
            out.flush();
            out.close();

        }
        catch (Exception e) {

        }
    }
    public void gamestart(int x,int y,int x1,int y1){
        int count=0;
        for(int i=x;i<x1;i++){
            for(int j=y;j<y1;j++){
                count=countnum(i,j);
                if(count==3){
                    square1[i][j]=1;
                }
                else if(count==2)
                    square1[i][j]=square[i][j];
                else
                    square1[i][j]=0;
            }
        }

    }
    public int countnum(int x,int y){
        if(x==0){
            if(y==0){
                return square[x+1][y]+square[x+1][y+1]+square[x][y+1];
            }
            else if(y==n){
                return square[x][y-1]+square[x+1][y]+square[x+1][y-1];
            }
            else
            {
                return square[x][y+1]+square[x][y-1]+square[x+1][y]+square[x+1][y+1]+square[x+1][y-1];
            }
        }
        else if(x==n)
        {
            if(y==0){
                return square[x-1][y]+square[x-1][y+1]+square[x][y+1];
            }else if(y==n){
                return square[x-1][y]+square[x-1][y-1]+square[x][y-1];
            }else
            {
                return square[x-1][y]+square[x-1][y+1]+square[x][y+1]+square[x][y-1]+square[x-1][y-1];
            }
        }
        else{
            if(y==0){
                return square[x-1][y]+square[x-1][y+1]+square[x][y+1]+square[x+1][y]+square[x+1][y+1];
            }
            else if(y==n){
                return square[x-1][y-1]+square[x-1][y]+square[x][y-1]+square[x+1][y-1]+square[x+1][y];
            }
            else{
                return square[x-1][y-1]+square[x-1][y]+square[x][y-1]+square[x+1][y-1]+square[x+1][y]+square[x-1][y+1]+square[x][y+1]+square[x+1][y+1];
            }
        }
    }
}
