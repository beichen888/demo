package cn.com.demo.common.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yuanshuai on 2016/1/26.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }

    @Override
    public void afterPropertiesSet() {
        // 重写，去除校验
    }

    /**
     * 读写库库
     */
    private DataSource write;

    /**
     * 只读库
     */
    private List<DataSource> reads;

    private AtomicInteger counter = new AtomicInteger();

    /**
     * 根据标识
     * 获取数据源
     */
    @Override
    protected DataSource determineTargetDataSource() {
        DataSource retDataSource = null;
        if (HandleDataSource.isWrite()) {
            retDataSource = write;
        } else if (HandleDataSource.isRead()) {
            int count = counter.incrementAndGet();
            if (count > 1000000) {
                counter.set(0);
            }
            int n = reads.size();
            int index = count % n;
            retDataSource = reads.get(index);
        } else {
            retDataSource = write;
        }
        return retDataSource;
    }


    public DataSource getWrite() {
        return write;
    }

    public void setWrite(DataSource write) {
        this.write = write;
    }

    public List<DataSource> getReads() {
        return reads;
    }

    public void setReads(List<DataSource> reads) {
        this.reads = reads;
    }
}
