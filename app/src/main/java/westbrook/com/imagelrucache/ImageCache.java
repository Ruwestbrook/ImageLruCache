package westbrook.com.imagelrucache;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by westbrook on 2017/12/8.
 * 要求:采用Lru算法,将最近的使用的图片加载到内存,然后若超出分配道德内存的额度,那么会把最没用到的图片删除出内存
 */

public class ImageCache {
    private Node last; //最后一张图片(若超出最大限制,那么这张图片会被删除)
    private Node first; //第一张图片(最近才被加载到内存,或者在内存中又被重新使用的图片)
    private int mCacheSize;//总共分配到的内存大小
    private int currentSize; //正在内存中图片的数量
    private HashMap<String,Node> nodes;
    public ImageCache(int i){
        mCacheSize=i;
        currentSize=0;
        nodes=new HashMap<>();
    }
    /*
    链表时的缓存 图片
     */
    private class  Node {
       Node previous; //上一个节点的Node
       Node next;   //下一个节点的Node
       String key; //唯一值 可以是 图片的UR, 也可以是特定的某些值
       Bitmap value;
    }

    void put(String key,Bitmap bitmap){
        //在获取这张图片的时候,根据LRU算法,这张图片会被放置到链表第一的位置
        if(currentSize>mCacheSize){
            if(last!=null){
                nodes.remove(last.key);
            }
            removeLast();
        }else {
            currentSize++;
        }
        Node node=new Node();
        node.key=key;
        node.value=bitmap;
        moveToFirst(node);
        nodes.put(key,node);

    }
    Bitmap get(String key){
        Node node=nodes.get(key);
            if(node!=null){
                moveToFirst(node);
                return  node.value;
            }
            return  null;
    }

    /*
      将使用最多的移动到第一位
     */
    private void moveToFirst(Node node){
        if(first==node)
            return;
        if(node.previous!=null)
            node.previous.next=node.next;
        if(node.next!=null)
            node.next.previous=node.previous;
        if (last == node)
            last = node.previous;
        if(first!=null){
            node.next=first;
            first.previous=node;
        }
        node.previous=null;
        first=node;
        if(last==null){
            last=first; //
        }
    }
 /*
   删除最少使用的
  */
    private void removeLast(){
            currentSize--;
            if(last!=null) {
                if (last.previous != null)
                last.previous.next=null;
             else
                 first=null; //如果最后一个节点的前一个为null,那么代表内存中只能缓存一张图片,first=last

                last=last.previous;

            }
    }
}
