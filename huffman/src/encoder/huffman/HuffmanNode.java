package encoder.huffman;

/**
 * Created by Sydney on 2018/11/22.
 */

public class HuffmanNode
{
    public int data;
    public char c;
    public HuffmanNode left;
    public HuffmanNode right;

    public HuffmanNode(int data, char c, HuffmanNode left, HuffmanNode right)
    {
        this.data = data;
        this.c = c;
        this.left = left;
        this.right = right;
    }


    public boolean isLeaf()
    {
        return (this.left == null && this.right == null && this.c != '\0');
    }

}
