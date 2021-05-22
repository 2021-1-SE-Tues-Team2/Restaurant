package Hobe.Restaurant.Repository;



import Hobe.Restaurant.Domain.Table;

import org.springframework.jdbc.datasource.DataSourceUtils;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcTableRepository implements TableRepository{
    private final DataSource dataSource;


    public JdbcTableRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Table save(Table table) {
        String sql = "insert into `table`(tableNumber, maxNumber) values( ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, table.getTableNumber());
            pstmt.setInt(2, table.getMaxNumber());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                //table.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return table;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Table> findByID(long id) {
        String sql = "select * from `table`";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Table> tables = new ArrayList<>();
            if(rs.next()) {
                Table table = new Table();
                table.setTableNumber(rs.getInt("tableNumber"));
                table.setMaxNumber(rs.getInt("maxNumber"));
                tables.add(table);
                return Optional.of(table);
            }
            else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Table> findAll() {
        String sql = "select * from `table`";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Table> tables = new ArrayList<>();
            while(rs.next()) {
                Table table = new Table();
                table.setTableNumber(rs.getInt("tableNumber"));
                table.setMaxNumber(rs.getInt("maxNumber"));
                tables.add(table);
            }
            return tables;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}

