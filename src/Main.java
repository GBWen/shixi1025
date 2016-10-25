import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by gbw on 16-10-25.
 */
public class Main
{
    public static boolean IsKey(String S)
    {
        if (S.length() < 2) return false;
        ArrayList<String> NotKey = new ArrayList<String>();
        NotKey.add("LTD");
        NotKey.add("CO");
        NotKey.add("INC");
        NotKey.add("CORP");
        NotKey.add("LLC");
        NotKey.add("KK");
        NotKey.add("AG");
        NotKey.add("GROUP");
        NotKey.add("INT");
        NotKey.add("ENG");
        NotKey.add("MFG");
        NotKey.add("SA");
        NotKey.add("NV");
        NotKey.add("CHINA");
        NotKey.add("CHINESE");
        for (int i=0;i<NotKey.size();i++)
            if (S.equals(NotKey.get(i)))
                return false;
        return true;
    }

    public static boolean Similar(Data A, Data B)
    {
        int i;
        // 某一个关键词匹配到即可
//        for (i=0;i<A.Words.size();i++)
//            for (j=0;j<B.Words.size();j++)
//            {
//                String S1 = A.Words.get(i);
//                String S2 = B.Words.get(j);
//                if (S1.equals(S2))
//                    return true;
//            }

//        //去掉整个字串完全匹配
//        String S1 = "";
//        String S2 = "";
//        for (i=0;i<A.Words.size();i++)
//        {
//            S1 = S1 + A.Words.get(i);
//            if (i != A.Words.size()-1)
//                S1 = S1 + " ";
//        }
//
//        for (i=0;i<B.Words.size();i++)
//        {
//            S2 = S2 + B.Words.get(i);
//            if (i != B.Words.size()-1)
//                S2 = S2 + " ";
//        }
////        System.out.println(S1 + A.Words.size());
////        System.out.println(S2 + B.Words.size());
////        System.out.println();
////        return false;
//        if (S2.indexOf(S1) >=0)
//        {
//            int idx = S2.indexOf(S1)+S1.length();
//            if (idx >= S2.length() || S2.charAt(idx) == ' ')
//                return true;
//        }
//        return false;

        //中文匹配
        String S1 = "";
        String S2 = "";
        S1 = A.Words.get(0);
        for (i=0;i<B.Words.size();i++)
            S2 = S2 + B.Words.get(i);
//        System.out.println(S1);
//        System.out.println(S2);
//        System.out.println(S2.indexOf(S1));
//        System.out.println();
        if (S2.indexOf(S1) >=0)
            return true;
        return false;
    }

    public static void main(String args[])
    {
        ArrayList<String> Name = new ArrayList<String>();
//        Name.add("中国公司5年中对应专利.csv");
//        Name.add("中国公司对应专利.csv");
//        Name.add("世界公司5年中对应专利.csv");
//        Name.add("世界公司对应专利.csv");
        Name.add("中国数据库中国公司对应专利.txt");
        Name.add("中国数据库5年中国公司对应专利.txt");
        Name.add("中国数据库中国公司对应专利(去掉5以下).txt");
        Name.add("中国数据库5年中国公司对应专利(去掉5以下).txt");
//        Name.add("wipo--20161025");
        try
        {
            String name;
            for (int p=0;p<Name.size();p++)
            {
                ArrayList<Data> Arr = new ArrayList<Data>();
                name = Name.get(p);
                FileReader fr = new FileReader("./input/" + name);
                BufferedReader br = new BufferedReader(fr);
                FileWriter fw = new FileWriter("./output/" + name + ".out");
                while (br.ready()) {
                    String Line = br.readLine();
                    ArrayList<String> S = new ArrayList<String>();
                    if (Line.length() > 1 && Line.indexOf(",") != -1)
                    {
                        boolean flag = true;
                        int start = 0;
                        int end = Line.length();
                        int index = Line.indexOf(" ");
                        if (index == -1)
                        {
                            index = Line.indexOf(",");
                            flag = false;
                        }
                        while (index != -1 && flag == true)
                        {
                            String sub = Line.substring(start, index);
                            sub = sub.trim();
                            start = index + 1;
                            index = Line.indexOf(" ", start);
                            if (index == -1) {
                                index = Line.indexOf(",");
                                flag = false;
                            }
                            if (IsKey(sub))
                                S.add(sub);
                        }
                        if (index - start >= 1)
                        {
                            String sub = Line.substring(start, index);
                            if (IsKey(sub))
                                S.add(sub);
                        }
                        //                    System.out.println(Line);
                        //                    System.out.println(S.size());
                        //                    for (int i=0;i<S.size();i++)
                        //                        System.out.println(S.get(i));
                        Data D = new Data(Line, S);
                        Arr.add(D);
                    }
                }
                fr.close();
                br.close();
                //            System.out.println(Arr.size());

                for (int i = 0; i < Arr.size(); i++)
                {
                    if (Arr.get(i).Flag == false)
                    {
                        Arr.get(i).Flag = true;
                        fw.write(Arr.get(i).All);
                        fw.write("\n");
                        for (int j = 0; j < Arr.size(); j++)
                        {
                            if (Arr.get(j).Flag == false && Similar(Arr.get(i), Arr.get(j)))
                            {
                                Arr.get(j).Flag = true;
                                Arr.get(j).Father = i;
                                //                        System.out.println(Arr.get(i).All);
                                //                        System.out.println(Arr.get(j).All);
                                //                        System.out.println("\n");
                                fw.write(Arr.get(j).All);
                                fw.write("\n");
                            }
                        }
                        fw.write("\n");
                    }

                }
                fw.close();
                System.out.println(name + " done!");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
