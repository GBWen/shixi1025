import java.util.ArrayList;

/**
 * Created by gbw on 16-10-25.
 */
public class Data
{
    String All = null;
    ArrayList<String> Words = null;
    boolean Flag;
    int Father;

    public Data(String S, ArrayList<String> W)
    {
        Words = new ArrayList<String>();
        All = S;
        int N = W.size();
        for (int i=0; i<N; i++)
            Words.add(W.get(i));
        Flag = false;
    }
}
