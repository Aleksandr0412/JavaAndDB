package dao;

public class FunWithJdbcMain {
    public static void main(String[] args) {
        DuckDAO duckDAO = new DuckDAO();
        System.out.println(duckDAO.getAll());
    }
}
