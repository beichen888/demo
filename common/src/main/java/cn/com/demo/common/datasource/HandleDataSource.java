package cn.com.demo.common.datasource;

/**
 * Created by yuanshuai on 2016/1/26.
 */
public class HandleDataSource {

    private static final String WRITE = "write";

    private static final String READ = "read";

    public static ThreadLocal<String> holder = new ThreadLocal<>();


    private static void setDataSource(String dataSource) {
        holder.set(dataSource);
    }

    public static String getDataSource() {
        return holder.get();
    }

    /**
     * 标志为read
     */
    public static void setRead() {
        setDataSource(READ);
    }

    /**
     * 标志为write
     */
    public static void setWrite() {
        setDataSource(WRITE);
    }

    public static boolean isWrite() {
        return getDataSource() == WRITE;
    }

    public static boolean isRead() {
        return getDataSource() == READ;
    }

    /**
     * 清除thread local中的数据源
     */
    public static void clearDataSource() {
        holder.remove();
    }
}
