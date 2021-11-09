package week7.homework9;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DataSourceRouter extends AbstractRoutingDataSource {
    private static String dataSourceconfig = "master"; //默认主库
    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceconfig;
    }
    public static void setMater() {
        dataSourceconfig = "master";
        System.out.println("设置为主库");
    }
    public static void setSlave() {
        dataSourceconfig = "slave";
        System.out.println("设置为从库");
    }
}
