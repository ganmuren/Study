public class Solution {
   public int lengthLongestPath(String input) {
        _max = 0;
        int sta1 = 0;
        int len1 = 0;
        int lastLe = 0;
        Stack<Integer> lengthStack = new Stack<>();
        len1 = getPartLength(input, sta1);
        while (len1 > 0) {
            String part = input.substring(sta1, sta1 + len1);
            int le = countTab(part);
            while (le <= lastLe&&lengthStack.size()>le) 
                lengthStack.pop();
            String sub = getSubString(part);
            calculateLength(sub, lengthStack);
            sta1 += len1+1;//skip '\n'
            len1 = getPartLength(input, sta1);
            lastLe=le;
        }
        return _max;
    }

    private int _max = 0;
    private int getPartLength(String path, int sta1) {
        if (sta1 >= path.length()) return -1;
        int len1;
        int ix1 = path.indexOf("\n", sta1);
        if (ix1 >= 0) len1 = ix1 - sta1;
        else {
            len1 = path.length() - sta1;
        }
        return len1;
    }

    private int countTab(String path) {
        int count = 0;
        int ix = path.indexOf("\t");
        while (ix >= 0) {
            count++;
            ix = path.indexOf("\t", ix + 1);
        }
        return count;
    }

    private String getSubString(String path) {
        int ix = path.lastIndexOf("\t");
        if (ix >= 0) return path.substring(ix+1);
        return path;
    }

    private void calculateLength(String path, Stack<Integer> lengthStack) {
        int length = path.length();
        if (lengthStack.size() != 0)
            length += lengthStack.peek() ;
        if (path.contains(".")) {
            _max = Math.max(length, _max);
        } else {
            //no '\t'
            lengthStack.push(length+1);//include '/'
        }

    }
}
