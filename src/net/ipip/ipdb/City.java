package net.ipip.ipdb;

import java.io.InputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class City {

    /**
     * @var Reader
     */
    private Reader reader;

    public City(String name) throws IOException, InvalidDatabaseException {
        this.reader = new Reader(name);
    }

    public City(InputStream in) throws IOException, InvalidDatabaseException {
        this.reader = new Reader(in);
    }

    public boolean reload(String name) {
        try {
            Reader r = new Reader(name);
            this.reader = r;
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public String[] find(String addr, String language) throws IPFormatException, InvalidDatabaseException {
        return this.reader.find(addr, language);
    }

    public Map<String, String> findMap(String addr, String language) throws IPFormatException, InvalidDatabaseException {
        String[] data = this.reader.find(addr, language);
        if (data == null) {
            return null;
        }
        String[] fields = this.reader.getSupportFields();
        Map<String, String> m = new HashMap<String, String>();
        for (int i = 0, l = data.length; i < l; i++) {
            m.put(fields[i], data[i]);
        }

        return m;
    }

    public CityInfo findInfo(String addr, String language) throws IPFormatException, InvalidDatabaseException {

        String[] data = this.reader.find(addr, language);
        if (data == null) {
            return null;
        }

        return new CityInfo(data);
    }

    public boolean isIPv4() {
        return (this.reader.getMeta().IPVersion & 0x01) == 0x01;
    }

    public void listAll() throws IOException {
        reader.listAll();
    }

    public boolean isIPv6() {
        return (this.reader.getMeta().IPVersion & 0x02) == 0x02;
    }

    public String[] fields() {
        return this.reader.getSupportFields();
    }

    public int buildTime() {
        return this.reader.getBuildUTCTime();
    }
}