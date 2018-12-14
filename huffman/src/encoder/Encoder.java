package encoder;

import encoder.huffman.HuffmanComparator;
import encoder.huffman.HuffmanNode;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileReader;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Encoder
{
    HashMap<Character, String> encodings = new HashMap<>();
    HuffmanNode rootNode;
    HashMap<Character, Integer> frequencies;

    public String encode(String path) throws Exception
    {
    // Call getFrequencies() to count frequencies of each character that appears in the file.
        frequencies = getFrequencies(path);

    // Build a Huffman encoding tree using a Priority Queue to store nodes and sort by character frequencies.
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<HuffmanNode>(frequencies.keySet().size(), new HuffmanComparator());
        for (char character : frequencies.keySet()) {
            HuffmanNode node = new HuffmanNode(frequencies.get(character), character, null, null);
            queue.add(node);
        }
        HuffmanNode rootNode = null;
        while (queue.size() != 1) {
            HuffmanNode smallest = queue.poll();
            HuffmanNode nextSmallest = queue.poll();
            HuffmanNode parent = new HuffmanNode(smallest.data + nextSmallest.data, '\0', smallest, nextSmallest);
            rootNode = parent;
            queue.add(parent);
        }
        this.rootNode = rootNode;

    //  Convert the Huffman tree to bits and store character encodings in the encodings HashMap.
        convertToBits(rootNode, "");

    //  Read the file and create an encoded string where each character in the file is represented with bits from the encodings HashMap.
        FileReader reader = new FileReader(path);
        int i;
        String encoded = "";
        while ((i = reader.read()) != -1) {
            encoded += encodings.get((char) i);
        }
        return encoded;

    }

    public String decode(String encoded) throws Exception
    {
        String decoded = "";
        int i = 0;
    // For each character in the encoded string, traverse the Huffman tree until we reach a leaf, then add its corresponding character to our decoded string.
        while (i < encoded.length() - 1) {
            HuffmanNode curr = this.rootNode;
            while (!curr.isLeaf()) {
                if (encoded.charAt(i) == '0') {
                    curr = curr.left;
                    i ++;
                } else {
                    curr = curr.right;
                    i ++;
                }
            }
            decoded += curr.c;
        }
        PrintWriter writer = new PrintWriter("./decoded.txt");
        writer.write(decoded);
        writer.close();
        return decoded;
    }

//
    public void convertToBits(HuffmanNode root, String s)
    {
        if (root.isLeaf()) {
            encodings.put(root.c, s);
            return;
        }
        convertToBits(root.left, s + "0");
        convertToBits(root.right, s + "1");
    }

    HashMap<Character, Integer> getFrequencies(String path) throws Exception
    {
        FileReader reader = new FileReader(path);
        HashMap<Character, Integer> frequencies = new HashMap<>();
        int i;
        while ((i=reader.read()) != -1) {
            char curr = (char) i;
            if (frequencies.containsKey(curr)) {
                frequencies.put(curr, frequencies.get(curr) + 1);
            } else {
                frequencies.put(curr, 1);
            }
        }
        return frequencies;
    }

    public static void main(String[] args) throws Exception{
        Encoder encoder = new Encoder();
        System.out.println(encoder.decode(encoder.encode("./data.txt")));
        System.out.println(encoder.decode(encoder.encode("./data2.txt")));
    }
}

