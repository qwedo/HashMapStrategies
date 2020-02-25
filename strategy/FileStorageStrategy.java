package com.javarush.task.Projects.HashMapStrategies.strategy;

import java.util.Objects;

public class FileStorageStrategy implements StorageStrategy {
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000;
    FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    int size;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    long maxBucketSize; // размер самого большого имеющегося бакета

    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }

    int hash(Long k){
        int h;
        return (k == null) ? 0 : (h = k.hashCode()) ^ (h >>> 16);
    }

    int indexFor(int hash, int length){
        return hash & (length - 1);
    }

    Entry getEntry(Long key){
        int hash = key == null ? 0 : hash(key);
        int index = indexFor(hash, table.length);
        if (table[index] == null) return null;
        for (Entry e = table[index].getEntry(); e != null; e = e.next){
            if (e.hash == hash && Objects.equals(key, e.key))
                return e;
        }
        return null;
    }

    void resize(int newCapacity){
        FileBucket[] newTable = new FileBucket[newCapacity];
        transfer(newTable);
        table = newTable;
    }

    void transfer(FileBucket[] newTable){
        FileBucket[] src = table;
        int newCapacity = newTable.length;
        for (int i = 0; i < src.length; i++) {
            if (src[i] != null && src[i].getFileSize() > 0) {
                Entry e = src[i].getEntry();
                while (e != null) {
                    int j = indexFor(hash(e.key), newCapacity);
                    newTable[j].putEntry(e);
                    e = e.next;
                }
            }
        }

        for (int i = 0; i < src.length; i++){
            if (src[i] != null)
                src[i].remove(); //удаление старых файлы, которые ранее использовались
        }


    }

    void addEntry(int hash, Long key, String value, int bucketIndex){
        if (table[bucketIndex] == null){
            table[bucketIndex] = new FileBucket();
            table[bucketIndex].putEntry(new Entry(hash, key, value, null));
        } else {
            Entry e = table[bucketIndex].getEntry();
            table[bucketIndex].putEntry(new Entry(hash, key, value, e));
        }
        if (table[bucketIndex].getFileSize() >= bucketSizeLimit)
            resize(2 * table.length);
    }

    void createEntry(int hash, Long key, String value, int bucketIndex){
        if (table[bucketIndex] == null) return;
        Entry e = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, e));
    }

    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        if (value == null) return false;
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                for (Entry e = table[i].getEntry(); e != null; e = e.next) {
                    if (value.equals(e.value))
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public void put(Long key, String value) {
        int hash = key == null ? 0 : hash(key);
        int index = indexFor(hash, table.length);
        addEntry(hash, key, value, index);
    }

    @Override
    public Long getKey(String value) {
        if (value == null) return null;
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                for (Entry e = table[i].getEntry(); e != null; e = e.next) {
                    if (value.equals(e.value))
                        return e.key;
                }
            }
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        Entry e = getEntry(key);
        return e != null ? e.value : null;
    }
}
