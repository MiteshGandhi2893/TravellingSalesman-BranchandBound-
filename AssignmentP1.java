/*ange this template, choose Tools | Templates
 * and open the template in the editor.
 */
//javaapplication11;
import java.util.PriorityQueue;
import java.util.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author Mitesh
 */
public class AssignmentP1{

    /**
     * @param args the command line arguments
     */
    public static String Permutation_string;
    public static ArrayList<Integer> minimumTour;

    public static int[][]inputCostArray;
    public  static int mincost;
    public static int minlength;
    public static int maxcost;
    public static int countNodes;

    public static void main(String[] args) {
        // TODO code application logic here
     int count =0;
      int size=0;
      int cursor=0;
      String sizesarray[]=null;
      String dataarray[]=null;


      try
      {
            LineNumberReader ln=new LineNumberReader(new FileReader(args[0]));
           FileOutputStream BW=new FileOutputStream(new File(args[1]));



            size=Integer.parseInt(ln.readLine());
            String data="";
           ln.setLineNumber(cursor+1);
            String Line=null;
            String sizes=size+"";
            while((Line=ln.readLine())!=null)
             {
                    if(count==((size*size)+cursor))
                    {
                        data=data+"@";
                        cursor=count;
                        ln.setLineNumber((size*size)+1);
                        size=Integer.parseInt(Line);
                        sizes=sizes+","+size;
                        ln.setLineNumber(cursor);

                    }
                    else
                    {
                        data=data+Line+"\n";
                        count++;
                }
           }

            sizesarray=sizes.split(",");
            dataarray=data.split("@");

           for(int i=0;i<sizesarray.length;i++)
           {
               int inputSize=Integer.parseInt(sizesarray[i].toString().trim());
               inputCostArray=new int[inputSize][inputSize];

            String inputDataArray[]=dataarray[i].split("[\\r\\n]+");

             for(int l=0;l<inputSize;l++)
             {
                 for(int m=0;m<inputSize;m++)
                 {

                     inputCostArray[l][m]=Integer.parseInt(inputDataArray[(m%inputSize)+(l*inputSize)].split("\\s+")[2]);
                 }
             }



                mincost=100000;
                minlength=10000;
                maxcost=-100000;
                minimumTour=new ArrayList<Integer>();

                long time1 = System.currentTimeMillis();//.nanoTime();

               minimumTour=travellSalesMan(inputSize,inputCostArray,minimumTour);

                long time2 = System.currentTimeMillis();
                long timeTaken = time2 - time1;



              // System.out.println(minimumTour);
                 String temp="";
                if(inputSize<=14)
                {
                for(int f:minimumTour)
                {
                        temp =temp+"\n"+f;
                }
                }
                else
                {
                        temp="\n";
                }
                   String Content="\n PNO : "+(i+1)+", input size : "+inputSize+", minimum length : "+minlength+", Number of Nodes :"+countNodes+", runtime: "+timeTaken+" ms "+temp;
                 byte[]con=(Content).getBytes();
                 BW.write(con);

           }
                BW.close();
        }
            catch(Exception ex)
        {
            ex.printStackTrace();
        }


    }
     public static ArrayList<Integer> travellSalesMan(int n,int costs[][],ArrayList<Integer>minimumTour)
    {
        Comparator<Node>com=new IntegerComparator();
        PriorityQueue<Node>PQ=new PriorityQueue<Node>(n,com);
        ArrayList<Integer>mintour=new ArrayList<Integer>();;
        int minBound=Integer.MAX_VALUE;
        Node BestNode=new Node();
        minlength=Integer.MAX_VALUE;

        Node v=new Node();
         v.level=0;
        v.path.add(0);
        v.bound=bound(costs,v);
        PQ.add(v);

        countNodes=1;
     // countNodes=PQ.size();
          while(!PQ.isEmpty())
        {


           if(v.bound<minBound)
           {
               BestNode=v;
               minBound=(int)v.bound;
           }
           v=PQ.remove();
        minBound=Integer.MAX_VALUE;
           if(v.bound<minBound)
           {
               int level=v.level+1;

               for(int i=1;i<n;i++)
               {

                   if(!v.path.contains(i))
                   {

                       Node newNode=new Node();
                       for(int l:v.path)
                       {
                           newNode.path.add(l);
                       }
                       newNode.level=level;
                       newNode.path.add(i);
                       newNode.bound=bound(costs,newNode);
                       if(newNode.bound<minBound)
                       {
                           PQ.add(newNode);
                  countNodes++;
                       }
                       else
                       {
                           newNode=null;
                       }
                   }
                   else
                   {
                       if(level==n-1)
                       {
                          for(int k=0;k<n;k++)
                          {
                              if(v.path.contains(k))
                              {
                                  continue;
                              }
                              else
                                  v.path.add(k);
                          }
                          v.path.add(v.path.get(0));

                           if(lengthOfPath(v, costs)<=minBound)
                           {
                               minlength=lengthOfPath(v, costs);
                               mintour=v.path;
                             return mintour;
                           }
                       }
                   }
               }


           }






        }

       return mintour;
    }


    private static int lengthOfPath(Node v,int cost[][])
    {
        int len=0;
       for(int i=0;i<v.path.size();i++)
       {
           len=len+cost[v.path.get(i)][v.path.get((i+1)%v.path.size())];
       }
       return len;
    }





     public static int bound(int cost[][],Node v)
    {
        int bound1=0;
        String costs="";
         ArrayList<Integer>TEMP=new ArrayList<Integer>();

         if(v.bound>=minlength)
         {
             return 0;
         }
         else
         {
         if(v.path.size()==1)
         {
             for(int i=0;i<cost.length;i++)
             {
                 for (int j=0;j<cost.length;j++)
                 {
                 if(cost[i][j]!=0)
                 {
                  costs=costs+","+cost[i][j];
                  }
                 }
                 costs=costs+"\n";
             }

         }

          else if(v.path.size()>1)

         {
             int temp =v.path.get(v.path.size()-1);
             for(int k=0;k<v.path.size()-1;k++)
             {
                costs=costs+"\n"+","+cost[v.path.get(k)][v.path.get(k+1)];

             }
             costs=costs+"\n";

            for(int i=0;i<cost.length;i++)
            {
                if(!v.path.contains(i))
                {
                    costs=costs+","+cost[temp][i];
                   TEMP.add(i);
                }
            }
            costs+="\n";
            TEMP.add(v.path.get(0));
            for(int i=0;i<TEMP.size()-1;i++)
            {
               // costs=costs+","+cost[i][v.path.get(0)];
                for(int j=0;j<TEMP.size();j++)
                {
                    if(cost[TEMP.get(i)][TEMP.get(j)]!=0)
                    {
                        costs=costs+","+cost[TEMP.get(i)][TEMP.get(j)];
                    }
                }
                costs=costs+"\n";
            }



         }


        for(int i:BoundArray(costs))
        {
           bound1=bound1+i;
        }

         }

     return bound1;
    }

    private static ArrayList<Integer>BoundArray(String costs)
 {
     ArrayList<Integer>MinArray=new ArrayList<Integer>();

     String Arr[]=costs.split("\n");
     for(int i=0;i<Arr.length;i++)
     {
        if(Arr[i]!=""&&Arr[i].contains(","))
         {
            MinArray.add(Minimum(Arr[i]));
         }
     }


     return MinArray;

 }



    private static int Minimum(String MinCosts)
{
   int min=Integer.MAX_VALUE;

    String []Minarray=MinCosts.split(",");


    for(int i=1;i<Minarray.length;i++)
    {
        //System.out.print(Minarray[2]);
        if(Minarray[i]!="")
        {
            int input=Integer.parseInt(Minarray[i]);
            if(input<min)
            {
                min=input;
            }
        }
    }
    return min;


}




}
class Node
{
    int level;
    ArrayList<Integer> path;
    double bound;
    Node()
    {
        level=0;
        path=new ArrayList<Integer>();
        bound=0.0;
    }

}
class IntegerComparator implements Comparator<Node>
{
    //@Override
    public int compare(Node u,Node v)
    {
        // Assume neither string is null. Real code should
        // probably be more robust
        // You could also just return x.length() - y.length(),
        // which would be more efficient.
        if (u.bound< v.bound)
        {
            return -1;
        }
        if (u.bound> v.bound)
        {
            return 1;
        }
        return 0;
    }
}
