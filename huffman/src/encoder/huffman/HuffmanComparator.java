package encoder.huffman;

import java.util.Comparator;

public class HuffmanComparator implements Comparator<HuffmanNode>
{

    public int compare(HuffmanNode a, HuffmanNode b)
    {
        return a.data - b.data;
    }
}
