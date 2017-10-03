package lemurlm;

import java.io.*;
import static java.nio.file.Files.lines;
import java.util.Scanner;
import lemurproject.indri.*;

public class LemurLM {

    IndexEnvironment IE = new IndexEnvironment();
    QueryEnvironment QE = new QueryEnvironment();
    Stopwords SW = new Stopwords();
    //public String StopPath;
    public String IndexPath;
    public int rank;
    
    public LemurLM()
    {
      //  StopPath = "<Path to >\\StopWordsList.txt";
        IndexPath = "<Path to >\\Index";
        //QueryPath = "<Path to >\\Queries.txt";
        rank=0;
    }
    
    public void Indexing() throws Exception
    {
        
        try
        {
            String[] ArrayStopWords = SW.Createstopwords();
            IE.setStoreDocs(true);
            IE.setStopwords(ArrayStopWords);
            IE.setStemmer("PORTER");
            IE.create(IndexPath);
            
            File content = new File("<Path to>\\Dataset");
            
            for(String fileFromFolderOne : content.list())
            {
                IE.addFile("<Path to Stopword list>\\".concat(fileFromFolderOne));
            }
            System.out.println("No Of Indexd Documents Are:"+IE.documentsIndexed());
            
           
            IE.close();
            
            
        }
        catch(IOException e)  
        {  
            // Print out the exception that occurred  
            System.out.println("Unable to create : "+e.getMessage());                
        }  
        
    }
    
    public void QuerySearching(String QueryPath) throws Exception
    {
        
        
        try 
        {
            QE.addIndex(IndexPath);
            ScoredExtentResult[] results = QE.runQuery(QueryPath, 5);
            String[] names = QE.documentMetadata(results, "docno");
            ParsedDocument[] documents = QE.documents(results);
           
            for (int i = 0; i < results.length; i++) 
            {
                rank++;
                System.out.println("1.Rank:" + rank + "\n2.DocId:" +names[i] + "\n3.Begin:" + results[i].begin + "\n4.End: " + results[i].end +
                "\n5.Score:" + results[i].score +  "\n6.Content:\n"+documents[i].content);
            }
        } 
        catch (Exception e) 
        {
            System.out.println("Exception was thrown: " + e.getMessage());
        }   
    }
    
    public static void main(String[] args) throws IOException, Exception 
    {
        // TODO code application logic here
        Scanner in = new Scanner(System.in);
             
             LemurLM lm = new LemurLM();
             String QueryPath;
             
             lm.Indexing();
             
             System.out.println("Enter a string:");
             QueryPath = in.nextLine();
             lm.QuerySearching(QueryPath);
        
    }
    
}
