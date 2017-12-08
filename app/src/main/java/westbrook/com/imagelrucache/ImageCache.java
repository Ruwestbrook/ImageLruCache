package westbrook.com.imagelrucache;

import android.graphics.Bitmap;

/**
 * Created by westbrook on 2017/12/8.
 * 要求:采用Lru算法,将最近的使用的图片加载到内存,然后若超出分配道德内存的额度,那么会把最没用到的图片删除出内存
 */

public class ImageCache {
    private Node last; //最后一张图片(若超出最大限制,那么这张图片会被删除)
    private Node first; //第一张图片(最近才被加载到内存,或者在内存中又被重新使用的图片)
    private int mCacheSize;//总共分配到的内存大小
    private int currentSize; //正在内存中图片的数量
    /*
    链表时的缓存 图片
     */
    private class  Node {
       Node previous; //上一个节点的Node
       Node next;   //下一个节点的Node
       String key; //唯一值

    }
}
