package com.sohu.sur.util;

/**
 * Created by IntelliJ IDEA.
 * User: xiaobinghan
 * Date: 11-5-31
 * Time: 下午5:56
 * To change this template use File | Settings | File Templates.
 */
public class Page {
    public static final int DEFAULTSIZE = 20;
    private int size;
    private int no;
    private long count;

    public Page() {
        this(DEFAULTSIZE, 1);
    }

    public Page(int size, int no) {
        this.count = 0;
        this.setSize(size);
        this.setNo(no);
    }

    public void setSize(int size) {
        this.size = size > 0 ? size : DEFAULTSIZE;
    }

    public void setNo(int no) {
        this.no = no <= 1 ? 1 : no;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getSize() {
        return size;
    }

    public int getNo() {
        return no;
    }

    public long getCount() {
        return count;
    }

    public int getStart() {
        return (no - 1) * size;
    }

    public int getEnd() {
        return (long) (no * size) < count ? no * size : (int) count;
    }

    public int getNextNo() {
        return no < getTailNo() ? no + 1 : getTailNo();
    }

    public int getPreNo() {
        return no > getHeadNo() ? no - 1 : getHeadNo();
    }

    public int getHeadNo() {
        return 1;
    }

    public int getTailNo() {
        return count == 0L ? getHeadNo() : count % size == 0L ? (int) (count / size) : (int) (count / size + 1);
    }

    @Override
    public String toString() {
        return new StringBuilder(super.toString())
                .append("[no=").append(no)
                .append(",size=").append(size)
                .append(",count=").append(count)
                .append("]").toString();
    }
}
